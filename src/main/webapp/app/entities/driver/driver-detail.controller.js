(function() {
    'use strict';

    angular
        .module('carpoolingCsid2016App')
        .controller('DriverDetailController', DriverDetailController);

    DriverDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Driver'];

    function DriverDetailController($scope, $rootScope, $stateParams, entity, Driver) {
        var vm = this;
        vm.driver = entity;
        vm.load = function (id) {
            Driver.get({id: id}, function(result) {
                vm.driver = result;
            });
        };
        var unsubscribe = $rootScope.$on('carpoolingCsid2016App:driverUpdate', function(event, result) {
            vm.driver = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
