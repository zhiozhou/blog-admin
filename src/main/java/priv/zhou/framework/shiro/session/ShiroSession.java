package priv.zhou.framework.shiro.session;

import lombok.Getter;
import lombok.Setter;
import org.apache.shiro.session.mgt.SimpleSession;

import java.io.Serializable;
import java.util.Date;

/**
 * 修改accessSeptum解决SessionDao的update方法频繁访问 *
 *
 * @author zhou
 * @since 2021.02.08
 */
// 忽略父级方法防止反序列化失败
@Getter
@Setter
public class ShiroSession extends SimpleSession implements Serializable {


    private String username;

    private String roleNames;

    private String browser;

    private String os;

    private Long accessSeptum;

    @Override
    public void setLastAccessTime(Date accessTime) {
        Date lastAccessTime = super.getLastAccessTime();
        if (null != lastAccessTime) {
            this.accessSeptum = lastAccessTime.getTime() - accessTime.getTime();
        }
        super.setLastAccessTime(accessTime);
    }


    @Override
    protected void expire() {
        this.stop();
        this.setExpired(true);
    }
}
