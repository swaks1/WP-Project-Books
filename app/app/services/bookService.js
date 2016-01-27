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
		}
	}
}]);