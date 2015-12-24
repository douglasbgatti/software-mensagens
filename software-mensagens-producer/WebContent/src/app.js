'use strict';

var openims = angular.module('openims',['ngRoute', 'ngMessages', 'toastr', 'ui.tree', 'angularModalService', 'ngTable']);

openims.constant('appInfo',
	{ 
		name: 'Software Mensanges',
		versionCode: '0.1'
	});

openims.constant('apiInfo',
	{
		baseUrl: 'http://localhost:8080/software-mensagens-producer/rest/resources'
	});

openims.config(function($routeProvider, $httpProvider){
	console.log('openims.config');

	$routeProvider
	.when('/', {
		templateUrl: 'views/dashboard.html',
		controller: 'DashboardController'		
	})
	.when('/messagebox', {
		templateUrl: 'views/messageBox.html',
		controller: 'MessageboxController'
	})
	.when('/composemessage', {
		templateUrl: 'views/composemessage.html',
		controller: 'ComposeMessageController'
	})
	.when('/managegroups', {
		templateUrl: 'views/managegroups.html',
		controller: 'ManageGroupsController'
	})
	.when('/messageinfo/:id', {
		templateUrl: 'views/messageinfo.html',
		controller: 'MessageInfoController'
	})
	.when('/manageconsumers', {
		templateUrl: 'views/manageconsumers.html',
		controller: 'ManageConsumersController'
	})
	.when('/consumer', {
		templateUrl: 'views/consumer.html',
		controller: 'ConsumerController'
	})

});
