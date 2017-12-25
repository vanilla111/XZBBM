package team.redrock.servlet;

import org.codehaus.jackson.map.ObjectMapper;
import team.redrock.bean.Discuss;
import team.redrock.bean.Student;
import team.redrock.common.Jurisdiction;
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

@WebServlet(name = "DiscussServlet", urlPatterns = {"/bbm/discuss"})
public class DiscussServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        Student student = (Student) request.getSession().getAttribute("user");
        String requestType = request.getParameter("type").toLowerCase();
        try (PrintWriter writer = response.getWriter()){
            if ("question".equals(requestType)) {
                // 提问
                String title = request.getParameter("title");
                String content = request.getParameter("content");
                String picName = request.getParameter("orgPic");
                String thumbPicName = request.getParameter("thumbPic");
                String tag = request.getParameter("tag");
                Discuss discuss = new Discuss();
                discuss.setAuthor_id(student.getStu_id());
                discuss.setNick_name(student.getNick_name());
                discuss.setHead_url(student.getHead_url());
                discuss.setTitle(title);
                discuss.setContent(content);
                discuss.setPic_name(picName);
                discuss.setPic_thumb(thumbPicName);
                discuss.setTag(tag);
                IDiscussService service = new DiscussServiceImpl();
                writer.print(new ObjectMapper().writeValueAsString(service.addOneDiscuss(discuss)));
            } else if ("answer".equals(requestType)) {
                // 回答
                try {
                    int pid = Integer.valueOf(request.getParameter("id"));
                    String content = request.getParameter("content");
                    String picName = request.getParameter("orgPic");
                    String thumbPicName = request.getParameter("thumbPic");
                    Discuss discuss = new Discuss();
                    discuss.setPid(pid);
                    discuss.setAuthor_id(student.getStu_id());
                    discuss.setNick_name(student.getNick_name());
                    discuss.setHead_url(student.getHead_url());
                    discuss.setContent(content);
                    discuss.setPic_name(picName);
                    discuss.setPic_thumb(thumbPicName);
                    //判断是否是学霸回答问题
                    if (student.getJurisdiction() == Jurisdiction.SUPERSCHOLAR)
                        discuss.setScholar(true);
                    IDiscussService service = new DiscussServiceImpl();
                    writer.print(new ObjectMapper().writeValueAsString(service.addOneDiscuss(discuss)));
                } catch (NumberFormatException e) {
                    ServerResponse serverResponse = ServerResponse.createByErrorMessage("pid 参数错误（整型）");
                    writer.print(new ObjectMapper().writeValueAsString(serverResponse));
                }
            } else {
                ServerResponse serverResponse = ServerResponse.createByErrorMessage("不支持的请求类型");
                writer.print(new ObjectMapper().writeValueAsString(serverResponse));
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");

        Student student = (Student) request.getSession().getAttribute("user");
        // 问题详情 一个问题的所有回复
        try (PrintWriter writer = response.getWriter()) {
            try {
                int pid = Integer.valueOf(request.getParameter("id"));
                IDiscussService service = new DiscussServiceImpl();
                ServerResponse serverResponse = service.queryRepliesByPid(pid, student.getStu_id());
                writer.print(new ObjectMapper().writeValueAsString(serverResponse));
            } catch (NumberFormatException e) {
                ServerResponse serverResponse = ServerResponse.createByErrorMessage("pid 参数错误（整型）");
                writer.print(new ObjectMapper().writeValueAsString(serverResponse));
            }
        }
    }
}
