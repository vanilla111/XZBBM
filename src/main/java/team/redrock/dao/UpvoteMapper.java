package team.redrock.dao;

import org.apache.ibatis.annotations.Param;
import team.redrock.bean.Discuss;
import team.redrock.bean.Upvote;

import java.util.List;

public interface UpvoteMapper {
    Upvote selectUpvoteByDidAiD(Upvote upvote);
    int inertOneUpvote(Upvote upvote);
    int upvoteDiscuss(Upvote upvote);
    int cancelUpvote(Upvote upvote);
    List<Upvote> selectByDList(@Param("authorId") String authorId, @Param("dList")List<Discuss> dList);
}
