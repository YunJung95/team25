package com.myin.team25.controller;


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.myin.team25.domain.BoardVo;
import com.myin.team25.domain.PageMaker;
import com.myin.team25.domain.SearchCriteria;
import com.myin.team25.service.BoardService;
import com.myin.team25.util.MediaUtils;
import com.myin.team25.util.UploadFileUtiles;

@Controller
public class BoardController {	
	
	@Autowired
	private BoardService bs;	
	
	@Resource(name="uploadPath")
	private String uploadPath;
	
	@RequestMapping(value="/BoardListController")
	public String boardList(SearchCriteria scri, Model model) {
		
		int cnt=0;
		cnt=bs.totalRecordCount(scri);
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setScri(scri);		
		pageMaker.setTotalCount(cnt);	
		
		ArrayList<BoardVo> alist =  bs.SelectBoardAll(scri);		
		model.addAttribute("alist", alist);
		model.addAttribute("pageMaker", pageMaker);	
		
		return "board/boardList";
	}
	
	

	//Ajax로 제작된 게시판 리스트 페이지 부르는 것
	@RequestMapping(value="/BoardList_AjaxController") 
	public String boardList_Ajax(SearchCriteria scri, Model model) {
		
		int cnt=0;
		cnt=bs.totalRecordCount(scri);
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setScri(scri);		
		pageMaker.setTotalCount(cnt);	
		
		ArrayList<BoardVo> alist =  bs.SelectBoardAll(scri);		
		model.addAttribute("alist", alist);
		model.addAttribute("pageMaker", pageMaker);	
		
		return "board/boardList_Ajax";
	}
	
	//json파일 가져오는 것 
	@RequestMapping(value="/BoardListAjaxController") 
	public @ResponseBody ArrayList<BoardVo> boardListAjax(SearchCriteria scri) {
		//@ResponseBody 는  객체만 리턴
		//클래스, 메소드에 @ResponseBody 이걸 붙여줄수있음 
		//메소드에 붙여주면 그 해당 메소드에만 적용이 됨
		int cnt=0;
		cnt=bs.totalRecordCount(scri);
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setScri(scri);		
		pageMaker.setTotalCount(cnt);	
		
		ArrayList<BoardVo> alist =  bs.SelectBoardAll(scri);		
		
		//System.out.println("alist1!!"+alist);
		
		return alist;
	}


	
	
	@RequestMapping(value="/BoardContentController")
	public String boardContent(SearchCriteria scri,@RequestParam("bbidx") int bbidx, Model model) {
		
		BoardVo bv = bs.SelectBoardOne(bbidx);		
		model.addAttribute("bv", bv);
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setScri(scri);
		model.addAttribute("pageMaker", pageMaker);	
		
		return "board/boardContent";
	}	
	
	@RequestMapping(value="/BoardDeleteController")
	public String boardDelete(SearchCriteria scri,@ModelAttribute("bbidx") int bbidx, Model model) {
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setScri(scri);
		model.addAttribute("pageMaker", pageMaker);
		
		return "board/boardDelete";
	}
	
	@RequestMapping(value="/BoardDeleteActionController")
	public String boardDeleteAction(SearchCriteria scri,HttpServletRequest request,@RequestParam("password") String password,@RequestParam("bbidx") int bbidx ,RedirectAttributes rttr) throws UnknownHostException {
				
		HttpSession session = request.getSession();		
		int sMemberMidx = (int) session.getAttribute("sMemberMidx");
			
		InetAddress local = InetAddress.getLocalHost();		
		String memberIp  = local.getHostAddress();			
			
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String Modifyday = sdf.format(dt);
		Modifyday = Modifyday.substring(2);			
		
		int res = bs.deleteBoard(password, memberIp, bbidx, sMemberMidx);
				
		rttr.addAttribute("page", scri.getPage());
		rttr.addAttribute("searchType", scri.getSearchType());
		rttr.addAttribute("keyword", scri.getKeyword());
		
		
		String page =null;
		if (res ==1) {
			rttr.addFlashAttribute("msg", "삭제되었습니다.");
			page = "redirect:/BoardListController";
		}else{
			rttr.addAttribute("bbidx", bbidx);
			page = "redirect:/BoardDeleteController";
		}
		
		return page;
	}
	
	@RequestMapping(value="/BoardWriteController")
	public String boardWrite(SearchCriteria scri,Model model) {
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setScri(scri);
		model.addAttribute("pageMaker", pageMaker);
				
		return "board/boardWrite";
	}
	
	@RequestMapping(value="/BoardWriteActionController")
	public String boardWriteAction(@ModelAttribute("bv") BoardVo bv ,
			SearchCriteria scri,HttpServletRequest request,
			Model model, MultipartFile uploadfile,
			@RequestParam("uplodadfile") String uplodadfile) throws Exception {
		//RedirectAttributes 숨겨서, 1회용으로 , 안보이게 숨겨서 보내는 
		//System.out.println("write들어옴?");
		MultipartFile file = bv.getUploadfile();
		//System.out.println("원본 파일 이름 "+file.getOriginalFilename());
	
	//	System.out.println("file.getBytes(): "+file.getBytes() );
		//복사하고 저장된 파일 이름 찾기
	//	String savedName = uploadFile(file.getOriginalFilename(), file.getBytes());
		//System.out.println("savedName: "+savedName);
	//	System.out.println("savedName;컨트롤러" +savedName);
		
	//	System.out.println("파일 이름" + filename);
	//	model.addAttribute("savedName", savedName); //담아서 보내기
	//	String savedName = uploadFile(originalName, fileData);
		
		/*String uploadedFileName = UploadFileUtiles.uploadFile(uploadPath, 
			업로드된 파일이름								file.getOriginalFilename(), 
															file.getBytes());*/
	//	String filename = null;
	//	filename = uploadedFileName;
	//	System.out.println("업로드된 파일이름 :"+filename);
		
		System.out.println("uplodadfile: "+uplodadfile);
		bv.setFilename(uplodadfile);
		
		System.out.println("");
		HttpSession session = request.getSession();		
		int sMemberMidx = (int) session.getAttribute("sMemberMidx");
		bv.setMemberMidx(sMemberMidx);
		
		InetAddress local = InetAddress.getLocalHost();		
		String memberIp = local.getHostAddress();	
		bv.setIp(memberIp);
		
	
		
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String writeday = sdf.format(dt);
		writeday = writeday.substring(2);	
		bv.setWriteday(writeday);
	
		
		int res = bs.insertBoard(bv);
		
	
		model.addAttribute("page", scri.getPage());
		model.addAttribute("searchType", scri.getSearchType());
		model.addAttribute("keyword", scri.getKeyword()); //RedirectAttributes에 담아서 사용
														//model에 담을때 model.addAttyibute
														//Redirect는 set
		String page =null;
		if (res ==1) {
			//rttr.addFlashAttribute("msg", "등록되었습니다."); //addFlashAttribute한번만 담아서 보내기1회용
			page = "redirect:/BoardListController";
		}else{
			page = "redirect:/BoardWriteController";
		}
		
		return page;
	}
	
	//파일 업로드하는 메소드
/*	private String uploadFile(String originalName, byte[] fileData)
			throws Exception {
		System.out.println("uploadFile 메소드 들어옴");
		
		UUID uid = UUID.randomUUID();
		String savedName = uid.toString()+"_"+originalName;
		//중복 이름이 있을수도있으니까  무작위로 앞에 붙여줌		
		System.out.println("메소드에서savedName: "+savedName);
		
		File target = new File(uploadPath,savedName);
		FileCopyUtils.copy(fileData, target);
		
		return savedName;
		
	}*/
	
	@RequestMapping(value="/BoardModifyController")
	public String boardModify(SearchCriteria scri,HttpServletRequest request,
			@RequestParam("bbidx") int bbidx,
			Model model) {
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setScri(scri);		
		
		BoardVo bv = bs.SelectBoardOne(bbidx);	
		
		model.addAttribute("bv", bv);
		model.addAttribute("pageMaker", pageMaker);
				
		return "board/boardModify";
	}
	
	@RequestMapping(value="/BoardModifyActionController")
	public String boardModifyAction(SearchCriteria scri,HttpServletRequest request,
			@RequestParam("bbidx") int bbidx,
			@RequestParam("subject") String subject,
			@RequestParam("content") String content,
			@RequestParam("writer") String writer,
			@RequestParam("password") String password,
		
			RedirectAttributes rttr) throws UnknownHostException {		
		
	
		HttpSession session = request.getSession();		
		int sMemberMidx = (int) session.getAttribute("sMemberMidx");
			
		InetAddress local = InetAddress.getLocalHost();		
		String memberIp  = local.getHostAddress();			
			
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String Modifyday = sdf.format(dt);
		Modifyday = Modifyday.substring(2);			
	
		int res = bs.updateBoard(subject, content, writer, password, memberIp, bbidx, sMemberMidx);			
		
		rttr.addAttribute("page", scri.getPage());
		rttr.addAttribute("searchType", scri.getSearchType());
		rttr.addAttribute("keyword", scri.getKeyword());		
		rttr.addAttribute("bbidx", bbidx);
		
		String page =null;
		if (res ==1) {
			rttr.addFlashAttribute("msg", "수정되었습니다.");
			page = "redirect:/BoardContentController";
		}else{
			page = "redirect:/BoardModifyController";
		}		
		
		return page;
	}
	
	@RequestMapping(value="/BoardReplyController")
	public String boardReply(SearchCriteria scri,
							 @ModelAttribute("bbidx") int bbidx,
							 @ModelAttribute("oidx") int oidx,
							 @ModelAttribute("updown") int updown,
							 @ModelAttribute("leftright") int leftright,
							 Model model) {
				
		PageMaker pageMaker = new PageMaker();
		pageMaker.setScri(scri);
		
		model.addAttribute("pageMaker", pageMaker);		
		
		return "board/boardReply";
	}
	
	@RequestMapping(value="/BoardReplyActionController")
	public String boardReplyAction(SearchCriteria scri,
			HttpServletRequest request,
			@ModelAttribute("bv") BoardVo bv,
			RedirectAttributes rttr) throws UnknownHostException {
		
		HttpSession session = request.getSession();		
		int sMemberMidx = (int) session.getAttribute("sMemberMidx");
		
		InetAddress local = InetAddress.getLocalHost();		
		String memberIp = local.getHostAddress();		
		
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String memberWriteday = sdf.format(dt);
		memberWriteday = memberWriteday.substring(2);		
		
		int res = bs.replyBoard(bv.getBbidx(),bv.getOidx(),bv.getUpdown(),bv.getLeftright(),sMemberMidx,bv.getSubject(),bv.getContent(),bv.getWriter(),bv.getPassword(), memberIp);
		
		rttr.addAttribute("page", scri.getPage());
		rttr.addAttribute("searchType", scri.getSearchType());
		rttr.addAttribute("keyword", scri.getKeyword());
		
		String page =null;
		if (res ==1) {
			rttr.addFlashAttribute("msg", "등록되었습니다.");
			page = "redirect:/BoardListController";
		}else{
			rttr.addAttribute("bbidx", bv.getBbidx());
			rttr.addAttribute("oidx", bv.getOidx());
			rttr.addAttribute("updown", bv.getUpdown());
			rttr.addAttribute("leftright", bv.getLeftright());
			
			page = "redirect:/BoardReplyController";
		}
		
		return page;
	}
	
	
	@RequestMapping(value="/BoardMemberInfoController")
	public String boardBoardList(Model model) {
		System.out.println("BoardMemberInfoController 들어옴");
		/*int cnt=0;
		cnt=bs.totalRecordCount(scri);
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setScri(scri);		
		pageMaker.setTotalCount(cnt);	*/
		
		ArrayList<HashMap<String, Object>> alist = null;
		alist =  bs.boardMemberInfo();
		System.out.println("확인 alist  :" +alist);
		System.out.println("확인 alist.get(0)  :" +alist.get(0));
		System.out.println("확인 alist.get('BBIDX')  :" +alist.get(0).get("BBIDX"));
		
		model.addAttribute("alist", alist);
		//model.addAttribute("pageMaker", pageMaker);	
		
		return "board/boardMemberInfo";
	}
	
	
/*	@RequestMapping(value = "/uploadFrom" , method  = RequestMethod.POST)
	public void uploadForm(MultipartFile file, Model model)
	throws Exception {
		logger.info("originalName :" + file.getOriginalFilename());
		logger.info("size : " + file.getSize());
		logger.info("contentType :" + file.getContentType());
	}
	*/
	
	
	@ResponseBody //객체 리턴
	@RequestMapping(value="/uploadAjax",method=RequestMethod.POST,produces="text/plain;charset=UTF-8")
	public ResponseEntity<String> uploadAjax(MultipartFile file) throws Exception{
		
		System.out.println("uploadAjax 원본이름:"+file.getOriginalFilename());		
		
		
		String uploadedFileName = UploadFileUtiles.uploadFile(uploadPath,file.getOriginalFilename(),file.getBytes());
		BoardVo bv = new BoardVo();
		bv.setFilename(uploadedFileName);
		
		ResponseEntity<String> entity = null;
		entity = new ResponseEntity<String>(uploadedFileName,HttpStatus.CREATED);
		
		return entity;
		
	}

	
	@ResponseBody
	@RequestMapping("/displayFile")
	public ResponseEntity<byte[]> displayFile(String fileName) throws Exception{
		//ResponseEntity<byte[]> 바이트 배열 타입
		System.out.println("displayfile 메소드 fileName !!!   "+fileName);
		
		InputStream in = null;//InputStream클래스는 동영상이나 이미지를 스윙으로 보낼때 사용?
		ResponseEntity<byte[]> entity = null;  
		
		try { 	//확장자를 알아보기 위해 씀
			String formatName = fileName.substring(fileName.lastIndexOf(".")+1);//확장자 뽑는
			System.out.println("formatName!!!!!!!!!"+formatName);
			
			MediaType mType = MediaUtils.getMediaType(formatName);// 이미지타입아니면 null
			System.out.println("mType!!!!!!!!!"+mType);
			
			HttpHeaders headers = new HttpHeaders();
			
			in = new FileInputStream(uploadPath+fileName);
			System.out.println("in "+in);
			
			//MIME TYPE 나의 타입? 이걸사용ㅇ해서 확장자를 비교함
			if (mType != null){
				headers.setContentType(mType);//타입이 img파일이면 보여주고
			}
			else{
				//이미지 파일이 아니면 다운로드 창이뜨게끔 하는 
				fileName = fileName.substring(fileName.indexOf("_")+1); 
				headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);//다운로드용
				headers.add("Content-Disposition", "attachment; filename=\""+
						new String(fileName.getBytes("UTF-8"),"ISO-8859-1")+"\""); 
				//이미지 파일이아니면 이런 설정으로 파일을 다운로드 한다.?
			}
			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), //IOUtils클래스에toByteArray메소드
					headers, //
					HttpStatus.CREATED);
		}catch(Exception e){
			e.printStackTrace();
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		}finally{
			in.close(); //in 닫기
		}
		return entity;
	}
	
	
	@ResponseBody
	@RequestMapping(value ="/deleteFile", method=RequestMethod.POST) 
	public ResponseEntity<String> deleteFile(String fileName) {
		System.out.println("deleteFile컨트롤러옴");
		System.out.println("deleteFile컨트롤러 fileName :: "+fileName);
		
		String formatName = fileName.substring(fileName.lastIndexOf(".")+1);
		System.out.println("deleteFile컨트롤러 formatName :: "+formatName);
		
		MediaType mType = MediaUtils.getMediaType(formatName);
		System.out.println("deleteFile컨트롤러 mType  ::"+mType);
		
		if(mType != null){
			System.out.println("deleteFile컨트롤러  mType if" );
			
			String front = fileName.substring(0, 12);
			System.out.println("deleteFile컨트롤러  front:::" +front);
			
			String end = fileName.substring(14);
			System.out.println("deleteFile컨트롤러  end:::" +end);
			
			new File(uploadPath + (front+end).replace('/',File.separatorChar)).delete();
		}
		new File(uploadPath + fileName.replace('/', File.separatorChar)).delete();
		
		return new ResponseEntity<String>("deleted", HttpStatus.OK);
	}
	
	
}//끝








