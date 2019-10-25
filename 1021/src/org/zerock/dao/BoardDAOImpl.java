package org.zerock.dao;

import java.util.List;
import java.util.stream.IntStream;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.zerock.dao.util.MyBatisLoader;
import org.zerock.domain.BoardVO;
import org.zerock.dto.PagingDTO;

import lombok.extern.log4j.Log4j;

@Log4j
public class BoardDAOImpl implements BoardDAO {
	private SqlSessionFactory factory = MyBatisLoader.INSTANCE.getFactory();
	
	@Override
	public boolean insert(BoardVO vo) {
		boolean result = false;

		SqlSession session = null;
		
		
		try {
			  session = factory.openSession(false);
			  
			  int count = session.insert("org.zerock.dao.BoardMapper.insert",vo);
			  
			  List<String> fnames = vo.getFnames();
			  
			  for (int i = 0; i < fnames.size(); i++) {
				  session.insert("org.zerock.dao.BoardMapper.insertFile",fnames.get(i));
				
			}
			  			  
			  session.commit();
			  
			  
			  result = count == 1? true:false;
			  
		}catch (Exception e) {
			//session.rollback();			
			e.printStackTrace();
		}
		return result;
	}
	public static void main(String[] args) throws Exception {
		BoardDAO dao = new BoardDAOImpl();
		PagingDTO pdto = new PagingDTO();
		pdto.setPage(5);
		dao.getList(pdto);
//		pdto.setPage(20);
//		pdto.setAmount(10);
//		
//		List<BoardVO> list = dao.getList(pdto);
//		list.forEach(board ->System.out.println(board));
//		BoardVO vo = dao.select(5L);
//		System.out.println(vo);
//		dao.select(5l);
//		IntStream.range(1000,1001).forEach(i->{
//			BoardVO vo = new BoardVO();
//			vo.setTitle("제목"+i);
//			vo.setContent("내용...."+i);
//			vo.setWriter("user"+i);
//			
//			System.out.println(dao.insert(vo));
//			
//		});
	}
	@Override
	public BoardVO selectOne(Long bno) {
		BoardVO result = null;

		try (SqlSession session = factory.openSession()) {
			
			result = session.selectOne("org.zerock.dao.BoardMapper.select",bno);
			System.out.println(result);
			  
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	@Override
	public boolean update(BoardVO vo) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean delete(Long key) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public List<BoardVO> getList(PagingDTO dto) {
		List<BoardVO> result = null;

		try (SqlSession session = factory.openSession()) {
			
			result = session.selectList("org.zerock.dao.BoardMapper.selectList",dto);
			System.out.println(result);
			  
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	@Override
	public int getCount() {
		int result = 0;

		try (SqlSession session = factory.openSession()) {
			
			result = session.selectOne("org.zerock.dao.BoardMapper.getCount");
			System.out.println(result);
			  
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}