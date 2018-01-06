package com.lynn.user.model.in;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lynn.user.model.BaseModel;
import org.hibernate.validator.constraints.NotBlank;

public class RefreshTokenIn extends BaseModel {

    @JsonProperty("grant_type")
    @NotBlank(message = "缺少grant_type参数")
    private String grantType;

    @JsonProperty("refresh_token")
    @NotBlank(message = "缺少refresh_token参数")
    private String refreshToken;

    @JsonProperty("client_id")
    @NotBlank(message = "缺少client_id参数")
    private String clientId;

    private String scope;

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
