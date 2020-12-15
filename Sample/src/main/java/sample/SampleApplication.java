package sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // 스프링 부트 애플리케이션의 핵심 어노테이션이라해도 무방할 정도로 다양한 기능을 한다.
public class SampleApplication {
	/* 톰캣이나 제티와 같은 웹 애플리케이션 서버에 배포하지 않고도 실행할 수 있는
	 JAR파일로 웹 애플리케이션을 개발할 수 있다. main 메서드는 스프링 부트의 SampleApplication.run()
	 메서드를 사용하여 스프링 부트 애플리케이션을 실행할 수 있게 한다. 스프링 부트 프로젝트를 생성하면서
	 단 하나의 XML 설정 파일도 사용하지 않았다. 심지어 웹 애플리케이션에는 꼭 있어야만 했던 web.xml
	 도 사용하지 않았다. 이렇게 스프링 부트로 생성된 애플리케이션은 순수 자바만을 이용해서 개발을 할 수 있도록 한다. 
	*/
	public static void main(String[] args) {
		SpringApplication.run(SampleApplication.class, args);
	}

}
