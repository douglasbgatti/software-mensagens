'use strict';

openims.controller('MessageInfoController', function($scope, $routeParams, messages, toastr) {

	$scope.messageId = $routeParams.id;

	$scope.getMessage = function(){
		messages.getMessage($scope.messageId).then(

			// success
			function(response){
				$scope.message = response.data;
				console.log(angular.toJson($scope.message));
				toastr.success('Success!', 'Message Retrieved Successfully!');
			},
			// error
			function(response){
				toastr.error('Error!', 'Something went wrong while retrieving your message!');
			}
			)
	}

	$scope.getMessage();

	$scope.redirect = function(location){
		if (location === 'messagebox')
			window.location = '#/messagebox'
	}

});