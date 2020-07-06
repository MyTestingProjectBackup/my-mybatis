package cn.hel.mybatis.session;

import cn.hel.mybatis.config.Configuration;

public class DefaultSqlSessionFactory implements SqlSessionFactory {

	private final Configuration conf;
	
	public DefaultSqlSessionFactory(Configuration configuration) {
	    this.conf = configuration;
	}

	public Sqlsession openSession() {
		return new DefaultSqlsession(conf);
	}

}
