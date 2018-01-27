/**
 * 
 */
package org.zerock.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.zerock.domain.BoardVO;
import org.zerock.persistence.BoardDAO;

/**
 * @author	: Juet
 * @date	: 2018. 1. 23.
 * @desc	: 
 * 
 */
@Service
public class BoardServiceImpl implements BoardService {
	
	@Inject
	private BoardDAO dao;

	/* (non-Javadoc)
	 * @see org.zerock.service.BoardService#regist(org.zerock.domain.BoardVO)
	 */
	@Override
	public void regist(BoardVO board) throws Exception {
		// TODO Auto-generated method stub
		dao.create(board);
	}

	/* (non-Javadoc)
	 * @see org.zerock.service.BoardService#read(java.lang.Integer)
	 */
	@Override
	public BoardVO read(Integer bno) throws Exception {
		// TODO Auto-generated method stub
		return dao.read(bno);
	}

	/* (non-Javadoc)
	 * @see org.zerock.service.BoardService#modify(org.zerock.domain.BoardVO)
	 */
	@Override
	public void modify(BoardVO board) throws Exception {
		// TODO Auto-generated method stub
		dao.update(board);
	}

	/* (non-Javadoc)
	 * @see org.zerock.service.BoardService#remove(java.lang.Integer)
	 */
	@Override
	public void remove(Integer bno) throws Exception {
		// TODO Auto-generated method stub
		dao.delete(bno);
	}

	/* (non-Javadoc)
	 * @see org.zerock.service.BoardService#listAll()
	 */
	@Override
	public List<BoardVO> listAll() throws Exception {
		// TODO Auto-generated method stub
		return dao.listAll();
	}
	
	public String nullCheck(BoardVO board){
		String msg = "";
		ArrayList<String> nMList = new ArrayList<>();
		if(board.getTitle() == null||board.getTitle().equals("")){
			nMList.add("제목");
		}
		if(board.getContent() == null||board.getContent().equals("")){
			nMList.add("내용");
		}
		if(board.getWriter() == null||board.getWriter().equals("")){
			nMList.add("작성인");
		}
		
		if(nMList.size()>0){
			msg = "해당 글의 ";
			for(String nullMessage:nMList){
				msg += nullMessage+", ";
			}
			msg = msg.substring(0, msg.length()-2);
			msg += "을 입력해주세요.";
		}
		
		return msg;
	}

}
