package priv.zhou.module.system.extend.service;

import priv.zhou.common.domain.Result;

import java.util.List;

public interface IExtendService {

    String CONFIG_KEY = "system_extend_config";

    String DEMO_PATH = "src/main/resources/templates/vm";

    Result<byte[]> module(List<String> tableNames) throws Exception;
}
