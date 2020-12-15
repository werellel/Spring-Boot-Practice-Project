package sample.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // 해당 클래스가 REST 컨트롤러 기능을 수행하도록 합니다. 
public class HelloController {
	@RequestMapping("/") //  실행할 수 있는 주소를 설정합니다.
	public String hello() { // Hello World!라는 메세지는 화면에 전달해 주는 역할을 합니다.
		return "Hello World!";
	}
}
