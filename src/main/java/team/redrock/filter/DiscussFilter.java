package team.redrock.filter;

import org.codehaus.jackson.map.ObjectMapper;
import team.redrock.bean.Student;
import team.redrock.common.Jurisdiction;
import team.redrock.common.ServerResponse;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebFilter(filterName = "DiscussFilter", urlPatterns = "/bbm/discuss")
public class DiscussFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");

        // 判断session中是否有该用户
        Student stu = (Student) request.getSession().getAttribute("user");
        PrintWriter writer = response.getWriter();
        if (stu == null) {
            ServerResponse serverResponse = ServerResponse.createByErrorMessage("非法操作");
            writer.print(new ObjectMapper().writeValueAsString(serverResponse));
            writer.close();
            return;
        }
        if ("POST".equals(request.getMethod()) && stu.getJurisdiction() == Jurisdiction.YOUKE) {
            ServerResponse serverResponse = ServerResponse.createByErrorMessage("非重邮学生或未绑定小帮手的用户无法操作");
            writer.print(new ObjectMapper().writeValueAsString(serverResponse));
            writer.close();
            return;
        }
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
