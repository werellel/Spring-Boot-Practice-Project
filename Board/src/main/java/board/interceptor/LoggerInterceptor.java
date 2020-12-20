package board.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/*
HandlerInterceptorAdapter를 상속받아서 LoggerInterceptor를 구현
 */
@Slf4j
public class LoggerInterceptor extends HandlerInterceptorAdapter{
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	/*
	컨트롤러가 실행되기 전 수행
	*/
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		log.debug("======================================          START         ======================================");
		log.debug(" Request URI \t:  " + request.getRequestURI());
		return super.preHandle(request, response, handler);
	}
	
	/*
	컨트롤러가 정살적으로 실행된 후 수행
	*/	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		log.debug("======================================           END          ======================================\n");
	}
}