package team.redrock.servlet;

import org.codehaus.jackson.map.ObjectMapper;
import team.redrock.bean.Student;
import team.redrock.common.ServerResponse;
import team.redrock.service.IDiscussService;
import team.redrock.service.impl.DiscussServiceImpl;
import team.redrock.util.BBMUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "IndexServlet", urlPatterns = {"/bbm/index"})
public class IndexServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        Student student = (Student) request.getSession().getAttribute("user");
        String type = request.getParameter("type");
        String page = request.getParameter("page");
        String size = request.getParameter("pageSize");
        try (PrintWriter writer = response.getWriter()) {
            int pageNum = BBMUtil.stringToInteger(page, 1);
            int pageSize = BBMUtil.stringToInteger(size, 50);
            ServerResponse serverResponse;
            IDiscussService service = new DiscussServiceImpl();
            if ("hot".equals(type)) {
                serverResponse = service.getIndexDiscusses(true, pageNum, pageSize);
            } else {
                serverResponse = service.getIndexDiscusses(false, pageNum, pageSize);
            }

            writer.print(new ObjectMapper().writeValueAsString(serverResponse));
        }
    }
}
