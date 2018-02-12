(function() {
    'use strict';

    angular
        .module('shopchallengeApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('employee-type', {
            parent: 'entity',
            url: '/employee-type?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'EmployeeTypes'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/employee-type/employee-types.html',
                    controller: 'EmployeeTypeController',
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
            }
        })
        .state('employee-type-detail', {
            parent: 'employee-type',
            url: '/employee-type/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'EmployeeType'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/employee-type/employee-type-detail.html',
                    controller: 'EmployeeTypeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'EmployeeType', function($stateParams, EmployeeType) {
                    return EmployeeType.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'employee-type',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('employee-type-detail.edit', {
            parent: 'employee-type-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/employee-type/employee-type-dialog.html',
                    controller: 'EmployeeTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['EmployeeType', function(EmployeeType) {
                            return EmployeeType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('employee-type.new', {
            parent: 'employee-type',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/employee-type/employee-type-dialog.html',
                    controller: 'EmployeeTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                salary: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('employee-type', null, { reload: 'employee-type' });
                }, function() {
                    $state.go('employee-type');
                });
            }]
        })
        .state('employee-type.edit', {
            parent: 'employee-type',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/employee-type/employee-type-dialog.html',
                    controller: 'EmployeeTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['EmployeeType', function(EmployeeType) {
                            return EmployeeType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('employee-type', null, { reload: 'employee-type' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('employee-type.delete', {
            parent: 'employee-type',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/employee-type/employee-type-delete-dialog.html',
                    controller: 'EmployeeTypeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['EmployeeType', function(EmployeeType) {
                            return EmployeeType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('employee-type', null, { reload: 'employee-type' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
