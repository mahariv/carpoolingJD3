(function() {
    'use strict';

    angular
        .module('carpoolingCsid2016App')
        .controller('DriverDialogController', DriverDialogController);

    DriverDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Driver'];

    function DriverDialogController ($scope, $stateParams, $uibModalInstance, entity, Driver) {
        var vm = this;
        vm.driver = entity;
        vm.load = function(id) {
            Driver.get({id : id}, function(result) {
                vm.driver = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('carpoolingCsid2016App:driverUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.driver.id !== null) {
                Driver.update(vm.driver, onSaveSuccess, onSaveError);
            } else {
                Driver.save(vm.driver, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
