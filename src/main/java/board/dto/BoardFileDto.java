package board.dto;

import lombok.Data;


//파일 업로딩을위한 DTO
@Data
public class BoardFileDto {
	
	private int idx;
	private int boardIdx;
	private String originalFileName;
	private String storeFilePath;
	private long fileSize;

}
