package board.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import board.entity.BoardEntity;
import board.entity.BoardFileEntity;
import board.repository.JpaBoardRepository;
import board.common.FileUtils;

@Service
public class JpaBoardServiceImpl implements JpaBoardService{
	
	@Autowired
	JpaBoardRepository jpaBoardRepository;
	
	@Autowired
	FileUtils fileUtils;

	@Override
	public List<BoardEntity> selectBoardList() throws Exception {
		/*
		게시글 번호로 정렬해서 전체 게시글 목록을 조회한다.
		*/		
		return jpaBoardRepository.findAllByOrderByBoardIdxDesc();
	}

	@Override
	public void saveBoard(BoardEntity board, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception {
		board.setCreatorId("admin");
		/*
		첨부파일의 정보를 저장하는 클래스가 BoardFileDto	클래스에서 BoardFileEntity
		클래스로 변경되었기 때문에, FileUtils 클래스의 parseFileInfo 메서드를 새로 만들었다.
		*/		
		List<BoardFileEntity> list = fileUtils.parseFileInfo(multipartHttpServletRequest);
		if(CollectionUtils.isEmpty(list) == false){
			/*
			첨부파일 목록을 BoardFileEntity 클래스에 추가한다.
			앞에서는 첨부파일 정보를 저장하는 쿼리를 따로 실행했지만 여기서는 게시글을 저장할 때 그 게시글에
			포함된 첨부파일의 목록도 자동으로 저장된다.
			BoardEntity 클래스에는 첨부파일 목록이 @OneToMany 어노테이션으로 연관 관계가 있기 떄문이다.
			*/			
			board.setFileList(list);
		}
		jpaBoardRepository.save(board);
	}
	
	@Override
	public BoardEntity selectBoardDetail(int boardIdx) throws Exception{
		/*
		자바 애플리케이션을 개발하면서 가장 많이 볼 수 있는 예외가 NullPointException이다.
		Optional 클래스는 어떤 객체의 값으로 Null이 아닌 값을 가지고 있다. 즉 Optional 클래스는
		절대로 Null이 아니기 때문에 NullPointException이 발생하지 않는다. 
		만일 해당하는 BoardIdx가 없다면 적절한 예외처리를 해야한다.  
		*/		
		Optional<BoardEntity> optional = jpaBoardRepository.findById(boardIdx);
		if(optional.isPresent()){
			BoardEntity board = optional.get();
			board.setHitCnt(board.getHitCnt() + 1);
			jpaBoardRepository.save(board);
			
			return board;
		}
		else {
			throw new NullPointerException();
		}
	}

	@Override
	public void deleteBoard(int boardIdx) {
		// 주어진 id를 가진 엔티티를 삭제한다. 		
		jpaBoardRepository.deleteById(boardIdx);
	}

	@Override
	public BoardFileEntity selectBoardFileInformation(int boardIdx, int idx) throws Exception {
		BoardFileEntity boardFile = jpaBoardRepository.findBoardFile(boardIdx, idx);
		return boardFile;
	}
}