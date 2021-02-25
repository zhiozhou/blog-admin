package priv.zhou.module.system.monitor.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.session.ExpiredSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.stereotype.Service;
import priv.zhou.common.constant.NULL;
import priv.zhou.common.domain.Result;
import priv.zhou.common.enums.ResultEnum;
import priv.zhou.common.tools.ShiroUtil;
import priv.zhou.framework.shiro.SyncLoginFilter;
import priv.zhou.module.system.monitor.domain.query.SessionQuery;
import priv.zhou.module.system.monitor.domain.vo.SessionVO;
import priv.zhou.module.system.monitor.service.ISessionService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements ISessionService {

    private final SessionDAO sessionDAO;

    private final SessionManager sessionManager;

    private final SyncLoginFilter syncLoginFilter;


    @Override
    public Result<List<SessionVO>> list(SessionQuery query) {
        return Result.success(sessionDAO.getActiveSessions().stream()
                .filter(s -> {
                    if (null == s.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY)) {
                        return false;
                    } else if (StringUtils.isNotBlank(query.getHost()) && !s.getHost().contains(query.getHost())) {
                        return false;
                    } else return !StringUtils.isNotBlank(query.getUsername()) || ((String) s.getAttribute("username")).contains(query.getUsername());
                })
                .map(s -> new SessionVO()
                        .setId((String) s.getId())
                        .setHost(s.getHost())
                        .setLoginTime(s.getStartTimestamp())
                        .setLastAccessTime(s.getLastAccessTime())
                        .setOs((String) s.getAttribute("os"))
                        .setRoleNames((String) s.getAttribute("roleNames"))
                        .setBrowser((String) s.getAttribute("browser"))
                        .setUsername((String) s.getAttribute("username")))
                .collect(Collectors.toList())
        );
    }

    @Override
    public Result<NULL> offline(String id) {
        if (id.equals(ShiroUtil.getSession().getId())) {
            return Result.fail(ResultEnum.FAIL_PARAM);
        }
        try {
            Session session = sessionManager.getSession(new DefaultSessionKey(id));
            if (null == session) {
                return Result.fail(ResultEnum.FAIL_PARAM);
            }
            syncLoginFilter.remove((String) session.getAttribute("username"));
            sessionDAO.delete(session);
            session.stop();
            return Result.success();
        } catch (ExpiredSessionException e) {
            return Result.success();
        } catch (UnknownSessionException e) {
            return Result.fail(ResultEnum.FAIL_OPERATION, e.getMessage());
        }
    }
}
