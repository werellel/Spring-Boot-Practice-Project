package board.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import board.interceptor.LoggerInterceptor;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer{
	
	@Override
	public void addInterceptors(InterceptorRegistry registry){
		/*
		인터셉터를 등록. addPathPattern 메서드와 excludePathPatterns 메서드를 이용하여 요청 주소의 패턴과
		제외할 요청 주소의 패턴을 지정하여 선택적으로 적용할 수 있다.
		*/		
		registry.addInterceptor(new LoggerInterceptor());
	}
}
