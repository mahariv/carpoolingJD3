(function() {
    'use strict';

    angular
        .module('carpoolingCsid2016App')
        .controller('CarTypeDialogController', CarTypeDialogController);

    CarTypeDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'CarType'];

    function CarTypeDialogController ($scope, $stateParams, $uibModalInstance, entity, CarType) {
        var vm = this;
        vm.carType = entity;
        vm.load = function(id) {
            CarType.get({id : id}, function(result) {
                vm.carType = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('carpoolingCsid2016App:carTypeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.carType.id !== null) {
                CarType.update(vm.carType, onSaveSuccess, onSaveError);
            } else {
                CarType.save(vm.carType, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
