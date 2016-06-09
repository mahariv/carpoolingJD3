(function() {
    'use strict';

    angular
        .module('carpoolingCsid2016App')
        .controller('CarTypeDetailController', CarTypeDetailController);

    CarTypeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'CarType'];

    function CarTypeDetailController($scope, $rootScope, $stateParams, entity, CarType) {
        var vm = this;
        vm.carType = entity;
        vm.load = function (id) {
            CarType.get({id: id}, function(result) {
                vm.carType = result;
            });
        };
        var unsubscribe = $rootScope.$on('carpoolingCsid2016App:carTypeUpdate', function(event, result) {
            vm.carType = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
