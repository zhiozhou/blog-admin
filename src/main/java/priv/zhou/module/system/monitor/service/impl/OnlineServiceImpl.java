package priv.zhou.module.system.monitor.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.stereotype.Service;
import priv.zhou.common.domain.Result;
import priv.zhou.common.misc.NULL;
import priv.zhou.common.misc.ResultEnum;
import priv.zhou.common.tools.ShiroUtil;
import priv.zhou.framework.shiro.LoginLimitFilter;
import priv.zhou.module.system.monitor.domain.dto.OnlineDTO;
import priv.zhou.module.system.monitor.service.IOnlineService;
import priv.zhou.module.system.role.domain.dto.RoleDTO;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OnlineServiceImpl implements IOnlineService {

    private final SessionDAO sessionDAO;

    private final SessionManager sessionManager;

    private final LoginLimitFilter loginLimitFilter;


    public OnlineServiceImpl(SessionDAO sessionDAO, SessionManager sessionManager, LoginLimitFilter loginLimitFilter) {
        this.sessionDAO = sessionDAO;
        this.sessionManager = sessionManager;
        this.loginLimitFilter = loginLimitFilter;
    }

    @Override
    public Result<List<OnlineDTO>> list(OnlineDTO onlineDTO) {
        return Result.success(sessionDAO.getActiveSessions().stream()
                .filter(s -> !(
                        null == s.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY) || // 强制退出，令牌为空
                                Strings.isNotBlank(onlineDTO.getUsername()) && !((String) s.getAttribute("username")).contains(onlineDTO.getUsername()) ||
                                null != onlineDTO.getRole() && null != onlineDTO.getRole().getId() && !((RoleDTO) s.getAttribute("role")).getId().equals(onlineDTO.getRole().getId())))
                .map(s -> new OnlineDTO()
                        .setHost(s.getHost())
                        .setId((String) s.getId())
                        .setLoginTime(s.getStartTimestamp())
                        .setLastAccessTime(s.getLastAccessTime())
                        .setOs((String) s.getAttribute("os"))
                        .setRole((RoleDTO) s.getAttribute("role"))
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
            loginLimitFilter.remove(session);
            sessionDAO.delete(session);
            return Result.success();
        } catch (UnknownSessionException e) {
            return Result.fail(ResultEnum.FAIL_OPERATION, e.getMessage());
        }
    }

}
