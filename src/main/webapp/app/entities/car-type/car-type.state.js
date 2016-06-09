(function() {
    'use strict';

    angular
        .module('carpoolingCsid2016App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('car-type', {
            parent: 'entity',
            url: '/car-type?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'carpoolingCsid2016App.carType.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/car-type/car-types.html',
                    controller: 'CarTypeController',
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
                    $translatePartialLoader.addPart('carType');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('car-type-detail', {
            parent: 'entity',
            url: '/car-type/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'carpoolingCsid2016App.carType.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/car-type/car-type-detail.html',
                    controller: 'CarTypeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('carType');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CarType', function($stateParams, CarType) {
                    return CarType.get({id : $stateParams.id});
                }]
            }
        })
        .state('car-type.new', {
            parent: 'car-type',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/car-type/car-type-dialog.html',
                    controller: 'CarTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nameType: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('car-type', null, { reload: true });
                }, function() {
                    $state.go('car-type');
                });
            }]
        })
        .state('car-type.edit', {
            parent: 'car-type',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/car-type/car-type-dialog.html',
                    controller: 'CarTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CarType', function(CarType) {
                            return CarType.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('car-type', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('car-type.delete', {
            parent: 'car-type',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/car-type/car-type-delete-dialog.html',
                    controller: 'CarTypeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CarType', function(CarType) {
                            return CarType.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('car-type', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
