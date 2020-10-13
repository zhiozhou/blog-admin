package priv.zhou.module.system.extend.service;

import priv.zhou.common.domain.vo.OutVO;

import java.util.List;

public interface IExtendService {

    String CONFIG_KEY = "system_extend_config";

    OutVO<byte[]> module(List<String> tableNames) throws Exception;
}
