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

import board.dto.BoardFileDto;
import board.entity.BoardFileEntity;

/*
Component 어노테이션을 이용해서 FileUtils 클래스를 스프링의 빈으로 등록 
 */
@Component
public class FileUtils {
	
	public List<BoardFileDto> parseFileInfo(int boardIdx, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception{
		if(ObjectUtils.isEmpty(multipartHttpServletRequest)){
			return null;
		}
		/*
		파일이 업로드될 폴더를 생성, 파일이 업로드될 때마다 images 폴더 밑에 yyyyMMdd 형식으로 폴더를 생성 
		*/		
		List<BoardFileDto> fileList = new ArrayList<>();
		
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd"); 
    	ZonedDateTime current = ZonedDateTime.now();
    	String path = "images/"+current.format(format);
    	File file = new File(path);
		if(file.exists() == false){
			file.mkdirs();
		}
		
		Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
		
		String newFileName, originalFileExtension, contentType;
		
		while(iterator.hasNext()){
			List<MultipartFile> list = multipartHttpServletRequest.getFiles(iterator.next());
			for (MultipartFile multipartFile : list){
				/*
				파일 형식을 확인하고 그에 따라 이미지의 확장자를 지정
				파일 확장자를 파일 이름에서 가져오는 방식을 간혹 블로그 글에서 볼 수 있는데, 이렇게 확장자를 확인하는 방식은 
				매우 위험하다. 왜냐하면 확장자는 쉽게 바꿀 수 있기 때문에 실제 파일의 형식과 확장자가 다를 수 있고, 파일의 위변조
				를 확인할 수 없기 때문이다. 실제로 개발을 할 떄에는 JDK1.7 이상에서 지원되는 nio 패키지를 이용하거나 아파치 티카
				와 같은 라이브러리를 이용하는 등의 방법으로 파일 형식을 확인한다. 
				*/				
				if(multipartFile.isEmpty() == false){
					contentType = multipartFile.getContentType();
					if(ObjectUtils.isEmpty(contentType)){
						break;
					}
					else{
						if(contentType.contains("image/jpeg")) {
							originalFileExtension = ".jpg";
						}
						else if(contentType.contains("image/png")) {
							originalFileExtension = ".png";
						}
						else if(contentType.contains("image/gif")) {
							originalFileExtension = ".gif";
						}
						else{
							break;
						}
					}
					/*
					서버에 저장될 파일 이름을 생성. 서버에 같은 이릉의 파일이 있다면 업로드된 파일이 정상적으로 저장되지 
					않기 때문에 절대 중복되지 않을 이름으로 바꿔준다. 파일이 업로드된 나노초를 이용해서 새로운 파일 이름으로 지정
					밀리초는 중복될 수 있다 
					*/					
					newFileName = Long.toString(System.nanoTime()) + originalFileExtension;
					/*
					데이터베이스에 저장할 파일 정보를 앞에서 만든 BoardFileDto에 저장.
					업로드된 파일을 추후 화면에 표시하기 위해서 파일의 원래 이름, 파일의 크기, 파일이 저장된 경로 저장.
					파일이 어떤 게시글에 속해 있는지 알 수 있도록 게시글 번호도 같이 저장
					 */
					BoardFileDto boardFile = new BoardFileDto();
					boardFile.setBoardIdx(boardIdx);
					boardFile.setFileSize(multipartFile.getSize());
					boardFile.setOriginalFileName(multipartFile.getOriginalFilename());
					boardFile.setStoredFilePath(path + "/" + newFileName);
					fileList.add(boardFile);
					/*
					업로드된 파일을 새로운 이름으로 바꾸어 지정된 경로에 저장  
					*/					
					file = new File(path + "/" + newFileName);
					multipartFile.transferTo(file);
				}
			}
		}
		return fileList;
	}
	
	public List<BoardFileEntity> parseFileInfo(MultipartHttpServletRequest multipartHttpServletRequest) throws Exception {
		
		if(ObjectUtils.isEmpty(multipartHttpServletRequest)) {
			return null;
		}
		
		List<BoardFileEntity> fileList = new ArrayList<>();
		
		// 파일 저장 경로 설정 
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd");
		ZonedDateTime current = ZonedDateTime.now(); // 오늘날짜를 가져옵니다. 
		String path = "images/"+current.format(format);
		File file = new File(path);
		if(file.exists() == false) {
			file.mkdirs();
		}
		
		Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
		String newFileName, originalFileExtension, contentType;
		while(iterator.hasNext()) {
			List<MultipartFile> list = multipartHttpServletRequest.getFiles(iterator.next());
			for(MultipartFile multipartFile : list) {
				if(multipartFile.isEmpty() == false) {
					// 파일 확장자 체크는 contentType으로 해야한다. 파일명에서 가져오면 위변조 할수 있기때문이다.
					contentType = multipartFile.getContentType();
					if(ObjectUtils.isEmpty(contentType)) {
						break;
					} else {
						if(contentType.contains("image/jpeg")) {
							originalFileExtension = ".jpg";
						}else if(contentType.contains("image/png")) {
							originalFileExtension = ".png";
						}else if(contentType.contains("image/gif")) {
							originalFileExtension = ".gif";
						}else {
							break;
						}
					}
					
					// 파일이름은 중복되지 않게 나노타임을 사용했다.
					newFileName = Long.toString(System.nanoTime()) + originalFileExtension;
					BoardFileEntity boardFile = new BoardFileEntity();
					boardFile.setFileSize(multipartFile.getSize());
					boardFile.setOriginalFileName(multipartFile.getOriginalFilename());
					boardFile.setStoredFilePath(path + "/" + newFileName);
					boardFile.setCreatorId("admin");
					fileList.add(boardFile);
					
					// 새로운 이름을 변경된 파일을 저장한다.
					file = new File(path + "/" + newFileName);
					multipartFile.transferTo(file);
				}
			}
			
		}
		
		
		return fileList;
	}
}