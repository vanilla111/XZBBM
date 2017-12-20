package team.redrock.dao;

import org.apache.ibatis.annotations.Param;
import team.redrock.bean.Discuss;

import java.util.List;

public interface DiscussMapper {
    int insertSelective(Discuss discuss);
    int incrementReplyCount(Integer id);
    int incrementLikeCount(Integer id);
    int decrementLikeCount(Integer id);
    Discuss selectDiscussByPrimaryKey(Integer discussId);
    Discuss selectQuestionByReplyId(Integer replyId);
    List<Discuss> selectDiscusses();
    List<Discuss> selectDiscussesOrderByReplyCount();
    List<Discuss> selectRepliesByPid(Integer pid);
    List<Discuss> selectMyQuestionsByAuthorId(String authorId);
    List<Discuss> selectDiscussesByAuthorId(String authorId);
    List<Discuss> selectDiscussesByAnswerStuId(String authorId);
    List<Discuss> selectDiscussesByDate(String startDate);
    List<Discuss> searchDiscusses(@Param("keyWord")String keyWord, @Param("tag") String tag);

    int acceptReply(@Param("replyId") Integer replyId);
    int cancelAcceptReply(@Param("replyId") Integer replyId);
}
