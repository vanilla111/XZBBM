package team.redrock.servlet;

import org.codehaus.jackson.map.ObjectMapper;
import team.redrock.bean.Student;
import team.redrock.common.ServerResponse;
import team.redrock.service.IDiscussService;
import team.redrock.service.impl.DiscussServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "DiscussOpServlet", urlPatterns = {"/bbm/discuss/operation"})
public class DiscussOpServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 删除
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        Student student = (Student) request.getSession().getAttribute("user");
        String requestType = request.getParameter("type").toLowerCase();

        try (PrintWriter writer = response.getWriter()) {
            try {
                int discussId = Integer.valueOf(request.getParameter("id"));
                String authorId = student.getStu_id();
                IDiscussService service = new DiscussServiceImpl();
                ServerResponse serverResponse;
                if ("accept".equals(requestType)) {
                    // 采纳
                    serverResponse = service.acceptReply(discussId, authorId, true);
                } else if ("not_accept".equals(requestType)) {
                    // 取消采纳
                    serverResponse = service.acceptReply(discussId, authorId, false);
                } else if ("like".equals(requestType)) {
                    // 点赞
                    serverResponse = service.likeReply(discussId, authorId, true);
                } else if ("unlike".equals(requestType)) {
                    // 取消点赞
                    serverResponse = service.likeReply(discussId, authorId, false);
                } else {
                    serverResponse = ServerResponse.createByErrorMessage("不支持的操作");
                }
                writer.print(new ObjectMapper().writeValueAsString(serverResponse));
            } catch (NumberFormatException e) {
                ServerResponse serverResponse = ServerResponse.createByErrorMessage("pid 参数错误(整型)");
                writer.print(new ObjectMapper().writeValueAsString(serverResponse));
            }
        }
    }
}
