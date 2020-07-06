package cn.hel;

import java.io.InputStream;
import java.util.List;

import cn.hel.entity.User;
import cn.hel.mapper.UserMapper;
import cn.hel.mybatis.builder.SqlSessionFactoryBuilder;
import cn.hel.mybatis.session.SqlSessionFactory;
import cn.hel.mybatis.session.Sqlsession;

public class Test01 {
	
	public static void main(String[] args) {
		
		// 初始化阶段， 读取配置文件相关信息， 完成各模块的初始化工作
		String resource = "mybatis-config.xml";
		InputStream inputStream = Test01.class.getClassLoader().getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		
		// 获取Sqlsession
		Sqlsession sqlSession = sqlSessionFactory.openSession();
		
		// 通过动态代理获取代理对象
		UserMapper mapper = sqlSession.getMapper(UserMapper.class);
		
		// 通过动态代理对象执行指定sql, 并将结果集通过反射的方式封装到返回对象中返回
		List<User> userList = mapper.getAllUsers();
		for (User user : userList) {
			System.out.println(user);
		}
		
        User user = mapper.getUserById(1002);
        System.out.println(user);
		
	}
}
