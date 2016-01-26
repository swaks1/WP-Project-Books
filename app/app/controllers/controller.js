bookProject
.controller("loginCtrl",[
            '$scope','$http','$state',
    function($scope, $http, $state){

        
        console.log("here");
        var usernames = [];
        $http({ method: 'GET', url: 'http://localhost:8080/book-project/api/books' }).
        success(function (data, status, headers, config) {
            usernames = data ;
            console.log(data);
             
         }).
         error(function (data, status, headers, config) {
            $scope.allOrders=[];
            console.log("nema nisto");
        });    



        $scope.logIn = function(){
            if ($scope.form.$valid) {

                 var uploadUrl = 'http://localhost:8080/book-project/api/users/login';
                 var fd = new FormData();
                fd.append('username', $scope.username);
                fd.append('password',$scope.password);
                console.log($scope.username + $scope.password);
                $http.post(uploadUrl, fd, {
                    transformRequest: angular.identity,
                    headers: {'Content-Type': undefined}
                })
                .success(function(data){
                    console.log(data);
                     $state.go("home");
                })
                .error(function(data){
                    console.log(data);
                });
            }
        }

 }])
 
 .controller("registerCtrl",[
            '$scope','$http','$state',
    function($scope,$http, $state){
        $scope.register = function(){
            if ($scope.user.password !== $scope.user.confirmPassword && $scope.form.$valid)
            {
                $("#noMatch").show();
            }
            if ($scope.user.password === $scope.user.confirmPassword && $scope.form.$valid)
            {
            
            var uploadUrl = 'http://localhost:8080/book-project/api/users/register';
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
            $http.post(uploadUrl, fd, {
                transformRequest: angular.identity,
                headers: {'Content-Type': undefined}
            })
            .success(function(data){
                console.log(data);
                $state.go("home");
            })
            .error(function(data){
                console.log(data);
            });
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