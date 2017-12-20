package team.redrock.dao;

import team.redrock.bean.AccessToken;

/**
 * Created by wang on 2017/9/22.
 */
public interface AccessTokenMapper {
    public AccessToken queryLastAccessToken();
    public void updateNewAccessToken(AccessToken accessToken);
}
