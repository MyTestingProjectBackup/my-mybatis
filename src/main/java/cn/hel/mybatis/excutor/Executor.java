package cn.hel.mybatis.excutor;

import java.util.List;

import cn.hel.mybatis.config.MapperStatment;

public interface Executor {

	<E> List<E> query(MapperStatment statment, Object parameter);

}
