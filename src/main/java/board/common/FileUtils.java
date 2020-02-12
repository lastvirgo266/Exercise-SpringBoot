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

//Component ������̼��� �̿��ؼ� FileUtils Ŭ������ �������� ������ ���
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
		ZonedDateTime current = ZonedDateTime.now(); //������ ��¥�� Ȯ���ϱ� ���� ���(JDK 1.8 ���� ��밡��)
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
					//���� ������ Ȯ���ϰ� �׿� ���� �̹����� Ȯ���ڸ� ������.
					/* ������ �̸����� Ȯ���ڸ� �������� ����� �ſ������� 
					 * --> Ȯ���ڴ� ���� �ٲܼ� �ֱ� ������ ���� ������ ���İ� Ȯ���ڰ� �ٸ� �� ����
					 * --> ������ �����Ҷ����� JDK 1.7�̻� �����Ǵ� nio ��Ű���� �̿��ϰų�
					 * ����ġ Ƽī�� ���� ���̺귯���� �̿��ϴ� ���� ������� ���� ������ Ȯ���ؾ��� */
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
					
					
					newFileName = Long.toString(System.nanoTime()) + originalFileExtension; //������ ����� ���� �̸��� ����, ������ ���� �̸��� ������ ������ ���� �ߺ����� ���� �̸����� �ٲ���
					
					//�����ͺ��̽��� ������ ���� ������ �տ��� ���� BoardFileDto�� ������
					BoardFileEntity boardFile = new BoardFileEntity();
					//boardFile.setBoardIdx(boardIdx); // �Խñ� ��ȣ
					boardFile.setFileSize(multipartFile.getSize()); //������ ũ��
					boardFile.setOriginalFileName(multipartFile.getOriginalFilename()); //������ ���� �̸�
					boardFile.setStoredFilePath(path + "/" + newFileName); //������ ����� �̸�
					boardFile.setCreatorId("admin");
					fileList.add(boardFile);
					
					//���ε�� ������ ���ο� �̸����� �ٲپ� ������ ��ο� ����
					file = new File(path + "/" + newFileName);
					multipartFile.transferTo(file);
					
					
				}//END if
				
				
			}//END for
			
			
		}//END While
		
		
		return fileList;
		
	}

}
