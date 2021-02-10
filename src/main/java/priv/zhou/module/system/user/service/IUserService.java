package priv.zhou.module.system.user.service;

import priv.zhou.common.constant.NULL;
import priv.zhou.common.domain.Result;
import priv.zhou.common.domain.dto.Page;
import priv.zhou.module.system.user.domain.dto.UserSaveDTO;
import priv.zhou.module.system.user.domain.dto.UserUpdateDTO;
import priv.zhou.module.system.user.domain.po.UserPO;
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

    Result<NULL> save(UserSaveDTO saveDTO);

    Result<NULL> remove(int[] ids);

    Result<NULL> update(UserUpdateDTO updateDTO);

    UserVO getVO(UserQuery query);

    List<UserTableVO> listTableVO(UserQuery query, Page page);

    Result<NULL> freeze(Integer id);

    Result<NULL> unfreeze(Integer id);

    Result<NULL> resetPwd(Integer id, String password);
}
