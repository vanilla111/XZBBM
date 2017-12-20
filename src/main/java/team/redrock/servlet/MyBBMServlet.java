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

@WebServlet(name = "MyBBMServlet", urlPatterns = "/bbm/mybbm")
public class MyBBMServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        Student student = (Student) request.getSession().getAttribute("user");
        String requestType = request.getParameter("type").toLowerCase();
        String page = request.getParameter("page");
        String size = request.getParameter("pageSize");
        try (PrintWriter writer = response.getWriter()) {
            int pageNum = BBMUtil.stringToInteger(page, 1), pageSize = BBMUtil.stringToInteger(size, 30);
            ServerResponse serverResponse = null;
            IDiscussService service = new DiscussServiceImpl();
            if ("question".equals(requestType)) {
                // 查看我的问题
                serverResponse = service.getMyDiscusses(student.getStu_id(), requestType, pageNum, pageSize);
            } else if ("answer".equals(requestType)) {
                // 查看我的回答
                serverResponse = service.getMyDiscusses(student.getStu_id(), requestType, pageNum, pageSize);
            } else {
                serverResponse = ServerResponse.createByErrorMessage("不支持的操作");
            }
            writer.print(new ObjectMapper().writeValueAsString(serverResponse));
        }
    }
}
