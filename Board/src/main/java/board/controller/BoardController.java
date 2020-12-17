package board.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import board.dto.BoardDto;
import board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/*
MVC의 컨트롤러를 의미합니다. Controller 어노테이션을 붙여줌으로써 해당 클래스를 컨트롤러로 동작하게 한다.
*/
@Controller
public class BoardController {
	/*
	비즈니스 로직을 처리하는 서비스 빈을 연결.
	*/	
	@Autowired
	private BoardService boardService;
	
	/*
	RequestMapping 어노테이션의 값으로 주소를 지정.
	웹 브라우저에서 /board/openBoardList.do라는 주소를 호출하면 스프링 디스패처는 호출 된 주소와
	@RequestMapping 어노테이션의 값이 동일한 메서드를 찾아서 실행
	즉, 클라이언트에서 호출한 주소와 그것울 수행할 메서드를 연결
	*/	
	@RequestMapping("/board/openBoardList.do")
	public ModelAndView openBoardList() throws Exception{
		/*
		호출된 요청의 결과를 보여 줄 뷰를 지정 
		templates 폴더 아래에 있는 board/boardList.html을 의미
		*/		
		ModelAndView mv = new ModelAndView("/board/boardList");
		
		/*
		게시글 목록을 조회. 게시글 목록을 조회한다는 비즈니스 로직을 수행하기 위해서 BoardService 클래스의
		selectBoardList 메서드를 호출.
		게시글 목록을 저장하기 위해서 List 인터페이스 사용
		*/		
		List<BoardDto> list = boardService.selectBoardList();
		/*
		실행된 비즈니스 로직의 결과 값을 뷰에 list라는 이름으로 저장
		*/		
		mv.addObject("list", list);
		return mv;
	}
	// 게시글 작성 화면을 호출하는 주소	
	@RequestMapping("/board/openBoardWrite.do")
	public String openBoardWrite() throws Exception{
		return "/board/boardWrite";
	}
	// 작성된 게시글을 등록하는 주소 <form>의 action 속성에 지정된 insertBoard.do를 확인할 수 있다.	
	@RequestMapping(value="/board/insertBoard.do")
	public String insertBoard(HttpServletRequest request, BoardDto board) throws Exception{
		board.title = request.getParameter("title");
		board.contents = request.getParameter("contents");
		// 사용자가 작성한 게시글을 저장하는 service 영역의 메서드를 호출		
		boardService.insertBoard(board);
		return "redirect:/board/openBoardList.do";
	}
}
