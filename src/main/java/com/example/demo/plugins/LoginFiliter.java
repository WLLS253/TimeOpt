//package com.example.demo.plugins;
//
//import com.example.demo.Serivce.CookieService;
//import com.example.demo.Serivce.TokenService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.AutoConfigureOrder;
//import org.springframework.core.annotation.Order;
//
//import javax.servlet.*;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.net.URLEncoder;
//
//@Order(1)
//@WebFilter(filterName="firstFilter", urlPatterns="/*")
//public class LoginFiliter implements Filter {
//
//
//    @Autowired
//    private TokenService tokenService;
//
//    @Autowired
//    private CookieService cookieService;
//    @Override
//    public void init(FilterConfig filterConfig) {
//        System.out.println("firstliter");
//    }
//
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        HttpServletResponse response=(HttpServletResponse)servletResponse;
//        if(cookieService.getCookie(request,"username")==null){
//
//            loginRedirect(request,response);
//        }
//        request.setAttribute("user", "0");
//        String token = request.getHeader("token");
//        if (token != null) {
//            String uuid = tokenService.validateToken(token, 24 * 3600L);
//            if (uuid != null) request.setAttribute("user", uuid);
//        }
//        filterChain.doFilter(servletRequest, servletResponse);
//    }
//
//    @Override
//    public void destroy() {
//        System.out.println("destroy");
//    }
//
//
//    public void loginRedirect(HttpServletRequest request, HttpServletResponse response) {
//
//        if (request.getMethod().equalsIgnoreCase("GET")) {
//
//            // 跳转地址
//            String redirectUrl = request.getQueryString() != null ? request.getRequestURI() + "?" + request.getQueryString() : request.getRequestURI();
//
//            // 重定向
//            try {
//                response.sendRedirect(request.getContextPath() + "/login=" + URLEncoder.encode(redirectUrl, "utf-8"));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        } else {
//
//            response.setCharacterEncoding("UTF-8");
//            response.setContentType("application/json; charset=utf-8");
//            response.setStatus(401);
//            PrintWriter out = null;
//            try {
//                out = response.getWriter();
//                out.append("{\"code\":401,\"msg\":\"未登录\"}");
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                if (out != null) {
//                    out.close();
//                }
//            }
//        }
//    }
//
//
//}
//
//
