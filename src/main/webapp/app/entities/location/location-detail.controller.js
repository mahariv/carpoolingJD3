(function() {
    'use strict';

    angular
        .module('carpoolingCsid2016App')
        .controller('LocationDetailController', LocationDetailController);

    LocationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Location'];

    function LocationDetailController($scope, $rootScope, $stateParams, entity, Location) {
        var vm = this;
        vm.location = entity;
        vm.load = function (id) {
            Location.get({id: id}, function(result) {
                vm.location = result;
            });
        };
        var unsubscribe = $rootScope.$on('carpoolingCsid2016App:locationUpdate', function(event, result) {
            vm.location = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
