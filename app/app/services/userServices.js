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
			if(sessionService.get('user'))
			 return true;
			else 
				return false;			
		},
		isloggedUser:function(){
			if(sessionService.get('user')){
			 	return JSON.parse(sessionService.get('user'));
			}
			else 
				return false;			
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
					alert("Failed to Register..Try Again");
				}				   
			}).error(function(data, status, headers, config) {
				alert("Failed to Register..Try Again Error"+ status);
				$state.go('register');
            });
		},
		getUsernames:function(callback){
			 console.log("getting Usernames");
			 $http({ method: 'GET', url: 'http://localhost:8080/book-project/api/users/all-usernames' }).
	        success(function (data, status, headers, config) {
	            console.log(data);
	          	callback(data);
	             
	         }).
	         error(function (data, status, headers, config) {
	            console.log("ERROR in usernames");
	        }); 
		}
	}

})
.factory("userProfileService",["$http", "loginService", function($http, loginService){
	return{
		getFavBooks:function(callback, userid){
			$http.get("http://localhost:8080/book-project/api/fav-books/of-user/" + userid)
			.success(function (data){
				callback(data);
			})
			.error(function (data){console.log("ERROR in fav-books");})
		},
		updateProfileInfo:function(callback, user){
			$http.post("http://localhost:8080/book-project/api/users/update", user )
			.success(function (data){
				callback(data);
			})
			.error(function (data){console.log("ERROR in fav-books");})
		}
		
	}
}]);