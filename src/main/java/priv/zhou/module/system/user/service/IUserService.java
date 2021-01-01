package priv.zhou.module.system.user.service;

import priv.zhou.common.domain.dto.Page;
import priv.zhou.common.domain.vo.OutVO;
import priv.zhou.common.domain.vo.TableVO;
import priv.zhou.common.misc.NULL;
import priv.zhou.module.system.user.domain.dto.UserDTO;

/**
 * 用户 服务层定义
 *
 * @author zhou
 * @since 2020.04.20
 */
public interface IUserService {

    OutVO<NULL> save(UserDTO userDTO);

    OutVO<NULL> remove(UserDTO userDTO);

    OutVO<NULL> update(UserDTO userDTO);

    OutVO<NULL> updateState(UserDTO userDTO);

    OutVO<NULL> resetPwd(UserDTO userDTO);

    OutVO<UserDTO> get(UserDTO userDTO);

    default OutVO<TableVO<UserDTO>> list(UserDTO userDTO) {
        return list(userDTO, null);
    }

    OutVO<TableVO<UserDTO>> list(UserDTO userDTO, Page page);
}
