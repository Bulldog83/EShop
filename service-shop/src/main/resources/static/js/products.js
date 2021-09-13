$eshop.controller('productsController', function($rootScope, $scope, $http) {
    $scope.currentPage = "products";

    $scope.products_data = {
        page: 1
    }

    $scope.loadProducts = function() {
        $http({
            url: requestPath + '/products',
            method: 'GET',
            params: $scope.products_data
        }).then(function(response) {
            $scope.page = response.data;
            $scope.updatePages();
            console.log(response);
        }).catch(function(error) {
            console.log(error);
        });
    };

    $scope.loadPage = function(pageIndex) {
        $scope.products_data.page = pageIndex;
        $scope.loadProducts();
    }

    $scope.deleteProduct = function(idx, id) {
        $http.delete(requestPath + '/products/' + id)
            .then(function(response) {
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