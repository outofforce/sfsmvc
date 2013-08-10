<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
    <form action="<%=request.getContextPath()%>/abc/index.do" method="post">
	<h1>${message}</h1>
    <button type="submit">跳转</button>
    </form>
</body>
</html>