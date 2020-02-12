package board.board.entity;

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


@Entity
@Table(name="t_jpa_board")
@NoArgsConstructor
@Data
public class BoardEntity {
	@Id //PK임을 나타냄
	@GeneratedValue(strategy=GenerationType.AUTO)//기본키의 생성전략을 설정(DB에서 제공하는 기본키 생성전략을 따르게됨)
	private int boardIdx;
	
	@Column(nullable=false)
	private String title;
	
	@Column(nullable=false)
	private String contents;
	
	@Column(nullable = false)
	private int hitCnt = 0;
	
	@Column(nullable = false)
	private String creatorId;
	
	@Column(nullable = false)
	private LocalDateTime createdDatetime = LocalDateTime.now();
	
	private String updateId;
	
	private LocalDateTime updatedDatetime;
	
	//하나의 게시물은 기본적으로 첨부파일이 없거나 1개 이상의 첨부파일을 가질 수 있음
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name="board_idx")
	private Collection<BoardFileEntity> fileList;
}
