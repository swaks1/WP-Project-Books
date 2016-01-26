bookProject
.controller('headerCtrl', function ($rootScope, loginService) { 
    $rootScope.loggedIn = loginService.islogged();
})

.controller("loginCtrl",[
            '$scope','$http','$state','loginService',
    function($scope, $http, $state,loginService){
        $scope.logIn = function(){
            if ($scope.form.$valid) {

                var fd = new FormData();
                fd.append('username', $scope.username);
                fd.append('password',$scope.password);
                loginService.login(fd, $scope );
            }
        }

 }])
 
 .controller("logoutCtrl",[
            '$scope', '$http', '$state',"loginService",
    function($scope, $http, $state, loginService){
        loginService.logout();
 }])

 .controller("registerCtrl",[
            '$scope','$http','$state','loginService','$rootScope',
    function($scope,$http, $state,loginService,$rootScope){
        $scope.register = function(){
            if ($scope.user.password !== $scope.user.confirmPassword && $scope.form.$valid)
            {
                $("#noMatch").show();
                
            }
            if ($scope.user.password === $scope.user.confirmPassword && $scope.form.$valid)
            {
            
            var fd = new FormData();
            fd.append('username',$scope.user.username);
            fd.append('password',$scope.user.password);
            fd.append('fname',$scope.user.fname);
            fd.append('lname',$scope.user.lname);
            fd.append('biography',"");  
            
            var file = $scope.myFile;
            if(file != null)
            {
                var fileType = file.type;
                if( fileType.indexOf("image")!= -1 )
                   fd.append('file', file);
                else 
                    console.log("Toa ne e slika hhihihi ");
            }
            loginService.register(fd,$scope);
            
        }
            // //POST SO REQUEST ATTRIBUT
            // postUrl="http://localhost:8080/book-project/api/users/register";
            // $http({
            //     method: 'POST',
            //     url: postUrl,
            //     headers: {'Content-Type': 'application/x-www-form-urlencoded'},
            //     transformRequest: function(obj) {
            //         var str = [];
            //         for(var p in obj)
            //         str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
            //         return str.join("&");
            //     },
            //     data: {username: "asdasd", password:"dasdas"}
            // }).success(function () {
            //     console.log("ggwp");
            // });
       }
    
 }]);