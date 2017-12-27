package team.redrock.service.impl;

import org.apache.ibatis.session.SqlSession;
import team.redrock.bean.SeniorStudent;
import team.redrock.bean.Student;
import team.redrock.common.ServerResponse;
import team.redrock.dao.JuniorMapper;
import team.redrock.dao.SeniorMapper;
import team.redrock.service.IUserService;
import team.redrock.util.SqlSessionFactoryUtil;

public class UserServiceImpl implements IUserService {

    public ServerResponse getUserDetailInfo(Integer id, boolean isScholar) {
        try (SqlSession sqlSession = SqlSessionFactoryUtil.getSqlSessionFactory().openSession()) {
            ServerResponse serverResponse = null;
            if (isScholar) {
                // 查找学霸
                SeniorMapper seniorMapper = sqlSession.getMapper(SeniorMapper.class);
                SeniorStudent seniorStudent = seniorMapper.querySeniorByPrimaryKey(id);
                serverResponse = ServerResponse.createBySuccess(seniorStudent);
            } else {
                // 查找其他人
                JuniorMapper juniorMapper = sqlSession.getMapper(JuniorMapper.class);
                Student student = juniorMapper.selectJuniorByPrimaryKey(id);
                serverResponse = ServerResponse.createBySuccess(student);
            }

            if (serverResponse == null)
                return ServerResponse.createByErrorMessage("查找失败");

            return serverResponse;
        }
    }
}
