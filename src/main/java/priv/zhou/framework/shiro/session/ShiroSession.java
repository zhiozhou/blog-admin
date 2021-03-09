package priv.zhou.framework.shiro.session;

import lombok.Getter;
import lombok.Setter;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SimpleSession;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import static priv.zhou.common.constant.GlobalConst.SERIAL_VERSION_UOD;

/**
 * 当只修改lastAccessTime时会触发间隔更新,用于解决SessionDao的update方法频繁访问
 *
 * @author zhou
 * @see ShiroSessionDAO#update(Session)
 * @since 2021.02.08
 */
@Getter
@Setter
public class ShiroSession extends SimpleSession implements Serializable {

    public static final long serialVersionUID = SERIAL_VERSION_UOD;

    private String username;

    private String roleNames;

    private String browser;

    private String os;

    private Integer state;

    private Date lastUpdatedTime;

    private boolean septumUpdate;

    public void setUsername(String username) {
        this.username = username;
        this.setSeptumUpdate(false);
    }

    public void setRoleNames(String roleNames) {
        this.roleNames = roleNames;
        this.setSeptumUpdate(false);
    }

    public void setBrowser(String browser) {
        this.browser = browser;
        this.setSeptumUpdate(false);
    }

    public void setOs(String os) {
        this.os = os;
        this.setSeptumUpdate(false);
    }

    public void setState(Integer state) {
        this.state = state;
        this.setSeptumUpdate(false);
    }

    @Override
    public void setId(Serializable id) {
        super.setId(id);
        this.setSeptumUpdate(false);
    }

    @Override
    public void setStopTimestamp(Date stopTimestamp) {
        super.setStopTimestamp(stopTimestamp);
        this.setSeptumUpdate(false);
    }

    @Override
    public void setExpired(boolean expired) {
        super.setExpired(expired);
        this.setSeptumUpdate(false);
    }

    @Override
    public void setTimeout(long timeout) {
        super.setTimeout(timeout);
        this.setSeptumUpdate(false);
    }

    @Override
    public void setHost(String host) {
        super.setHost(host);
        this.setSeptumUpdate(false);
    }

    @Override
    public void setAttributes(Map<Object, Object> attributes) {
        super.setAttributes(attributes);
        this.setSeptumUpdate(false);
    }

    @Override
    public void setAttribute(Object key, Object value) {
        super.setAttribute(key, value);
        this.setSeptumUpdate(false);
    }

    @Override
    public Object removeAttribute(Object key) {
        this.setSeptumUpdate(false);
        return super.removeAttribute(key);
    }

    @Override
    public void stop() {
        super.stop();
        this.setSeptumUpdate(false);
    }

    @Override
    protected void expire() {
        this.stop();
        this.setExpired(true);
    }


}
