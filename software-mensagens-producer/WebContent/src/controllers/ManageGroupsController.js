'use strict';

openims.controller('ManageGroupsController', function($scope, $location, ModalService, groups) {


 $scope.showModal = function(nodeData) {

    // Just provide a template url, a controller and call 'showModal'.
    ModalService.showModal({
      templateUrl: "views/modal/editGroups.html",
      controller: "EditGroupsController"
    }).then(function(modal) {
      // The modal object has the element built, if this is a bootstrap modal
      // you can call 'modal' to show it, if it's a custom modal just show or hide
      // it as you need to.

      modal.element.modal();

      modal.close.then(function(result){
      	$scope.group = result;
      	console.log("add node data:" + angular.toJson($scope.group));

      	$scope.node = {
      		id : nodeData.id,
      		title : $scope.group.groupName,
      		nodes : []
      	};

	  	
	  	if($scope.group.addGroup === 'yes'){
	  		console.log("NODEDATA:" + angular.toJson(nodeData));
	  		console.log('POST:'+ angular.toJson($scope.node) );
	  		$scope.addNewGroup(angular.toJson($scope.node));

	  		nodeData.nodes.push({
				id: nodeData.id * 10 + nodeData.nodes.length,
				// title: nodeData.title + '.' + (nodeData.nodes.length + 1),
				title: $scope.group.groupName,
				nodes: []
			});
  		}

      });

    });

  };

   $scope.showModalNewGroup = function() {

    // Just provide a template url, a controller and call 'showModal'.
    ModalService.showModal({
      templateUrl: "views/modal/editGroups.html",
      controller: "EditGroupsController"
    }).then(function(modal) {
      // The modal object has the element built, if this is a bootstrap modal
      // you can call 'modal' to show it, if it's a custom modal just show or hide
      // it as you need to.

      modal.element.modal();

      modal.close.then(function(result){
        $scope.group = result;
        console.log("add node data:" + angular.toJson($scope.group));

        $scope.node = {
          id : 0,
          title : $scope.group.groupName,
          nodes : []
        };

      
      if($scope.group.addGroup === 'yes'){
        console.log('POST:'+ angular.toJson($scope.node) );
        $scope.addNewGroup(angular.toJson($scope.node));

      // $scope.getAllGroups();

      //   nodeData.nodes.push({
      //   id: nodeData.id * 10 + nodeData.nodes.length,
      //   // title: nodeData.title + '.' + (nodeData.nodes.length + 1),
      //   title: $scope.group.groupName,
      //   nodes: []
      // });
      }

      });

    });

  };


      $scope.remove = function (scope) {
        scope.remove();
      };

      $scope.toggle = function (scope) {
        scope.toggle();
      };

      $scope.newSubItem = function (scope) {
        var nodeData = scope.$modelValue;

        $scope.showModal(nodeData);
      };

      $scope.newItem = function(){
        $scope.showModalNewGroup();
      };

 

      // $scope.tree2 = [{
      //   'id': 1,
      //   'title': 'tree2 - item1',
      //   'nodes': []
      // }, {
      //   'id': 2,
      //   'title': 'tree2 - item2',
      //   'nodes': []
      // }, {
      //   'id': 3,
      //   'title': 'tree2 - item3',
      //   'nodes': []
      // }, {
      //   'id': 4,
      //   'title': 'tree2 - item4',
      //   'nodes': []
      // }];

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


  		$scope.addNewGroup = function(node){
  			groups.addNewGroup(node).then(
  				// success
  				function(response){
  					console.log('SUCCESS');
            $scope.responseGroups = response.data;
  				},
  				// error
  				function(response){
  					console.log('ERROR - addNewGroup:' + angular.toJson(response));
  				}
  				)
  		}

  		$scope.getAllGroups();


});