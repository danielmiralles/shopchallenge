(function() {
    'use strict';

    angular
        .module('shopchallengeApp')
        .controller('AssistanceDetailController', AssistanceDetailController);

    AssistanceDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Assistance', 'Shop', 'Employee'];

    function AssistanceDetailController($scope, $rootScope, $stateParams, previousState, entity, Assistance, Shop, Employee) {
        var vm = this;

        vm.assistance = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('shopchallengeApp:assistanceUpdate', function(event, result) {
            vm.assistance = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
