package priv.zhou.module.system.user.service;

import priv.zhou.common.domain.Result;
import priv.zhou.common.domain.dto.Page;
import priv.zhou.common.misc.NULL;
import priv.zhou.module.system.user.domain.dto.UserDTO;

import java.util.List;

/**
 * 用户 服务层定义
 *
 * @author zhou
 * @since 2020.04.20
 */
public interface IUserService {

    Result<NULL> save(UserDTO userDTO);

    Result<NULL> remove(UserDTO userDTO);

    Result<NULL> update(UserDTO userDTO);

    Result<NULL> updateState(UserDTO userDTO);

    Result<NULL> resetPwd(UserDTO userDTO);

    Result<UserDTO> get(UserDTO userDTO);

    default Result<List<UserDTO>> list(UserDTO userDTO) {
        return list(userDTO, null);
    }

    Result<List<UserDTO>> list(UserDTO userDTO, Page page);
}
