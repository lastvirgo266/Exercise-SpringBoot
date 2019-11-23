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
		ModelAndView mv = new ModelAndView("/board/boardList"); //Thymeleaf�� ����  .html ���̻縦 ���� �� �� �ִ�.
		
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
			HttpServletResponse response) throws Exception //HttpServletResponse�� ����ڿ��� ������ �����͸� �������
	{
		BoardFileDto boardFile = boardService.selectBoardFileInformation(idx, boardIdx);
		
		if(ObjectUtils.isEmpty(boardFile) == false)
		{
			String fileName = boardFile.getOriginalFileName();
			
			//���⼭ storedFilePath ���� �̿��ؼ� ������ ����Ǿ� �ִ� ������ �о�� �� bytesp[] ���·� ��ȯ��
			byte [] files = FileUtils.readFileToByteArray(new File(boardFile.getStoredFilePath()));
			
			//response�� ����� ������ Ÿ��, ũ�� �� ���� ���� �����Ҽ� ����
			response.setContentType("application/octet-stream"); //������ Ÿ��
			response.setContentLength(files.length); //ũ��
			response.setHeader("Content-Disposition", "attachment; fileName=\""+ URLEncoder.encode(fileName,"UTF-8") + "\";");
			
			response.getOutputStream().write(files);
			response.getOutputStream().flush();
			response.getOutputStream().close();
		}
	}
	

}
