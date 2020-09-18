package priv.zhou.module.system.extend.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;
import priv.zhou.common.tools.DateUtil;
import priv.zhou.module.system.extend.domain.Demo;

import java.util.List;

import static priv.zhou.common.param.CONSTANT.SEPARATOR;


@Getter
@Setter
@Accessors(chain = true)
public class AppConfig {

    /**
     * 作者
     */
    private String author;

    /**
     * 时间
     */
    private String since;

    /**
     * 包引用
     */
    private String packet;

    /**
     * 模块名
     */
    private String module;

    /**
     * 模块路径
     */
    private String moduleRef;

    /**
     * 模块路径
     */
    private String modulePath;

    /**
     * 包路径
     */
    private String packetPath;

    /**
     * 所有vm路径
     */
    private List<Demo> demoList;

    /**
     * 保持前缀
     */
    private Boolean keepPrefix;


    public AppConfig() {
        this.since = DateUtil.format("yyyy.MM.dd");
    }

    public AppConfig setPacket(String packet) {
        this.packet = packet;
        this.packetPath = packet.replace(".", SEPARATOR);
        return this;
    }

    public AppConfig setModule(String module) {
        if (StringUtils.isBlank(module)) {
            this.module = "";
            this.moduleRef = "";
            this.modulePath = "";
        } else {
            this.module = module;
            this.moduleRef += ".";
            this.modulePath += SEPARATOR;
        }
        return this;
    }
}
