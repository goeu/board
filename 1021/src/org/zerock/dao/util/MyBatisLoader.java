package org.zerock.dao.util;

import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public enum MyBatisLoader {
	
	INSTANCE;
	
	private SqlSessionFactory factory;
	
	public SqlSessionFactory getFactory() {
		return this.factory;
	}
	
	private MyBatisLoader() {
		try {
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		factory = new SqlSessionFactoryBuilder().build(inputStream);
			
		System.out.println(factory);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	
}
