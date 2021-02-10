package priv.zhou.framework.shiro.session;

import lombok.Getter;
import lombok.Setter;
import org.apache.shiro.session.mgt.SimpleSession;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * SimpleSession 修改lastAccessTime也会调用SessionDao的update方法频繁访问
 * 修改为如只修改lastAccessTime则直接返回
 *
 * @author zhou
 * @date 2021.02.08
 */
// 忽略父级方法防止反序列化失败
@Getter
@Setter
public class ShiroSession extends SimpleSession implements Serializable {

    // 除lastAccessTime以外其他字段发生改变时为true
    private boolean isChanged = false;

    public ShiroSession() {
        super();
        this.setChanged(true);
    }

    @Override
    public void setId(Serializable id) {
        super.setId(id);
        this.setChanged(true);
    }

    @Override
    public void setStopTimestamp(Date stopTimestamp) {
        super.setStopTimestamp(stopTimestamp);
        this.setChanged(true);
    }

    @Override
    public void setExpired(boolean expired) {
        super.setExpired(expired);
        this.setChanged(true);
    }

    @Override
    public void setTimeout(long timeout) {
        super.setTimeout(timeout);
        this.setChanged(true);
    }

    @Override
    public void setHost(String host) {
        super.setHost(host);
        this.setChanged(true);
    }

    @Override
    public void setAttributes(Map<Object, Object> attributes) {
        super.setAttributes(attributes);
        this.setChanged(true);
    }

    @Override
    public void setAttribute(Object key, Object value) {
        super.setAttribute(key, value);
        this.setChanged(true);
    }

    @Override
    public Object removeAttribute(Object key) {
        this.setChanged(true);
        return super.removeAttribute(key);
    }

    @Override
    public void stop() {
        super.stop();
        this.setChanged(true);
    }

    @Override
    protected void expire() {
        this.stop();
        this.setExpired(true);
    }
}
