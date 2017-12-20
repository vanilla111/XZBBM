package team.redrock.dao;

import team.redrock.bean.Student;

public interface JuniorMapper {
    Student selectByStuId(String stuId);
    Student queryJuniorByOpenid(String openId);
    int updateWXInfoByPrimaryKey(Student student);
    int insertSelective(Student student);
}
