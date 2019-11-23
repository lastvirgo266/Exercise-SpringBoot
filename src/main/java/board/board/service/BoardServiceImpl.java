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
//@Transactional //���⿡ ������̼� �ٿ��ָ� Ʈ����� ó�� ����
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
		
		//���ε�� ������ ������ �����ϰ� ������ ������ ������
		List<BoardFileDto> list = fileUtils.parseFileInfo(board.getBoardIdx(), multipartHttpServletRequest);
		
		if(CollectionUtils.isEmpty(list) == false)
		{
			boardMapper.insertBoardFileList(list);
		}
		
		/* File Uploading Test code */
		
//		if(ObjectUtils.isEmpty(multipartHttpServletRequest) == false)
//		{
//			Iterator<String> iterator = multipartHttpServletRequest.getFileNames(); //return ���� �����̸����� �ҷ���
//			String name;
//			
//			while(iterator.hasNext()) //���ͷ����� �������� �����̸����� �ϳ��ϳ� �ҷ���
//			{
//				name = iterator.next();
//				log.debug("file tag name " + name);
//				List<MultipartFile> list = multipartHttpServletRequest.getFiles(name); //���� �±� �̸��� �̿��Ͽ� ���õ� ���� �ε�
//				
//				//�޾ƿ� ���� ������ ���. ���ε�� ������ MultipartFile �������̽��� ǥ����
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
