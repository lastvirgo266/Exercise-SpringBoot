package board.board.service;


import board.dto.BoardDto;
import java.util.List;

public interface BoardService {
	List<BoardDto> selectBoardList() throws Exception;
}
