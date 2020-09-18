package priv.zhou.module.system.extend.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


@Getter
@Setter
@Accessors(chain = true)
public class Demo {

    /**
     * 基础路径
     */
    private String path;


    public Demo(String path) {
        this.path = path;
    }

    public String getOutPath(String moduleName, String className, String objectName) {
        return path.replace("CLASS", className)
                .replace("class", objectName)
                .replace("MODULE/", moduleName.replaceAll("\\.","/")); // 包符号替换为路径符
    }
}
