'use strict';

openims.factory('consumers', function ($http, apiInfo) {
	return {
		all: function(){
			return $http.get(apiInfo.baseUrl+ '/getAllConsumers');
		},
		addNewConsumer: function(newConsumer){
			console.log(angular.toJson(newConsumer));
			return $http.post(apiInfo.baseUrl + '/addNewConsumer', angular.toJson(newConsumer) );
		},
		getConsumer: function(id){
			return $http.get(apiInfo.baseUrl+ '/getConsumer', {params: { 'id': id }} );
		}
	};
});