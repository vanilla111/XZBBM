package team.redrock.DiscussTest;

import com.github.pagehelper.PageInfo;
import org.apache.ibatis.session.SqlSession;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import team.redrock.bean.Discuss;
import team.redrock.bean.Upvote;
import team.redrock.common.ServerResponse;
import team.redrock.dao.DiscussMapper;
import team.redrock.dao.UpvoteMapper;
import team.redrock.service.IDiscussService;
import team.redrock.service.impl.DiscussServiceImpl;
import team.redrock.util.PropertiesUtil;
import team.redrock.util.SqlSessionFactoryUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapperTest {

    private static SqlSession sqlSession = SqlSessionFactoryUtil.getSqlSessionFactory().openSession();

    @Test
    public void selectDiscussByDate() {
        DiscussMapper mapper = sqlSession.getMapper(DiscussMapper.class);
        List<Discuss> discusses = mapper.selectDiscussesByDate(PropertiesUtil.getProperty("bbmStartTime"));
        System.out.println(discusses.size());
        for (Discuss discuss : discusses) {
            System.out.println( discuss.getContent() + " " + discuss.isAccepted() + " " + discuss.getCreate_time());
        }
    }

    @Test
    public void selectDiscussById() {
        DiscussMapper mapper = sqlSession.getMapper(DiscussMapper.class);
        Discuss discuss = mapper.selectDiscussByPrimaryKey(25);
        System.out.println(discuss.isScholar());
        sqlSession.close();
    }

    @Test
    public void selectQuestionByReplyId() {
        DiscussMapper mapper = sqlSession.getMapper(DiscussMapper.class);
        int res = mapper.acceptReply(24);
        System.out.println(res);
        sqlSession.close();
    }

    @Test
    public void acceptTest() {
        IDiscussService service = new DiscussServiceImpl();
        ServerResponse response;
        // 值为空
        response = service.acceptReply(24, null, true);
        if (response.isSuccess()) System.out.println("wrong" + response.getMsg());
        // 操作id 不存在
        response = service.acceptReply(30, "2015211516", true);
        if (!response.isSuccess()) System.out.println("wrong" + response.getMsg());
        //非提问者操作
        response = service.acceptReply(24, "1234567", true);
        if (response.isSuccess()) System.out.println("wrong 1" + response.getMsg());
        //提问者操作
        response = service.acceptReply(24, "2015211516", true);
        if (!response.isSuccess()) System.out.println("wrong 2" + response.getMsg());
        //重复操作
        response = service.acceptReply(24, "2015211516", true);
        if (!response.isSuccess()) System.out.println("wrong 3" + response.getMsg());
        //取消采纳
        response = service.acceptReply(24, "2015211516", false);
        if (!response.isSuccess()) System.out.println("wrong 4" + response.getMsg());
        //重复操作
        response = service.acceptReply(24, "2015211516", false);
        if (!response.isSuccess()) System.out.println("wrong 5" + response.getMsg());
    }

    @Test
    public void likeTest() {
        IDiscussService service = new DiscussServiceImpl();
        ServerResponse response;
        // null
        response = service.likeReply(24, null, true);
        if (response.isSuccess()) System.out.println("wrong 1" + response.getMsg());

        // 横向越权
        response = service.likeReply(23, "2015211516", true);
        if (response.isSuccess()) System.out.println("wrong 2" + response.getMsg());

        // 点赞操作
        response = service.likeReply(24, "2015211516", true);
        if (!response.isSuccess()) System.out.println("wrong 3" + response.getMsg());
        // 重复点赞
        response = service.likeReply(24, "2015211516", true);
        if (!response.isSuccess()) System.out.println("wrong 4" + response.getMsg());

        // 取消点赞
        response = service.likeReply(24, "2015211516", false);
        if (!response.isSuccess()) System.out.println("wrong 5" + response.getMsg());
        // 重复取消点赞
        response = service.likeReply(24, "2015211516", false);
        if (!response.isSuccess()) System.out.println("wrong 6" + response.getMsg());
    }

    @Test
    public void whereInTest() {
        UpvoteMapper upvoteMapper = sqlSession.getMapper(UpvoteMapper.class);
        List<Discuss> discusses = new ArrayList<>();
        for (int i = 0; i < 13; i++) {
            Discuss discuss = new Discuss();
            discuss.setId(10 + i);
            discusses.add(discuss);
        }
        List<Upvote> list = upvoteMapper.selectByDList("2015211516", discusses);
        System.out.println(list.size());
        for (Upvote upvote : list) {
            System.out.println(upvote.getAid());
        }
    }

    @Test
    public void getMyDiscussesTest() throws IOException {
        IDiscussService service = new DiscussServiceImpl();
        ServerResponse serverResponse;
        serverResponse = service.getMyDiscusses("2015211516", "question", 6, 10);
        PageInfo list = (PageInfo) serverResponse.getData();
        System.out.println(new ObjectMapper().writeValueAsString(serverResponse));
    }

    @Test
    public void searchTest() {
        DiscussMapper mapper = sqlSession.getMapper(DiscussMapper.class);
        List<Discuss> list = mapper.searchDiscusses(null, null);
        for (Discuss discuss : list) {
            System.out.println(discuss.getId() + " " + discuss.getTitle());
        }
    }
}
