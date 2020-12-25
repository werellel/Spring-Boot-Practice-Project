package board.dto;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import lombok.Data;

/*
롬복 어노테이션으로 모든 필드의 getter와 setter를 생성하고 toString, hashCode, equals 메서드도 생성
단, setter의 경우 final이 선언되지 않은 필드에만 적용
*/
@Data
public class BoardDto {
	/*
	데이터베이스의 게시판 테이블 컬럼과 매칭.
	일반적으로 자바는 카멜표기법을 사용하지만 데이터베이스는 _를 사용하는 스네이크표기법을 사용.
	따라서 표기법과 관련된 설정도 필요.
	카멜 표기법: 각 단어의 첫 글자만 대문자로 표기해 낙타 등 처럼 보이기에 이러한 명칭이 붙음
	자바는 쿨래스 이름은 대문자로 시작하고 변수나 메서드 이름은 클래스의 이름과 구분하기 위해서 소문자로 시작
	*/	
	public int boardIdx;
	public String title;
	public String contents;
	public int hitCnt;
	public String creatorId;
	public String createdDatetime;
	public String updaterId;
	public String updatedDatetime;
	public List<BoardFileDto> fileList;
	
}
