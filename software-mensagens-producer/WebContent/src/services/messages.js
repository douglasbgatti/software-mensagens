'use strict';

openims.factory('messages', function ($http, apiInfo) {
	return {
		all: function(){
			return $http.get(apiInfo.baseUrl+ '/getAllMessages');
		},
		sendMessage: function(newMessage){
			console.log(angular.toJson(newMessage));

			
			// return $http({
			// 	url: apiInfo.baseUrl + '/sendMessage',
			// 	method:'POST',
			// 	data: angular.toJson(newMessage)
			// });

			return $http.post(apiInfo.baseUrl + '/sendMessage', angular.toJson(newMessage) );
		},
		recentMessages: function(){
			return $http.get(apiInfo.baseUrl+ '/getRecentMessages');
		},
		getMessage: function(id){
			return $http.get(apiInfo.baseUrl+ '/getMessage', {params: { 'id': id }} );
		}
	};
});