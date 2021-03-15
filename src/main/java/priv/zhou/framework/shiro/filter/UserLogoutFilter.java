package priv.zhou.framework.shiro.filter;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import priv.zhou.module.system.user.domain.bo.UserPrincipal;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import static priv.zhou.common.constant.ShiroConst.LOGIN_PATH;

/**
 * @author zhou
 * @date 2020.03.15
 */
public class UserLogoutFilter extends LogoutFilter {

    public static final String name = "logout";

    private final SyncOnlineFilter onlineFilter;

    public UserLogoutFilter(SyncOnlineFilter onlineFilter) {
        this.onlineFilter = onlineFilter;
        super.setRedirectUrl(LOGIN_PATH);
    }

    /**
     * 自定义登出,登出之后,清理当前用户redis部分缓存信息
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {

        //登出操作 清除缓存  subject.logout() 可以自动清理缓存信息, 这些代码是可以省略的  这里只是做个笔记 表示这种方式也可以清除
        Subject subject = getSubject(request, response);
        UserPrincipal userPrincipal = (UserPrincipal) subject.getPrincipal();
        onlineFilter.logout(userPrincipal.getUsername(), subject.getSession().getId());

        //登出
        subject.logout();
        //获取登出后重定向到的地址
        issueRedirect(request, response, getRedirectUrl(request, response, subject));
        return false;
    }

}
