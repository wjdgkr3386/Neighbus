package com.neighbus;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class Util {


	//파일 재입력 메소드
	public static int saveFileToDirectory(ExamDTO examDTO) {
		int access=0;
        String folderPath = "/static/img/" + exam_code;
		
        fileListNullDelete(examDTO);
        
        file_nameInput(examDTO);
        
        fileDelete( folderPath );
        
        List<MultipartFile> fileList = examDTO.getFile();
        if(fileList!=null && !fileList.isEmpty() && fileList.size()>0) {
        	access=fileCreate( folderPath, examDTO );
        }else {
        	access=1;
        }
        return access;
	}
	
	//리스트에 값 없는 공간 삭제
	public static void fileListNullDelete( ExamDTO examDTO ) {
		List<MultipartFile> files = examDTO.getFile();
		if( files != null && !files.isEmpty() ) {
			for (int i = files.size() - 1; i >= 0; i--) {
			    MultipartFile file = files.get(i);
			    if (file.isEmpty()) { files.remove(i); }
			}
			examDTO.setFile(files);
		}
	}

	//파일 불러와 파일이름을 DTO에 저장
	public static void file_nameInput( ExamDTO examDTO ) {
		List<MultipartFile> files = examDTO.getFile();
		List<String> file_nameList = new ArrayList<String>();
		if( files != null && !files.isEmpty() ) {
			for( MultipartFile file : files) {
				String originalfileName = file.getOriginalFilename();
				file_nameList.add(originalfileName);
			}
			examDTO.setFile_name(file_nameList);
		}
	}
	
	//경로에 있는 폴더 삭제 메소드
	public static void fileDelete(String folderPath) {
	    File folder = new File(folderPath);
	    File[] files = folder.listFiles();
	    
	    //폴더 안의 파일들 삭제
	    if( files != null  && files.length > 0 ) {
	        for( File file : files ) {
	            try {
	                if( file.isDirectory() ) {
	                    fileDelete(file.getAbsolutePath());
	                } else {
	                    file.delete();
	                }
	            } catch( Exception e ) {
	    	        System.err.println("Exception occurred at: " + e.getStackTrace()[0]);
	    	        e.printStackTrace();
	            }
	        }
	    }
	    //폴더 삭제
	    if( folder.exists() ) { folder.delete(); }
	}
	
	//지정된 경로에 파일 저장하는 메소드
	public static int fileCreate( String folderPath, ExamDTO examDTO ) {
		List<MultipartFile> fileList = examDTO.getFile();
        
		//폴더가 없으면 생성
		File folder = new File(folderPath);
		if ( !folder.exists() ) { folder.mkdirs(); }
		
		if( fileList!=null && !fileList.isEmpty() ) {
			for( MultipartFile file : fileList ) {
				String originalFileName = file.getOriginalFilename();
	            if(extensionCheck(originalFileName)==-13) { return -13; }
			}
			
			for( MultipartFile file : fileList ) {
				String originalFileName = file.getOriginalFilename();
	            //업로드된 파일을 지정된 경로에 저장
	            String filePath = folderPath + "/" + originalFileName;
	            File dest = new File( filePath );
	
	            try {
	            	file.transferTo(dest);
	            }catch(IOException e) {
	    	        System.err.println("Exception occurred at: " + e.getStackTrace()[0]);
	    	        e.printStackTrace();
	            }
			}
		}
		return 1;
	}

	public static int extensionCheck( String originalFileName) {
		
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
	
	public static int isExtension( ExamDTO examDTO ) {
        Util.fileListNullDelete(examDTO);
        Util.file_nameInput(examDTO);
        
		List<MultipartFile> fileList = examDTO.getFile();
		if( fileList!=null && !fileList.isEmpty() ) {
			for( MultipartFile file : fileList ) {
				String originalFileName = file.getOriginalFilename();
	            if(Util.extensionCheck(originalFileName)==-13) {
	            	return -13;
	            }
			}
		}
		return 1;
	}
}
