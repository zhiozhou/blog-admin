package priv.zhou.module.system.extend.service;

import priv.zhou.common.domain.Result;
import priv.zhou.module.system.extend.domain.dto.ExtendDTO;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public interface IExtendService {

    String CONFIG_KEY = "system_extend_config";

    String DEMO_PATH = "src/main/resources/vm";

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

    Set<String> KEYWORD_SET = new HashSet<String>() {{
        add("status");
        add("level");
        add("key");
        add("name");
        add("period");
        add("password");
        add("order");
        add("while");
        add("sql");
        add("unique");
        add("or");
        add("outer");
        add("range");
        add("lock");
        add("grant");
    }};


    String PRIMARY = "PRI";

    Result<byte[]> generate(ExtendDTO extendDTO) throws Exception;
}
