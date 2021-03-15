package priv.zhou.framework.shiro.session;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import priv.zhou.common.tools.DateUtil;

/**
 * @author zhou
 * @since 2021.02.08
 */
@Slf4j
public class ShiroSessionDAO extends EnterpriseCacheSessionDAO {

    /**
     * session访问时间更新间隔
     */
    private final long accessTimeUpdateSeptum;

    public ShiroSessionDAO(long accessTimeUpdateSeptum) {
        this.accessTimeUpdateSeptum = accessTimeUpdateSeptum;
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        if (session instanceof ShiroSession) {
            ShiroSession shiroSession = (ShiroSession) session;
            if (null != shiroSession.getLastUpdatedTime() && shiroSession.isSeptumUpdate() &&
                    shiroSession.getLastAccessTime().getTime() - shiroSession.getLastUpdatedTime().getTime() < accessTimeUpdateSeptum) {
                // 间隔内已更新过
                return;
            }
            shiroSession.setSeptumUpdate(true);
            shiroSession.setLastUpdatedTime(DateUtil.now());
        }
        super.update(session);
    }



}
