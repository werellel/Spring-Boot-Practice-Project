/*
BoardService 인터페이스에는 비즈니스 로직을 수행하기 위한 메서드를 정의
*/
package board.service;

import board.dto.BoardDto;
import board.dto.BoardFileDto;

import java.util.List;

import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface BoardService {
	List<BoardDto> selectBoardList() throws Exception;
	
	void insertBoard(BoardDto board, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception;
	
	BoardDto selectBoardDetail(int boardIdx) throws Exception;
	
	void updateBoard(BoardDto board) throws Exception;
	
	void deleteBoard(int boardIdx) throws Exception;
	
	BoardFileDto selectBoardFileInformation(int idx, int boardIdx) throws Exception;
}
