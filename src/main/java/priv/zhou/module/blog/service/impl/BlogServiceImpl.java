package priv.zhou.module.blog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import priv.zhou.common.domain.dto.DTO;
import priv.zhou.common.domain.dto.Page;
import priv.zhou.common.domain.vo.ListVO;
import priv.zhou.common.domain.vo.OutVO;
import priv.zhou.common.param.NULL;
import priv.zhou.common.param.OutVOEnum;
import priv.zhou.module.blog.domain.dao.BlogDAO;
import priv.zhou.module.blog.domain.dto.BlogDTO;
import priv.zhou.module.blog.domain.po.BlogPO;
import priv.zhou.module.blog.service.IBlogService;

import java.util.List;

import static java.util.Objects.isNull;


/**
 *  服务层实现
 *
 * @author zhou
 * @since 2020.05.15
 */
@Service
public class BlogServiceImpl implements IBlogService {

    private BlogDAO blogDAO;


    public BlogServiceImpl(BlogDAO blogDAO) {
        this.blogDAO = blogDAO;
    }

    @Override
    public OutVO<NULL> save(BlogDTO blogDTO) {

        BlogPO blogPO = blogDTO.toPO();
        blogPO.setState(0)
                .setPv(0L);
        return blogDAO.save(blogPO) > 0 ?
                OutVO.success():
                OutVO.fail(OutVOEnum.FAIL_OPERATION);

    }

    @Override
    public OutVO<NULL> remove(BlogDTO blogDTO) {
        if (isNull(blogDTO.getId())) {
            return OutVO.fail(OutVOEnum.EMPTY_PARAM);
        }
        blogDAO.update(new BlogPO().setId(blogDTO.getId()).setState(11));
        return OutVO.success();
    }

    @Override
    public OutVO<NULL> update(BlogDTO blogDTO) {

        BlogPO blogPO = blogDTO.toPO();
        return  blogDAO.update(blogPO) > 0 ?
                OutVO.success():
                OutVO.fail(OutVOEnum.FAIL_OPERATION);

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
        return OutVO.list(DTO.ofPO(poList, BlogDTO::new),  pageInfo.getTotal());
    }

    @Override
    public OutVO<Integer> count(BlogDTO blogDTO) {
        return OutVO.success(blogDAO.count(blogDTO));
    }
}
