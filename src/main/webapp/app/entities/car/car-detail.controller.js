(function() {
    'use strict';

    angular
        .module('carpoolingCsid2016App')
        .controller('CarDetailController', CarDetailController);

    CarDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Car', 'Driver', 'CarBrand', 'CarType'];

    function CarDetailController($scope, $rootScope, $stateParams, entity, Car, Driver, CarBrand, CarType) {
        var vm = this;
        vm.car = entity;
        vm.load = function (id) {
            Car.get({id: id}, function(result) {
                vm.car = result;
            });
        };
        var unsubscribe = $rootScope.$on('carpoolingCsid2016App:carUpdate', function(event, result) {
            vm.car = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
