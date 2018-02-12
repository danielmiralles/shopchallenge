(function() {
    'use strict';
    angular
        .module('shopchallengeApp')
        .factory('Assistance', Assistance);

    Assistance.$inject = ['$resource', 'DateUtils'];

    function Assistance ($resource, DateUtils) {
        var resourceUrl =  'api/assistances/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.init = DateUtils.convertDateTimeFromServer(data.init);
                        data.end = DateUtils.convertDateTimeFromServer(data.end);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
