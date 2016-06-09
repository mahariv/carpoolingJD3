(function() {
    'use strict';

    angular
        .module('carpoolingCsid2016App')
        .controller('PathDetailController', PathDetailController);

    PathDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Path', 'Location'];

    function PathDetailController($scope, $rootScope, $stateParams, entity, Path, Location) {
        var vm = this;
        vm.path = entity;
        vm.load = function (id) {
            Path.get({id: id}, function(result) {
                vm.path = result;
            });
        };
        var unsubscribe = $rootScope.$on('carpoolingCsid2016App:pathUpdate', function(event, result) {
            vm.path = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
