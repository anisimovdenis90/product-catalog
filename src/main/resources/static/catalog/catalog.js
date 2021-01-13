angular.module('app').controller('catalogController', function ($scope, $http, $window) {
    const contextPath = 'http://localhost:8190/catalog';

    $scope.fillTable = function (pageIndex = 1) {
        $http({
            url: contextPath + '/products',
            method: 'GET',
            params: {
                name: $scope.filter ? $scope.filter.name : null,
                p: pageIndex
            }
        })
            .then(function (response) {
                $scope.ProductsPage = response.data;
                $scope.PaginationArray = $scope.generatePagesInd(1, $scope.ProductsPage.totalPages);
            });
    };

    $scope.generatePagesInd = function(startPage, endPage) {
        let arr = [];
        for (let i = startPage; i < endPage + 1; i++) {
            arr.push(i);
        }
        return arr;
    };

    $scope.delete = function (productId) {
        $http.delete(contextPath + '/products/' + productId)
            .then(function (response) {
                $scope.fillTable();
            });
    };

    $scope.submitCreateNewProduct = function () {
        $http.post(contextPath + '/products', $scope.newProduct)
            .then(function successCallback(response) {
                window.alert('Новый продукт успешно сохранен');
                $scope.newProduct = null;
                $scope.fillTable();
            }, function errorCallback(response) {
                window.alert(response.data.message);
            });
    };

    $scope.submitUpdateProduct = function (productId, description) {
        $http({
            url: contextPath + '/products/' + productId,
            method: 'PUT',
            params: {
                description: description
            }
        })
        .then(function successCallback(response) {
            window.alert('Изменение описание продукта выполнено успешно')
            $scope.fillTable();
        }, function errorCallback(response) {
            window.alert(response.data.message);
        });
    };

    $scope.editProduct = function (productId) {
        $http.get(contextPath + '/products/' + productId)
            .then(function (response) {
                $scope.product = response.data;
        });
    };

    $scope.clearFilter = function () {
            $scope.filter = null;
            $scope.fillTable();
    };

    $scope.fillTable();
});