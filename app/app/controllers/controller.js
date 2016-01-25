bookProject
.controller("loginCtrl",[
            '$scope','$http','$state',
    function($scope, $http, $state){
        $scope.logIn = function(){

            //POST SO REQUEST ATTRIBUT
            var req = {
                method: 'POST',
                url: 'http://localhost:8080/book-project/api/users/login',
                headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
                },
                data: "username="+ $scope.username +"&password="+encodeURIComponent($scope.password)
                }

            $http(req).then(function(data){
                console.log(data.data);
            }, function(data){
            console.log(data);
            });
        }

    
 }]).controller("registerCtr",[
            '$scope','$http','$state',
    function($scope,$http, $state){
        $scope.register = function(){
            //POST SO REQUEST ATTRIBUT
            postUrl="http://localhost:8080/book-project/api/users/register";
            $http({
                method: 'POST',
                url: postUrl,
                headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                transformRequest: function(obj) {
                    var str = [];
                    for(var p in obj)
                    str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
                    return str.join("&");
                },
                data: {newPizzaType: "asdasd", password:"dasdas"}
            }).success(function () {
                console.log("ggwp");
            });
       }
    
 }]);