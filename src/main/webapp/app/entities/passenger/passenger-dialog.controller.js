(function() {
    'use strict';

    angular
        .module('carpoolingCsid2016App')
        .controller('PassengerDialogController', PassengerDialogController);

    PassengerDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Passenger'];

    function PassengerDialogController ($scope, $stateParams, $uibModalInstance, entity, Passenger) {
        var vm = this;
        vm.passenger = entity;
        vm.load = function(id) {
            Passenger.get({id : id}, function(result) {
                vm.passenger = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('carpoolingCsid2016App:passengerUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.passenger.id !== null) {
                Passenger.update(vm.passenger, onSaveSuccess, onSaveError);
            } else {
                Passenger.save(vm.passenger, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
