package board.board.service;

import board.board.mapper.BoardMapper;
import board.common.FileUtils;
import board.dto.BoardDto;
import board.dto.BoardFileDto;

import java.util.Iterator;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Slf4j
@Service
//@Transactional //여기에 어노테이션 붙여주면 트랜잭션 처리 가능
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	private BoardMapper boardMapper;
	
	@Autowired
	private FileUtils fileUtils;
	
	
	@Override
	public List<BoardDto> selectBoardList() throws Exception{
		return boardMapper.selectBoardList();
	}
	
	
	@Override
	public void insertBoard(BoardDto board, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception
	{
		boardMapper.insertBoard(board);
		
		//업로드된 파일을 서버에 저장하고 파일의 정보를 가져옴
		List<BoardFileDto> list = fileUtils.parseFileInfo(board.getBoardIdx(), multipartHttpServletRequest);
		
		if(CollectionUtils.isEmpty(list) == false)
		{
			boardMapper.insertBoardFileList(list);
		}
		
		/* File Uploading Test code */
		
//		if(ObjectUtils.isEmpty(multipartHttpServletRequest) == false)
//		{
//			Iterator<String> iterator = multipartHttpServletRequest.getFileNames(); //return 받은 파일이름들을 불러옴
//			String name;
//			
//			while(iterator.hasNext()) //이터레이터 형식으로 파일이름들을 하나하나 불러옴
//			{
//				name = iterator.next();
//				log.debug("file tag name " + name);
//				List<MultipartFile> list = multipartHttpServletRequest.getFiles(name); //파일 태그 이름을 이용하여 선택된 파일 로드
//				
//				//받아온 파일 정보를 출력. 업로드된 파일은 MultipartFile 인터페이스로 표현됨
//				for(MultipartFile multipartFile : list)
//				{
//					log.debug("start file information");
//					log.debug("file name : " + multipartFile.getOriginalFilename());
//					log.debug("file size : " + multipartFile.getSize());
//					log.debug("fiel content type : " + multipartFile.getContentType());
//					log.debug("end file information.\n");
//				}
//			}
//		
//		
//		}
	}
	
	@Override
	public BoardDto selectBoardDetail(int boardIdx) throws Exception
	{
		BoardDto board = boardMapper.selectBoardDetail(boardIdx);
		List<BoardFileDto> fileList = boardMapper.selectBoardFileList(boardIdx);
		board.setFileList(fileList);
		
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
	
	@Override
	public BoardFileDto selectBoardFileInformation(int idx, int boardIdx)
	{
		return boardMapper.selectBoardFileInformation(idx, boardIdx);
	}
	

}
