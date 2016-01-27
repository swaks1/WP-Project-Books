bookProject
.controller("bookCtrl",[
            '$scope','$http','$state','bookService',
    function($scope, $http, $state,bookService){
    	  $scope.books = [];
    	  $scope.displayedCollection=[];
    	  
        bookService.getBooks(function(data){
            $scope.books = data;
            console.log("books from ctrl");
        });

  
 }])