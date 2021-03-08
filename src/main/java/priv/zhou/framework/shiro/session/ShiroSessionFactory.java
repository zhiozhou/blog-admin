package priv.zhou.framework.shiro.session;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.session.mgt.SessionFactory;
import org.apache.shiro.web.session.mgt.DefaultWebSessionContext;
import priv.zhou.common.tools.HttpUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wangsaichao
 * @date 2018/6/23
 * @description shiroSession工厂
 */
public class ShiroSessionFactory implements SessionFactory {

    @Override
    public Session createSession(SessionContext initData) {
        ShiroSession session = new ShiroSession();
        session.setState(0);
        HttpServletRequest request = (HttpServletRequest) initData.get(DefaultWebSessionContext.class.getName() + ".SERVLET_REQUEST");
        session.setHost(HttpUtil.getIpAddress(request));
        return session;
    }
}
