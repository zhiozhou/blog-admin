package priv.zhou.module.system.extend.domain.bo;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.File;


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
                .replace("MODULE" + File.separator, moduleName.replaceAll("\\.", File.separator)); // 包符号替换为路径符
    }
}
