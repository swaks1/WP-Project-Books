
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	 <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
</head>
<body>
 
 <form method="POST" style="width:300px;float:left;'"
 		action="/book-project/api/users/register" enctype="multipart/form-data">
		<h2>INSER USER</h2>
 	 <div class="form-group">
	    <label for="personMobile">First Name:</label>
	    <input type="text" name="fname" class="form-control" >
 	 </div>
 	 <div class="form-group">
	    <label for="personMobile">Last Name:</label>
	    <input type="text" name="lname"  class="form-control" >
 	 </div>
 	 <div class="form-group">
	    <label for="personMobile">Username :</label>
	    <input type="text" name="username" class="form-control">
 	 </div>
 	 
 	 <div class="form-group">
	    <label for="personMobile">Password :</label>
	    <input type="password" name="password" class="form-control" >
 	 </div>
 	 
 	 <div class="form-group">
	    <label for="personMobile">Biography :</label>
	    <input type="text"  name="biography" class="form-control" >
 	 </div>
 	 
 	  <div class="form-group">
	    <label for="personMobile">Your Image :</label>
	    <input type="file"  name="file" class="form-control" >
 	 </div>
 	
 	 <input type="submit" class="btn btn-primary" value="Upload"> 
 	 
 	 </form>
 	 
 	 HELLO NIGGAS
 <img style="float:left" alt="hey" style="height:300px; width:300px"  
 			src="/book-project/api/users/get-image/47">
 	 
	<form method="POST" style="width:300px;float:left;'"
 		action="/book-project/api/books/new-book" enctype="multipart/form-data">
 		
		<h2>INSER BOOK</h2>
 	 <div class="form-group">
	    <label for="personMobile">Book Title:</label>
	    <input type="text" name="title" class="form-control" >
 	 </div>
 	 <div class="form-group">
	    <label for="personMobile">Book Description:</label>
	    <input type="text" name="description"  class="form-control" >
 	 </div>
 	
 	  <div class="form-group">
	    <label for="personMobile">Book Image :</label>
	    <input type="file"  name="file" class="form-control" >
 	 </div>
 	 
 	 <div class="form-group">
	    <label for="personMobile">Author Id :</label>
	    <input type="text"  name="authorId" class="form-control" >
 	 </div>
 	 
 	 <div class="form-group">
	    <label for="personMobile">Genres :</label>
	    <input type="checkbox"  name="genres" class="form-control" value = "1">Genre 1
	    <input type="checkbox"  name="genres" class="form-control" value = "2">Genre 2
	    <input type="checkbox"  name="genres" class="form-control" value = "31">Genre 3
 	 </div>
 	
 	 <input type="submit" class="btn btn-primary" value="Upload"> 
 	 
 	 </form>
 	
 	
 	<form method="POST" style="width:300px;float:left;'"
 		action="/book-project/api/books/by-genre" enctype="multipart/form-data">
 		
		<h2>Search By Genre</h2>
 	
 	 <div class="form-group">
	    <label for="personMobile">Genres :</label>
	    <input type="checkbox"  name="genres" class="form-control" value = "1">Genre 1
	    <input type="checkbox"  name="genres" class="form-control" value = "2">Genre 2
	    <input type="checkbox"  name="genres" class="form-control" value = "31">Genre 3
 	 </div>
 	
 	 <input type="submit" class="btn btn-primary" value="FIND"> 
 	 
 	 </form>
 
 
 	<!-- jQuery library -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<!-- Latest compiled JavaScript -->
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>
</html>