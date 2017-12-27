package team.redrock.servlet;

import org.codehaus.jackson.map.ObjectMapper;
import team.redrock.bean.Student;
import team.redrock.common.ServerResponse;
import team.redrock.service.IUserService;
import team.redrock.service.impl.UserServiceImpl;
import team.redrock.util.BBMUtil;
import team.redrock.vo.StudentVo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "UserServlet", urlPatterns = {"/bbm/user"})
public class UserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        String userType = request.getParameter("type");

        try (PrintWriter writer = response.getWriter()) {
            ServerResponse serverResponse;
            IUserService service = new UserServiceImpl();
            int id = BBMUtil.stringToInteger(request.getParameter("id"), 0);
            if ("scholar".equals(userType)) {
                serverResponse = service.getUserDetailInfo(id ,true);
            } else {
                serverResponse = service.getUserDetailInfo(id, false);
            }

            writer.print(new ObjectMapper().writeValueAsString(serverResponse));
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");

        try (PrintWriter writer = response.getWriter()) {
            Student stu = (Student) request.getSession().getAttribute("user");
            ServerResponse serverResponse;
            if (stu == null) {
                serverResponse = ServerResponse.createByErrorMessage("用户信息不存在");
            } else {
                StudentVo studentVo = new StudentVo(stu);
                serverResponse = ServerResponse.createBySuccess(studentVo);
            }
            writer.print(new ObjectMapper().writeValueAsString(serverResponse));
        }
    }
}
