(function() {
    'use strict';

    angular
        .module('carpoolingCsid2016App')
        .controller('CarBrandDialogController', CarBrandDialogController);

    CarBrandDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'CarBrand'];

    function CarBrandDialogController ($scope, $stateParams, $uibModalInstance, entity, CarBrand) {
        var vm = this;
        vm.carBrand = entity;
        vm.load = function(id) {
            CarBrand.get({id : id}, function(result) {
                vm.carBrand = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('carpoolingCsid2016App:carBrandUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.carBrand.id !== null) {
                CarBrand.update(vm.carBrand, onSaveSuccess, onSaveError);
            } else {
                CarBrand.save(vm.carBrand, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
