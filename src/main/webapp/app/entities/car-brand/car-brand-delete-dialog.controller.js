(function() {
    'use strict';

    angular
        .module('carpoolingCsid2016App')
        .controller('CarBrandDeleteController',CarBrandDeleteController);

    CarBrandDeleteController.$inject = ['$uibModalInstance', 'entity', 'CarBrand'];

    function CarBrandDeleteController($uibModalInstance, entity, CarBrand) {
        var vm = this;
        vm.carBrand = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            CarBrand.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
