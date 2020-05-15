package priv.zhou.module.system.extend.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import priv.zhou.common.tools.DateUtil;
import priv.zhou.module.system.extend.domain.Demo;

import java.util.List;

import static priv.zhou.common.param.CONSTANT.*;


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


    public AppConfig(String packet) {
        this.since = DateUtil.format("yyyy.MM.dd");
        this.packet = packet;
        this.packetPath = packet.replace(".", SEPARATOR);
    }

}
