/*
BoardService 인터페이스에는 비즈니스 로직을 수행하기 위한 메서드를 정의
*/
package board.service;

import board.dto.BoardDto;
import java.util.List;

public interface BoardService {
	List<BoardDto> selectBoardList() throws Exception;
	
	void insertBoard(BoardDto board) throws Exception;
}
