bookProject
.controller("authorCtrl",[
            "$scope","$state","$rootScope","authorService","$stateParams",
    function($scope, $state, $rootScope,authorService,$stateParams){
      var authorId = $stateParams.authorId;
      //console.log(authorId)
     $scope.author = {"id":"","name":"","":"","image":"default.jpg","biography":"","books":""}
      if(authorId){
      	authorService.getAuthorWithBooks(function(data){
      		$scope.author = data;
      		console.log($scope.author);
      	},authorId)

      }

}])