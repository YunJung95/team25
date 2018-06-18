<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.myin.team25.domain.*" %>
<% 
int bbidx = (int) request.getAttribute("bbidx");
PageMaker pm = (PageMaker)request.getAttribute("pageMaker");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>삭제페이지</title>
</head>
<body>
<form name="del" action="<%=request.getContextPath()%>/BoardDeleteActionController" method="post">
<input type=hidden name="bbidx" value="<%=bbidx %>" />
<input type=hidden name="page" value="<%=pm.getScri().getPage() %>" />
<input type=hidden name="searchType" value="<%=pm.getScri().getSearchType() %>" />
<input type=hidden name="keyword" value="<%=pm.getScri().getKeyword() %>" />
<table border=1 style="text-align:center;">
<tr>
<td colspan=2>
삭제하시겠습니까?
</td>
</tr>
<tr>
<td>작성시 비밀번호</td>
<td>
<input type="password" name="password" />
<input type="submit" name="submit" value="삭제" />
</td>
</tr>
</table>
</form>
</body>
</html>