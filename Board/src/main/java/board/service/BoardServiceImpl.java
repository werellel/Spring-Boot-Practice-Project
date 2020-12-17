/*
BoardServiceImpl 클래스는 BoardService 인터페이스를 사용하여 실제 기능을 구현
*/
package board.service;

import board.dto.BoardDto;
import board.mapper.BoardMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*
비즈니스 로직을 처리하는 서비스 클래스를 나타내는 어노테이션
MVC의 서비스임을 나타낸다. 
*/
@Service
public class BoardServiceImpl implements BoardService{
	/*
	데이터베이스에 접근하는 DAO 빈을 선언합니다. 
	*/	
	@Autowired
	private BoardMapper boardMapper;
	
	/*
	사용자 요청을 처리하기 위한 비즈니스 로직을 구 
	*/	
	@Override
	public List<BoardDto> selectBoardList() throws Exception {
		return boardMapper.selectBoardList();
	}
	
	@Override
	public void insertBoard(BoardDto board) throws Exception {
		System.out.println("board.title: " + board.title);
		boardMapper.insertBoard(board);
	}
	
	@Override
	public BoardDto selectBoardDetail(int boardIdx) throws Exception {
		boardMapper.updateHitCount(boardIdx);
		
		BoardDto board = boardMapper.selectBoardDetail(boardIdx);
		
		return board;
	}
}
