package team.redrock.filter;

import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import team.redrock.bean.SeniorStudent;
import team.redrock.bean.Student;
import team.redrock.common.Jurisdiction;
import team.redrock.dao.JuniorMapper;
import team.redrock.dao.SeniorMapper;
import team.redrock.util.PropertiesUtil;
import team.redrock.util.SqlSessionFactoryUtil;
import team.redrock.util.WXUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "AuthenticationFilter", urlPatterns = "/bbm/*")
public class AuthenticationFilter implements Filter {

    private static String openIdToStuInfoURL = "https://hongyan.cqupt.edu.cn/MagicLoop/index.php?s=/addon/UserCenter/UserCenter/getStuInfoByOpenId&openId=";

    private static String openIdBindStuURL = "https://wx.idsbllp.cn/MagicLoop/index.php?s=/addon/Bind/Bind/bind/openid/{openid}/token/gh_68f0a1ffc303&redirect=";

    private static String redirectURI = "https://wx.idsbllp.cn/MagicLoop/index.php?s=/addon/Api/Api/oauth&redirect=";

    private static String notFoundUrl = PropertiesUtil.getProperty("bbmNotFound");

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        //测试代码
//        response.setHeader("Access-Control-Allow-Origin", "*");
//        response.setHeader("Access-Control-Allow-Methods", "POST, GET, DELETE, PUT");
//        Student student = new Student();
//        student.setStu_id("2015211516");
//        student.setNick_name("admin");
//        student.setOpenId("zxcvbnm,");
//        student.setHead_url("www.baidu.com");
//        student.setJurisdiction(Jurisdiction.SUPERSCHOLAR);
//        request.getSession().setAttribute("user", student);

        // 判断session中是否有该用户
        Student stu = (Student) request.getSession().getAttribute("user");
        if (stu == null) {
            // 如果没有则获取openid
            // 判断请求中是否有 oauthStatus
            String oauthStatus = request.getParameter("oauthstatus");
            if ( oauthStatus == null || "".equals(oauthStatus) || !"success".equals(oauthStatus)) {
                //如果没有code 跳转 snsapi_userinfo
                String redirectUrl = WXUtil.getAuthorizeUrl(true);
                response.sendRedirect(redirectUrl);
                return ;
            }

            String openid = request.getParameter("openid");
            String nickname = request.getParameter("nickname");
            String sex = request.getParameter("sex");
            String headimgurl = request.getParameter("headimgurl");

            // 首先查询数据库中是否有相应用户
            SqlSession sqlSession = SqlSessionFactoryUtil.getSqlSessionFactory().openSession();
            SeniorMapper seniorDB = sqlSession.getMapper(SeniorMapper.class);
            stu = seniorDB.querySeniorByOpenid(openid);  //如果学霸表中有此人则停止查找
            if (stu == null) { // 查找普通用户表
                JuniorMapper juniorDB = sqlSession.getMapper(JuniorMapper.class);
                stu = juniorDB.queryJuniorByOpenid(openid);
                if (stu == null) { // 查询小帮手绑定接口，获取用户信息
                    JSONObject stuInfo = null;
                    stu = new Student();
                    boolean flag = false;
                    for (int i = 0; i < 3; i++) {
                        try {
                            stuInfo = WXUtil.doGetStr(openIdToStuInfoURL + openid);
                        } catch (IOException e) {
                            stu.setStu_id("-1");
                        }
                        if (stuInfo != null && stuInfo.getInt("status") == 200) {
                            //再次查询 判断是否是学霸第一次登陆
                            //如果学霸第一次登陆,应该更新senior中对应的openid
                            JSONObject stuData = stuInfo.getJSONObject("data");
                            stu.setCollege(stuData.getString("collage"));
                            stu.setClass_num(stuData.getString("class"));
                            stu.setMajor(stuData.getString("major"));
                            stu.setStu_id(stuData.getString("usernumber"));
                            stu.setGender(stuData.getString("gender"));
                            stu.setName(stuData.getString("realname"));
                            stu.setGender(sex);
                            stu.setOpenId(openid);
                            stu.setNick_name(nickname);
                            stu.setHead_url(headimgurl);
                            SeniorStudent tempStu = seniorDB.querySeniorByAuthorId(stu.getStu_id());
                            if (tempStu != null && tempStu.getJurisdiction() == Jurisdiction.SUPERSCHOLAR) {
                                //第一次登陆 更新openid
                                stu.setId(tempStu.getId());
                                stu.setIdentity(Jurisdiction.SUPERSCHOLAR.getType());
                                seniorDB.updateWXInfoByPrimaryKey(stu);
                                sqlSession.commit();
                            } else {
                                stu.setIdentity(Jurisdiction.DUMBASS.getType());
                                juniorDB.insertSelective(stu);
                                sqlSession.commit();
                            }
                            flag = true;
                            break;
                        }
                    }
                    if (!flag) {
                        // 获取失败设定为游客
                        //response.sendRedirect(openIdBindStuURL.replaceFirst("\\{openid\\}", openid) + PropertiesUtil.getProperty("bbmIndex"));
                        stu.setGender(sex);
                        stu.setOpenId(openid);
                        stu.setNick_name(nickname);
                        stu.setHead_url(headimgurl);
                        stu.setJurisdiction(Jurisdiction.YOUKE);
                    }
                } else if (!nickname.equals(stu.getNick_name())
                        || !headimgurl.equals(stu.getHead_url())) {
                    stu.setIdentity(Jurisdiction.DUMBASS.getType());
                    stu.setNick_name(nickname);
                    stu.setHead_url(headimgurl);
                    juniorDB.updateWXInfoByPrimaryKey(stu);
                    sqlSession.commit();
                }
            } else if (!nickname.equals(stu.getNick_name())
                    || !headimgurl.equals(stu.getHead_url())) {
                stu.setJurisdiction(Jurisdiction.SUPERSCHOLAR);
                stu.setNick_name(nickname);
                stu.setHead_url(headimgurl);
                seniorDB.updateWXInfoByPrimaryKey(stu);
                sqlSession.commit();
            }
            sqlSession.close();
            // 将用户信息存入session
            request.getSession().setAttribute("user", stu);
        }

        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
