$eshop.controller('cartController', function($rootScope, $scope, $http) {

    $scope.currentPage = "cart";

    $scope.loadCart = function() {
        $http({
            url: requestPath + '/carts',
            method: 'GET'
        }).then(function(response) {
            $scope.cart = response.data;
            console.log(response);
        });
    }

    $scope.addToCart = function(id) {
        $http({
            url: requestPath + '/carts/add/' + id,
            method: 'PUT'
        }).then(function(response) {
            $scope.loadCart();
        })
    }

    $scope.removeFromCart = function(id) {
        $http({
            url: requestPath + '/carts/remove/' + id,
            method: 'PUT'
        }).then(function(response) {
            $scope.loadCart();
        });
    }

    $scope.deleteFromCart = function(id) {
        $http({
            url: requestPath + '/carts/delete/' + id,
            method: 'PUT'
        }).then(function(response) {
            $scope.loadCart();
        });
    }

    $scope.clearCart = function() {
        $http({
            url: requestPath + '/carts/clear',
            method: 'PUT'
        }).then(function(response) {
            $scope.loadCart();
        });
    }

    $rootScope.mergeCart = function(session) {
        $http({
            url: requestPath + '/carts/merge',
            method: 'PUT',
            params: {
                session: session
            }
        }).then(function(response) {
            $scope.loadCart();
        });
    }

    $scope.loadCart();
});