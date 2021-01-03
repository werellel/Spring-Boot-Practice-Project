package board.entity;

import java.time.LocalDateTime;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

/*
Entity 어노테이션은 해당 클래스가 JPA 엔티티임을 나타낸다. 엔티티 클래스는 테이블과 매핑된다. 
*/
@Entity
/*
t_jpa_board 테이블과 매핑되도록 나타낸다. 
*/
@Table(name="t_jpa_board")
@NoArgsConstructor
@Data
public class BoardEntity {
	/*
	Id는 엔티티의 기본키임을 나타낸다.
	*/	
	@Id
	/*
	기본키의 생성 전략을 설정한다. AUTO로 설정할 경우 데이터베이스에서 제공하는 기본키 생성 전략
	을 따르게 된다. 
	*/	
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int boardIdx;
	/*
	컬럼에 not null 속성을 지정한다. 
	*/	
	@Column(nullable=false)
	private String title;
	
	@Column(nullable=false)
	private String contents;
	
	@Column(nullable=false)
	private int  hitCnt = 0;
	
	@Column(nullable=false)
	private String creatorId;
	/*
	작성시간의 초깃값을 설정한다. Column 어노테이션을 이용해서 초깃값을 지정할 수도 있지만
	사용하는 데이터베이스에 따라서 초깃값을 다르게 설정해햐 할 수도 있다. 
	*/	
	@Column(nullable=false)
	private LocalDateTime createdDatetime = LocalDateTime.now();
	
	private String updatorId;
	
	private LocalDateTime updateDatetime;
	/*
	하나의 게시글은 기본적으로 첨부파일이 없거나 1개 이사의 첨부파일을 가질수 있다.
	1:N 관게를 표현하는 JPA 어노테이션이다.  
	*/	
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	// 릴레이션과 관계가 있는 테이블의 컬럼을 지정한다.	
	@JoinColumn(name="board_idx")
	private Collection<BoardFileEntity> fileList;
	
}
