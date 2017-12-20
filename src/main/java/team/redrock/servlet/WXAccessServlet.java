package team.redrock.servlet;

import org.dom4j.DocumentException;
import team.redrock.bean.TextMessage;
import team.redrock.util.EncryptUtil;
import team.redrock.util.MessageUtil;
import team.redrock.util.PropertiesUtil;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

@WebServlet(name = "WXAccessServlet", urlPatterns = {"/wxaccess"})
public class WXAccessServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        try {
            Map<String, String> map = MessageUtil.XMLToMap(request);
            String fromUserName = map.get("FromUserName");
            String toUserName = map.get("ToUserName");
            String msgType = map.get("MsgType");
            String content = map.get("Content");
            String xmlText = null;
            if ("text".equals(msgType)) {
                TextMessage message = new TextMessage();
                message.setFromUserName(toUserName);
                message.setToUserName(fromUserName);
                message.setMsgType("text");
                message.setCreateTime(new Date().getTime());
                message.setContent("your message is :" + content);
                xmlText = MessageUtil.textMessageToXML(message);
            }
            out.print(xmlText);
        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");

        String token = PropertiesUtil.getProperty("token");

        String[] arr = {token, timestamp, nonce};
        for (String str : arr) {
            if (str == null) {
                return ;
            }
        }
        Arrays.sort(arr);
        StringBuilder sb = new StringBuilder();
        for (String str : arr) {
            sb.append(str);
        }

        String sign = EncryptUtil.sha1(sb.toString());
        if (sign.equalsIgnoreCase(signature)) {
            try (PrintWriter writer = response.getWriter()) {
                writer.print(echostr);
            }
        }
    }
}
