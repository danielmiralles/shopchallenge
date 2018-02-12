(function() {
    'use strict';

    angular
        .module('shopchallengeApp')
        .controller('AssistanceController', AssistanceController);

    AssistanceController.$inject = ['Assistance', 'AssistanceSearch'];

    function AssistanceController(Assistance, AssistanceSearch) {

        var vm = this;

        vm.assistances = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Assistance.query(function(result) {
                vm.assistances = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            AssistanceSearch.query({query: vm.searchQuery}, function(result) {
                vm.assistances = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
