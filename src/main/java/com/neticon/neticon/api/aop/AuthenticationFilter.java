package com.neticon.neticon.api.aop;

import com.neticon.neticon.common.enums.NetIconErrorCodeEnum;
import com.neticon.neticon.common.response.BaseResponse;
import com.neticon.neticon.common.vo.UserInfoVo;
import com.neticon.neticon.repository.redis.impl.TokenRedisRepositoryImpl;
import com.neticon.neticon.tools.JsonUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Component
public class AuthenticationFilter implements Filter {
    private final TokenRedisRepositoryImpl tokenRedisRepository;

    public AuthenticationFilter(TokenRedisRepositoryImpl tokenRedisRepository) {
        this.tokenRedisRepository = tokenRedisRepository;
    }

    private static Map<String, String> authWhiteUrlMap;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        authWhiteUrlMap = new HashMap<>();
        authWhiteUrlMap.put("/session", "post");
        authWhiteUrlMap.put("/user", "post");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes();
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        // websocket has its own way to authenticate
        if (httpRequest.getHeader("Upgrade") != null
                && httpRequest.getHeader("Upgrade").equals("websocket")) {
            chain.doFilter(request, response);
            return;
        }
        if (!"options".equals(httpRequest.getMethod().toLowerCase())) {
            if (isRequestNeedAuthentication(
                    requestAttributes.getRequest().getRequestURI(),
                    httpRequest.getMethod().toLowerCase())) {
                String token = requestAttributes.getRequest().getHeader("AccessToken");
                String nickname = tokenRedisRepository.getUserNickname(token);
                if (nickname == null) {
                    returnJson(response,
                            JsonUtils.toJson(
                                    BaseResponse
                                            .newFailResponse()
                                            .errorCode(NetIconErrorCodeEnum.SESSION_EXPIRED.getCode())
                                            .errorMsg(NetIconErrorCodeEnum.SESSION_EXPIRED.getMsg())
                                            .build()));
                    return;
                }
                UserInfoVo userInfoVo = new UserInfoVo();
                userInfoVo.setName(nickname);
                userInfoVo.setToken(token);
                requestAttributes.setAttribute("userInfo", userInfoVo, RequestAttributes.SCOPE_REQUEST);
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

    private boolean isRequestNeedAuthentication(String uri, String method) {
        for (Map.Entry<String, String> entry : authWhiteUrlMap.entrySet()) {
            if (uri.indexOf(entry.getKey()) == 0 && method.equals(entry.getValue())) {
                return false;
            }
        }
        return true;
    }

    private void returnJson(ServletResponse response, String json) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try (PrintWriter writer = response.getWriter()) {
            writer.print(json);
        } catch (IOException e) {
            System.out.println("IO error");
        }
    }
}
