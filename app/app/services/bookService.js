bookProject
.factory('bookService', ['$http', function($http){
	return{

		getBooks:function(callback){
			 console.log("getting Boooks");
			 $http({ method: 'GET', url: 'http://localhost:8080/book-project/api/books' }).
	        success(function (data, status, headers, config) {
	            //console.log(data);
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
}])
.factory('favBookService',['$http', function($http){
	return{
		toggleFavBook: function(callback,data){
			$http.post('http://localhost:8080/book-project/api/fav-books/toggle-to-user',data, {
                    transformRequest: angular.identity,
                    headers: {'Content-Type': undefined}
               }).success(function(data){
               		callback(data);
               }).error(function(data){}); 
		},

		getFavBookState: function(callback, userId , bookId){
			var params = "userId=" + userId + "&bookId=" + bookId;
			$http.get('http://localhost:8080/book-project/api/fav-books/get-favbook-state?' + params )
			.success(function(data){
               		callback(data);
               }).error(function(data){}); 
		}
	}

}])

.factory('rateBookService',['$http', function($http){
	return{
		rateBook: function(callback,data){
			$http.post('http://localhost:8080/book-project/api/rate/rate',data, {
                    transformRequest: angular.identity,
                    headers: {'Content-Type': undefined}
               }).success(function(data){
               		callback(data);
               }).error(function(data){}); 
		},

		getRateBookState: function(callback, userId , bookId){
			var params = "userId=" + userId + "&bookId=" + bookId;
			$http.get('http://localhost:8080/book-project/api/rate/get-rate-state?' + params )
			.success(function(data){
               		callback(data);
               }).error(function(data){}); 
		},

		getAverageRate: function(callback, bookId){
			$http.get('http://localhost:8080/book-project/api/rate/' + bookId)
			.success(function(data){
               		callback(data);
               }).error(function(data){console.log(bookId)}); 
		},
	}

}]);