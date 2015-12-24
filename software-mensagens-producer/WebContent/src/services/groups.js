'use strict';

openims.factory('groups', function ($http, apiInfo) {
	return {
		all: function(){
			return $http.get(apiInfo.baseUrl + '/getAllGroups');
		},
		addNewGroup: function(node){
			return $http.post(apiInfo.baseUrl + '/addNewGroup', node);
		}
		// sendMessage: function(newMessage){
		// 	console.log(angular.toJson(newMessage));

			
		// 	// return $http({
		// 	// 	url: apiInfo.baseUrl + '/sendMessage',
		// 	// 	method:'POST',
		// 	// 	data: angular.toJson(newMessage)
		// 	// });

		// 	return $http.post(apiInfo.baseUrl + '/sendMessage', angular.toJson(newMessage));
		// }
	};
});