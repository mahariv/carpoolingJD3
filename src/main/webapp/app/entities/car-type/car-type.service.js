(function() {
    'use strict';
    angular
        .module('carpoolingCsid2016App')
        .factory('CarType', CarType);

    CarType.$inject = ['$resource'];

    function CarType ($resource) {
        var resourceUrl =  'api/car-types/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
