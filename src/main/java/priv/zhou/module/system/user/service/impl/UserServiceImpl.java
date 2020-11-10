package priv.zhou.module.system.user.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import priv.zhou.common.domain.dto.DTO;
import priv.zhou.common.domain.dto.Page;
import priv.zhou.common.domain.vo.ListVO;
import priv.zhou.common.domain.vo.OutVO;
import priv.zhou.common.param.NULL;
import priv.zhou.common.param.OutVOEnum;
import priv.zhou.common.tools.RandomUtil;
import priv.zhou.common.tools.ShiroUtil;
import priv.zhou.framework.exception.GlobalException;
import priv.zhou.module.system.user.domain.dao.UserDAO;
import priv.zhou.module.system.user.domain.dto.UserDTO;
import priv.zhou.module.system.user.domain.po.UserPO;
import priv.zhou.module.system.user.service.IUserService;

import java.util.List;

import static priv.zhou.common.param.CONSTANT.SHIRO_ALGORITHM;
import static priv.zhou.common.param.CONSTANT.SHIRO_ITERATIONS;


/**
 * 用户 服务层实现
 *
 * @author zhou
 * @since 2020.04.20
 */
@Service
public class UserServiceImpl implements IUserService {

    private final UserDAO userDAO;

    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public OutVO<NULL> save(UserDTO userDTO) {

        // 1.验证参数
        if (StringUtils.isBlank(userDTO.getPassword())) {
            return OutVO.fail(OutVOEnum.EMPTY_PARAM);
        } else if (userDAO.count(new UserDTO().setUsername(userDTO.getUsername())) > 0) {
            return OutVO.fail(OutVOEnum.EXIST_KEY);
        }


        // 2.转换类型
        UserPO userPO = userDTO.toPO()
                .setState(0)
                .setSalt(RandomUtil.chars(6));

        // 3.密码加密
        userPO.setPassword(new SimpleHash(SHIRO_ALGORITHM, userPO.getPassword(), ByteSource.Util.bytes(userPO.getSalt()), SHIRO_ITERATIONS).toString());

        // 4.保存用户
        if (userDAO.save(userPO) < 1) {
            return OutVO.fail(OutVOEnum.FAIL_OPERATION);
        }

        // 5.保存角色
        userDAO.saveRole(userPO);
        return OutVO.success();

    }

    @Override
    public OutVO<NULL> remove(UserDTO userDTO) {
        if (null == userDTO.getId()) {
            return OutVO.fail(OutVOEnum.EMPTY_PARAM);
        }

        userDAO.update(new UserPO().setId(userDTO.getId()).setState(12));
        return OutVO.success();
    }

    @Override
    @Transactional
    public OutVO<NULL> update(UserDTO userDTO) {

        UserPO userPO = userDTO.toPO();

        if (userDAO.count(new UserDTO().setUsername(userPO.getUsername()).setNoid(userPO.getId())) > 0) {
            return OutVO.fail(OutVOEnum.EXIST_KEY);
        } else if (userDAO.update(userPO) < 1) {
            return OutVO.fail(OutVOEnum.FAIL_OPERATION);
        }else if ( userDAO.removeRole(userPO)<1 || userDAO.saveRole(userPO)<1){
            throw new GlobalException().setOutVO(OutVO.fail(OutVOEnum.FAIL_OPERATION));
        }
        return OutVO.success();
    }

    @Override
    public OutVO<NULL> updateState(UserDTO userDTO) {
        if (null == userDTO.getId() || null == userDTO.getState()) {
            return OutVO.fail(OutVOEnum.EMPTY_PARAM);
        }
        return userDAO.update(userDTO.toPO()) > 0 ? OutVO.success() : OutVO.fail(OutVOEnum.FAIL_OPERATION);
    }





    @Override
    public OutVO<NULL> resetPwd(UserDTO userDTO) {

        if (null == userDTO.getId() || StringUtils.isBlank(userDTO.getPassword())) {
            return OutVO.fail(OutVOEnum.EMPTY_PARAM);
        }

        UserPO userPO = userDTO.toPO(),
                db = userDAO.get(new UserDTO().setId(userPO.getId()));
        if (null == db) {
            return OutVO.fail(OutVOEnum.FAIL_PARAM);
        }
        userPO.setPassword(new SimpleHash(SHIRO_ALGORITHM, userPO.getPassword(), ByteSource.Util.bytes(db.getSalt()), SHIRO_ITERATIONS).toString());
        ShiroUtil.getUserRealm().clearAllCachedAuthenticationInfo();
        return userDAO.update(userPO) > 0 ? OutVO.success() : OutVO.fail(OutVOEnum.FAIL_OPERATION);
    }


    @Override
    public OutVO<UserDTO> get(UserDTO userDTO) {
        UserPO userPO = userDAO.get(userDTO);
        if (null == userPO) {
            return OutVO.fail(OutVOEnum.EMPTY_DATA);
        }
        return OutVO.success(new UserDTO(userPO));
    }

    @Override
    public OutVO<ListVO<UserDTO>> list(UserDTO userDTO, Page page) {
        PageHelper.startPage(page.getPage(), page.getLimit(), page.isCount());
        List<UserPO> poList = userDAO.list(userDTO);
        PageInfo<UserPO> pageInfo = new PageInfo<>(poList);
        return OutVO.list(DTO.ofPO(poList, UserDTO::new), pageInfo.getTotal());
    }
}
