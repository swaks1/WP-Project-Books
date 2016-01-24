<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Upload File Request Page</title>
</head>
<body>
 
    <form method="POST" action="file-upload" enctype="multipart/form-data">
        File to upload: <input type="file" name="file"><br /> 
        <input type="submit" value="Upload"> Press here to upload the file!
    </form>
     
</body>
</html>