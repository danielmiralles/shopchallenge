(function() {
    'use strict';

    angular
        .module('shopchallengeApp')
        .controller('EmployeeTypeDialogController', EmployeeTypeDialogController);

    EmployeeTypeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'EmployeeType'];

    function EmployeeTypeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, EmployeeType) {
        var vm = this;

        vm.employeeType = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.employeeType.id !== null) {
                EmployeeType.update(vm.employeeType, onSaveSuccess, onSaveError);
            } else {
                EmployeeType.save(vm.employeeType, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('shopchallengeApp:employeeTypeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
