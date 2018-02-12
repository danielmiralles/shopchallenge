(function() {
    'use strict';

    angular
        .module('shopchallengeApp')
        .controller('EmployeeDetailController', EmployeeDetailController);

    EmployeeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Employee', 'EmployeeType'];

    function EmployeeDetailController($scope, $rootScope, $stateParams, previousState, entity, Employee, EmployeeType) {
        var vm = this;

        vm.employee = entity;
        vm.employeeType = EmployeeType.get({id: vm.employee.typeId});
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('shopchallengeApp:employeeUpdate', function(event, result) {
            vm.employee = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
