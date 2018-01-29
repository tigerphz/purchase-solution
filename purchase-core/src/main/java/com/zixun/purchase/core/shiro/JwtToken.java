package com.zixun.purchase.core.shiro;

import lombok.Getter;
import lombok.Setter;
import org.apache.shiro.authc.HostAuthenticationToken;

/**
 * @description:
 * @author: tiger
 * @date: 18-1-12 上午11:31
 * @version: V1.0
 * @modified by:
 */
public class JwtToken implements HostAuthenticationToken {
    /**
     * 消息摘要
     */
    @Getter
    @Setter
    private String token;

    private String host;

    public JwtToken(String token, String host) {
        this.token = token;
        this.host = host;
    }

    /**
     * 身份，比如用户名、手机号、邮箱等
     *
     * @return
     */
    @Override
    public Object getPrincipal() {
        return getToken();
    }

    /**
     * 凭证，比如密码证明身份，shiro会使用登录给的凭证跟在Realm里给的凭证做比较
     *
     * @return
     */
    @Override
    public Object getCredentials() {
        return getToken();
    }

    @Override
    public String getHost() {
        return host;
    }
}
