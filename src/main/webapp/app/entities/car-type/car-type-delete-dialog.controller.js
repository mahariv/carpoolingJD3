(function() {
    'use strict';

    angular
        .module('carpoolingCsid2016App')
        .controller('CarTypeDeleteController',CarTypeDeleteController);

    CarTypeDeleteController.$inject = ['$uibModalInstance', 'entity', 'CarType'];

    function CarTypeDeleteController($uibModalInstance, entity, CarType) {
        var vm = this;
        vm.carType = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            CarType.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
