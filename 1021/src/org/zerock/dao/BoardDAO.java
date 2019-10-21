package org.zerock.dao;

import org.zerock.domain.BoardVO;

public interface BoardDAO {
	
	// select
	public BoardVO selectOne(Long bno);
	
	public boolean insert(BoardVO vo);
}
