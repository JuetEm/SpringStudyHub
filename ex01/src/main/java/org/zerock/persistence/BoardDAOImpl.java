/**
 * 
 */
package org.zerock.persistence;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import org.zerock.domain.BoardVO;

/**
 * @author	: Juet
 * @date	: 2018. 1. 22.
 * @desc	: 
 * 
 */

@Repository
public class BoardDAOImpl implements BoardDAO {
	
	@Inject
	private SqlSession session;
	
	private static String namespace = "org.zerock.mapper.BoardMapper";

	/* (non-Javadoc)
	 * @see persistence.BoardDAO#create(domain.BoardVO)
	 */
	@Override
	public void create(BoardVO vo) throws Exception {
		// TODO Auto-generated method stub
		session.insert(namespace+".create", vo);
	}

	/* (non-Javadoc)
	 * @see persistence.BoardDAO#read(java.lang.Integer)
	 */
	@Override
	public BoardVO read(Integer bno) throws Exception {
		// TODO Auto-generated method stub
		return session.selectOne(namespace+".read", bno);
	}

	/* (non-Javadoc)
	 * @see persistence.BoardDAO#update(domain.BoardVO)
	 */
	@Override
	public void update(BoardVO vo) throws Exception {
		// TODO Auto-generated method stub
		session.update(namespace+".update", vo);
	}

	/* (non-Javadoc)
	 * @see persistence.BoardDAO#delete(java.lang.Integer)
	 */
	@Override
	public void delete(Integer bno) throws Exception {
		// TODO Auto-generated method stub
		session.delete(namespace+".delete", bno);
	}

	/* (non-Javadoc)
	 * @see persistence.BoardDAO#listAll()
	 */
	@Override
	public List<BoardVO> listAll() throws Exception {
		// TODO Auto-generated method stub
		return session.selectList(namespace+".listAll");
	}
	
}
