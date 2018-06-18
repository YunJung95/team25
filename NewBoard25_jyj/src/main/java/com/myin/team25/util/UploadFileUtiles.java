package com.myin.team25.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.UUID;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public class UploadFileUtiles {
	
	private static final Logger logger = 
			LoggerFactory.getLogger(UploadFileUtiles.class);

	public static String uploadFile(String uploadPath,//업로드 되는 파일 위치
									String originalName,
									byte[] fileData)	
	throws Exception{
		
		UUID uid = UUID.randomUUID();
		String savedName = uid.toString() +"_"+originalName;//저장할때 나오는 이름
		
//		String path = UploadFileUtiles.class.getResource("").getPath();
//		System.out.println("현재클래스path:"+path);
		
//  	실행되는 시스템 위치	
//		System.out.println(System.getProperty("user.dir"));
       
       /* String realpath = request.getSession().getServletContext().getRealPath(uploadPath);
		System.out.println("UploadFileUtiles__realpath : "+realpath);
        */
		
		String savedPath = calcPath(uploadPath); //세이브된 경로 위치를 알고싶어서 사용
                                            //
		                                
		File target = new File(uploadPath+savedPath,savedName); //
//  	등록한 파일 상대경로
//		String loc = target.getCanonicalPath();

		FileCopyUtils.copy(fileData,target);
		
		String formatName = originalName.substring(originalName.lastIndexOf(".")+1);
		
		String uploadedFileName = null;
		
		if(MediaUtils.getMediaType(formatName) != null){
			uploadedFileName = makeThumbnail(uploadPath,savedPath, savedName);
		}else{
			uploadedFileName = makeIcon(uploadPath,savedPath,savedName);
		}
				
		return uploadedFileName;
	}	

	private  static String makeIcon(String uploadPath,
			String path,
			String fileName)throws Exception{

		String iconName = uploadPath+path+File.separator+fileName;				
		
		return iconName.substring(uploadPath.length()).replace(File.separatorChar, '/');
	}
	
	private static String calcPath(String uploadPath){	
		                        //calcPath 메소드는 파일 올린 날짜 가져오는?
		Calendar cal = Calendar.getInstance();
		String yearPath = File.separator+cal.get(Calendar.YEAR);
		
		String monthPath = yearPath+
				File.separator +
				new DecimalFormat("00").format(cal.get(Calendar.MONTH)+1);
		
		String datePath = monthPath +
				File.separator +
				new DecimalFormat("00").format(cal.get(Calendar.DATE));
			
		makeDir(uploadPath, yearPath, monthPath, datePath);//년도 날 일 
		      //makeDir 디렉토리(폴더) 만들기 
		logger.info(datePath);
		
		return datePath;
	}
	               //경로를 가지고 디렉토리를 만드는 메소드
	private static void makeDir(String uploadPath,String...paths){ //String...paths 배열에 담아서 넘기는 것처럼 
			
		if(new File(uploadPath+paths[paths.length -1]).exists()) //이미 폴더가 파일이 있으면 리턴!
			return;
		
		for(String path : paths){
			
			File dirPath = new File(uploadPath + path);		
	//		System.out.println("dirPath:"+dirPath);			
			
			if (! dirPath.exists()){			//해당된 경로에 ex)2018폴더가 없으면 폴더를 만들어라 
				dirPath.mkdir();		//mkdir 폴더 만드는 		
			}
		}
	}
	
	private static String makeThumbnail(String uploadPath,
			String path,
			String fileName) throws Exception{
		
		BufferedImage sourceImg = 
				ImageIO.read(new File(uploadPath+path,fileName));
		BufferedImage destImg = //
				Scalr.resize(sourceImg,  // 
						Scalr.Method.AUTOMATIC, 
						Scalr.Mode.FIT_TO_HEIGHT,100);
		
		String thumbnailName = 
				uploadPath + 
				path + 
				File.separator + 
				"s-"+fileName;
		
		File newFile = new File(thumbnailName);
		String formatName = fileName.substring(fileName.lastIndexOf(".")+1); // lastIndexOf ()사이에 들어간 문자를 뒤에서 부터 찾아 얻는다.(0123456이렇게 셈)
                                                                            //파일 이름에서 . 들어간뒤에 글자 
	
		ImageIO.write(destImg, formatName.toUpperCase(), newFile); //formatName 
		                      //toUpperCase 대문자로 바꿔주는  자바가 대소문자 구별하니
		return thumbnailName.substring(uploadPath.length()).replace(File.separatorChar, '/'); //썸네임을 만들때? 사용하는거
	}	
}
