var bookProject = angular.module('book-project', [
  'ui.router',
  'ngResource',
  'pascalprecht.translate',
  'smart-table',
  'mgcrea.ngStrap',
  'toastr',
  'angular-loading-bar',
  'ui.select',
  'ngQuickDate'])
  .config(['$stateProvider', '$urlRouterProvider','$locationProvider',
         function($stateProvider, $urlRouterProvider,$locationProvider){
       $urlRouterProvider.otherwise("/");
       
       $stateProvider
       .state('home',{
           url:'/',
           templateUrl:'views/home.html',
           controller:"headerCtrl"
       })
       .state('login',{
           templateUrl: "views/login.html",
           controller: "loginCtrl"
       })
       .state('logout',{
           url:"/logout",
           controller: "logoutCtrl"
       })
       .state("register",{
           url:"/register",
           templateUrl:"views/register.html",
           controller: "registerCtrl"
       })
       .state("single",{
           url:"/single/:itemId",
           templateUrl:"views/single.html"
       })
       .state("test",{
           url:"/test",
           templateUrl:"views/list-search.html"
       })
       .state("user",{
           url:"/user",
           templateUrl:"views/user-profile.html",
           controller:"userProfile"
       })
        .state("allBooks",{
           url:"/all-books",
           templateUrl:"views/all-books.html",
           controller: "bookCtrl"
       })
       .state("authorInfo",{
           url:"/author-info/:authorId",
           templateUrl:"views/author-info.html",
           controller: "authorCtrl"
       })
       .state("user.edit", {
        views:{
          '@':{
                   url:"/user/edit",
                   templateUrl:"views/user-profile.edit.html",
                   controller:"userProfileEdit"
              }
        }
       });
      
      
  }]);

