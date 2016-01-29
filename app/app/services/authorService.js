bookProject
.factory('authorService', ['$http', function($http){
	return{
		test:function(){
			console.log("author serv");
			return;
		},
		getAuthorWithBooks:function(callback,authorId){
			var author ={};
			console.log("getting Author");
			$http({ method: 'GET', url: 'http://localhost:8080/book-project/api/authors/'+authorId }).
	        success(function (data, status, headers, config) {
	            //console.log(data);
	            author = data;
	            		//get the books
			        $http({ method: 'GET', url: 'http://localhost:8080/book-project/api/books/by-author/'+authorId }).
			        success(function (data, status, headers, config) {
			            author.books = data;
			         	callback(author);

			         }).
			         error(function (data, status, headers, config) {
			            console.log("ERROR in getting Books of Author");
			        }); 

	         }).
	         error(function (data, status, headers, config) {
	            console.log("ERROR in getting Author");
	        }); 
		}
	}
}]);