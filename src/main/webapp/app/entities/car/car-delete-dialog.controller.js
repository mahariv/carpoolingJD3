(function() {
    'use strict';

    angular
        .module('carpoolingCsid2016App')
        .controller('CarDeleteController',CarDeleteController);

    CarDeleteController.$inject = ['$uibModalInstance', 'entity', 'Car'];

    function CarDeleteController($uibModalInstance, entity, Car) {
        var vm = this;
        vm.car = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Car.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
