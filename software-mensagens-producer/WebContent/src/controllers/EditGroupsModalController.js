'use strict';

openims.controller('EditGroupsController', ['$scope', 'close', function($scope, close) {

	$scope.groupName = null;

  $scope.close = function(result) {
  		var group = {
	  		groupName: $scope.groupName,
	  		addGroup: 'no'
  		};

	 	close(group, 500);  // close, but give 500ms for bootstrap to animate
  	};

  $scope.addGroup = function(result){
  	
  	var group = {
  		groupName: $scope.groupName,
  		addGroup: 'yes'
  	};

  	console.log('ADDGROUP_MODAL! ' + angular.toJson(group));
  	close(group, 500); 
  };


}]);