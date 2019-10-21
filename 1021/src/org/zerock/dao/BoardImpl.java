package org.zerock.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.zerock.dao.util.MyBatisLoader;
import org.zerock.domain.BoardVO;

public class BoardImpl implements BoardDAO {
	
	private SqlSessionFactory factory = MyBatisLoader.INSTANCE.getFactory();

	@Override
	public BoardVO selectOne(Long bno) {
		
		BoardVO result = null;

		try (SqlSession session = factory.openSession()) {
			 result = session.selectOne("org.zerock.dao.BoardMapper.selectOne", bno);
			 
			 System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public static void main(String[] args) {
		BoardDAO dao = new BoardImpl();
		BoardVO vo = new BoardVO();
		
		vo.setTitle("ㄹㄹㄹ");
		vo.setContent("ffff");
		vo.setWriter("ddddddd");
		
		System.out.println(dao.insert(vo));
	}

	@Override
	public boolean insert(BoardVO vo) {
		
		boolean result = false;
		int ddd = 0;
		
		try (SqlSession session = factory.openSession()) {
			 ddd = session.insert("org.zerock.dao.BoardMapper.insert", vo);
			 
			 session.commit();
			 
			 result = ddd == 1? true:false;
			 
			 
			 System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		return result;
	}
	
}
