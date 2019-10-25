<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
</head>
<body>

<div class="input-group">
<form action="/board/register" method="post" enctype="multipart/form-data">
  <input type="text" class="form-control" name="title" placeholder="title" aria-describedby="basic-addon1" value="한글제목">
   <textarea rows="20" cols="100" name="content" value="한글내용"></textarea>
      <input type="text" class="form-control" name="writer" placeholder="writer" aria-describedby="basic-addon1" value="한글작성자">
      <input type="file" name='fs' multiple="multiple">
		<button type="submit" class="btn btn-default btn-lg">
			<span aria-hidden="true"></span>
			등록
		</button>
		</form>
</div>
</body>
</html>