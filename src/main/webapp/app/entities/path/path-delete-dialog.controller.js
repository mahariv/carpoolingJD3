(function() {
    'use strict';

    angular
        .module('carpoolingCsid2016App')
        .controller('PathDeleteController',PathDeleteController);

    PathDeleteController.$inject = ['$uibModalInstance', 'entity', 'Path'];

    function PathDeleteController($uibModalInstance, entity, Path) {
        var vm = this;
        vm.path = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Path.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
