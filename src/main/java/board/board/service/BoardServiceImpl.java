package board.board.service;

import board.board.mapper.BoardMapper;
import board.dto.BoardDto;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	private BoardMapper boardMapper;
	
	@Override
	public List<BoardDto> selectBoardList() throws Exception{
		return boardMapper.selectBoardList();
	}
	
	@Override
	public void insertBoard(BoardDto board) throws Exception
	{
		boardMapper.insertBoard(board);
	}
	
	@Override
	public BoardDto selectBoardDetail(int boardIdx) throws Exception
	{
		BoardDto board = boardMapper.selectBoardDetail(boardIdx);
		return board;
	}
}