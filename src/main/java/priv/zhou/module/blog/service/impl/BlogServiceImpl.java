package priv.zhou.module.blog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.assertj.core.util.Sets;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import priv.zhou.common.domain.dto.DTO;
import priv.zhou.common.domain.dto.Page;
import priv.zhou.common.domain.vo.ListVO;
import priv.zhou.common.domain.vo.OutVO;
import priv.zhou.common.misc.NULL;
import priv.zhou.common.misc.OutVOEnum;
import priv.zhou.common.tools.ShiroUtil;
import priv.zhou.module.blog.domain.dao.BlogDAO;
import priv.zhou.module.blog.domain.dao.TagDAO;
import priv.zhou.module.blog.domain.dto.BlogDTO;
import priv.zhou.module.blog.domain.dto.TagDTO;
import priv.zhou.module.blog.domain.po.BlogPO;
import priv.zhou.module.blog.domain.po.TagPO;
import priv.zhou.module.blog.service.IBlogService;

import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toSet;


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
    @Transactional
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

        saveTags(blogPO.getId(), blogPO.getTags(), false);

        return OutVO.success();
    }


    @Override
    public OutVO<NULL> remove(BlogDTO blogDTO) {
        if (null == blogDTO.getId()) {
            return OutVO.fail(OutVOEnum.EMPTY_PARAM);
        }

        return blogDAO.update(blogDTO.toPO().setState(11)) > 0 ? OutVO.success() :
                OutVO.fail(OutVOEnum.FAIL_OPERATION);
    }

    @Override
    public OutVO<NULL> update(BlogDTO blogDTO) {
        if (null == blogDTO.getId()) {
            return OutVO.fail(OutVOEnum.EMPTY_PARAM);
        } else if ( blogDAO.count(new BlogDTO().setTitle(blogDTO.getTitle()).setExclId(blogDTO.getId())) > 0) {
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

        saveTags(blogPO.getId(), blogPO.getTags(), true);

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

    @Transactional
    synchronized void saveTags(Integer blogId, List<TagPO> formTags, boolean isUpdate) {

        Set<TagPO> saveTags, removeTags;
        if (isUpdate) {
            List<TagPO> dbTags = blogDAO.get(new BlogDTO().setId(blogId)).getTags();
            saveTags = formTags.stream().filter(tag -> !dbTags.contains(tag)).collect(toSet());
            removeTags = dbTags.stream().filter(tag -> !formTags.contains(tag)).collect(toSet());
        } else {
            removeTags = null;
            saveTags = Sets.newHashSet(formTags);
        }

        if (!saveTags.isEmpty()) {
            Set<TagPO> tagSet = Sets.newHashSet();
            for (TagPO tag : saveTags) {
                TagPO tagPO = tagDAO.get(new TagDTO().setName(tag.getName()));
                if (null == tagPO) {
                    tagDAO.save(tagPO = new TagPO()
                            .setCount(1)
                            .setName(tag.getName())
                            .setCreateId(ShiroUtil.getUserId())
                    );
                } else {
                    tagDAO.update(tagPO.setCount(tagPO.getCount() + 1));
                }
                tagSet.add(tagPO);
            }
            tagDAO.saveMap(tagSet, blogId);
        }

        if (isUpdate && !removeTags.isEmpty()) {

            for (TagPO tag : removeTags) {
                tagDAO.update(tag.setCount(tag.getCount() - 1));
            }
            tagDAO.removeMap(removeTags, blogId);
        }

    }
}
