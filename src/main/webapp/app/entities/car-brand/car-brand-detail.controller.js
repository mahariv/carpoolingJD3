(function() {
    'use strict';

    angular
        .module('carpoolingCsid2016App')
        .controller('CarBrandDetailController', CarBrandDetailController);

    CarBrandDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'CarBrand'];

    function CarBrandDetailController($scope, $rootScope, $stateParams, entity, CarBrand) {
        var vm = this;
        vm.carBrand = entity;
        vm.load = function (id) {
            CarBrand.get({id: id}, function(result) {
                vm.carBrand = result;
            });
        };
        var unsubscribe = $rootScope.$on('carpoolingCsid2016App:carBrandUpdate', function(event, result) {
            vm.carBrand = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
