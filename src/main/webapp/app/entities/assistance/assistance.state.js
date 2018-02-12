(function() {
    'use strict';

    angular
        .module('shopchallengeApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('assistance', {
            parent: 'entity',
            url: '/assistance',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Assistances'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/assistance/assistances.html',
                    controller: 'AssistanceController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('assistance-detail', {
            parent: 'assistance',
            url: '/assistance/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Assistance'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/assistance/assistance-detail.html',
                    controller: 'AssistanceDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Assistance', function($stateParams, Assistance) {
                    return Assistance.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'assistance',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('assistance-detail.edit', {
            parent: 'assistance-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/assistance/assistance-dialog.html',
                    controller: 'AssistanceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Assistance', function(Assistance) {
                            return Assistance.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('assistance.new', {
            parent: 'assistance',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/assistance/assistance-dialog.html',
                    controller: 'AssistanceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                init: null,
                                end: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('assistance', null, { reload: 'assistance' });
                }, function() {
                    $state.go('assistance');
                });
            }]
        })
        .state('assistance.edit', {
            parent: 'assistance',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/assistance/assistance-dialog.html',
                    controller: 'AssistanceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Assistance', function(Assistance) {
                            return Assistance.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('assistance', null, { reload: 'assistance' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('assistance.delete', {
            parent: 'assistance',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/assistance/assistance-delete-dialog.html',
                    controller: 'AssistanceDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Assistance', function(Assistance) {
                            return Assistance.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('assistance', null, { reload: 'assistance' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
