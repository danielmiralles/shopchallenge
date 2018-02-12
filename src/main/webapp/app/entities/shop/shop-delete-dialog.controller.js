(function() {
    'use strict';

    angular
        .module('shopchallengeApp')
        .controller('ShopDeleteController',ShopDeleteController);

    ShopDeleteController.$inject = ['$uibModalInstance', 'entity', 'Shop'];

    function ShopDeleteController($uibModalInstance, entity, Shop) {
        var vm = this;

        vm.shop = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Shop.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
