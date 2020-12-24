package board;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;

/*
exclude를 이용하여 특정한 자동구성을 사용하지 않도록 할 수 있다.
MultipartAutoConfiguration 클래스를 자동구성하지 않도록 설정하면 파일 관련 설정 완료
*/
@SpringBootApplication(exclude= {MultipartAutoConfiguration.class})
public class BoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoardApplication.class, args);
	}

}
