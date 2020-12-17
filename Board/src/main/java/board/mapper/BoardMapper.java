package board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import board.dto.BoardDto;

// 마이바티스 매퍼 인터페이스임을 선언
@Mapper
public interface BoardMapper {
	/*
	인터페이스이기 때문에 메서드의 이름과 반환 형식만 지정
	여기서 지정한 메서드의 이름은 잠시 후 나올 SQL의 이름과 동일해야 한다.
	*/	
	List<BoardDto> selectBoardList() throws Exception;
	
	void insertBoard(BoardDto board) throws Exception;
}
