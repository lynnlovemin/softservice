package com.lynn.user.service;

import com.lynn.user.result.Code;
import com.lynn.user.result.SingleResult;
import com.lynn.user.utils.Algorithm;
import com.lynn.user.utils.MessageDigestUtils;
import com.lynn.user.utils.XXTEA;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class UserService extends BaseService {

    /**
     * 登录，成功则返回openid
     * @param mobile
     * @param password
     * @return
     */
    public SingleResult<String> login(String mobile,String password){
        SingleResult<String> result = new SingleResult<>();
        String openid = userMapper.findOpenidByMobileAndPassword(mobile, MessageDigestUtils.encrypt(password, Algorithm.SHA1));
        if(StringUtils.isNotBlank(openid)){
            result.setCode(Code.SUCCESS);
            result.setData(openid);
        }else {
            result.setCode(Code.ERROR);
            result.setMessage("手机号或密码输入不正确！");
        }
        return result;
    }
}
