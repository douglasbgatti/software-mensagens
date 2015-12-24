'use strict';

openims.controller('ComposeMessageController', function($scope, $location, messages, toastr, groups) {
	$scope.message = {
		id: 0,
		type: '',
		title: '',
		message: '',
		destination: []

	};

	$scope.sendMessage = function(){

		if($scope.message.destination.length > 0){
			// $scope.userId = 1;
			// $scope.message.id = 0;
			// $scope.message.sentBy = $scope.user;
			// $scope.message.destination = $scope.sendMessageGroups;
			messages.sendMessage($scope.message).then(
				//success
				function(){
					toastr.success('Sucess!', 'Youre message has been sent!');
				},
				//error
				function(){
					toastr.error('Error!', 'Something went wrong youre message was not sent!');
				}	
			)
		}
	};


	$scope.toggle = function (scope) {
        scope.toggle();
      };

	      $scope.getAllGroups = function(){

		      groups.all().then(
		      	// success
		      	function(response){
		      		console.log("SUCCESS!!!");
		      		console.log(response);
		      		$scope.responseGroups = response.data;
		      	},

		      	// error
		      	function(response){
		      		console.log("SUCCESS!!!");
		      		console.log(response.data);
		      	}
		      )
  		};



  		$scope.getAllGroups();

});