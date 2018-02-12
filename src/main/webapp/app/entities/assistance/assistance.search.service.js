(function() {
    'use strict';

    angular
        .module('shopchallengeApp')
        .factory('AssistanceSearch', AssistanceSearch);

    AssistanceSearch.$inject = ['$resource'];

    function AssistanceSearch($resource) {
        var resourceUrl =  'api/_search/assistances/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
