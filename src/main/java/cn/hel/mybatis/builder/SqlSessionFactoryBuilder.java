package cn.hel.mybatis.builder;

import java.io.InputStream;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import cn.hel.mybatis.config.Configuration;
import cn.hel.mybatis.config.MapperStatment;
import cn.hel.mybatis.session.DefaultSqlSessionFactory;
import cn.hel.mybatis.session.SqlSessionFactory;

public class SqlSessionFactoryBuilder {

	public SqlSessionFactory build(InputStream inputStream) {
		Configuration conf = new Configuration();
		conf = loadConfigInfo(conf, inputStream);
		return new DefaultSqlSessionFactory(conf);
	}

	@SuppressWarnings("unchecked")
	private Configuration loadConfigInfo(Configuration conf, InputStream inputStream) {
		SAXReader sax = new SAXReader();

		Document document = null;
		try {
			document = sax.read(inputStream);
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		Element root = document.getRootElement();
		// 加载数据库配置信息
		List<Element> environments = root.elements("environments");
		loadDbInfo(conf, environments);

		// 加载mapper文件信息
		List<Element> mapperPathList = root.elements("mappers");
		loadMapperInfo(conf, mapperPathList);

		return conf;
	}

	@SuppressWarnings("unchecked")
	private void loadDbInfo(Configuration conf, List<Element> environments) {
		if(environments.isEmpty()) {
			return;
		}
		
		for (Element element : environments) {
			List<Element> environment = element.elements("environment");
			for (Element element2 : environment) {
				List<Element> dataSources = element2.elements("dataSource");
				for (Element datasource : dataSources) {
					List<Element> properties = datasource.elements("property");
					for (Element propertie : properties) {
						String name = propertie.attribute("name").getData().toString();
						String value = propertie.attribute("value").getData().toString();
						switch (name) {
						case "driver":
							conf.setJdbcDriver(value);
							break;
						case "url":
							conf.setJdbcUrl(value);
							break;
						case "username":
							conf.setJdbcUsername(value);
							break;
						case "password":
							conf.setJdbcPassword(value);
							break;
						}
					}
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void loadMapperInfo(Configuration conf, List<Element> mapperPathList) {
		if (mapperPathList.isEmpty()) {
			return;
		}

		for (Element mappers : mapperPathList) {
			List<Element> mapperList = mappers.elements("mapper");
			for (Element mapper : mapperList) {
				// 获取mapper.xml路径,  如：mapper/UserMapper.xml
				String mapperPath = mapper.attribute("resource").getData().toString();
				
				
				InputStream inputStream = SqlSessionFactoryBuilder.class.getClassLoader().getResourceAsStream(mapperPath);

				SAXReader sax = new SAXReader();
				Document document = null;
				try {
					document = sax.read(inputStream);
				} catch (DocumentException e) {
					e.printStackTrace();
				}
				Element root = document.getRootElement();
				// 获取namespace
				String namespace = root.attribute("namespace").getData().toString();

				List<Element> elements = root.elements("select");
				for (Element element : elements) {
					MapperStatment statment = new MapperStatment();
					String id = element.attribute("id").getData().toString();
					String resultType = element.attribute("resultType").getData().toString();
					String sql = element.getData().toString();
					String sourceId = namespace + "." + id;
					statment.setNamespace(namespace);
					statment.setResultType(resultType);
					statment.setSourceId(sourceId);
					statment.setSql(sql);
					conf.getMapStatments().put(sourceId, statment);
				}
			}
		}
	}
}
