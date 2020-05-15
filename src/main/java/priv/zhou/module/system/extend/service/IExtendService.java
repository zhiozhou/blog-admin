package priv.zhou.module.system.extend.service;

import priv.zhou.common.domain.vo.OutVO;
import priv.zhou.module.system.extend.domain.dto.AppConfig;

import java.util.List;

public interface IExtendService {

    OutVO<byte[]> module(List<String> tableNames) throws Exception;
}
