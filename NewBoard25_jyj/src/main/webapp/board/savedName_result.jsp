<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>savedName_result</title>
</head>
<body>

<script type="text/javascript">

var result = '<%= request.getAttribute("savedName")%>';/* ${savedName}사용가능 */
parent.addFilePath(result);

</script>

</body>
</html>