package com.lynn.user.controller;

import com.lynn.user.model.in.AccessTokenIn;
import com.lynn.user.model.in.AuthorizeIn;
import com.lynn.user.model.in.RefreshTokenIn;
import com.lynn.user.model.out.AccessTokenOut;
import com.lynn.user.result.Code;
import com.lynn.user.result.SingleResult;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

/**
 * 提供登录统一的登录界面
 * 用户输入账号和密码，如果请求成功，调用authorize接口
 * 客户端拿到code获得access_token
 * 客户端拿到access_token和openid可以获得用户信息
 */
@RequestMapping("oauth2")
@RestController
public class OAuthController extends BaseController{

    @Value("${self.data.openid_session_attribute}")
    private String sessionOpenIdAttribute;

    /**
     * 授权页面
     * 生成code，重定向到response_uri，带上code
     * 若有state参数，则原样带上state
     * @param authorize
     * @return
     */
    @GetMapping("authorize")
    public void authorize(@Valid AuthorizeIn authorize, BindingResult ret, HttpServletRequest request, HttpServletResponse response)throws IOException{
        validate(ret);
        //生成code(放到redis中，有效期10分钟，使用后清除)，重定向到response_uri,带上code和state
        if("code".equals(authorize.getResponseType())){
            String openid = (String) WebUtils.getSessionAttribute(request,sessionOpenIdAttribute);
            if(StringUtils.isNotBlank(openid)){
                authorize.setOpenid(openid);
                SingleResult<String> result = oAuthService.authorize(authorize);
                if(result.getCode() == Code.SUCCESS.getStatus()){
                    response.sendRedirect(result.getData());
                }else {
                    Assert.isTrue(false,result.getMessage());
                }
            }else {
                //重新登录
            }
        }else {
            Assert.isTrue(false,"response_type参数固定值为code");
        }
    }

    @GetMapping("access_token")
    public AccessTokenOut getAccessToken(@Valid AccessTokenIn accessTokenIn,BindingResult ret){
        validate(ret);
        AccessTokenOut accessTokenOut = new AccessTokenOut();
        if("authorization_code".equals(accessTokenIn.getGrantType())){
            //根据redirect_uri、client_id 判断code是否合法
            //如果合法则生成access_token和refresh_token
            //其中access_token默认有效期为7200秒，refresh_token有效期为半年
            SingleResult<AccessTokenOut> result = oAuthService.getAccessToken(accessTokenIn);
            if(result.getCode() == Code.SUCCESS.getStatus()){
                accessTokenOut = result.getData();
            }else {
                Assert.isTrue(false,result.getMessage());
            }
        }else {
            Assert.isTrue(false,"grant_type参数固定值为authorization_code");
        }
        return accessTokenOut;
    }

    @GetMapping("refresh_token")
    public AccessTokenOut getAccessToken(@Valid RefreshTokenIn refreshTokenIn,BindingResult ret){
        validate(ret);
        AccessTokenOut accessTokenOut = new AccessTokenOut();
        if("refresh_token".equals(refreshTokenIn.getGrantType())){
            //根据client_id判断refresh_token是否有效
            //重新生成access_token和refresh_token
            SingleResult<AccessTokenOut> result = oAuthService.getAccessToken(refreshTokenIn);
            if(result.getCode() == Code.SUCCESS.getStatus()){
                accessTokenOut = result.getData();
            }else {
                Assert.isTrue(false,result.getMessage());
            }
        }else {
            Assert.isTrue(false,"grant_type参数固定值为refresh_token");
        }
        return accessTokenOut;
    }
}
