package com.neighbus;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.neighbus.gallery.GalleryDTO;

public class Util {


	//파일 재입력 메소드
	public static int saveFileToDirectory(GalleryDTO gelleryDTO) {
		System.out.println("Util - saveFileToDirectory");
		List<MultipartFile> fileList = gelleryDTO.getFileList();
		List<String> fileNameList = gelleryDTO.getFileNameList();
		int status = 0;
		try {
			//이미지를 저장할 경로
			String folderPath = "C:\\Users\\aa\\git\\Neighbus\\Neighbus\\src\\main\\resources\\static\\img\\gallery\\";
			System.out.println(folderPath);
			
			//이미지 저장
			status = fileSave(folderPath, fileList, fileNameList);
			//DTO에 이미지 이름 저장
			gelleryDTO.setFileNameList(fileNameList);
		}catch(Exception e) {
			System.out.println(e);
		}
		return status;
	}
	
	//지정된 경로에 파일 저장하는 메소드
	public static int fileSave( String folderPath, List<MultipartFile> fileList, List<String> fileNameList) {
		System.out.println("Util - fileSave");
		//폴더가 없으면 생성
		File folder = new File(folderPath);
		if ( !folder.exists() ) { folder.mkdirs(); }
		
		if( fileList!=null && !fileList.isEmpty() ) {
			for( MultipartFile file : fileList ) {
				String originalFileName = file.getOriginalFilename();
	            if(extensionCheck(originalFileName)==-13) { return -13; }
	            
	            //업로드된 파일을 지정된 경로에 저장
	            String uuid = UUID.randomUUID().toString()+".png";
	            File dest = new File(folderPath, uuid);
	
	            try {
	            	file.transferTo(dest);
	            	fileNameList.add(uuid);
	            }catch(IOException e) {
	    	        System.err.println("Exception occurred at: " + e.getStackTrace()[0]);
	    	        e.printStackTrace();
	            }
			}
		}
		return 1;
	}

	//확장자 체크
	public static int extensionCheck( String originalFileName) {
		System.out.println("Util - extensionCheck");
		String[] allowedExtensions = {"jpg", "jpeg", "jfif", "png"};
		String extension = "";
		
        //확장자 확인
        if (originalFileName != null && originalFileName.lastIndexOf(".") != -1) {
        	extension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
        }
        
		boolean checkExtension = Arrays.asList(allowedExtensions).contains(extension.toLowerCase());
        if(!checkExtension) {
        	return -13;
        }
		return 1;
	}
	
	//경로에 있는 파일 삭제 메서드
	public static void fileDelete(String folderPath, String fileName) {
		System.out.println("Util - fileDelete");
		File file = new File(folderPath, fileName);
	    
		if (file.exists() && file.isFile()) {
            if (file.delete()) {
                System.out.println(fileName + " 파일이 삭제되었습니다.");
            } else {
                System.out.println(fileName + " 파일 삭제에 실패했습니다.");
            }
        } else {
            System.out.println(fileName + " 파일이 존재하지 않습니다.");
        }
	}
	
	//쿠키값 가지고 들어가기
	public static void getCookie(HttpServletRequest request, Model model) {
		System.out.println("Util - getCookie");
		Cookie[] cookies=request.getCookies(); // 모든 쿠키 가져오기
	    if(cookies!=null){
	        for (Cookie c : cookies) {
	            String name = c.getName(); // 쿠키 이름 가져오기
	            String value = c.getValue(); // 쿠키 값 가져오기
	            if (name.equals("username")) {
	            	model.addAttribute("username", value);
	            }
	        }
	    }
	}
	
	// List<Map> 을 보기 좋게 출력
	public static void printMapList(List<Map<String, Object>> mapList) {
	    System.out.println("[");
	    for (Map<String, Object> map : mapList) {
	        System.out.println("  {");
	        int count = 0;
	        for (Map.Entry<String, Object> entry : map.entrySet()) {
	            System.out.print("    " + entry.getKey() + ": " + entry.getValue());
	            count++;
	            if (count < map.size()) {
	                System.out.println(",");
	            } else {
	                System.out.println();
	            }
	        }
	        System.out.println("  },");
	    }
	    System.out.println("]");
	}
}
