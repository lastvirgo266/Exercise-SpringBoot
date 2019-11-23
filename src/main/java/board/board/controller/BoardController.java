package board.board.controller;

import java.io.File;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import board.board.service.BoardService;
import org.apache.commons.io.FileUtils;
import board.dto.BoardDto;
import board.dto.BoardFileDto;

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
	
	@RequestMapping("/board/downloadBoardFile.do")
	public void downloadBoardFile(@RequestParam int idx, @RequestParam int boardIdx,
			HttpServletResponse response) throws Exception //HttpServletResponse는 사용자에게 전달할 데이터를 담고있음
	{
		BoardFileDto boardFile = boardService.selectBoardFileInformation(idx, boardIdx);
		
		if(ObjectUtils.isEmpty(boardFile) == false)
		{
			String fileName = boardFile.getOriginalFileName();
			
			//여기서 storedFilePath 값을 이용해서 실제로 저장되어 있는 파일을 읽어온 후 bytesp[] 형태로 변환함
			byte [] files = FileUtils.readFileToByteArray(new File(boardFile.getStoredFilePath()));
			
			//response의 헤더에 콘텐츠 타입, 크기 및 형태 등을 설정할수 있음
			response.setContentType("application/octet-stream"); //콘텐츠 타입
			response.setContentLength(files.length); //크기
			response.setHeader("Content-Disposition", "attachment; fileName=\""+ URLEncoder.encode(fileName,"UTF-8") + "\";");
			
			response.getOutputStream().write(files);
			response.getOutputStream().flush();
			response.getOutputStream().close();
		}
	}
	

}
