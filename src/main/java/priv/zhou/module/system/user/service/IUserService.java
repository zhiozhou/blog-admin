package priv.zhou.module.system.user.service;

import priv.zhou.common.interfaces.NULL;
import priv.zhou.common.domain.Result;
import priv.zhou.common.domain.dto.Page;
import priv.zhou.module.system.user.domain.dto.UserDTO;
import priv.zhou.module.system.user.domain.query.UserQuery;
import priv.zhou.module.system.user.domain.vo.UserTableVO;
import priv.zhou.module.system.user.domain.vo.UserVO;

import java.util.List;

/**
 * 用户 服务层定义
 *
 * @author zhou
 * @since 2020.04.20
 */
public interface IUserService {

    Result<NULL> save(UserDTO userDTO);

    Result<NULL> remove(List<String> usernames);

    Result<NULL> update(UserDTO userDTO);

    UserVO getVO(UserQuery query);

    List<UserTableVO> listTableVO(UserQuery query, Page page);

    Result<NULL> freeze(String username);

    Result<NULL> unfreeze(String username);

    Result<NULL> resetPwd(Integer id, String password);
}
