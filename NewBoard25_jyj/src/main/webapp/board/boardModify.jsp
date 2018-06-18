<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.myin.team25.domain.*" %>
<% 
BoardVo bv = (BoardVo)request.getAttribute("bv");
PageMaker pm = (PageMaker)request.getAttribute("pageMaker");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>수정페이지</title>
<script type="text/javascript">
function check() {	
  
  var formname = document.frm;
  
  if (formname.subject.value =="") {
	  alert("제목입력하세요");
	  formname.subject.focus();	  
	  return ;	  
  } else if (formname.content.value ==""){	
	  alert("내용 입력하세요");
	  formname.content.focus();	  
	  return ;
  } else if (formname.writer.value ==""){	
	  alert("작성자 입력하세요");
	  formname.writer.focus();	  
	  return ;
  } else if (formname.password.value ==""){	  
	  alert("비밀번호입력하세요");
	  formname.password.focus();	  
	  return ;
  } 
    var res;
  	res = confirm("수정하시겠습니까?");
  
  	if (res == true) {
	   	formname.method ="post";
	   	formname.action ="<%=request.getContextPath() %>/BoardModifyActionController";
	   	formname.submit();  
  	}
  	return ;
}	

</script>
</head>
<body>
<form name="frm">
<input type="hidden" name="bbidx" value="<%=bv.getBbidx() %>" />
<input type=hidden name="page" value="<%=pm.getScri().getPage() %>" />
<input type=hidden name="searchType" value="<%=pm.getScri().getSearchType() %>" />
<input type=hidden name="keyword" value="<%=pm.getScri().getKeyword() %>" />
<table border=1 width="100%" height="600px">
<tr>
<td>제목</td>
<td>
<input type="text" name="subject" id="subject" size="10" maxlength="10" value="<%=bv.getSubject() %>" />
</td>
</tr>
<tr>
<td>내용</td>
<td>
<textarea name="content" rows=20 cols=100 >
<%=bv.getContent() %>
</textarea>
</td>
</tr>
<tr>
<td>작성자</td>
<td>
<input type="text" name="writer"  size="20" value="<%=bv.getWriter() %>" />
</td>
</tr>
<tr>
<td>작성시 비밀번호</td>
<td>
<input type="password" name="password" size="10" maxlength="10" />
</td>
</tr>

<tr>
<td></td>
<td align="center">
<input type="button" name="button" value="확인" onclick="javascript:check();" />
<input type="button" value="리셋" onclick="reset();"/>
</td>
</tr>
</table>
</form>
</body>
</html>
