(function() {
    'use strict';

    angular
        .module('carpoolingCsid2016App')
        .controller('PathDialogController', PathDialogController);

    PathDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Path', 'Location'];

    function PathDialogController ($scope, $stateParams, $uibModalInstance, entity, Path, Location) {
        var vm = this;
        vm.path = entity;
        vm.locations = Location.query();
        vm.load = function(id) {
            Path.get({id : id}, function(result) {
                vm.path = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('carpoolingCsid2016App:pathUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.path.id !== null) {
                Path.update(vm.path, onSaveSuccess, onSaveError);
            } else {
                Path.save(vm.path, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
