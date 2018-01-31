package com.lynn.user.service;

import com.lynn.user.model.in.AccessTokenIn;
import com.lynn.user.model.in.AuthorizeIn;
import com.lynn.user.model.in.RefreshTokenIn;
import com.lynn.user.model.out.AccessTokenOut;
import com.lynn.user.model.out.ApplicationOut;
import com.lynn.user.result.Code;
import com.lynn.user.result.SingleResult;
import com.lynn.user.utils.Algorithm;
import com.lynn.user.utils.MessageDigestUtils;
import com.lynn.user.utils.XXTEA;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class OAuthService extends BaseService{

    @Autowired
    private StringRedisTemplate template;
    @Value("${self.data.redis.code.expire}")
    private int codeExpire;
    @Value("${self.data.redis.access_token.expire}")
    private int accessTokenExpire;
    @Value("${self.data.redis.refresh_token.expire}")
    private int refreshTokenExpire;
    @Value("${self.data.xxtea_key}")
    private String xxteaKey;

    /**
     * 授权，主要是获得授权码code
     * @param authorize
     */
    public SingleResult<String> authorize(AuthorizeIn authorize){
        SingleResult<String> result = new SingleResult<>();
        //判断client_id是否存在
        ApplicationOut applicationOut = applicationMapper.findByClientId(authorize.getClientId());
        if(null != applicationOut){
            //code生成规则，从session中取出用户的openid，通过xxtea加密即是code
            String openid = authorize.getOpenid();
            String code = getAuthorizeCode(openid);
            StringBuilder uri = new StringBuilder(authorize.getRedirectUri());
            uri.append("?code=").append(code);
            if(StringUtils.isNotBlank(authorize.getState())){
                uri.append("&state=").append(authorize.getState());
            }
            //将code存入redis,有效期设置为10分钟
            //key:client_id+"-"+redirect_uri
            template.opsForValue().set(getRedisCodeKey(authorize.getClientId(),authorize.getRedirectUri()),code,codeExpire, TimeUnit.SECONDS);
            result.setCode(Code.SUCCESS);
            result.setData(uri.toString());

        }else {
            result.setCode(Code.ERROR);
            result.setMessage("invalid client_id");
        }
        return result;
    }

    /**
     * 根据code获得access_token
     * @param accessTokenIn
     * @return
     */
    public SingleResult<AccessTokenOut> getAccessToken(AccessTokenIn accessTokenIn){
        SingleResult<AccessTokenOut> result = new SingleResult<>();
        //根据redirect_uri、client_id 判断code是否合法
        String code = template.opsForValue().get(getRedisCodeKey(accessTokenIn.getClientId(),accessTokenIn.getRedirectUri()));
        if(StringUtils.isNotBlank(code)){
            if(code.equals(accessTokenIn.getCode())){
                //判断client_id和secret是否存在
                ApplicationOut applicationOut = applicationMapper.findByClientIdAndSecret(accessTokenIn.getClientId(),accessTokenIn.getSecret());
                if(null != applicationOut){
                    //如果合法则生成access_token和refresh_token
                    //access_token生成规则：SHA1(client_id+secret+timestamp)
                    //refres_token生成规则：SHA1(client_id+secret+timestamp+随机数)
                    //其中access_token默认有效期为7200秒，refresh_token有效期为半年
                    AccessTokenOut accessTokenOut = getAccessTokenOut(accessTokenIn.getClientId(),accessTokenIn.getSecret());
                    try {
                        accessTokenOut.setOpenid(XXTEA.decrypt(code,xxteaKey));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    result.setCode(Code.SUCCESS);
                    result.setData(accessTokenOut);
                }else {
                    result.setCode(Code.ERROR);
                    result.setMessage("invalid client_id or secret");
                }
            }else {
                result.setCode(Code.ERROR);
                result.setMessage("invalid code");
            }
        }else {
            result.setCode(Code.ERROR);
            result.setMessage("code不存在！");
        }
        return result;
    }

    /**
     * 通过refresh_token获得access_token
     * 只刷新access_token不支持返回openid，openid请通过code获得
     * @param refreshTokenIn
     * @return
     */
    public SingleResult<AccessTokenOut> getAccessToken(RefreshTokenIn refreshTokenIn){
        SingleResult<AccessTokenOut> result = new SingleResult<>();
        //根据client_id判断refresh_token是否有效
        //重新生成access_token和refresh_token
        String refreshTokenRedis = template.opsForValue().get(refreshTokenIn.getClientId()+"refresh_token");
        if(StringUtils.isNotBlank(refreshTokenRedis)){
            //判断client_id和secret是否存在
            ApplicationOut applicationOut = applicationMapper.findByClientIdAndSecret(refreshTokenIn.getClientId(),refreshTokenIn.getSecret());
            if(null != applicationOut){
                //如果合法则生成access_token和refresh_token
                //access_token生成规则：SHA1(client_id+secret+timestamp)
                //refres_token生成规则：SHA1(client_id+secret+timestamp+随机数)
                //其中access_token默认有效期为7200秒，refresh_token有效期为半年
                AccessTokenOut accessTokenOut = getAccessTokenOut(refreshTokenIn.getClientId(),refreshTokenIn.getSecret());
                result.setCode(Code.SUCCESS);
                result.setData(accessTokenOut);
            }else {
                result.setCode(Code.ERROR);
                result.setMessage("invalid client_id or secret");
            }
        }else {
            result.setCode(Code.ERROR);
            result.setMessage("invalid refresh_token");
        }
        return result;
    }

    public AccessTokenOut getAccessTokenOut(String clientId,String secret){
        String accessToken = getAccessToken(clientId,secret);
        String refreshToken = getAccessToken(clientId,secret);
        AccessTokenOut accessTokenOut = new AccessTokenOut();
        accessTokenOut.setAccessToken(accessToken);
        accessTokenOut.setRefreshToken(refreshToken);
        accessTokenOut.setExpiresIn(accessTokenExpire);
        template.opsForValue().set(clientId+"access_token",accessToken,accessTokenExpire,TimeUnit.SECONDS);
        template.opsForValue().set(clientId+"refresh_token",refreshToken,refreshTokenExpire,TimeUnit.SECONDS);
        return accessTokenOut;
    }

    /**
     * 生成存储code到redis的key
     * @param clientId
     * @param redirectUri
     * @return
     *
     */
    private String getRedisCodeKey(String clientId,String redirectUri){
        return new StringBuilder(clientId).append('-').append(redirectUri).toString();
    }

    /**
     * 生成授权码
     * @return
     */
    protected String getAuthorizeCode(String openid){
        return XXTEA.encrypt(openid,xxteaKey);
    }

    /**
     * 生成access_token
     * @param clientId
     * @param secret
     * @return
     */
    private String getAccessToken(String clientId,String secret){
        return MessageDigestUtils.encrypt(clientId+secret+System.currentTimeMillis(), Algorithm.SHA1);
    }

    /**
     * 生成refresh_token
     * @param clientId
     * @param secret
     * @return
     */
    private String getRefreshToken(String clientId,String secret){
        return MessageDigestUtils.encrypt(clientId+secret+System.currentTimeMillis()+ new Random().nextInt(100),Algorithm.SHA1);
    }
}
