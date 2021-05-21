package priv.zhou.module.blog.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import priv.zhou.common.interfaces.NULL;
import priv.zhou.common.domain.Result;
import priv.zhou.common.domain.dto.Page;
import priv.zhou.common.enums.ResultEnum;
import priv.zhou.common.service.BaseService;
import priv.zhou.common.tools.ShiroUtil;
import priv.zhou.framework.exception.RestException;
import priv.zhou.module.blog.domain.dao.BlogDAO;
import priv.zhou.module.blog.domain.dao.BlogTagDAO;
import priv.zhou.module.blog.domain.dao.TagDAO;
import priv.zhou.module.blog.domain.dto.BlogDTO;
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
import java.util.stream.Collectors;


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

    private final BlogTagDAO blogTagDAO;

    @Override
    @Transactional
    public Result<NULL> save(BlogDTO blogDTO) {
        if (blogDAO.count(new BlogQuery().setTitle(blogDTO.getTitle())) > 0) {
            return Result.fail(ResultEnum.EXIST_BLOG_TITLE);
        }
        Integer creatorId = ShiroUtil.getUserId();
        BlogPO blogPO = new BlogPO()
                .setTitle(blogDTO.getTitle())
                .setContent(blogDTO.getContent())
                .setPreview(blogDTO.getPreview())
                .setRemark(blogDTO.getRemark())
                .setAbs(blogDTO.getAbs())
                .setPv(0L)
                .setState(0)
                .setCreateBy(creatorId);
        if (blogDAO.save(blogPO) < 0) {
            return Result.fail(ResultEnum.LATER_RETRY);
        }
        List<TagPO> tags = blogDTO.getTagNames()
                .stream()
                .map(String::trim)
                .distinct()
                .map(tagName -> new TagPO()
                        .setCreateBy(creatorId)
                        .setName(tagName))
                .collect(Collectors.toList());
        if (tagDAO.incrSaveList(tags) != tags.size() ||
                blogTagDAO.listSave(tags, blogPO.getId()) != tags.size()) {
            throw new RestException(ResultEnum.LATER_RETRY);
        }
        return Result.success();
    }

    @Override
    @Transactional
    public Result<NULL> remove(Integer id) {
        List<TagPO> tags = tagDAO.list(new TagQuery().setBlogId(id));
        if (tags.isEmpty()) {
            return Result.fail(ResultEnum.EMPTY_DATA);
        }
        List<String> tagNames = tags.stream()
                .map(TagPO::getName)
                .collect(Collectors.toList());
        if (blogDAO.remove(id) < 1) {
            return Result.fail(ResultEnum.LATER_RETRY);
        } else if (blogTagDAO.delete(id) != tagDAO.incrList(tagNames, -1)) {
            throw new RestException(ResultEnum.LATER_RETRY);
        }
        return Result.success();
    }

    @Override
    @Transactional
    public Result<NULL> update(BlogDTO blogDTO) {
        if (blogDAO.count(new BlogQuery().setTitle(blogDTO.getTitle()).setRidId(blogDTO.getId())) > 0) {
            return Result.fail(ResultEnum.EXIST_BLOG_TITLE);
        }
        List<TagPO> blogTags = tagDAO.list(new TagQuery().setBlogId(blogDTO.getId()));
        if (blogTags.isEmpty()) {
            return Result.fail(ResultEnum.EMPTY_DATA);
        }

        Integer updaterId = ShiroUtil.getUserId();
        BlogPO blogPO = new BlogPO()
                .setId(blogDTO.getId())
                .setTitle(blogDTO.getTitle())
                .setContent(blogDTO.getContent())
                .setPreview(blogDTO.getPreview())
                .setRemark(blogDTO.getRemark())
                .setAbs(blogDTO.getAbs())
                .setModifiedBy(updaterId);
        if (blogDAO.update(blogPO) < 0) {
            return Result.fail(ResultEnum.LATER_RETRY);
        }

        Set<String> tagDBSet = blogTags.stream()
                .map(TagPO::getName)
                .collect(Collectors.toSet());
        List<String> tagNames = blogDTO.getTagNames()
                .stream()
                .map(String::trim)
                .collect(Collectors.toList());
        if (tagDBSet.size() != tagNames.size() ||
                !tagDBSet.containsAll(tagNames)) {
            if (blogTagDAO.delete(blogPO.getId()) != tagDAO.incrList(tagNames, -1)) {
                throw new RestException(ResultEnum.LATER_RETRY);
            }
            List<TagPO> tags = tagNames.stream()
                    .map(tagName -> new TagPO()
                            .setName(tagName)
                            .setCreateBy(updaterId))
                    .collect(Collectors.toList());
            if (tagDAO.incrSaveList(tags) != tags.size() ||
                    blogTagDAO.listSave(tags, blogPO.getId()) != tags.size()) {
                throw new RestException(ResultEnum.LATER_RETRY);
            }
        }
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
        return tagDAO.listVO(query);
    }
}
