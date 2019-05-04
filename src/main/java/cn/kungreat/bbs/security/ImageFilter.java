package cn.kungreat.bbs.security;

import cn.kungreat.bbs.vo.JsonResult;
import com.alibaba.fastjson.JSON;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class ImageFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest re = ((HttpServletRequest)request);
        String requestURI = re.getRequestURI();
        if("/defaultLogin".equals(requestURI) || "/login".equals(requestURI)){
            //如果已经存在认证对象 时不要再次认证
            SecurityContext context = SecurityContextHolder.getContext();
            if(context.getAuthentication() != null){
                String isAuth = context.getAuthentication().getName();
                if(isAuth != null && !"anonymousUser".equals(isAuth)){
                    HttpServletResponse rp = (HttpServletResponse)response;
                    rp.sendRedirect("/home.html");
                    return ;
                }
            }
        }
        if("/defaultLogin".equals(requestURI) || "/register".equals(requestURI)){
            Object code = re.getSession().getAttribute("image_code");
            long seconds = 90000;
            try{
                Object obj = re.getSession().getAttribute("time");
                long time = (obj==null?90000:(long)obj);
                seconds = new Date().getTime()-time;
            }catch (Exception e){
                e.printStackTrace();
            }
            if(code == null || seconds > 80000 || !code.equals(request.getParameter("code"))){
                re.getSession().removeAttribute("image_code");
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write(JSON.toJSONString(new JsonResult(false,"验证码错误-或者超时","/register.html")));
                return ;
            }
        }
        chain.doFilter(request,response);
    }
}