package board.board.service;

import board.dto.BoardDto;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	private BoardMapper boardMapper;
	
	@Override
	public List<BoardDto> SelectBoardList() throws Exception{
		return boardMapper.selectBoardList();
	}
}
