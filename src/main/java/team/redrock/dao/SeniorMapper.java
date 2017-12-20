package team.redrock.dao;

import team.redrock.bean.SeniorStudent;
import team.redrock.bean.Student;

public interface SeniorMapper {
    SeniorStudent querySeniorByPrimaryKey(Integer id);
    SeniorStudent querySeniorByOpenid(String openId);
    SeniorStudent querySeniorByAuthorId(String authorId);
    int updateWXInfoByPrimaryKey(Student student);
}
