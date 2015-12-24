'use strict';

openims.controller('DashboardController', function($scope, $location, messages, toastr, NgTableParams) {

	$scope.getRecentMessages = function(){
		messages.recentMessages().then(
			// success
			function(response){
				console.log('SUCCESS GET RECENT MESSAGES!');
				$scope.messages = response.data;
			},
			// erro
			function(response){
				toastr.error('Error!', 'Something went wrong while retrieving recent messages!');
			}
			)
	};

	$scope.tableParams = 
		new NgTableParams({
	      page: 1, // show first page
	      count: 10 // count per page
		}, 
		{
			dataset: $scope.message
		});


	$scope.getRecentMessages();

	   $scope.openMessageInfo = function(message){
   		window.location = "#/messageinfo/"+message.id;
   
}
});