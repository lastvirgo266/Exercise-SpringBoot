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
	
	//mybatis�� map�� �Ķ���ͷ� ����ϴ� ����� ���� --> Param ������̼��� ����ϸ� �ش� �Ķ���͵��� Map�� ����Ǿ� DTO�� ������ �ʴ���
	//�������� �Ķ���͸� �����Ҽ�����
	//@Param : �Ķ���͸� �����ϰ� Ű�� ������(xml�� parameterType = "map"�� ���� ���·� �ٷ� ��밡��)
	BoardFileDto selectBoardFileInformation(@Param("idx")int idx, @Param("boardIdx") int boardIdx);
	
	
}
