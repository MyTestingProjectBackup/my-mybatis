package cn.hel.mybatis.reflection;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReflectionUtil {
	/**
	 * 为指定的bean的propName属性的值设为value
	 * 
	 * @param bean
	 * @param propName
	 * @param value
	 */
	public static void setPropToBean(Object bean, String propName, Object value) {
		Field f;
		try {
			f = bean.getClass().getDeclaredField(propName); // 获得对象指定的属性3
			f.setAccessible(true); // 将字段设置为可通过反射访问
			f.set(bean, value);
		} catch (NoSuchFieldException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void setPropToBeanFromResultSet(Object entity, ResultSet resultSet) throws SQLException {
		Field[] declaredFields = entity.getClass().getDeclaredFields();
		for (int i = 0; i < declaredFields.length; i++) {
			String simpleName = declaredFields[i].getType().getSimpleName();
			String value = declaredFields[i].getName();
			if ("String".equals(simpleName)) {
				setPropToBean(entity, value, resultSet.getString(value));
			} else if ("Integer".equals(simpleName)) {
				setPropToBean(entity, value, resultSet.getInt(value));
			} else if ("Long".equals(simpleName)) {
				setPropToBean(entity, value, resultSet.getLong(value));
			}
		}
	}
}
