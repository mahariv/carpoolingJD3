(function() {
    'use strict';
    angular
        .module('carpoolingCsid2016App')
        .factory('Passenger', Passenger);

    Passenger.$inject = ['$resource'];

    function Passenger ($resource) {
        var resourceUrl =  'api/passengers/:id';

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
