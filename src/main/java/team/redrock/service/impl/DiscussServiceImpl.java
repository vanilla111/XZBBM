package team.redrock.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.session.SqlSession;
import team.redrock.bean.Discuss;
import team.redrock.bean.Upvote;
import team.redrock.common.ServerResponse;
import team.redrock.dao.DiscussMapper;
import team.redrock.dao.UpvoteMapper;
import team.redrock.service.IDiscussService;
import team.redrock.util.SqlSessionFactoryUtil;
import team.redrock.vo.DiscussVo;

import java.util.List;

public class DiscussServiceImpl implements IDiscussService {

    public ServerResponse<PageInfo> getIndexDiscusses(Boolean hot, Integer pageNum, Integer pageSize) {
        try (SqlSession sqlSession = SqlSessionFactoryUtil.getSqlSessionFactory().openSession()) {
            PageHelper.startPage(pageNum, pageSize);
            DiscussMapper discussMapper = sqlSession.getMapper(DiscussMapper.class);
            List<Discuss> discusses;
            if (hot)
                discusses = discussMapper.selectDiscussesOrderByReplyCount();
            else
                discusses = discussMapper.selectDiscusses();
            PageInfo pageRes = new PageInfo(discusses);
            return ServerResponse.createBySuccess(pageRes);
        }
    }

    public ServerResponse addOneDiscuss(Discuss discuss) {
        try (SqlSession sqlSession = SqlSessionFactoryUtil.getSqlSessionFactory().openSession()) {
            DiscussMapper discussMapper = sqlSession.getMapper(DiscussMapper.class);
            if (discuss.getPid() > 0) {
                int temp = discussMapper.incrementReplyCount(discuss.getPid());
                if (temp == 0) return ServerResponse.createByErrorMessage("增加失败");
            }
            int res = discussMapper.insertSelective(discuss);

            if (res <= 0)
                return ServerResponse.createByErrorMessage("增加失败");

            sqlSession.commit();
            return ServerResponse.createBySuccessMessage("增加成功");
        }
    }

    public ServerResponse<DiscussVo> queryRepliesByPid(int pid, String openid) {
        try (SqlSession sqlSession = SqlSessionFactoryUtil.getSqlSessionFactory().openSession()) {
            DiscussMapper discussMapper = sqlSession.getMapper(DiscussMapper.class);
            UpvoteMapper upvoteMapper = sqlSession.getMapper(UpvoteMapper.class);
            Discuss discuss = discussMapper.selectDiscussByPrimaryKey(pid);
            if (discuss == null)
                return ServerResponse.createByErrorMessage("id 对应讨论不存在");
            List<Discuss> replies = discussMapper.selectRepliesByPid(pid);
            if (replies.size() > 0) {
                List<Upvote> upvotes = upvoteMapper.selectByDList(openid, replies);
                //TODO 程序规模较大时，务必更改点赞功能的实现
                setUpvoteStatus(replies, upvotes);
            }

            DiscussVo discussVo = new DiscussVo(discuss);
            if (openid != null && openid.equals(discuss.getAuthor_id()))
                discussVo.setMine(true);
            discussVo.setRepliesList(replies);
            return ServerResponse.createBySuccess(discussVo);
        }
    }

    public ServerResponse<PageInfo> getMyDiscusses(String authorId, String discussType, Integer pageNum, Integer pageSize) {
        if (authorId == null) {
            return ServerResponse.createByErrorMessage("当前用户暂未持有学号");
        }
        try (SqlSession sqlSession = SqlSessionFactoryUtil.getSqlSessionFactory().openSession()) {
            PageHelper.startPage(pageNum, pageSize);
            DiscussMapper discussMapper = sqlSession.getMapper(DiscussMapper.class);
            List<Discuss> discusses = null;
            if ("question".equals(discussType))
                discusses = discussMapper.selectMyQuestionsByAuthorId(authorId);
            else if ("answer".equals(discussType))
                discusses = discussMapper.selectDiscussesByAnswerStuId(authorId);

            PageInfo pageRes = new PageInfo(discusses);
            return ServerResponse.createBySuccess(pageRes);
        }
    }

    public ServerResponse<PageInfo> searchDiscusses(String keyWord, String tag, Integer pageNum, Integer pageSize) {
        try (SqlSession sqlSession = SqlSessionFactoryUtil.getSqlSessionFactory().openSession()) {
            PageHelper.startPage(pageNum, pageSize);
            DiscussMapper discussMapper = sqlSession.getMapper(DiscussMapper.class);
            List<Discuss> discusses = discussMapper.searchDiscusses(keyWord, tag);
            PageInfo pageRes = new PageInfo(discusses);
            return ServerResponse.createBySuccess(pageRes);
        }
    }

    public ServerResponse acceptReply(int replyId, String authorId, boolean accept) {
        if (authorId == null)
            return ServerResponse.createByErrorMessage("学号为空，请求失败");

        try (SqlSession sqlSession = SqlSessionFactoryUtil.getSqlSessionFactory().openSession()) {
            DiscussMapper discussMapper = sqlSession.getMapper(DiscussMapper.class);
            Discuss discuss = discussMapper.selectQuestionByReplyId(replyId);
            if (discuss == null || !authorId.equals(discuss.getAuthor_id()))
                return ServerResponse.createByErrorMessage("只能操作自己提出问题下的回复哦！～");
            int res;
            if (accept)
                res = discussMapper.acceptReply(replyId);
            else
                res = discussMapper.cancelAcceptReply(replyId);

            if (res == 0)
                return ServerResponse.createByErrorMessage("对应回复不存在");

            sqlSession.commit();

            return ServerResponse.createBySuccessMessage("operation success");
        }
    }

    public ServerResponse likeReply(int replyId, String openid, boolean like) {
        if (openid == null)
            return ServerResponse.createByErrorMessage("openid为空，请求失败");

        try (SqlSession sqlSession = SqlSessionFactoryUtil.getSqlSessionFactory().openSession()) {
            UpvoteMapper upvoteMapper = sqlSession.getMapper(UpvoteMapper.class);
            DiscussMapper discussMapper = sqlSession.getMapper(DiscussMapper.class);
            Upvote upvote = new Upvote();
            upvote.setDid(replyId);
            upvote.setAid(openid);
            Upvote selectUpvote = upvoteMapper.selectUpvoteByDidAiD(upvote);
            int res = 0;
            if (like) {
                //点赞
                if (selectUpvote == null) //一次都未点过赞
                    res = upvoteMapper.inertOneUpvote(upvote);
                else {
                    if (selectUpvote.isLike())
                        return ServerResponse.createBySuccess();
                    else
                        res = upvoteMapper.upvoteDiscuss(upvote); //取消点赞后又点赞
                }
                if (res > 0) {
                     int temp = discussMapper.incrementLikeCount(replyId);
                     if (temp == 0) return ServerResponse.createByError();
                     sqlSession.commit();
                     return ServerResponse.createBySuccess();
                }
            } else {
                // 取消点赞
                if (selectUpvote == null || !selectUpvote.isLike()) //本来就没有点赞
                    return ServerResponse.createBySuccess();
                else {
                    res = upvoteMapper.cancelUpvote(upvote);
                    int temp = discussMapper.decrementLikeCount(replyId);
                    if (res > 0 && temp > 0) {
                        sqlSession.commit();
                        return ServerResponse.createBySuccess();
                    }
                }
            }

            return ServerResponse.createByError();
        }
    }

    private void setUpvoteStatus(List<Discuss> discussList, List<Upvote> upvoteList) {
        for (int i = 0; i < discussList.size(); i++) {
            Discuss discuss = discussList.get(i);
            for (int j = 0; j < upvoteList.size(); j++) {
                Upvote upvote = upvoteList.get(j);
                if (discuss.getId() == upvote.getDid())
                    discuss.setUpvote(true);
            }
        }
    }

}
