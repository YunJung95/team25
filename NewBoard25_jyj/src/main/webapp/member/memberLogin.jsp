<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@include file="../include/header.jsp" %>

<script type="text/javascript">
function check() {	
  
  var formname = document.frm;
  
  if (formname.memberId.value =="") {
	  alert("아이디입력");
	  formname.memberId.focus();	  
	  return ;	  
  } else if (formname.memberPassword.value ==""){	
	  alert("패스워드입력");
	  formname.memberPassword.focus();	  
	  return ;
  }
    var res;
  	res = confirm("로그인 하시겠습니까?");
  
  	if (res == true) {
	   	formname.method ="post";
	   	formname.action ="<%=request.getContextPath()%>/MemberLoginActionController";
	   	formname.submit();  
  	}
  	return ;
}	
</script>

<form name="frm">
<div class="box-body">
	<div class="form-group">
	<label for="exampleInputEmail1">ID</label>
	<input type="text" name="memberId" id="memberId" class="form-control" size="20" maxlength="20" placeholder="Enter ID" />
	</div>
	<div class="form-group">
	<label for="exampleInputPassword1">PASSWORD</label>
	<input type="password" name="memberPassword" id="memberPassword" class="form-control" size="20" maxlength="20" placeholder="Enter PASSWORD" />
	</div>
</div>
<div class="box-footer">
	<button type="butten" class="btn btn-primary" onclick="javascript:check();">확인</button>
	자동 로그인 <input type="checkbox" name="useCookie" id="useCookie" /> 체크
</div>
</form>

<%@include file="../include/footer.jsp" %>
