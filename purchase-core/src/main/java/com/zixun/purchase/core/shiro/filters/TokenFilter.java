package com.zixun.purchase.core.shiro.filters;

import com.alibaba.fastjson.JSON;
import com.zixun.purchase.core.shiro.JwtToken;
import com.zixun.purchase.model.vo.ResultBean;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description:
 * @author: tiger
 * @date: 18-1-12 下午1:12
 * @version: V1.0
 * @modified by:
 */
public class TokenFilter extends AuthenticatingFilter {
    private Logger logger = LoggerFactory.getLogger(TokenFilter.class);

    public static final String AUTHORIZATION = "Authorization";

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        AuthenticationToken token = createToken(request, response);

        try {
            // 提交给realm进行登入，如果错误他会抛出异常并被捕获
            Subject subject = getSubject(request, response);
            subject.login(token);
            return this.onLoginSuccess(token, subject, request, response);
        } catch (AuthenticationException e) {
            return this.onLoginFailure(token, e, request, response);
        }
    }

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
        String token = getRequestToken(request);
        String host = getHost(request);

        logger.debug("获取到token：{}", token);

        JwtToken jwtToken = new JwtToken(token, host);

        return jwtToken;
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        try {
            setLoginFailureMessage(response, e.getMessage());
        } catch (Exception exception) {
            logger.error(exception.getMessage());
        }

        return false;
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        String token = getRequestToken(request);
        if (token == null) {
            return setLoginFailureMessage(response, "没有获取到资源访问令牌,请确认是否设置好token");
        }

        return executeLogin(request, response);
    }

    /**
     * 对跨域提供支持
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }

    /**
     * 获取请求头的token信息
     *
     * @param request
     * @return
     */
    private String getRequestToken(ServletRequest request) {
        HttpServletRequest req = (HttpServletRequest) request;
        String authorization = req.getHeader(AUTHORIZATION);
        return authorization;
    }

    /**
     * 设置错误返回信息
     *
     * @param response
     * @throws Exception
     */
    private Boolean setLoginFailureMessage(ServletResponse response, String message) throws Exception {
        return setLoginFailureMessage(response, message, ResultBean.COMMON_ERROR);
    }

    /**
     * 设置错误返回信息
     *
     * @param response
     * @throws Exception
     */
    private Boolean setLoginFailureMessage(ServletResponse response, String message, Integer code) throws Exception {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setContentType("application/json;charset=UTF-8");

        ResultBean<Boolean> resultBean = new ResultBean<>();
        resultBean.setCode(code);
        resultBean.setStatus(ResultBean.FAIL);
        resultBean.setData(Boolean.FALSE);
        resultBean.setMessage(message);

        httpServletResponse.getWriter().print(JSON.toJSONString(resultBean));

        return false;
    }
}
