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

. controller("singleCtrl", ["$stateParams", "$scope", "$rootScope", "bookService","loginService","favBookService",
	function($stateParams, $scope, $rootScope,bookService, loginService,favBookService){
		$rootScope.loggedIn = loginService.islogged();
		var bookId = $stateParams.itemId;
		var userId = loginService.isloggedUser().id;
		$scope.contentLoaded = false;


		bookService.getBookById(function (data){
			$scope.book = data;
			$scope.contentLoaded = true;
			console.log($scope.url);
		},bookId);

		favBookService.getFavBookState(function (data){
			$scope.isliked = data;
			console.log($scope.isliked);
		},userId, bookId);

		$scope.toggle = function(){
			var fd = new FormData();
	            fd.append('userId', userId);
	            fd.append('bookId',bookId);
			favBookService.toggleFavBook(function (data){
				$scope.isliked = data;
				console.log(data);
			}, fd);
		}

}]);