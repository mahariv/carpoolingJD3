(function() {
    'use strict';

    angular
        .module('carpoolingCsid2016App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('car-brand', {
            parent: 'entity',
            url: '/car-brand?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'carpoolingCsid2016App.carBrand.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/car-brand/car-brands.html',
                    controller: 'CarBrandController',
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
                    $translatePartialLoader.addPart('carBrand');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('car-brand-detail', {
            parent: 'entity',
            url: '/car-brand/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'carpoolingCsid2016App.carBrand.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/car-brand/car-brand-detail.html',
                    controller: 'CarBrandDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('carBrand');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CarBrand', function($stateParams, CarBrand) {
                    return CarBrand.get({id : $stateParams.id});
                }]
            }
        })
        .state('car-brand.new', {
            parent: 'car-brand',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/car-brand/car-brand-dialog.html',
                    controller: 'CarBrandDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nameBrand: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('car-brand', null, { reload: true });
                }, function() {
                    $state.go('car-brand');
                });
            }]
        })
        .state('car-brand.edit', {
            parent: 'car-brand',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/car-brand/car-brand-dialog.html',
                    controller: 'CarBrandDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CarBrand', function(CarBrand) {
                            return CarBrand.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('car-brand', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('car-brand.delete', {
            parent: 'car-brand',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/car-brand/car-brand-delete-dialog.html',
                    controller: 'CarBrandDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CarBrand', function(CarBrand) {
                            return CarBrand.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('car-brand', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
