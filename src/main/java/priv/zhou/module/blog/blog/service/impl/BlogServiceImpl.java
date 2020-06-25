package priv.zhou.module.blog.blog.service.impl;

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
import priv.zhou.module.blog.blog.service.IBlogService;
import priv.zhou.module.blog.blogType.domain.dao.BlogTypeDAO;
import priv.zhou.module.blog.blogType.domain.dto.BlogTypeDTO;
import priv.zhou.module.blog.blogType.domain.po.BlogTypePO;

import java.util.List;

import static java.util.Objects.isNull;
import static priv.zhou.common.param.CONSTANT.BLOG_SERVICE_BLOG_KEY;


/**
 * 博客 服务层实现
 *
 * @author zhou
 * @since 2020.05.15
 */
@Service
public class BlogServiceImpl implements IBlogService {

    private final BlogDAO blogDAO;

    private final BlogTypeDAO blogTypeDAO;

    private final Integer SINGLE_BLOG_STATE = 7;

    public BlogServiceImpl(BlogDAO blogDAO, BlogTypeDAO blogTypeDAO) {
        this.blogDAO = blogDAO;
        this.blogTypeDAO = blogTypeDAO;
    }

    @Override
    public OutVO<NULL> save(BlogDTO blogDTO) {

        BlogPO blogPO = blogDTO.toPO()
                .setState(0)
                .setPv(0L);
        return blogDAO.save(blogPO) > 0 ?
                OutVO.success() :
                OutVO.fail(OutVOEnum.FAIL_OPERATION);

    }

    @Override
    public OutVO<NULL> remove(BlogDTO blogDTO) {
        if (isNull(blogDTO.getId())) {
            return OutVO.fail(OutVOEnum.EMPTY_PARAM);
        }

        return blogDAO.update(new BlogPO().setId(blogDTO.getId()).setState(11)) > 0 ?
                OutVO.success() :
                OutVO.fail(OutVOEnum.FAIL_OPERATION);
    }

    @Override
    public OutVO<NULL> update(BlogDTO blogDTO) {
        BlogPO blogPO = blogDTO.toPO();

        BlogTypePO blogTypePO = blogTypeDAO.get(new BlogTypeDTO().setKey(blogDTO.getType()));
        if (null == blogTypePO) {
            return OutVO.fail(OutVOEnum.FAIL_PARAM);
        } else if (blogDAO.update(blogPO) < 1) {
            return OutVO.fail(OutVOEnum.FAIL_OPERATION);
        }

        RedisUtil.delete(BLOG_SERVICE_BLOG_KEY +
                (SINGLE_BLOG_STATE.equals(blogTypePO.getState()) ? blogTypePO.getKey() : blogPO.getId()));
        return OutVO.success();

    }


    @Override
    public OutVO<BlogDTO> get(BlogDTO blogDTO) {

        BlogPO blogPO = blogDAO.get(blogDTO);
        return OutVO.success(new BlogDTO(blogPO));
    }

    @Override
    public OutVO<ListVO<BlogDTO>> list(BlogDTO blogDTO, Page page) {
        PageHelper.startPage(page.getPage(), page.getLimit(), page.isCount());
        List<BlogPO> poList = blogDAO.list(blogDTO);
        PageInfo<BlogPO> pageInfo = new PageInfo<>(poList);
        return OutVO.list(DTO.ofPO(poList, BlogDTO::new), pageInfo.getTotal());
    }
}
