(function() {
    'use strict';

    angular
        .module('carpoolingCsid2016App')
        .factory('notificationInterceptor', notificationInterceptor);

    notificationInterceptor.$inject = ['$q', 'AlertService'];

    function notificationInterceptor ($q, AlertService) {
        var service = {
            response: response
        };

        return service;

        function response (response) {
            var alertKey = response.headers('X-carpoolingCsid2016App-alert');
            if (angular.isString(alertKey)) {
                AlertService.success(alertKey, { param : response.headers('X-carpoolingCsid2016App-params')});
            }
            return response;
        }
    }
})();
