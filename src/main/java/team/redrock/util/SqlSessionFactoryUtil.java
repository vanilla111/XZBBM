package team.redrock.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

public class SqlSessionFactoryUtil {
    //创建静态成员变量sqlSessionFactory被所有对象共享
    private static SqlSessionFactory sqlSessionFactory = null;
    private static String resource = "Configuration.xml";

    public static SqlSessionFactory getSqlSessionFactory() {
        //如果sqlSessionFactory被创建过就使用已经存在的 没有的话就读取配置文件
        if(sqlSessionFactory == null){
            synchronized (SqlSessionFactoryUtil.class) {
                if (sqlSessionFactory == null) {
                    try{
                        Reader reader = Resources.getResourceAsReader(resource);
                        sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }
        }
        return sqlSessionFactory;
    }

}
