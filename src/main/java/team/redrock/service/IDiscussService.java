package team.redrock.service;

import com.github.pagehelper.PageInfo;
import team.redrock.bean.Discuss;
import team.redrock.common.ServerResponse;
import team.redrock.vo.DiscussVo;

public interface IDiscussService {
    ServerResponse<PageInfo> getIndexDiscusses(Boolean hot, Integer pageNum, Integer pageSize);
    ServerResponse addOneDiscuss(Discuss discuss);
    ServerResponse<DiscussVo> queryRepliesByPid(int pid,String openid, String authorId);
    ServerResponse acceptReply(int replyId, String authorId, boolean accept);
    ServerResponse likeReply(int replyId, String authorId, boolean like);
    ServerResponse<PageInfo> getMyDiscusses(String authorId, String discussType, Integer pageNum, Integer pageSize);
    ServerResponse<PageInfo> searchDiscusses(String keyWord, String tag, Integer pageNum, Integer pageSize);
}
