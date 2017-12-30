package team.redrock.servlet;

import org.codehaus.jackson.map.ObjectMapper;
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

@WebServlet(name = "SearchServlet", urlPatterns = {"/bbm/search"})
public class SearchServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        String page = request.getParameter("page");
        String size = request.getParameter("pageSize");
        String keyWord = request.getParameter("key");
        String tag = request.getParameter("tag");

        try (PrintWriter writer = response.getWriter()) {
            int pageNum = BBMUtil.stringToInteger(page, 1);
            int pageSize = BBMUtil.stringToInteger(size, 10);
            ServerResponse serverResponse;
            if (keyWord != null) keyWord = keyWord.trim();
            if (tag != null) tag = tag.trim();
            if (("".equals(keyWord) || keyWord == null) && ("".equals(tag) || tag == null)) {
                serverResponse = ServerResponse.createByErrorMessage("请选择查询条件");
                writer.print(new ObjectMapper().writeValueAsString(serverResponse));
            } else {
                IDiscussService service = new DiscussServiceImpl();
                if ("".equals(keyWord)) keyWord = null;
                if ("".equals(tag)) tag = null;
                serverResponse = service.searchDiscusses(keyWord, tag, pageNum, pageSize);
                writer.print(new ObjectMapper().writeValueAsString(serverResponse));
            }
        }
    }
}
