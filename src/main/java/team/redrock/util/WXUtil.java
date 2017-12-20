package team.redrock.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import team.redrock.bean.AccessToken;
import team.redrock.dao.AccessTokenMapper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

/**
 * Created by wang on 2017/9/22.
 */
public class WXUtil {
    private static final String appID = PropertiesUtil.getProperty("appID");
    private static final String secret = PropertiesUtil.getProperty("appsecret");
    private static String bbmIndexURL =  PropertiesUtil.getProperty("bbmIndex");

    // 生成菜单接口
    private static final String CREATE_MENU = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=";

    // 用户授权接口
    private static final String AUTHORIZE_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?";
    // 静默授权接口
    private static String       SNSAPI_BASE;
    // 获取用户具体信息接口
    private static String       SNSAPI_USERINFO;

    // code 换取 网页access_token 和 openID 接口
    private static String CODE_TO_TOKEN_OPENID = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" +
            appID + "&secret=" + secret + "&code=CODE&grant_type=authorization_code";

    // web access_token 换取用户信息
    private static String TOKEN_TO_USERINFO = "https://api.weixin.qq.com/sns/userinfo?access_token={ACCESS_TOKEN}&openid={OPENID}&lang=zh_CN";

    // 获取access_token 接口
    private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential"
            + "&appid=" + appID + "&secret=" + secret;

    static {
        try {
            SNSAPI_BASE = "https://open.weixin.qq.com/connect/oauth2/authorize?"
                    + "appid=" + appID + "&redirect_uri=" + URLEncoder.encode(bbmIndexURL, "UTF-8")
                    + "&response_type=code&scope=snsapi_base#wechat_redirect";
            SNSAPI_USERINFO = "https://open.weixin.qq.com/connect/oauth2/authorize?"
                    + "appid=" + appID + "&redirect_uri=" + URLEncoder.encode(bbmIndexURL, "UTF-8")
                    + "&response_type=code&scope=snsapi_userinfo#wechat_redirect";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * get 请求
     * @param url
     * @return
     * @throws IOException
     */
    public static JSONObject doGetStr(String url) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        JSONObject jsonObject = null;
        HttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            String result = EntityUtils.toString(entity, "UTF-8");
            jsonObject = new JSONObject(result);
        }

        return jsonObject;
    }

    /**
     * post 请求
     * @param url
     * @param outStr
     * @return
     * @throws IOException
     */
    public static JSONObject doPostStr(String url, String outStr) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        JSONObject jsonObject = null;
        httpPost.setEntity(new StringEntity(outStr, "UTF-8"));
        HttpResponse response = httpClient.execute(httpPost);
        HttpEntity entity = response.getEntity();
        String result = EntityUtils.toString(entity, "UTF-8");
        jsonObject = new JSONObject(result);

        return jsonObject;
    }

    public static String getAuthorizeUrl(boolean userInfo) {
        if (userInfo)
            return SNSAPI_USERINFO;

        return SNSAPI_BASE;
    }

    public static JSONObject getWebAccessToken(String code) throws IOException {
        String url = CODE_TO_TOKEN_OPENID;
        JSONObject res = doGetStr(url.replace("CODE", code));
        if (res.has("access_token"))
            return res;
        else
            return null;
    }

    public static JSONObject getWebUserInfo(String code) throws IOException {
        JSONObject temp = getWebAccessToken(code);
        if (temp == null) return null;
        String url = TOKEN_TO_USERINFO.replaceFirst("\\{ACCESS_TOKEN\\}", temp.getString("access_token"))
                .replaceFirst("\\{OPENID\\}", temp.getString("openid"));
        JSONObject res = doGetStr(url);
        if (res.has("openid"))
            return res;

        return null;
    }

    public static AccessToken getAccessToken() {
        //将返回的access_token
        AccessToken accessToken = new AccessToken();
        //从数据库取出的token
        AccessToken temp = getAccessTokenFromDB();
        long current_time = new Date().getTime() / 1000;
        if ("".equals(temp.getToken()) || current_time - temp.getUpdated_at() > 7180) {
            try {
                JSONObject jsonObject = WXUtil.doGetStr(ACCESS_TOKEN_URL);
                if (jsonObject != null) {
                    accessToken.setToken(jsonObject.getString("access_token"));
                    accessToken.setExpires(jsonObject.getInt("expires_in"));
                    accessToken.setUpdated_at(new Date().getTime() / 1000);
                    setAccessTokenToDB(accessToken);
                }
            } catch (IOException e) {
                //TODO 获取失败相应处理
                e.printStackTrace();
            }
        } else
            return temp;

        return accessToken;
    }

    private static AccessToken getAccessTokenFromDB() {
        AccessToken accessToken = null;
        try (SqlSession sqlSession = SqlSessionFactoryUtil.getSqlSessionFactory().openSession()) {
            AccessTokenMapper mapper = sqlSession.getMapper(AccessTokenMapper.class);
            accessToken = mapper.queryLastAccessToken();
        }
        return accessToken;
    }

    private static void setAccessTokenToDB(AccessToken accessToken) {
        try (SqlSession sqlSession = SqlSessionFactoryUtil.getSqlSessionFactory().openSession()){
            AccessTokenMapper mapper = sqlSession.getMapper(AccessTokenMapper.class);
            mapper.updateNewAccessToken(accessToken);
            sqlSession.commit();
        }
    }

}
