$eshop.controller('indexController', function($rootScope, $scope, $http) {

    $scope.currentPage = "products";

    $scope.minPrice = 0.01;
    $scope.maxPrice = 0.01;

    $scope.loadProducts = function(pageIndex = 0) {
        $http({
            url: requestPath + '/products',
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
            url: requestPath + '/products/filter',
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
            url: requestPath + '/products/' + id,
            method: 'DELETE'
        }).then(function(response) {
            $scope.loadProducts($scope.page.number);
        });
    };

    $scope.addToCart = function(id) {
        $http({
            url: requestPath + '/carts/add/' + id,
            method: 'PUT'
        });
    }

    $scope.updatePages = function() {
        $scope.pages = [];
        for(var i = 0; i < $scope.page.totalPages; i++) {
            $scope.pages[i] = i;
        }
    }

    $scope.loadProducts();
});