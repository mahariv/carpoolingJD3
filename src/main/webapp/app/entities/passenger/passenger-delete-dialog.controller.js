(function() {
    'use strict';

    angular
        .module('carpoolingCsid2016App')
        .controller('PassengerDeleteController',PassengerDeleteController);

    PassengerDeleteController.$inject = ['$uibModalInstance', 'entity', 'Passenger'];

    function PassengerDeleteController($uibModalInstance, entity, Passenger) {
        var vm = this;
        vm.passenger = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Passenger.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
