package priv.zhou.module.blog.blogType.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import priv.zhou.common.domain.dto.DTO;
import priv.zhou.common.domain.dto.Page;
import priv.zhou.common.domain.vo.ListVO;
import priv.zhou.common.domain.vo.OutVO;
import priv.zhou.common.param.NULL;
import priv.zhou.common.param.OutVOEnum;
import priv.zhou.common.tools.RedisUtil;
import priv.zhou.module.blog.blog.domain.dao.BlogDAO;
import priv.zhou.module.blog.blog.domain.dto.BlogDTO;
import priv.zhou.module.blog.blog.domain.po.BlogPO;
import priv.zhou.module.blog.blogType.domain.dao.BlogTypeDAO;
import priv.zhou.module.blog.blogType.domain.dto.BlogTypeDTO;
import priv.zhou.module.blog.blogType.domain.po.BlogTypePO;
import priv.zhou.module.blog.blogType.service.IBlogTypeService;

import java.util.List;

import static java.util.Objects.isNull;
import static priv.zhou.common.param.CONSTANT.*;


/**
 * 博客类型 服务层实现
 *
 * @author zhou
 * @since 2020.05.21
 */
@Service
public class BlogTypeServiceImpl implements IBlogTypeService {

    private final BlogDAO blogDAO;

    private final BlogTypeDAO blogTypeDAO;

    public BlogTypeServiceImpl(BlogDAO blogDAO, BlogTypeDAO blogTypeDAO) {
        this.blogDAO = blogDAO;
        this.blogTypeDAO = blogTypeDAO;
    }

    @Override
    public OutVO<NULL> save(BlogTypeDTO blogTypeDTO) {

        // 1.转换类型
        BlogTypePO blogTypePO = blogTypeDTO.toPO();

        // 2.验证参数
        if (blogTypeDAO.count(new BlogTypeDTO().setName(blogTypePO.getName())) > 0) {
            return OutVO.fail(OutVOEnum.EXIST_NAME);
        } else if (blogTypeDAO.count(new BlogTypeDTO().setKey(blogTypePO.getKey())) > 0) {
            return OutVO.fail(OutVOEnum.EXIST_KEY);
        }

        // 3.补充参数
        blogTypePO.setState(0);
        return blogTypeDAO.save(blogTypePO) > 0 ?
                OutVO.success() :
                OutVO.fail(OutVOEnum.FAIL_OPERATION);

    }

    @Override
    public OutVO<NULL> remove(BlogTypeDTO blogTypeDTO) {
        if (isNull(blogTypeDTO.getId())) {
            return OutVO.fail(OutVOEnum.EMPTY_PARAM);
        }

        return blogTypeDAO.update(new BlogTypePO().setId(blogTypeDTO.getId()).setState(11)) > 0 ?
                OutVO.success() :
                OutVO.fail(OutVOEnum.FAIL_OPERATION);
    }

    @Override
    public OutVO<NULL> update(BlogTypeDTO blogTypeDTO) {

        BlogTypePO blogTypePO = blogTypeDTO.toPO();

        if (blogTypeDAO.count(new BlogTypeDTO().setName(blogTypePO.getName()).setNoid(blogTypePO.getId())) > 0) {
            return OutVO.fail(OutVOEnum.EXIST_NAME);
        } else if (blogTypeDAO.count(new BlogTypeDTO().setKey(blogTypePO.getKey()).setNoid(blogTypePO.getId())) > 0) {
            return OutVO.fail(OutVOEnum.EXIST_KEY);
        } else if (blogTypeDAO.update(blogTypePO) < 1) {
            return OutVO.fail(OutVOEnum.FAIL_OPERATION);
        }

        RedisUtil.delete(BLOG_SERVICE_BLOG_TYPE_KEY + blogTypePO.getKey());

        switch (blogTypePO.getState()) {
            default:
                for (BlogPO blogPO : blogDAO.list(new BlogDTO().setType(blogTypePO.getKey()))) {
                    RedisUtil.delete(BLOG_SERVICE_BLOG_KEY + blogPO.getId());
                }
                break;

            case SINGLE_BLOG_STATE:
                RedisUtil.delete(BLOG_SERVICE_BLOG_KEY + blogTypePO.getKey());
                break;
            case SEAT_BLOG_STATE:
                break;
        }
        return OutVO.success();
    }


    @Override
    public OutVO<BlogTypeDTO> get(BlogTypeDTO blogTypeDTO) {

        BlogTypePO blogTypePO = blogTypeDAO.get(blogTypeDTO);
        return OutVO.success(new BlogTypeDTO(blogTypePO));
    }

    @Override
    public OutVO<ListVO<BlogTypeDTO>> list(BlogTypeDTO blogTypeDTO, Page page) {
        PageHelper.startPage(page.getPage(), page.getLimit(), page.isCount());
        List<BlogTypePO> poList = blogTypeDAO.list(blogTypeDTO);
        PageInfo<BlogTypePO> pageInfo = new PageInfo<>(poList);
        return OutVO.list(DTO.ofPO(poList, BlogTypeDTO::new), pageInfo.getTotal());
    }
}
