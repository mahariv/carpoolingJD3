(function() {
    'use strict';

    angular
        .module('carpoolingCsid2016App')
        .controller('DriverDeleteController',DriverDeleteController);

    DriverDeleteController.$inject = ['$uibModalInstance', 'entity', 'Driver'];

    function DriverDeleteController($uibModalInstance, entity, Driver) {
        var vm = this;
        vm.driver = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Driver.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
