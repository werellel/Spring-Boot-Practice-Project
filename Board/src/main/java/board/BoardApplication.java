package board;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

@EnableJpaAuditing
/*
EntityScan 어노테이션은 애플리케이션이 실행될 때 
basePackages로 지정된 패키지 하위에서 JPA 엔티티(Entit 어노테이션이 설정된 도메인 클래스들)를
검색한다. 여기에 Jsr310JpaConveters 클래스를 등록하면 된다.   
*/
@EntityScan(
		basePackageClasses = {Jsr310JpaConverters.class},
		basePackages = {"board"})
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
