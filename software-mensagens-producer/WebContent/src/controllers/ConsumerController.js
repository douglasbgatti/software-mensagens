'use strict';

openims.controller('ConsumerController', function($scope, $location, toastr, consumers, groups) {

    $scope.consumer = {
        id: 0,
        username: '',
        groups: []
    };

    $scope.addNewConsumer = function() {
        if ($scope.consumer.username.length > 0) {
            consumers.addNewConsumer($scope.consumer).then(
                //success
                function(response) {
                    toastr.success('Sucess!', 'Consumer has been added!');
                },
                //error
                function(response) {
                    toastr.error('Error!', 'Something went wrong... Check if the username isnt already used!');
                }

            )

        }
    };

    $scope.getAllGroups = function() {

        groups.all().then(
            // success
            function(response) {
                console.log("SUCCESS!!!");
                console.log(response);
                $scope.responseGroups = response.data;
            },

            // error
            function(response) {
                console.log("ERROR!!!");
                console.log(response.data);
            }
        )
    };

    	$scope.toggle = function (scope) {
        scope.toggle();
      };




      $scope.getAllGroups();


});
