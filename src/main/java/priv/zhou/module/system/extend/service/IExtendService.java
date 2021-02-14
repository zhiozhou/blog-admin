package priv.zhou.module.system.extend.service;

import priv.zhou.common.domain.Result;
import priv.zhou.module.system.extend.domain.dto.ExtendDTO;

import java.util.HashMap;
import java.util.Map;

public interface IExtendService {

    String CONFIG_KEY = "system_extend_config";

    String DEMO_PATH = "src/main/resources/templates/vm";

    Map<String, String> TYPE_MAP = new HashMap<String, String>() {{
        put("tinyint", "Boolean");
        put("smallint", "Integer");
        put("mediumint", "Integer");
        put("int", "Integer");
        put("integer", "Integer");
        put("bigint", "Long");
        put("float", "Float");
        put("double", "Double");
        put("decimal", "BigDecimal");
        put("bit", "Boolean");
        put("char", "String");
        put("varchar", "String");
        put("tinytext", "String");
        put("text", "String");
        put("mediumtext", "String");
        put("longtext", "String");
        put("date", "Date");
        put("time", "Time");
        put("datetime", "Date");
        put("timestamp", "Date");
    }};

    String PRIMARY = "PRI";

    Result<byte[]> generate(ExtendDTO extendDTO) throws Exception;
}
