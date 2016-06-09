(function() {
    'use strict';

    angular
        .module('carpoolingCsid2016App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('driver', {
            parent: 'entity',
            url: '/driver?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'carpoolingCsid2016App.driver.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/driver/drivers.html',
                    controller: 'DriverController',
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
                    $translatePartialLoader.addPart('driver');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('driver-detail', {
            parent: 'entity',
            url: '/driver/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'carpoolingCsid2016App.driver.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/driver/driver-detail.html',
                    controller: 'DriverDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('driver');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Driver', function($stateParams, Driver) {
                    return Driver.get({id : $stateParams.id});
                }]
            }
        })
        .state('driver.new', {
            parent: 'driver',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/driver/driver-dialog.html',
                    controller: 'DriverDialogController',
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
                    $state.go('driver', null, { reload: true });
                }, function() {
                    $state.go('driver');
                });
            }]
        })
        .state('driver.edit', {
            parent: 'driver',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/driver/driver-dialog.html',
                    controller: 'DriverDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Driver', function(Driver) {
                            return Driver.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('driver', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('driver.delete', {
            parent: 'driver',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/driver/driver-delete-dialog.html',
                    controller: 'DriverDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Driver', function(Driver) {
                            return Driver.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('driver', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
