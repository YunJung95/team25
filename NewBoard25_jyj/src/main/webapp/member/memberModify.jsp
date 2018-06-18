<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import ="com.myin.team25.domain.*" %>
<%@ include file="../include/header.jsp" %>
<% MemberVo mv = (MemberVo)request.getAttribute("mv"); %>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>회원정보수정</title>
<script type="text/javascript">
function check() {	  
  var formname = document.frm;
  
  
  if (formname.memberId.value =="") {
	  alert("아이디 입력할래? 말래?");
	  formname.memberId.focus();	  
	  return ;	  
  } else if (formname.memberName.value ==""){	
	  alert("이름 입력해");
	  formname.memberName.focus();	  
	  return ;
  } else if (formname.memberJumin.value ==""){	
	  alert("주민번호 입력해");
	  formname.memberJumin.focus();	  
	  return ;
  } else if (isNaN(formname.memberJumin.value) == true){
	  alert("숫자만 입력해");
	  formname.memberJumin.value ="";
	  formname.memberJumin.focus();			 
	  return;
  } else if (formname.memberPassword.value ==""){	  
	  alert("비밀번호입력해");
	  formname.memberPassword.focus();	  
	  return ;
  } else if (formname.memberPassword2.value ==""){	
	  alert("너도 비밀번호 입력해");	  
	  formname.memberPassword2.focus();	  
	  return ;
  } else if (formname.memberPassword.value != formname.memberPassword2.value){	
	  alert("비밀번호 달라.");
	  formname.memberPassword.value="";
	  formname.memberPassword2.value="";
	  formname.memberPassword.focus();	  
	  return ;
  }  
    var res;
  	res = confirm("수정하시겠습니까??");
  
  	if (res == true) {
	   	formname.method ="post";
	   	formname.action ="<%=request.getContextPath() %>/MemberModifyActionController";
	   	formname.submit();  
  	}
  	return ;
}	

function inputAge(){	
	document.frm.memberAge.value = 2018-("19"+document.frm.memberJumin.value.substr(0,2));
	return;
}

</script>
</head>
<body onload="inputAge();">
<h1>회원정보 수정</h1>
<form name="frm">
<input type="hidden" name="memberMidx" value="1" />
<table border=1 width="100%" height="600px">
<tr>
<td>ID</td>
<td colspan=3>
<input type="text" name="memberId" id="memberId" size="20" maxlength="20" value="<%=mv.getMemberId() %>"  readonly="readonly" style="background-color:#e2e2e2;"/>
</td>
</tr>
<tr>
<td>이름</td>
<td><input type="text" name="memberName" id="memberName" size="20" maxlength="20" value="<%=mv.getMemberName() %>"  readonly="readonly" style="background-color:#e2e2e2;"/>
</td>
<td>성별</td>
<td>
<input type="radio" name="memberSex" id="memberMale" value="M" <% if (mv.getMemberSex().equals("M")) {  %>checked <% } %> />남
<input type="radio" name="memberSex" id="memberFemale" value="F" <% if (mv.getMemberSex().equals("F")) {  %>checked <% } %> />여
</td>
</tr>
<tr>
<td>Email</td>
<td colspan=3>
<input type="text" name ="memberEmail1"  size="20" value="<%=mv.getMemberEmail().split("@")[0] %>" />
<select name="memberEmail2">
<option value="naver.com" <%if (mv.getMemberEmail().split("@")[1].equals("naver.com")) { %>selected <%} %> >네이버</option>
<option value="daum.net" <%if (mv.getMemberEmail().split("@")[1].equals("daum.net")) { %>selected <%} %> >다음</option>
<option value="nate.com" <%if (mv.getMemberEmail().split("@")[1].equals("nate.com")) { %>selected <%} %> >네이트</option>
</select>
</td>
</tr>
<tr>
<td>주소</td>
<td colspan=3>
<input type="text" name="memberAddr"  size="50" value="<%=mv.getMemberAddr() %>" />
</td>
</tr>
<tr>
<td>주민번호</td>
<td colspan=3>
<input type="text" name="memberJumin" size="13" maxlength="13" value="<%=mv.getMemberJumin() %>"  readonly="readonly" style="background-color:#e2e2e2;"/>
&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;
<input type="text" name="memberAge" size="3" maxlength="3" readonly="readonly" style="background-color:#e2e2e2;"/>
나이
</td>
</tr>
<tr>
<td>PASSWORD</td>
<td colspan=3>
<input type="password" name="memberPassword" size="20" />
</td>
</tr>
<tr>
<td>PASSWORD2</td>
<td colspan=3>
<input type="password" name="memberPassword2" size="20" />
</td>
</tr>
<tr>
<td>부서명</td>
<td colspan=3>
<select name="bidx">
<option value="1" <%if (mv.getBidx() ==1){ %>selected<% } %>  >전산실</option>
<option value="2" <%if (mv.getBidx() ==2){ %>selected<% } %>  >영업실</option>
<option value="3" <%if (mv.getBidx() ==3){ %>selected<% } %>  >운영실</option>
</select>
</td>
<tr>
<td colspan=4 align="center">
<input type="button" name="button" value="확인" onclick="javascript:check();" />
<input type="button" value="리셋" onclick="reset();"/>
<input type="button" name="list" value="목록" onclick="javascript:location.href='<%=request.getContextPath()%>/MemberListController';" />
</td>
</tr>
</table>
</form>
</body>
<%@include file="../include/footer.jsp" %>
