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
           templateUrl:'views/home.html'
       })
       .state('login',{
           url:"/login",
           templateUrl: "views/login.html",
           controller: "loginCtrl"
       })
       .state("register",{
           url:"/register",
           templateUrl:"views/register.html",
           controller: "registerCtrl"
       })
       .state("single",{
           url:"/single",
           templateUrl:"views/single.html"
       })
       .state("user",{
           url:"/user",
           templateUrl:"views/user-profile.html"
       })
       .state("user.edit", {
        views:{
          '@':{
                   url:"/user/edit",
                   templateUrl:"views/user-profile.edit.html"
              }
        }
       });
      
      
  }]);

