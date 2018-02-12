(function() {
    'use strict';

    angular
        .module('shopchallengeApp')
        .factory('EmployeeTypeSearch', EmployeeTypeSearch);

    EmployeeTypeSearch.$inject = ['$resource'];

    function EmployeeTypeSearch($resource) {
        var resourceUrl =  'api/_search/employee-types/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
