package board.board.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import board.board.service.BoardService;
import board.dto.BoardDto;

@Controller
public class BoardController {
	
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private BoardService boardService;
	
	@RequestMapping("/board/openBoardList.do")
	public ModelAndView openBoardList() throws Exception{
		log.debug("openBoardList");
		ModelAndView mv = new ModelAndView("/board/boardList"); //Thymeleaf에 의해  .html 접미사를 생략 할 수 있다.
		
		List<BoardDto> list = boardService.selectBoardList();
		mv.addObject("list", list);
		
		return mv;
	}
	
	@RequestMapping("/board/openBoardWrite.do")
	public String openBoadWrite() throws Exception
	{
		return "/board/boardWrite";
	}
	
	@RequestMapping("/board/insertBoard.do")
	public String insertBoard(BoardDto board, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception
	{
		boardService.insertBoard(board, multipartHttpServletRequest);
		//Test
		//System.out.println(board.getContents());
		return "redirect:/board/openBoardList.do";
	}
	
	@RequestMapping("/board/openBoardDetail.do")
	public ModelAndView openBoardDetail(@RequestParam int boardIdx) throws Exception
	{
		ModelAndView mv = new ModelAndView("/board/boardDetail");
		//Test
		BoardDto board = boardService.selectBoardDetail(boardIdx);
		mv.addObject("board", board);
		
		return mv;
	}
	
	@RequestMapping("/board/updateBoard.do")
	public String updateBoard(BoardDto board) throws Exception
	{
		boardService.updateBoard(board);
		return "redirect:/board/openBoardList.do";
	}
	
	@RequestMapping("/board/deleteBoard.do")
	public String deleteBoard(int boardIdx) throws Exception
	{
		boardService.deleteBoard(boardIdx);
		return "redirect:/board/openBoardList.do";
	}
	

}
