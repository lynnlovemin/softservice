package com.lynn.user.model.in;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lynn.user.model.BaseModel;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 请求access_token的输入参数
 */
public class AccessTokenIn extends BaseModel {

    @JsonProperty("grant_type")
    @NotBlank(message = "缺少grant_type参数")
    private String grantType;

    @NotBlank(message = "缺少code参数")
    private String code;

    @JsonProperty("redirect_uri")
    @NotBlank(message = "缺少redirect_uri参数")
    private String redirectUri;

    @JsonProperty("client_id")
    @NotBlank(message = "缺少client_id参数")
    private String clientId;

    @NotBlank(message = "缺少secret参数")
    private String secret;

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
