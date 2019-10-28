package org.zerock.dao;

import org.zerock.domain.BoardVO;

public interface BoardDAO extends CrudDAO<BoardVO, Long>{

	
	public int getCount();

	public boolean updateViewCnt(Long bno);
}
