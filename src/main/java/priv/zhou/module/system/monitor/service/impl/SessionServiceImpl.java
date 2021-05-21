package priv.zhou.module.system.monitor.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.session.ExpiredSessionException;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.stereotype.Service;
import priv.zhou.common.interfaces.NULL;
import priv.zhou.common.domain.Result;
import priv.zhou.common.enums.ResultEnum;
import priv.zhou.common.properties.AppProperties;
import priv.zhou.common.tools.AesUtil;
import priv.zhou.common.tools.ShiroUtil;
import priv.zhou.framework.shiro.filter.SyncOnlineFilter;
import priv.zhou.framework.shiro.session.ShiroSession;
import priv.zhou.module.system.monitor.domain.query.SessionQuery;
import priv.zhou.module.system.monitor.domain.vo.SessionVO;
import priv.zhou.module.system.monitor.service.ISessionService;

import java.util.List;
import java.util.stream.Collectors;

import static priv.zhou.common.constant.GlobalConst.INT_0;

@Slf4j
@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements ISessionService {

    private final SessionDAO sessionDAO;

    private final AppProperties appProperties;

    private final SyncOnlineFilter syncOnlineFilter;


    @Override
    public Result<List<SessionVO>> list(SessionQuery query) {

        return Result.success(sessionDAO.getActiveSessions().stream()
                .map(s -> (ShiroSession) s)
                .filter(s -> {
                    if (!INT_0.equals(s.getState()) || null == s.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY)) {
                        return false;
                    } else if (StringUtils.isNotBlank(query.getHost()) && !s.getHost().contains(query.getHost())) {
                        return false;
                    } else
                        return !StringUtils.isNotBlank(query.getUsername()) || s.getUsername().contains(query.getUsername());
                })
                .map(s -> new SessionVO()
                        .setId(AesUtil.encrypt((String) s.getId(), appProperties.getAesSeed()))
                        .setHost(s.getHost())
                        .setOs(s.getOs())
                        .setBrowser(s.getBrowser())
                        .setUsername(s.getUsername())
                        .setRoleNames(s.getRoleNames())
                        .setLoginTime(s.getStartTimestamp())
                        .setLastAccessTime(s.getLastAccessTime()))
                .collect(Collectors.toList())
        );
    }

    @Override
    public Result<NULL> offline(String id) {
        id = AesUtil.decrypt(id, appProperties.getAesSeed());
        if (id.equals(ShiroUtil.getSession().getId())) {
            return Result.fail(ResultEnum.FAIL_PARAM);
        }
        try {
            syncOnlineFilter.logout(ShiroUtil.getUserName(), id);
            return Result.success();
        } catch (ExpiredSessionException e) {
            return Result.success();
        } catch (UnknownSessionException e) {
            return Result.fail(ResultEnum.LATER_RETRY, e.getMessage());
        }
    }
}
