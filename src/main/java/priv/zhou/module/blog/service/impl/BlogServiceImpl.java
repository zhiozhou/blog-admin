package priv.zhou.module.blog.service.impl;

import org.assertj.core.util.Sets;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import priv.zhou.common.domain.Result;
import priv.zhou.common.domain.dto.DTO;
import priv.zhou.common.domain.dto.Page;
import priv.zhou.common.misc.NULL;
import priv.zhou.common.misc.OutEnum;
import priv.zhou.common.service.BaseService;
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
public class BlogServiceImpl extends BaseService implements IBlogService {

    private final TagDAO tagDAO;

    private final BlogDAO blogDAO;

    public BlogServiceImpl(TagDAO tagDAO, BlogDAO blogDAO) {
        this.tagDAO = tagDAO;
        this.blogDAO = blogDAO;
    }

    @Override
    @Transactional
    public Result<NULL> save(BlogDTO blogDTO) {

        if (blogDAO.count(new BlogDTO().setTitle(blogDTO.getTitle())) > 0) {
            return Result.fail(OutEnum.EXIST_NAME, "标题已存在");
        }

        BlogPO blogPO = blogDTO.toPO()
                .setState(0)
                .setPv(0L)
                .setCreateId(ShiroUtil.getUserId());
        if (blogDAO.save(blogPO) < 0) {
            return Result.fail(OutEnum.FAIL_OPERATION);
        }

        saveTags(blogPO.getId(), blogPO.getTags(), false);

        return Result.success();
    }


    @Override
    public Result<NULL> remove(BlogDTO blogDTO) {
        if (null == blogDTO.getId()) {
            return Result.fail(OutEnum.EMPTY_PARAM);
        }

        return blogDAO.update(blogDTO.toPO().setState(11)) > 0 ? Result.success() :
                Result.fail(OutEnum.FAIL_OPERATION);
    }

    @Override
    public Result<NULL> update(BlogDTO blogDTO) {
        if (null == blogDTO.getId()) {
            return Result.fail(OutEnum.EMPTY_PARAM);
        } else if (blogDAO.count(new BlogDTO().setTitle(blogDTO.getTitle()).setExclId(blogDTO.getId())) > 0) {
            return Result.fail(OutEnum.EXIST_NAME, "标题已存在");
        }
        BlogPO dbPO = blogDAO.get(new BlogDTO().setId(blogDTO.getId()));
        if (null == dbPO) {
            return Result.fail(OutEnum.EMPTY_DATA);
        }

        BlogPO blogPO = blogDTO.toPO();
        if (blogDAO.update(blogPO) < 0) {
            return Result.fail(OutEnum.FAIL_OPERATION);
        }

        saveTags(blogPO.getId(), blogPO.getTags(), true);

        return Result.success();

    }


    @Override
    public Result<BlogDTO> get(BlogDTO blogDTO) {

        BlogPO blogPO = blogDAO.get(blogDTO);
        return Result.success(new BlogDTO(blogPO));
    }

    @Override
    public Result<List<BlogDTO>> list(BlogDTO blogDTO, Page page) {
        startPage(page);
        return Result.success(DTO.ofPO(blogDAO.list(blogDTO), BlogDTO::new));
    }

    @Override
    public Result<List<TagDTO>> tagList(TagDTO tagDTO) {
        return Result.success(DTO.ofPO(tagDAO.list(tagDTO), TagDTO::new));
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
