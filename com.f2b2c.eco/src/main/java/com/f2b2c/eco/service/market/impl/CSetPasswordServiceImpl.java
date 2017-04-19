package com.f2b2c.eco.service.market.impl;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.f2b2c.eco.controller.market.api.BOrderController;
import com.f2b2c.eco.dao.market.CUserDao;
import com.f2b2c.eco.model.common.ApiResultModel;
import com.f2b2c.eco.model.market.CUserModel;
import com.f2b2c.eco.service.market.CSetPasswordService;
import com.f2b2c.eco.util.Pbkdf2Encryption;
import jodd.util.StringUtil;

/**
 * 设置密码Service实现类
 * @author jane.hui
 *
 */
@Service("cSetPasswordService")
public class CSetPasswordServiceImpl implements CSetPasswordService{

    @Autowired
    private CUserDao cUserDao;
    
    /**
     * 日志记录器
     */
    private Logger logger=LoggerFactory.getLogger(BOrderController.class);
    
    /**
     * 设置密码
     */
    @Override
    public ApiResultModel<String> setPassword(Integer userId, String password) {
        ApiResultModel<String> result = new ApiResultModel<String>();
       /* CUserModel cUser = cUserDao.select(userId);
        if(cUser!=null&&StringUtil.isNotEmpty(cUser.getPayPassword())){
            result.setMsg("您的支付密码已设置！");
            result.setStatus(400);
            return result;
        }*/
        CUserModel newCUser = new CUserModel();
        try {
            newCUser.setId(userId);
            newCUser.setPayPassword(Pbkdf2Encryption.encode(password));
            int size = cUserDao.updateByPrimaryKeySelective(newCUser);
            if(size==0){
                result.setMsg("设置密码操作错误");
                result.setStatus(400);
                return result;
            }
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            logger.error("设置密码Pbkdf2加密出现异常，异常信息:"+e.toString());
            result.setMsg("设置密码操作错误");
            result.setStatus(400);
            return result;
        }
        result.setMsg("设置密码操作成功");
        result.setStatus(200);
        return result;
    }

    /**
     * 输入密码校验功能
     */
    @Override
    public ApiResultModel<String> savePassword(Integer userId, String password,String mergeOrderPrice) {
        ApiResultModel<String> result = new ApiResultModel<String>();
        CUserModel cUserModel = cUserDao.select(userId);
        if(StringUtil.isEmpty(cUserModel.getPayPassword())){
            result.setStatus(500);
            result.setMsg("未设置支付密码");
            return result;
        }
        // 判断输入密码与数据库的密码是否一致
        if (!Pbkdf2Encryption.checkPassword(password, cUserModel.getPayPassword())) {
            result.setStatus(400);
            result.setMsg("输入密码不正确");
            return result;
        }
        result.setStatus(200);
        result.setMsg("输入密码正确");
        return result;
    }

    /**
     * 修改密码
     * @param userId:用户外键
     * @param oldPassword:旧密码
     * @param password:新密码
     * @return 返回修改密码操作结果
     */
    @Override
    public ApiResultModel<String> modifyPassword(Integer userId, String oldPassword, String password) {
        ApiResultModel<String> result = new ApiResultModel<String>();
        CUserModel cUser = cUserDao.select(userId);
        if(StringUtil.isEmpty(cUser.getPayPassword())){
            result.setStatus(500);
            result.setMsg("未设置支付密码");
            return result;
        }
        // 判断输入密码与数据库密码是否一致
        if(!Pbkdf2Encryption.checkPassword(oldPassword, cUser.getPayPassword())){
            result.setStatus(400);
            result.setMsg("输入原密码错误");
            return result;
        }
        if(password.equals(oldPassword)){
            result.setStatus(400);
            result.setMsg("输入新密码不能与旧密码相同,请重新输入!");
            return result;
        }
        CUserModel newCUser = new CUserModel();
        newCUser.setId(userId);
        try {
            newCUser.setPayPassword(Pbkdf2Encryption.encode(password));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            logger.error("设置密码Pbkdf2加密出现异常，异常信息:"+e.toString());
            result.setMsg("设置密码操作错误");
            result.setStatus(400);
            return result;
        }
        int size = cUserDao.updateByPrimaryKeySelective(newCUser);
        if(size==0){
            result.setStatus(400);
            result.setMsg("修改密码操作错误");
            return result;
        }
        result.setStatus(200);
        result.setMsg("修改密码操作成功");
        return result; 
    }
}