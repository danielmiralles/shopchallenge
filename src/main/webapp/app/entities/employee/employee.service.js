(function() {
    'use strict';
    angular
        .module('shopchallengeApp')
        .factory('Employee', Employee);

    Employee.$inject = ['$resource', 'DateUtils'];

    function Employee ($resource, DateUtils) {
        var resourceUrl =  'api/employees/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.employmentDate = DateUtils.convertDateTimeFromServer(data.employmentDate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
