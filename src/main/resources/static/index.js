(function () {
    'use strict';

    angular
        .module('app', ['ngRoute', 'ngStorage'])
        .config(config)
        .run(run);

    function config($routeProvider, $httpProvider) {
        $routeProvider
            .when('/', {
                templateUrl: 'catalog/catalog.html',
                controller: 'catalogController'
            })
            .otherwise({
                redirectTo: '/'
            });

    }
})();