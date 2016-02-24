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

		  bookService.getBooksByGenre(function(data){
		  		  $scope.books = data;
		  },genreArray);
		  
		};


 }])

. controller("singleCtrl", 
	["$stateParams", "$scope", "$rootScope", "bookService","loginService","favBookService","rateBookService",
	function($stateParams, $scope, $rootScope,bookService, loginService,favBookService,rateBookService){
		$rootScope.loggedIn = loginService.islogged();
		var bookId = $stateParams.itemId;
		var userId = loginService.isloggedUser().id;
		$scope.contentLoaded = false;

		// load book
		bookService.getBookById(function (data){
			$scope.book = data;
			$scope.contentLoaded = true;
		},bookId);

		
		if(userId != null){ // if logged in
			favBookService.getFavBookState(function (data){
				$scope.isliked = data; //show like button
			},userId, bookId);

			rateBookService.getRateBookState(function (data){
				for(i = 1; i <= data; i++){
					$("#" + i).addClass("starRate"); // color yellow....
				}
			},userId,bookId);

			$scope.toggle = function(){
				var fd = new FormData(); 
	            fd.append('userId', userId);
	            fd.append('bookId',bookId);
				favBookService.toggleFavBook(function (data){
					$scope.isliked = data;
				}, fd);
			}

			$scope.rate = function(stars){
				var fd = new FormData(); 
	            fd.append('userId', userId);
	            fd.append('bookId',bookId);
	            fd.append('stars',stars);
				rateBookService.rateBook(function (data){
					for(i = 1; i <= 5; i++){ // if it has it and recives lower rate
						$("#" + i).removeClass("starRate"); // remove color black....
					}
					for(i = 1; i <= data; i++){
						$("#" + i).addClass("starRate"); // color black....
					}
				},fd);
			}

		}
		else{ 
			$(".like").hide(); // hide like button
			$(".rating").hide(); // hide stars
		}



		//rating info
		rateBookService.getAverageRate(function (data){
			$scope.rating = data;
		},bookId);



}]);