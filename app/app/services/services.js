bookProject
.factory('sessionService', ['$http', function($http){
	return{
		set:function(key,value){
			return sessionStorage.setItem(key,value);
		},
		get:function(key){
			return sessionStorage.getItem(key);
		},
		destroy:function(key){
			//$http.post('http://localhost:8080/book-project/api/users/abandon-session');
			return sessionStorage.removeItem(key);
		}
	};
}])
.factory('loginService',function($http, $state, sessionService){
	return{
		login:function(data,scope){
			var $promise = $http.post('http://localhost:8080/book-project/api/users/login',data, {
                    transformRequest: angular.identity,
                    headers: {'Content-Type': undefined}
                }); //send data 
			$promise.then(function(msg){
				var user = msg.data;
				if(user){
					sessionService.set('user',JSON.stringify(user));
					$state.go("home");
					}	       
				else  {
					scope.msgtxt='Incorrect password or username';
					$state.go('login');
				}				   
			}, function(data, status, headers, config) {
				$state.go('login');
            });
		},
		logout:function(){
			sessionService.destroy('user');
			$state.go('home');
		},
		islogged:function(){
			// var $checkSessionServer = $http.get('http://localhost:8080/book-project/api/users/get-session');
			// return $checkSessionServer;
			if(sessionService.get('user')) return true;
			else return false;			
		},
		register:function(data,scope){
			var $promise = $http.post('http://localhost:8080/book-project/api/users/register',data, {
                    transformRequest: angular.identity,
                    headers: {'Content-Type': undefined}
                }); //send data 
			$promise
			.success(function(msg){
				var user = msg;
				console.log(user);
				if(user != null){
					sessionService.set('user',JSON.stringify(user));
					$state.go("home");
					}	       
				else  {
					scope.msgtxt='Incorrect password or username';
					$state.go('login');
				}				   
			}).error(function(data, status, headers, config) {
				$state.go('register');
            });
		}
	}

});