package priv.zhou.framework.shiro.session;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.session.mgt.WebSessionKey;

import javax.servlet.ServletRequest;
import java.io.Serializable;

/**
 * 解决单次请求频繁访问Redis
 *
 * @author zhou
 * @since 2021.02.08
 */
@Slf4j
public class ShiroSessionManager extends DefaultWebSessionManager {

    @Override
    protected Session retrieveSession(SessionKey sessionKey) throws UnknownSessionException {
        Session session;
        Serializable sessionId = getSessionId(sessionKey);
        ServletRequest request = sessionKey instanceof WebSessionKey ?
                ((WebSessionKey) sessionKey).getServletRequest() : null;
        if (null == request || null == sessionId) {
            return super.retrieveSession(sessionKey);
        } else if (null != (session = (Session) request.getAttribute(sessionId.toString()))) {
            log.debug("read session from request");
            return session;
        }
        // session存储到请求的attribute中，一次请求内复用session
        session = super.retrieveSession(sessionKey);
        request.setAttribute(sessionId.toString(), session);
        return session;
    }
}
