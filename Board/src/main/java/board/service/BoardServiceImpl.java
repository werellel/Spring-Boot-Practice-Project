/*
BoardServiceImpl 클래스는 BoardService 인터페이스를 사용하여 실제 기능을 구현
*/
package board.service;

import board.common.FileUtils;
import board.dto.BoardDto;
import board.dto.BoardFileDto;
import board.mapper.BoardMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.Iterator;
import java.util.List;

/*
비즈니스 로직을 처리하는 서비스 클래스를 나타내는 어노테이션
MVC의 서비스임을 나타낸다. 
*/
@Service
@Transactional // 인터페이스나 클래스, 메서드에 사용할 수 있습니다. 어노테이션이 적용된 대상은 설정된 트랜잭션 빈에 의해서 트랜잭션 처리된다.
public class BoardServiceImpl implements BoardService{
	/*
	데이터베이스에 접근하는 DAO 빈을 선언합니다. 
	*/	
	@Autowired
	private BoardMapper boardMapper;
	
	@Autowired
	private FileUtils fileUtils;
	
	/*
	사용자 요청을 처리하기 위한 비즈니스 로직을 구 
	*/	
	@Override
	public List<BoardDto> selectBoardList() throws Exception {
		return boardMapper.selectBoardList();
	}
	
	@Override
	public void insertBoard(BoardDto board, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception {
		boardMapper.insertBoard(board);
		List<BoardFileDto> list = fileUtils.parseFileInfo(board.getBoardIdx(), multipartHttpServletRequest);
		if(CollectionUtils.isEmpty(list) == false){
			boardMapper.insertBoardFileList(list);
		}
	}
	
	@Override
	public BoardDto selectBoardDetail(int boardIdx) throws Exception {
		/*
		게시글의 조회수를 증가시킨다. 
		*/		
		boardMapper.updateHitCount(boardIdx);
		/*
		게시글의 내용을 조회한다.
		*/		
		BoardDto board = boardMapper.selectBoardDetail(boardIdx);
		/*
		게시글 번호로 게시글의 첨부파일 목록 조회하고 게시글의 정보를 담고있는 BoardDto 클래스에 조회된 첨부파일 목록을 저장한다..
		*/		
		List<BoardFileDto> fileList = boardMapper.selectBoardFileList(boardIdx);
		board.setFileList(fileList);
		
		return board;
	}
	
	@Override
	public void updateBoard(BoardDto board) throws Exception{
		boardMapper.updateBoard(board);
	}
	
	@Override
	public void deleteBoard(int boardIdx) throws Exception{
		boardMapper.deleteBoard(boardIdx);
	}
	
	@Override
	public BoardFileDto selectBoardFileInformation(int idx, int boardIdx) throws Exception {
		return boardMapper.selectBoardFileInformation(idx, boardIdx);
	}
}
