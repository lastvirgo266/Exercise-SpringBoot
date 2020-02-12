package board.common;

import java.io.File;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import board.board.entity.BoardFileEntity;
import board.dto.BoardFileDto;

//Component 어노테이션을 이용해서 FileUtils 클래스를 스프링의 빈으로 등록
@Component
public class FileUtils {
	
	public List<BoardFileEntity> parseFileInfo(MultipartHttpServletRequest multipartHttpServletRequest) throws Exception
	{
		if(ObjectUtils.isEmpty(multipartHttpServletRequest))
		{
			return null;
		}
		
		
		List<BoardFileEntity> fileList = new ArrayList<>();
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd");
		ZonedDateTime current = ZonedDateTime.now(); //오늘의 날짜를 확인하기 위해 사용(JDK 1.8 부터 사용가능)
		String path = "images/"+current.format(format);
		File file = new File(path);
		
		if(file.exists() == false)
		{
			file.mkdirs();
		}
		
		Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
		
		String newFileName, originalFileExtension, contentType;
		
		
		while(iterator.hasNext())
		{
			List<MultipartFile> list = multipartHttpServletRequest.getFiles(iterator.next());
			
			for(MultipartFile multipartFile : list)
			{
				if(multipartFile.isEmpty() == false)
				{
					//파일 형식을 확인하고 그에 따라 이미지의 확장자를 지정함.
					/* 파일의 이름에서 확장자를 가져오는 방식은 매우위험함 
					 * --> 확장자는 쉽게 바꿀수 있기 때문에 실제 파일의 형식과 확장자가 다를 수 있음
					 * --> 실제로 개발할때에는 JDK 1.7이상 지원되는 nio 패키지를 이용하거나
					 * 아파치 티카와 같은 라이브러리를 이용하는 등의 방법으로 파일 형식을 확인해야함 */
					contentType = multipartFile.getContentType();
					if(ObjectUtils.isEmpty(contentType))
					{
						break;
					}
					
					else
					{
						if(contentType.contains("image/jpeg"))
						{
							originalFileExtension = ".jpg";
						}
						
						else if(contentType.contains("image/png"))
						{
							originalFileExtension = ".png";
						}
						
						else if(contentType.contains("image/gif"))
						{
							originalFileExtension = ".gif";
						}
						
						else
						{
							break;
						}
						
						
					}
					
					
					newFileName = Long.toString(System.nanoTime()) + originalFileExtension; //서버에 저장될 파일 이름을 생성, 서버에 같은 이름의 파일이 있으면 절대 중복되지 않을 이름으로 바꿔줌
					
					//데이터베이스에 저장할 파일 정보를 앞에서 만든 BoardFileDto에 저장함
					BoardFileEntity boardFile = new BoardFileEntity();
					//boardFile.setBoardIdx(boardIdx); // 게시글 번호
					boardFile.setFileSize(multipartFile.getSize()); //파일의 크기
					boardFile.setOriginalFileName(multipartFile.getOriginalFilename()); //파일의 원래 이름
					boardFile.setStoredFilePath(path + "/" + newFileName); //파일이 저장된 이름
					boardFile.setCreatorId("admin");
					fileList.add(boardFile);
					
					//업로드된 파일을 새로운 이름으로 바꾸어 지정된 경로에 저장
					file = new File(path + "/" + newFileName);
					multipartFile.transferTo(file);
					
					
				}//END if
				
				
			}//END for
			
			
		}//END While
		
		
		return fileList;
		
	}

}
