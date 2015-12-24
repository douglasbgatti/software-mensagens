'use strict';

openims.controller('ManageConsumersController', function($scope, $location, $filter, NgTableParams, toastr, consumers) {
    $scope.data = [];

    $scope.getAllConsumers = function() {
        consumers.all().then(
            // success
            function(response) {
                console.log('SUCCESS GET ALL CONSUMERS!');
                $scope.data = response.data;
                $scope.tableParams.reload();
            },
            // erro
            function(response) {
                toastr.error('Error!', 'Something went wrong while retrieving consumers!');
            }
        )
    };

    $scope.getAllConsumers();


    $scope.tableParams = new NgTableParams({
        page: 1, // show first page
        count: 10, // count per page
        sorting: {
            id: 'asc' // initial sorting
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

    $scope.openConsumerInfo = function(consumer){
   		window.location = "#/consumer-info/"+consumer.id;
   }


});
