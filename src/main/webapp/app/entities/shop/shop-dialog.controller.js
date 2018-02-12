(function() {
    'use strict';

    angular
        .module('shopchallengeApp')
        .controller('ShopDialogController', ShopDialogController);

    ShopDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Shop'];

    function ShopDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Shop) {
        var vm = this;

        vm.shop = entity;
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
            if (vm.shop.id !== null) {
                Shop.update(vm.shop, onSaveSuccess, onSaveError);
            } else {
                Shop.save(vm.shop, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('shopchallengeApp:shopUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
