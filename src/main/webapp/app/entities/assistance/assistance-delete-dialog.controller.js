(function() {
    'use strict';

    angular
        .module('shopchallengeApp')
        .controller('AssistanceDeleteController',AssistanceDeleteController);

    AssistanceDeleteController.$inject = ['$uibModalInstance', 'entity', 'Assistance'];

    function AssistanceDeleteController($uibModalInstance, entity, Assistance) {
        var vm = this;

        vm.assistance = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Assistance.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
