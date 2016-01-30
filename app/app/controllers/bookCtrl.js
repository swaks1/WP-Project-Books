bookProject
.controller("bookCtrl",[
            '$scope','$http','$state','bookService', '$rootScope', 'loginService',
    function($scope, $http, $state,bookService,$rootScope, loginService){
    	    $rootScope.loggedIn = loginService.islogged();
    	$scope.genres=[];
    	$scope.genreObj = {};
    	  $scope.books = [];
    	  $scope.displayedCollection=[];
    	  
        bookService.getBooks(function(data){
            $scope.books = data;
            console.log("books from ctrl");
        });

        bookService.getGenres(function(data){
            $scope.genres=data;
            //console.log("books from ctrl");
        });

         $scope.ShowSelected = function() {
		   var cbs = $(".cbGenres");
		   var genreArray=[];
		   cbs.each(function(index,item){
		   		if(item.checked)
		   			genreArray.push(item.id);
		   });

		   if(genreArray.length == 0){
		   		bookService.getBooks(function(data){
		            $scope.books = data;
		            console.log("books from ctrl");
		        });
		        return ;
		   }

		  //console.log(genreArray);
		  bookService.getBooksByGenre(function(data){
		  		  $scope.books = data;
		  },genreArray);
		  
		};

 }])

. controller("singleCtrl", ["$stateParams", "$scope", "$rootScope", "bookService","loginService",
	function($stateParams, $scope, $rootScope,bookService, loginService){
		$rootScope.loggedIn = loginService.islogged();
		var bookid = $stateParams.itemId;
		$scope.contentLoaded = false;


		bookService.getBookById(function(data){
			$scope.book = data;
			$scope.contentLoaded = true;
			console.log($scope.url);
		},bookid);


}]);