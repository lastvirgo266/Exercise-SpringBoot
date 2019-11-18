package board.board.service;

import board.board.mapper.BoardMapper;
import board.dto.BoardDto;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
//@Transactional //여기에 어노테이션 붙여주면 트랜잭션 처리 가능
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
		boardMapper.updateHitCount(boardIdx);
		//For test code
		//int i = 10 / 0;
		return board;
	}
	
	
	@Override
	public void updateBoard(BoardDto board) throws Exception
	{
		boardMapper.updateBoard(board);
	}
	
	
	@Override
	public void deleteBoard(int boardIdx) throws Exception
	{
		boardMapper.deleteBoard(boardIdx);
	}
}
