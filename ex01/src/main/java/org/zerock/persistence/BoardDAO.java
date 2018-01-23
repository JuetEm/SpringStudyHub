/**
 * 
 */
package org.zerock.persistence;

import java.util.List;

import org.zerock.domain.BoardVO;

/**
 * @author	: Juet
 * @date	: 2018. 1. 22.
 * @desc	: BoardDAO 인터페이스 
 * 
 */
public interface BoardDAO {

	public void create(BoardVO vo)throws Exception;
	
	public BoardVO read(Integer bno)throws Exception;
	
	public void update(BoardVO vo)throws Exception;
	
	public void delete(Integer bno)throws Exception;
	
	public List<BoardVO> listAll()throws Exception;
}
