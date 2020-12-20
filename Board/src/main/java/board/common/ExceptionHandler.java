package board.common;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

/* ControllerAdvice 어노테이션을 사용해 해당 클래스가 예외처리 클래스임을 알려준다.*/
@ControllerAdvice
public class ExceptionHandler {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	/*
	해당 메서드에서 처리할 예외를 지정한다. 여기서는 기능을 확인하기 위해 간단히 모든 예외를 처리했다.
	실무에서는 다양한 예외를 처리하기 위한 각각의 예외에 맞는 적절한 예외처리가 필요하다. 절대로 이렇게 사용하지 않는다.  
	 */	
	@org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
	public ModelAndView defaultExceptionHandler(HttpServletRequest request, Exception exception) {
		// 얘외 발생 시 보여 줄 화면을 지정한다. 애플리케이션이 실행되는 중 에러가 발생하면 그 에러에 따라 적절한 예외처리 화면을 사용자에게 보여준다.		
		ModelAndView mv = new ModelAndView("/error/error_default");
		mv.addObject("exception", exception);
		
		// 에러 로그를 출력한다.		
		log.error("exception", exception);
		
		return mv;
	}
}
