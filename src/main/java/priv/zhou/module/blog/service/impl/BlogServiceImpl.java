package priv.zhou.module.blog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.assertj.core.util.Lists;
import org.springframework.stereotype.Service;
import priv.zhou.common.domain.dto.DTO;
import priv.zhou.common.domain.dto.Page;
import priv.zhou.common.domain.vo.ListVO;
import priv.zhou.common.domain.vo.OutVO;
import priv.zhou.common.param.NULL;
import priv.zhou.common.param.OutVOEnum;
import priv.zhou.common.tools.ShiroUtil;
import priv.zhou.module.blog.domain.dao.BlogDAO;
import priv.zhou.module.blog.domain.dao.TagDAO;
import priv.zhou.module.blog.domain.dto.BlogDTO;
import priv.zhou.module.blog.domain.dto.TagDTO;
import priv.zhou.module.blog.domain.po.BlogPO;
import priv.zhou.module.blog.domain.po.TagPO;
import priv.zhou.module.blog.service.IBlogService;

import java.util.List;

import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;


/**
 * 博客 服务层实现
 *
 * @author zhou
 * @since 2020.09.14
 */
@Service
public class BlogServiceImpl implements IBlogService {

    private final TagDAO tagDAO;

    private final BlogDAO blogDAO;

    public BlogServiceImpl(TagDAO tagDAO, BlogDAO blogDAO) {
        this.tagDAO = tagDAO;
        this.blogDAO = blogDAO;
    }

    @Override
    public OutVO<NULL> save(BlogDTO blogDTO) {

        if (blogDAO.count(new BlogDTO().setTitle(blogDTO.getTitle())) > 0) {
            return OutVO.fail(OutVOEnum.EXIST_NAME, "标题已存在");
        }

        BlogPO blogPO = blogDTO.toPO()
                .setState(0)
                .setPv(0L)
                .setCreateId(ShiroUtil.getUserId());
        if (blogDAO.save(blogPO) < 0) {
            return OutVO.fail(OutVOEnum.FAIL_OPERATION);
        }

        saveTagMap(blogPO, blogDTO.getTags());

        return OutVO.success();
    }


    @Override
    public OutVO<NULL> remove(BlogDTO blogDTO) {
        if (isNull(blogDTO.getId())) {
            return OutVO.fail(OutVOEnum.EMPTY_PARAM);
        }

        return blogDAO.update(blogDTO.toPO().setState(11)) > 0 ? OutVO.success() :
                OutVO.fail(OutVOEnum.FAIL_OPERATION);
    }

    @Override
    public synchronized OutVO<NULL> update(BlogDTO blogDTO) {
        if (null == blogDTO.getId()) {
            return OutVO.fail(OutVOEnum.EMPTY_PARAM);
        } else if (blogDAO.count(new BlogDTO().setTitle(blogDTO.getTitle()).setNoid(blogDTO.getId())) > 0) {
            return OutVO.fail(OutVOEnum.EXIST_NAME, "标题已存在");
        }
        BlogPO dbPO = blogDAO.get(new BlogDTO().setId(blogDTO.getId()));
        if (null == dbPO) {
            return OutVO.fail(OutVOEnum.EMPTY_DATA);
        }

        BlogPO blogPO = blogDTO.toPO();
        if (blogDAO.update(blogPO) < 0) {
            return OutVO.fail(OutVOEnum.FAIL_OPERATION);
        }

        List<TagPO> saveTags = blogPO.getTags().stream().filter(tag -> !dbPO.getTags().contains(tag)).collect(toList()),
                removeTags = dbPO.getTags().stream().filter(tag -> !blogPO.getTags().contains(tag)).collect(toList());
        for (TagPO tag : removeTags) {
            tagDAO.update(tag.setCount(tag.getCount()));
        }

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

    @Override
    public OutVO<List<TagDTO>> tagList(TagDTO tagDTO) {
        return OutVO.success(DTO.ofPO(tagDAO.list(tagDTO), TagDTO::new));
    }

    private void saveTagMap(BlogPO blogPO, List<TagDTO> tags) {
        List<TagPO> tagPOList = Lists.newArrayList();
        for (TagDTO tagDTO : tags) {
            TagPO tagPO = tagDAO.get(tagDTO);
            if (null == tagPO) {
                tagDAO.save(tagPO = new TagPO()
                        .setCount(1)
                        .setName(tagDTO.getName())
                        .setCreateId(ShiroUtil.getUserId())
                );
            } else {
                tagDAO.update(tagPO.setCount(tagPO.getCount() + 1));
            }
            tagPOList.add(tagPO);
        }
        tagDAO.saveMap(tagPOList, blogPO.getId());
    }
}
