angular.module('app', ['toastr', 'ngAnimate'])

  .config(function(toastrConfig) {
    toastrConfig.positionClass = 'toast-bottom-right';
    toastrConfig.timeOut = 0;
    //toastrConfig.closeButton = true;
    //toastrConfig.maxOpened = 3;
    //toastrConfig.autoDismiss = true;
    toastrConfig.extendedTimeOut = 0;
    //toastrConfig.preventDuplicates = true;
    //toastrConfig.preventOpenDuplicates = true;
    //toastrConfig.prepend = true;
    //toastrConfig.templates.toast = 'template.html';
    //toastrConfig.progressBar = true;
    //toastrConfig.newestOnTop = false;
    //toastrConfig.target = '#herae';
  })

  .run(function($templateCache) {
    $templateCache.put('foo', '<div>{{extraData}}</div>');
  })

  .controller('MainCtrl', function($scope, $timeout, $interval, toastr, $q) {
    var toast;

    $scope.fn = function() {
      console.log('Works!');
    };

    toastr.info('We are open today from 10 to 22', 'Information');

    var x = 1;
    //$interval(function() {
    //  toastr.info('success', 'Toastr ' + x);
    //  x++;
    //}, 10, 20);

    $scope.open = function() {
      var toast = toastr.info('I am gonna bloow ' + x++, null);

      //setTimeout(function() {
      //  console.log('Opened: ', toast.isOpened);
      //}, 10000);
    };
  });
