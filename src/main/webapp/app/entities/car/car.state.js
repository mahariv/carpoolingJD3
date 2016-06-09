(function() {
    'use strict';

    angular
        .module('carpoolingCsid2016App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('car', {
            parent: 'entity',
            url: '/car?page&sort&search',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'carpoolingCsid2016App.car.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/car/cars.html',
                    controller: 'CarController',
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
                    $translatePartialLoader.addPart('car');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('car-detail', {
            parent: 'entity',
            url: '/car/{id}',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'carpoolingCsid2016App.car.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/car/car-detail.html',
                    controller: 'CarDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('car');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Car', function($stateParams, Car) {
                    return Car.get({id : $stateParams.id});
                }]
            }
        })
        .state('car.new', {
            parent: 'car',
            url: '/new',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/car/car-dialog.html',
                    controller: 'CarDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                seatCount: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('car', null, { reload: true });
                }, function() {
                    $state.go('car');
                });
            }]
        })
        .state('car.edit', {
            parent: 'car',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/car/car-dialog.html',
                    controller: 'CarDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Car', function(Car) {
                            return Car.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('car', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('car.delete', {
            parent: 'car',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/car/car-delete-dialog.html',
                    controller: 'CarDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Car', function(Car) {
                            return Car.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('car', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
