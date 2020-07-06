package cn.hel.mybatis.session;

import java.lang.reflect.Proxy;
import java.util.List;

import cn.hel.mybatis.binding.MapperProxy;
import cn.hel.mybatis.config.Configuration;
import cn.hel.mybatis.excutor.DefaultExecutor;
import cn.hel.mybatis.excutor.Executor;

public class DefaultSqlsession implements Sqlsession {

	public final Configuration conf;

	private Executor executor;

	public DefaultSqlsession(Configuration conf) {
		this.conf = conf;
		this.executor = new DefaultExecutor(conf);
	}

	@Override
	public <T> T selectOne(String statement) {
		return this.selectOne(statement, null);
	}

	@Override
	public <T> T selectOne(String statement, Object parameter) {
		List<T> list = this.selectList(statement, parameter);
		if (list.size() == 1) {
			return list.get(0);
		} else if (list.size() > 1) {
			throw new RuntimeException("Expected one result (or null) to be returned by selectOne(), but found: " + list.size());
		} else {
			return null;
		}
	}

	@Override
	public <E> List<E> selectList(String statement) {
		return this.selectList(statement, null);
	}

	@Override
	public <E> List<E> selectList(String statement, Object parameter) {
		return this.executor.query(conf.getMapStatments().get(statement), parameter);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getMapper(Class<T> type) {
		MapperProxy mp = new MapperProxy(this);
		return (T) Proxy.newProxyInstance(type.getClassLoader(), new Class[] {type}, mp);
	}

}
