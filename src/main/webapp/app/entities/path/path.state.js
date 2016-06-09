(function() {
    'use strict';

    angular
        .module('carpoolingCsid2016App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('path', {
            parent: 'entity',
            url: '/path?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'carpoolingCsid2016App.path.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/path/paths.html',
                    controller: 'PathController',
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
                    $translatePartialLoader.addPart('path');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('path-detail', {
            parent: 'entity',
            url: '/path/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'carpoolingCsid2016App.path.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/path/path-detail.html',
                    controller: 'PathDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('path');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Path', function($stateParams, Path) {
                    return Path.get({id : $stateParams.id});
                }]
            }
        })
        .state('path.new', {
            parent: 'path',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/path/path-dialog.html',
                    controller: 'PathDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                namePath: null,
                                numOfDistance: null,
                                timeDuration: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('path', null, { reload: true });
                }, function() {
                    $state.go('path');
                });
            }]
        })
        .state('path.edit', {
            parent: 'path',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/path/path-dialog.html',
                    controller: 'PathDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Path', function(Path) {
                            return Path.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('path', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('path.delete', {
            parent: 'path',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/path/path-delete-dialog.html',
                    controller: 'PathDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Path', function(Path) {
                            return Path.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('path', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
