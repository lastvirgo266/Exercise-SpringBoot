package board.board.controller;

import java.io.File;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import board.dto.BoardDto;
import board.dto.BoardFileDto;
import board.board.service.BoardService;


@Controller
public class RestBoardController {

	@Autowired
	private BoardService boardService;
	
	/*  RESTful 에서는 value로 주소를 지정하고 method속성으로 요청방식을 반드시 지정해 주어야한다.
	 *  이러한 방식은 따로 어노테이션이 있는데
	 *  @GetMapping, @PostMapping, @PutMapping, @DeleteMapping 어노테이션을 사용할 수 도있다. */
	
	@RequestMapping(value="/board", method=RequestMethod.GET)
	public ModelAndView openBoardList() throws Exception{
		ModelAndView mv = new ModelAndView("/board/restBoardList");
		
		List<BoardDto> list = boardService.selectBoardList();
		mv.addObject("list", list);
		
		return mv;
	}
	
	@RequestMapping(value="/board/write", method =RequestMethod.GET)
	public String openBoardWrite() throws Exception{
		return "/board/restBoardWrite";
	}
	
	
	@RequestMapping(value="/board/write", method=RequestMethod.POST)
	public String insertBoard(BoardDto board, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception{
		boardService.insertBoard(board, multipartHttpServletRequest);
		return "redirect:/board";
	}
	
	@RequestMapping(value="/board/{boardIdx}", method=RequestMethod.GET)
	public ModelAndView openBoardDetail(@PathVariable("boardIdx") int boardIdx) throws Exception{
		ModelAndView mv = new ModelAndView("/board/restBoardDetail");
		
		BoardDto board = boardService.selectBoardDetail(boardIdx);
		mv.addObject("board", board);
		
		return mv;
	}
	
	@RequestMapping(value="/board/{boardIdx}", method= RequestMethod.PUT)
	public String updateBoard(BoardDto board) throws Exception{
		boardService.updateBoard(board);
		return "redirect:/board";
	}
	
	@RequestMapping(value="/board/{boardIdx}", method= RequestMethod.DELETE)
	public String deleteBoard(@PathVariable("boardIdx") int boardIdx) throws Exception{
		boardService.deleteBoard(boardIdx);
		return "redirect:/board";
	}
	
	@RequestMapping(value="/board/life", method=RequestMethod.GET)
	public void downloadBoardFile(@RequestParam int idx, @RequestParam int boardIdx, HttpServletResponse response) throws Exception{
		BoardFileDto boardFile = boardService.selectBoardFileInformation(idx, boardIdx);
		
		if(ObjectUtils.isEmpty(boardFile) == false) {
			String fileName = boardFile.getOriginalFileName();
			
			byte[] files = FileUtils.readFileToByteArray(new File(boardFile.getStoredFilePath()));
			
			response.setContentType("application/octet-stream");
			response.setContentLength(files.length); //크기
			response.setHeader("Content-Disposition", "attachment; fileName=\""+ URLEncoder.encode(fileName,"UTF-8") + "\";");
			
			response.getOutputStream().write(files);
			response.getOutputStream().flush();
			response.getOutputStream().close();
		}
	}
	
}
