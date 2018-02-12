(function() {
    'use strict';

    angular
        .module('shopchallengeApp')
        .controller('EmployeeTypeDetailController', EmployeeTypeDetailController);

    EmployeeTypeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'EmployeeType'];

    function EmployeeTypeDetailController($scope, $rootScope, $stateParams, previousState, entity, EmployeeType) {
        var vm = this;

        vm.employeeType = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('shopchallengeApp:employeeTypeUpdate', function(event, result) {
            vm.employeeType = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
