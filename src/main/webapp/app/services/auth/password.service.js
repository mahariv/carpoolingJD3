(function() {
    'use strict';

    angular
        .module('carpoolingCsid2016App')
        .factory('Password', Password);

    Password.$inject = ['$resource'];

    function Password($resource) {
        var service = $resource('api/account/change_password', {}, {});

        return service;
    }
})();
