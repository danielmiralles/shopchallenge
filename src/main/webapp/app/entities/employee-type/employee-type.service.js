(function() {
    'use strict';
    angular
        .module('shopchallengeApp')
        .factory('EmployeeType', EmployeeType);

    EmployeeType.$inject = ['$resource'];

    function EmployeeType ($resource) {
        var resourceUrl =  'api/employee-types/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
