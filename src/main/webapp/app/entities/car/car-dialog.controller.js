(function() {
    'use strict';

    angular
        .module('carpoolingCsid2016App')
        .controller('CarDialogController', CarDialogController);

    CarDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Car', 'Driver', 'CarBrand', 'CarType'];

    function CarDialogController ($scope, $stateParams, $uibModalInstance, entity, Car, Driver, CarBrand, CarType) {
        var vm = this;
        vm.car = entity;
        vm.drivers = Driver.query();
        vm.carbrands = CarBrand.query();
        vm.cartypes = CarType.query();
        vm.load = function(id) {
            Car.get({id : id}, function(result) {
                vm.car = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('carpoolingCsid2016App:carUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.car.id !== null) {
                Car.update(vm.car, onSaveSuccess, onSaveError);
            } else {
                Car.save(vm.car, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
