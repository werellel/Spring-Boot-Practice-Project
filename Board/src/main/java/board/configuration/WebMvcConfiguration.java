package board.configuration;

import java.nio.charset.Charset;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

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

	@Bean
	public CharacterEncodingFilter characterEncodingFilter() {
		/*
		CharacterEncodingFilter은 스프링이 제공하는 클래스로 웹에서 주고받는 데이터의 헤더값을 UTF-8로 인코딩해 준다.
		*/	
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		/*
		 기본값은 false이다. 기본값으로 설정되어 있으면 HttpServletRequest의 getCharacterEncoding 메서드의
		 반환값이 null이 아닌 경우에만 인코딩을 변경하지 않는다. null이 아니라는 것은 특정한 인코딩이 설정되어 있다는 의미다.
		 즉, 특정한 인코딩이 지정되지 않을 때만 인코딩을 변경한다.
		 반대로 null인 경우에는 설정된 인코딩으로 변경한다.  
		 forceEncoding이 true이면
		 입력값(HttpServletRequest)과 결과값(HttpServeltResponse)
		 이 강제적으로 설정된 인코딩으로 변경한다. 즉 HttpServletRequest에 특정한 인코딩이 설정되어
		 있는 경우에는 인코딩을 변경하지 않고, 설정되어 있지 않은 경우에만 지정한 인코딩으로 변경한다.
		*/
		characterEncodingFilter.setForceEncoding(true);
		
		return characterEncodingFilter;
	}
	
	@Bean
	public HttpMessageConverter<String> responseBodyConverter(){
		return new StringHttpMessageConverter(Charset.forName("UTF-8"));
	}
	
	@Bean
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
		commonsMultipartResolver.setDefaultEncoding("UTF-8"); // 파일의 인코딩을 UTF-8로 설정
		commonsMultipartResolver.setMaxUploadSizePerFile(5*1024*1024); // 업로드되는 파일의 크기 제한(바이트 단위 설정 가능) 5mb로 제한
		return commonsMultipartResolver;
	}
}
