angular.module('eshop', []).controller('indexController', function($scope, $http) {
    const root = 'http://' + location.host + '/products';

    $scope.minPrice = 0.01;
    $scope.maxPrice = 0.01;

    $scope.loadProducts = function(pageIndex) {
        $http({
            url: root,
            method: 'GET',
            params: {
                page: pageIndex + 1
            }
        }).then(function(response) {
            $scope.filtered = false;
            $scope.page = response.data;
            $scope.updatePages();
            console.log(response);
        });
    };

    $scope.applyFilter = function(pageIndex) {
        $http({
            url: root + '/filter',
            method: 'GET',
            params: {
                min: $scope.minPrice,
                max: $scope.maxPrice,
                page: pageIndex + 1
            }
        }).then(function(response) {
            $scope.filtered = true;
            $scope.page = response.data;
            $scope.updatePages();
        });
    };

    $scope.deleteProduct = function(idx, id) {
        $http({
            url: root + '/' + id,
            method: 'DELETE',
            params: {}
        }).then(function(response) {
            $scope.products.splice(idx, 1);
        });
    };

    $scope.updatePages = function() {
        $scope.pages = [];
        for(var i = 0; i < $scope.page.totalPages; i++) {
            $scope.pages[i] = i;
        }
    };

    $scope.loadProducts(0);
});