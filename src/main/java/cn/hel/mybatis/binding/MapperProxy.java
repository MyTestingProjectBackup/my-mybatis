package cn.hel.mybatis.binding;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Collection;

import cn.hel.mybatis.session.Sqlsession;

public class MapperProxy implements InvocationHandler {

	private Sqlsession sqlSession;

	public MapperProxy(Sqlsession sqlSession) {
		super();
		this.sqlSession = sqlSession;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		// sourceId = package path + method name, 例： cn.hel.mapper.getAllUsers
		String sourceId = method.getDeclaringClass().getName()+"."+method.getName();
		if(Collection.class.isAssignableFrom(method.getReturnType())) {
			return sqlSession.selectList(sourceId, args == null ? null : args[0]);
		}else {
			return sqlSession.selectOne(sourceId, args == null ? null : args[0]);
		}
	}

}
