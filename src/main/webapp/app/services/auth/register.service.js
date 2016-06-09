(function () {
    'use strict';

    angular
        .module('carpoolingCsid2016App')
        .factory('Register', Register);

    Register.$inject = ['$resource'];

    function Register ($resource) {
        return $resource('api/register', {}, {});
    }
})();
