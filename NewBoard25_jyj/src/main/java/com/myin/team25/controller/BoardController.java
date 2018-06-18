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
	
	

	//Ajax�� ���۵� �Խ��� ����Ʈ ������ �θ��� ��
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
	
	//json���� �������� �� 
	@RequestMapping(value="/BoardListAjaxController") 
	public @ResponseBody ArrayList<BoardVo> boardListAjax(SearchCriteria scri) {
		//@ResponseBody ��  ��ü�� ����
		//Ŭ����, �޼ҵ忡 @ResponseBody �̰� �ٿ��ټ����� 
		//�޼ҵ忡 �ٿ��ָ� �� �ش� �޼ҵ忡�� ������ ��
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
			rttr.addFlashAttribute("msg", "�����Ǿ����ϴ�.");
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
		//RedirectAttributes ���ܼ�, 1ȸ������ , �Ⱥ��̰� ���ܼ� ������ 
		//System.out.println("write����?");
		MultipartFile file = bv.getUploadfile();
		//System.out.println("���� ���� �̸� "+file.getOriginalFilename());
	
	//	System.out.println("file.getBytes(): "+file.getBytes() );
		//�����ϰ� ����� ���� �̸� ã��
	//	String savedName = uploadFile(file.getOriginalFilename(), file.getBytes());
		//System.out.println("savedName: "+savedName);
	//	System.out.println("savedName;��Ʈ�ѷ�" +savedName);
		
	//	System.out.println("���� �̸�" + filename);
	//	model.addAttribute("savedName", savedName); //��Ƽ� ������
	//	String savedName = uploadFile(originalName, fileData);
		
		/*String uploadedFileName = UploadFileUtiles.uploadFile(uploadPath, 
			���ε�� �����̸�								file.getOriginalFilename(), 
															file.getBytes());*/
	//	String filename = null;
	//	filename = uploadedFileName;
	//	System.out.println("���ε�� �����̸� :"+filename);
		
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
		model.addAttribute("keyword", scri.getKeyword()); //RedirectAttributes�� ��Ƽ� ���
														//model�� ������ model.addAttyibute
														//Redirect�� set
		String page =null;
		if (res ==1) {
			//rttr.addFlashAttribute("msg", "��ϵǾ����ϴ�."); //addFlashAttribute�ѹ��� ��Ƽ� ������1ȸ��
			page = "redirect:/BoardListController";
		}else{
			page = "redirect:/BoardWriteController";
		}
		
		return page;
	}
	
	//���� ���ε��ϴ� �޼ҵ�
/*	private String uploadFile(String originalName, byte[] fileData)
			throws Exception {
		System.out.println("uploadFile �޼ҵ� ����");
		
		UUID uid = UUID.randomUUID();
		String savedName = uid.toString()+"_"+originalName;
		//�ߺ� �̸��� �������������ϱ�  �������� �տ� �ٿ���		
		System.out.println("�޼ҵ忡��savedName: "+savedName);
		
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
			rttr.addFlashAttribute("msg", "�����Ǿ����ϴ�.");
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
			rttr.addFlashAttribute("msg", "��ϵǾ����ϴ�.");
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
		System.out.println("BoardMemberInfoController ����");
		/*int cnt=0;
		cnt=bs.totalRecordCount(scri);
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setScri(scri);		
		pageMaker.setTotalCount(cnt);	*/
		
		ArrayList<HashMap<String, Object>> alist = null;
		alist =  bs.boardMemberInfo();
		System.out.println("Ȯ�� alist  :" +alist);
		System.out.println("Ȯ�� alist.get(0)  :" +alist.get(0));
		System.out.println("Ȯ�� alist.get('BBIDX')  :" +alist.get(0).get("BBIDX"));
		
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
	
	
	@ResponseBody //��ü ����
	@RequestMapping(value="/uploadAjax",method=RequestMethod.POST,produces="text/plain;charset=UTF-8")
	public ResponseEntity<String> uploadAjax(MultipartFile file) throws Exception{
		
		System.out.println("uploadAjax �����̸�:"+file.getOriginalFilename());		
		
		
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
		//ResponseEntity<byte[]> ����Ʈ �迭 Ÿ��
		System.out.println("displayfile �޼ҵ� fileName !!!   "+fileName);
		
		InputStream in = null;//InputStreamŬ������ �������̳� �̹����� �������� ������ ���?
		ResponseEntity<byte[]> entity = null;  
		
		try { 	//Ȯ���ڸ� �˾ƺ��� ���� ��
			String formatName = fileName.substring(fileName.lastIndexOf(".")+1);//Ȯ���� �̴�
			System.out.println("formatName!!!!!!!!!"+formatName);
			
			MediaType mType = MediaUtils.getMediaType(formatName);// �̹���Ÿ�Ծƴϸ� null
			System.out.println("mType!!!!!!!!!"+mType);
			
			HttpHeaders headers = new HttpHeaders();
			
			in = new FileInputStream(uploadPath+fileName);
			System.out.println("in "+in);
			
			//MIME TYPE ���� Ÿ��? �̰ɻ�뤷�ؼ� Ȯ���ڸ� ����
			if (mType != null){
				headers.setContentType(mType);//Ÿ���� img�����̸� �����ְ�
			}
			else{
				//�̹��� ������ �ƴϸ� �ٿ�ε� â�̶߰Բ� �ϴ� 
				fileName = fileName.substring(fileName.indexOf("_")+1); 
				headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);//�ٿ�ε��
				headers.add("Content-Disposition", "attachment; filename=\""+
						new String(fileName.getBytes("UTF-8"),"ISO-8859-1")+"\""); 
				//�̹��� �����̾ƴϸ� �̷� �������� ������ �ٿ�ε� �Ѵ�.?
			}
			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), //IOUtilsŬ������toByteArray�޼ҵ�
					headers, //
					HttpStatus.CREATED);
		}catch(Exception e){
			e.printStackTrace();
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		}finally{
			in.close(); //in �ݱ�
		}
		return entity;
	}
	
	
	@ResponseBody
	@RequestMapping(value ="/deleteFile", method=RequestMethod.POST) 
	public ResponseEntity<String> deleteFile(String fileName) {
		System.out.println("deleteFile��Ʈ�ѷ���");
		System.out.println("deleteFile��Ʈ�ѷ� fileName :: "+fileName);
		
		String formatName = fileName.substring(fileName.lastIndexOf(".")+1);
		System.out.println("deleteFile��Ʈ�ѷ� formatName :: "+formatName);
		
		MediaType mType = MediaUtils.getMediaType(formatName);
		System.out.println("deleteFile��Ʈ�ѷ� mType  ::"+mType);
		
		if(mType != null){
			System.out.println("deleteFile��Ʈ�ѷ�  mType if" );
			
			String front = fileName.substring(0, 12);
			System.out.println("deleteFile��Ʈ�ѷ�  front:::" +front);
			
			String end = fileName.substring(14);
			System.out.println("deleteFile��Ʈ�ѷ�  end:::" +end);
			
			new File(uploadPath + (front+end).replace('/',File.separatorChar)).delete();
		}
		new File(uploadPath + fileName.replace('/', File.separatorChar)).delete();
		
		return new ResponseEntity<String>("deleted", HttpStatus.OK);
	}
	
	
}//��








