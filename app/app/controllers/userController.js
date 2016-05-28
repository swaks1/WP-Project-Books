bookProject
.controller('headerCtrl', function ($scope,$rootScope,$state, loginService,userProfileService,bookService,authorService,rateBookService)
 { 
    $rootScope.loggedIn = loginService.islogged();
    $rootScope.loggedUser = loginService.isloggedUser();
    $( "#autocomplete").val("");
   
    console.log("HEADER CTRL");
    //za AUTO COMPELTE
    $rootScope.allUsers = [];

    userProfileService.getUsers(function(data){
        var people = data;
        $rootScope.allUsers = [];
        
        $(people).each(function(index,item){
            $rootScope.allUsers.push({
                id:item.id,
                label:item.fname + " "+item.lname,
                date:item.dateCreated
            })
        });


          var autocompleteUsers = $rootScope.allUsers; 
         
              $( "#autocomplete").autocomplete({
                        minLength: 0,
                        source: autocompleteUsers,
                        focus: function( event, ui ) {
                          //$( "#autocomplete" ).val( ui.item.label );
                          return false;
                        },
                        select: function( event, ui ) {
                            userProfileService.viewUser(ui.item.id);
                            $( "#autocomplete" ).val( ui.item.label );
                   
                          return false;
                        }
                      })
                      .autocomplete( "instance" )._renderItem = function( ul, item ) {
                         var icon = $("<img>").attr("src","http://localhost:8080/book-project/api/users/get-image/"+item.id);
                        icon.attr("style","width:40px; height:40px; float:left; display:block");

                        return $( "<li style='height:50px'>" )
                          .append(icon).append( "<a>" + item.label + "<br>" + "" + "</a>" )
                          .appendTo( ul );
                      };
        
      

    });

    // za klik na kopceto SEARCH
  $rootScope.searchUser = function(){
        var text = $("#autocomplete").val();
        var users = $rootScope.allUsers;
         $rootScope.matchedUsers = [];

        $(users).each(function(index,item){       
            if(item.label.toLowerCase().indexOf(text.toLowerCase()) != -1){
                $rootScope.matchedUsers.push(item);
                return;
            }
       
        });

        $state.go('searchUsers');
        
    };  

    if(!$scope.caraouselBooks){
         $scope.caraouselBooks=[];

        bookService.getBooks(function(data){
            $scope.caraouselBooks = data;

            //for featured book
            $scope.book = data[0];
            //rating info
            rateBookService.getAverageRate(function (data){
                $scope.rating = data;
            }, $scope.book.id);

            //featured autor
            $scope.author = [];
                authorService.getAuthorWithBooks(function(data){
                    $scope.author = data;
                },$scope.book.author.id)
            console.log(data);
    });
    }
    
    //funckija on selekt za Searchot za Books 
    $scope.onSelect = function($item, $model, $label){
        $state.go('single',{"itemId":$item.id});
    };



    $scope.slides = [
    {
      image: 'http://lorempixel.com/400/200/'
    },
    {
      image: 'http://lorempixel.com/400/200/food'
    },
    {
      image: 'http://lorempixel.com/400/200/sports'
    },
    {
      image: 'http://lorempixel.com/400/200/people'
    }
  ];

   
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
            '$scope', '$http', '$state',"loginService","$rootScope",
    function($scope, $http, $state, loginService,$rootScope){
        loginService.logout();
        $rootScope.loggedIn = loginService.islogged();
 }])

 .controller("registerCtrl",[
            '$scope','$http','$state','loginService',
    function($scope,$http, $state,loginService){

        var usernames = [];
        loginService.getUsernames(function(data){
            usernames = data;
        });

        $scope.register = function(){
            console.log(usernames);
            if ($scope.user.password !== $scope.user.confirmPassword && $scope.form.$valid)
            {
                $("#noMatch").show();
                return;
                
            }
                $("#noMatch").hide();
            if (usernames.indexOf($scope.user.username.toLowerCase()) != -1 )
            {
               
                $("#userTaken").show();
                return;
                
            }
                $("#userTaken").hide();
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
       }
    
 }])

.controller("userProfileEdit",[
            "$scope","$state","userProfileService","loginService","$rootScope",
    function($scope, $state, userProfileService,loginService,$rootScope){
        var user = loginService.isloggedUser();
        $rootScope.loggedIn = loginService.islogged();
        $scope.user = user;

        $scope.save= function(){
            if(user){
                 var fd = new FormData();
               
                fd.append('id',$scope.user.id);
                fd.append('fname',$scope.user.fname);
                fd.append('lname',$scope.user.lname);
                fd.append('biography',$scope.user.biography);  
                
                var file = $scope.myFile;
                if(file != null)
                {
                    var fileType = file.type;
                    if( fileType.indexOf("image")!= -1 )
                       fd.append('file', file);
                    else 
                        console.log("Toa ne e slika hhihihi ");
                }
               
                userProfileService.updateProfileInfo(function(data){
                    sessionStorage.setItem("user",JSON.stringify(data));
                    location.reload();

                }, fd);
                $scope.$state = $state;
                $state.go("^", { obj : user});
            }
            else
                $state.go("login");
        };

}])

.controller("userProfileEditGenres",[
           '$scope','$http','$state','bookService','userProfileService', '$rootScope', 'loginService',
    function($scope, $http, $state,bookService, userProfileService, $rootScope, loginService){
            $rootScope.loggedIn = loginService.islogged();
        $scope.genres=[];
        var HC = {};
        var user = loginService.isloggedUser();
        HC.userId = user.id;

        bookService.getGenres(function(data){
            $scope.genres=data;
        });

        //checking on fav genres
        var userGenres = [];
        for(index in user.genres){
            userGenres[index] = (user.genres[index].id);
        }
        
        $scope.userGenres=userGenres; 
                   
         $scope.addSelected = function() {
           var cbs = $(".cbGenres");
           HC.genreIds=[];
           cbs.each(function(index,item){
                if(item.checked)
                    HC.genreIds.push(item.id);
           });

           
          userProfileService.addGenres(function(data){
                  sessionStorage.setItem('user',JSON.stringify(data));
                  $state.go("^", { obj : data});

          },HC);


        };
}])

.controller("userProfile",[
            "$scope","$state","userProfileService","loginService","$rootScope",'$stateParams',
    function($scope, $state, userProfileService,loginService,$rootScope,$stateParams){
        var user = loginService.isloggedUser();
        $rootScope.loggedIn = loginService.islogged();

        if($stateParams.obj)
        {
            user = $stateParams.obj;
        }
        if(user){
            console.log($stateParams.obj);
            $scope.userId= user.id;
            $scope.user = user.fname + " " + user.lname;
            $scope.bio = user.biography;
            $scope.genres = user.genres;
            userProfileService.getFavBooks(function(data){
                    $scope.favbooks = data;
            }, user.id);
        }
        else
            $state.go("login");
}])
.controller("visitUserCtrl",[
            "$scope","$state","$stateParams","userProfileService",
    function($scope, $state, $stateParams,userProfileService){
       
        var userId = $stateParams.userId;
         $scope.visitedUser = "";

        userProfileService.getUserById(function(data){
            $scope.visitedUser = data;
           // $("#profilepic").attr('src', "http://localhost:8080/book-project/api/users/get-image/" + userId);

            userProfileService.getFavBooks(function(data){
                    $scope.visitedUser.favbooks = data;
            },userId);

        },userId);

        //console.log(initComment);



}])
;

