package board.board.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import board.dto.BoardDto;
import board.dto.BoardFileDto;

@Mapper
public interface BoardMapper {
	List<BoardDto> selectBoardList() throws Exception;
	void insertBoard(BoardDto board) throws Exception;
	void insertBoardFileList(List<BoardFileDto> list) throws Exception;
	BoardDto selectBoardDetail(int boardIdx) throws Exception;
	List<BoardFileDto> selectBoardFileList(int boardIdx) throws Exception;
	void updateHitCount(int boardIdx);
	void updateBoard(BoardDto dtd);
	void deleteBoard(int boardIdx);
	
	//mybatis는 map을 파라미터로 사용하는 기능을 지원 --> Param 어노테이션을 사용하면 해당 파라미터들이 Map에 저장되어 DTO를 만들지 않더라도
	//여러개의 파라미터를 전달할수있음
	//@Param : 파라미터를 지정하고 키를 저장함(xml에 parameterType = "map"와 같은 형태로 바로 사용가능)
	BoardFileDto selectBoardFileInformation(@Param("idx")int idx, @Param("boardIdx") int boardIdx);
	
	
}
