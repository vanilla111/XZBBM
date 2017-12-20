<%--
  Created by IntelliJ IDEA.
  User: wang
  Date: 2017/12/17
  Time: 下午1:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div class="upload">
    <form action="/bbm.pic_upload.do" method="POST" enctype="multipart/form-data">
        上传文件:<input type="file" name="uploadFile">
        <input type="submit" value="upload">上传
        <input type="reset" value="reset">重置
    </form>
</div>
</body>
</html>
