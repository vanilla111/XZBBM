package team.redrock.service;

import team.redrock.common.ServerResponse;

public interface IUserService {
    public ServerResponse getUserDetailInfo(Integer id, boolean isScholar);
}
