package cn.hel.mybatis.excutor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cn.hel.mybatis.config.Configuration;
import cn.hel.mybatis.config.MapperStatment;
import cn.hel.mybatis.reflection.ReflectionUtil;

public class DefaultExecutor implements Executor {

	private final Configuration conf;

	public DefaultExecutor(Configuration conf) {
		this.conf = conf;
	}

	@Override
	public <E> List<E> query(MapperStatment statment, Object parameter) {
		System.out.println(statment.getSql());
		List<E> ret = new ArrayList<E>();
		try {
			Class.forName(conf.getJdbcDriver());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet resultSet = null;

		try {
			conn = DriverManager.getConnection(conf.getJdbcUrl(), conf.getJdbcUsername(), conf.getJdbcPassword());
			stat = conn.prepareStatement(statment.getSql());
			// 处理sql中的占位符
			setParameters(stat, parameter);
			// 使用jdbc执行sql
			resultSet = stat.executeQuery();
			// 返回结果映射到list中
			handleResultSet(resultSet, ret, statment.getResultType());
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				resultSet.close();
				stat.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return ret;
	}

	@SuppressWarnings("unchecked")
	private <E> void handleResultSet(ResultSet resultSet, List<E> ret, String resultType) {
		Class<E> clazz = null;
		try {
			clazz = (Class<E>) Class.forName(resultType);
			while (resultSet.next()) {
				Object entity = clazz.newInstance();
				ReflectionUtil.setPropToBeanFromResultSet(entity, resultSet);
				ret.add((E) entity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("rawtypes")
	private void setParameters(PreparedStatement stat, Object parameter) throws SQLException {
		if (parameter == null) {
			return;
		}

		if (Collection.class.isAssignableFrom(parameter.getClass())) {
			Object[] objects = ((Collection) parameter).toArray();
			for (int i = 0; i < objects.length; i++) {
				Object p = objects[i];
				if (p instanceof Integer) {
					stat.setInt(i + 1, (Integer) p);
				} else if (p instanceof String) {
					stat.setString(i + 1, (String) p);
				} else if (p instanceof Long) {
					stat.setLong(i + 1, (Long) p);
				}
			}
		}else {
//			if (parameter instanceof Integer) {
//				stat.setInt(1, (Integer) parameter);
//			} else if (parameter instanceof String) {
//				stat.setString(1, (String) parameter);
//			} else if (parameter instanceof Long) {
//				stat.setLong(1, (Long) parameter);
//			}
			stat.setObject(1, parameter);
		}
	}

}
