package com.lynn.user.model.in;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lynn.user.model.BaseModel;
import org.hibernate.validator.constraints.NotBlank;

public class AuthorizeIn extends BaseModel{

    @JsonProperty("response_type")
    @NotBlank(message = "缺少response_type参数")
    private String responseType;

    @JsonProperty("client_id")
    @NotBlank(message = "缺少client_id参数")
    private String clientId;

    private String state;

    @JsonProperty("redirect_uri")
    @NotBlank(message = "缺少redirect_uri参数")
    private String redirectUri;

    private String openid;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getResponseType() {
        return responseType;
    }

    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }
}
