package org.zerock.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.zerock.dao.util.MyBatisLoader;

public class TimeDAOImpl implements TimeDAO {
	
	private SqlSessionFactory factory = MyBatisLoader.INSTANCE.getFactory();

	@Override
	public String getTime() {

		String result = null;
		
		try (SqlSession session = factory.openSession()) {
			
			result = session.selectOne("org.zerock.dao.TimeMapper.getTime");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

}
