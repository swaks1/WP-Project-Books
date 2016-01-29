bookProject
.factory('bookService', ['$http', function($http){
	return{

		getBooks:function(callback){
			 console.log("getting Boooks");
			 $http({ method: 'GET', url: 'http://localhost:8080/book-project/api/books' }).
	        success(function (data, status, headers, config) {
	            console.log(data);
	          	callback(data);
	             
	         }).
	         error(function (data, status, headers, config) {
	            console.log("ERROR in books");
	        }); 
		},
		getBooksByGenre:function(callback,genres){
			    console.log("getting Boooks By Genre");
			    var obj ={
			    	genreIds:genres
			    }
		       $http.post("http://localhost:8080/book-project/api/books/by-genre", obj )
					.success(function (data){
						console.log(data);
						callback(data);
					})
					.error(function (data){
						console.log("ERROR in geting book genres");
					})

			
		},
		getGenres:function(callback){
			 console.log("getting all genres");
			 $http({ method: 'GET', url: 'http://localhost:8080/book-project/api/genres' }).
	        success(function (data, status, headers, config) {
	            console.log(data);
	          	callback(data);
	             
	         }).
	         error(function (data, status, headers, config) {
	            console.log("ERROR in genres");
	        }); 
		},
		getBookById: function(callback, bookid){
			$http.get("http://localhost:8080/book-project/api/books/" + bookid)
			.success(function (data, status, headers, config){
				callback(data);
			})
			.error(function (data, status, headers, config) {
	            console.log("ERROR in bookid");
	        }); 
		}
	}
}]);