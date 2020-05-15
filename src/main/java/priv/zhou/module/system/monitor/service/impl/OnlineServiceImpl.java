package priv.zhou.module.system.monitor.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import priv.zhou.common.domain.vo.OutVO;
import priv.zhou.common.param.NULL;
import priv.zhou.common.param.OutVOEnum;
import priv.zhou.common.tools.ShiroUtil;
import priv.zhou.framework.shiro.LoginLimitFilter;
import priv.zhou.module.system.monitor.domain.dto.OnlineDTO;
import priv.zhou.module.system.monitor.service.IOnlineService;
import priv.zhou.module.system.role.domain.dto.RoleDTO;

import java.util.Collection;
import java.util.List;

@Slf4j
@Service
public class OnlineServiceImpl implements IOnlineService {

    @Autowired
    private SessionDAO sessionDAO;

    @Autowired
    private LoginLimitFilter loginLimitFilter;

    @Autowired
    private SessionManager sessionManager;

    @Override
    public OutVO<List<OnlineDTO>> list(OnlineDTO onlineDTO) {
        List<OnlineDTO> list = Lists.newArrayList();
        Collection<Session> sessions = sessionDAO.getActiveSessions();
        for (Session session : sessions) {

            String username = (String) session.getAttribute("username");
            RoleDTO role = (RoleDTO) session.getAttribute("role");
            if (Strings.isNotBlank(onlineDTO.getUsername()) && !username.contains(onlineDTO.getUsername())
                    || null != onlineDTO.getRole() && null != onlineDTO.getRole().getId() && !role.getId().equals(onlineDTO.getRole().getId())) {
                continue;
            }

            list.add(new OnlineDTO()
                    .setId((String) session.getId())
                    .setHost(session.getHost())
                    .setLastAccessTime(session.getLastAccessTime())
                    .setOs((String) session.getAttribute("os"))
                    .setLoginTime(session.getStartTimestamp())
                    .setBrowser((String) session.getAttribute("browser"))
                    .setRole(role)
                    .setUsername(username));
        }
        return OutVO.success(list);
    }

    @Override
    public OutVO<NULL> forceOff(OnlineDTO onlineDTO) {
        try {
            if (onlineDTO.getId().equals(ShiroUtil.getSession().getId())) {
                return OutVO.fail(OutVOEnum.FAIL_PARAM);
            }
            Session session = sessionManager.getSession(new DefaultSessionKey(onlineDTO.getId()));
            loginLimitFilter.remove(session);
            sessionDAO.delete(session);
        } catch (UnknownSessionException e) {
            log.info(e.getMessage());
        }
        return OutVO.success();
    }

}
