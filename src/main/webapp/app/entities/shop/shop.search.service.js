(function() {
    'use strict';

    angular
        .module('shopchallengeApp')
        .factory('ShopSearch', ShopSearch);

    ShopSearch.$inject = ['$resource'];

    function ShopSearch($resource) {
        var resourceUrl =  'api/_search/shops/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
