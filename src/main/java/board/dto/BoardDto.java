package board.dto;

import java.util.List;

import lombok.Data;

@Data
public class BoardDto {
	private List<BoardFileDto> fileList;
	
	private int boardIdx;
	private String title;
	private String contents;
	private int hitCnt;
	private String creatorId;
	private String createdDatetime;
	private String updaterId;
	private String updatedDatetime;

}
