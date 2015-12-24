'use strict';

openims.controller('MessageboxController', function($scope, $location, $filter, toastr, messages, NgTableParams) {
	$scope.data = [];
	$scope.getAllMessages = function(){
		messages.all().then(
			// success
			function(response){
				console.log('SUCCESS GET RECENT MESSAGES!');
				$scope.data  = response.data;
				$scope.tableParams.reload();
			},
			// erro
			function(response){
				toastr.error('Error!', 'Something went wrong while retrieving messages!');
			}
			)
	};

	$scope.getAllMessages();

   $scope.tableParams = new NgTableParams({
        page: 1,            // show first page
        count: 10,          // count per page
		sorting: {
            id: 'asc'     // initial sorting
        }
    }, {
        filterSwitch: true,
        total: 0, // length of data
        getData: function($defer, params) {
            var orderedData = params.sorting() ? $filter('orderBy')($scope.data, params.orderBy()) : $scope.data;
            params.total($scope.data.length);
            $defer.resolve(orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count()));
        }
    });

   $scope.openMessageInfo = function(message){
   		window.location = "#/messageinfo/"+message.id;
   }


});