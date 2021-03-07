package priv.zhou.module.blog.service.impl;

import lombok.RequiredArgsConstructor;
import org.assertj.core.util.Sets;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import priv.zhou.common.constant.NULL;
import priv.zhou.common.domain.Result;
import priv.zhou.common.domain.dto.DTO;
import priv.zhou.common.domain.dto.Page;
import priv.zhou.common.enums.ResultEnum;
import priv.zhou.common.service.BaseService;
import priv.zhou.common.tools.ShiroUtil;
import priv.zhou.framework.exception.RestException;
import priv.zhou.module.blog.domain.dao.BlogDAO;
import priv.zhou.module.blog.domain.dao.TagDAO;
import priv.zhou.module.blog.domain.dto.BlogDTO;
import priv.zhou.module.blog.domain.dto.TagDTO;
import priv.zhou.module.blog.domain.po.BlogPO;
import priv.zhou.module.blog.domain.po.TagPO;
import priv.zhou.module.blog.domain.query.BlogQuery;
import priv.zhou.module.blog.domain.query.TagQuery;
import priv.zhou.module.blog.domain.vo.BlogTableVO;
import priv.zhou.module.blog.domain.vo.BlogVO;
import priv.zhou.module.blog.domain.vo.TagVO;
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
@RequiredArgsConstructor
public class BlogServiceImpl extends BaseService implements IBlogService {

    private final TagDAO tagDAO;

    private final BlogDAO blogDAO;


    @Override
    @Transactional
    public Result<NULL> save(BlogDTO blogDTO) {

        if (blogDAO.count(new BlogQuery().setTitle(blogDTO.getTitle())) > 0) {
            return Result.fail(ResultEnum.EXIST_BLOG_TITLE);
        }

        BlogPO blogPO = new BlogPO()
                .setTitle(blogDTO.getTitle())
                .setContent(blogDTO.getContent())
                .setPreview(blogDTO.getPreview())
                .setRemark(blogDTO.getRemark())
                .setAbs(blogDTO.getAbs())
                .setPv(0L)
                .setState(0)
                .setCreateBy(ShiroUtil.getUserId());
        if (blogDAO.save(blogPO) < 0) {
            return Result.fail(ResultEnum.LATER_RETRY);
        }

//        saveTags(blogPO.getId(), blogPO.getTags(), false);

        return Result.success();
    }


    @Override
    public Result<NULL> remove(List<Integer> ids) {
        if(blogDAO.removeList(ids) != ids.size()){
            throw new RestException(ResultEnum.LATER_RETRY);
        }
        // todo:删除对应map，减少对应tag引用
        return Result.success();
    }

    @Override
    public Result<NULL> update(BlogDTO blogDTO) {
        if (blogDAO.count(new BlogQuery().setTitle(blogDTO.getTitle()).setRidId(blogDTO.getId())) > 0) {
            return Result.fail(ResultEnum.EXIST_BLOG_TITLE);
        }
        BlogPO blogPO = new BlogPO()
                .setTitle(blogDTO.getTitle())
                .setContent(blogDTO.getContent())
                .setPreview(blogDTO.getPreview())
                .setRemark(blogDTO.getRemark())
                .setAbs(blogDTO.getAbs())
                .setModifiedBy(ShiroUtil.getUserId());
        if (blogDAO.update(blogPO) < 0) {
            return Result.fail(ResultEnum.LATER_RETRY);
        }

//        saveTags(blogPO.getId(), blogPO.getTags(), true);

        return Result.success();

    }


    @Override
    public BlogVO getVO(BlogQuery query) {
        return blogDAO.getVO(query);
    }

    @Override
    public List<BlogTableVO> listTableVO(BlogQuery query, Page page) {
        startPage(page);
        return blogDAO.listTableVO(query);
    }

    @Override
    public List<TagVO> listTag(TagQuery query) {
        return null;
    }

//    @Transactional
//    synchronized void saveTags(Integer blogId, List<TagPO> formTags, boolean isUpdate) {
//
//        Set<TagPO> saveTags, removeTags;
//        if (isUpdate) {
//            List<TagPO> dbTags = blogDAO.get(new BlogDTO().setId(blogId)).getTags();
//            saveTags = formTags.stream().filter(tag -> !dbTags.contains(tag)).collect(toSet());
//            removeTags = dbTags.stream().filter(tag -> !formTags.contains(tag)).collect(toSet());
//        } else {
//            removeTags = null;
//            saveTags = Sets.newHashSet(formTags);
//        }
//
//        if (!saveTags.isEmpty()) {
//            Set<TagPO> tagSet = Sets.newHashSet();
//            for (TagPO tag : saveTags) {
//                TagPO tagPO = tagDAO.get(new TagDTO().setName(tag.getName()));
//                if (null == tagPO) {
//                    tagDAO.save(tagPO = new TagPO()
//                            .setCount(1)
//                            .setName(tag.getName())
//                            .setCreateId(ShiroUtil.getUserId())
//                    );
//                } else {
//                    tagDAO.update(tagPO.setCount(tagPO.getCount() + 1));
//                }
//                tagSet.add(tagPO);
//            }
//            tagDAO.saveMap(tagSet, blogId);
//        }
//
//        if (isUpdate && !removeTags.isEmpty()) {
//
//            for (TagPO tag : removeTags) {
//                tagDAO.update(tag.setCount(tag.getCount() - 1));
//            }
//            tagDAO.removeMap(removeTags, blogId);
//        }
//
//    }
}
