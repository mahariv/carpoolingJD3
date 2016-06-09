(function() {
    'use strict';

    angular
        .module('carpoolingCsid2016App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('passenger', {
            parent: 'entity',
            url: '/passenger?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'carpoolingCsid2016App.passenger.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/passenger/passengers.html',
                    controller: 'PassengerController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('passenger');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('passenger-detail', {
            parent: 'entity',
            url: '/passenger/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'carpoolingCsid2016App.passenger.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/passenger/passenger-detail.html',
                    controller: 'PassengerDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('passenger');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Passenger', function($stateParams, Passenger) {
                    return Passenger.get({id : $stateParams.id});
                }]
            }
        })
        .state('passenger.new', {
            parent: 'passenger',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/passenger/passenger-dialog.html',
                    controller: 'PassengerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                firstName: null,
                                lastName: null,
                                age: null,
                                phone: null,
                                mail: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('passenger', null, { reload: true });
                }, function() {
                    $state.go('passenger');
                });
            }]
        })
        .state('passenger.edit', {
            parent: 'passenger',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/passenger/passenger-dialog.html',
                    controller: 'PassengerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Passenger', function(Passenger) {
                            return Passenger.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('passenger', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('passenger.delete', {
            parent: 'passenger',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/passenger/passenger-delete-dialog.html',
                    controller: 'PassengerDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Passenger', function(Passenger) {
                            return Passenger.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('passenger', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
