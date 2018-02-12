(function() {
    'use strict';

    angular
        .module('shopchallengeApp')
        .controller('AssistanceDialogController', AssistanceDialogController);

    AssistanceDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Assistance', 'Shop', 'Employee'];

    function AssistanceDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Assistance, Shop, Employee) {
        var vm = this;

        vm.assistance = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.shops = Shop.query();
        vm.employees = Employee.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.assistance.id !== null) {
                Assistance.update(vm.assistance, onSaveSuccess, onSaveError);
            } else {
                Assistance.save(vm.assistance, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('shopchallengeApp:assistanceUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.init = false;
        vm.datePickerOpenStatus.end = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
