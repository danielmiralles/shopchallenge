(function() {
    'use strict';

    angular
        .module('shopchallengeApp')
        .controller('EmployeeTypeDeleteController',EmployeeTypeDeleteController);

    EmployeeTypeDeleteController.$inject = ['$uibModalInstance', 'entity', 'EmployeeType'];

    function EmployeeTypeDeleteController($uibModalInstance, entity, EmployeeType) {
        var vm = this;

        vm.employeeType = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            EmployeeType.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
