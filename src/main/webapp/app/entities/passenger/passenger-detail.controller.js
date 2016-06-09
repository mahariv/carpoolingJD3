(function() {
    'use strict';

    angular
        .module('carpoolingCsid2016App')
        .controller('PassengerDetailController', PassengerDetailController);

    PassengerDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Passenger'];

    function PassengerDetailController($scope, $rootScope, $stateParams, entity, Passenger) {
        var vm = this;
        vm.passenger = entity;
        vm.load = function (id) {
            Passenger.get({id: id}, function(result) {
                vm.passenger = result;
            });
        };
        var unsubscribe = $rootScope.$on('carpoolingCsid2016App:passengerUpdate', function(event, result) {
            vm.passenger = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
