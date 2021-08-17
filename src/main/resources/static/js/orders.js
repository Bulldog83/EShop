$eshop.controller('ordersController', function($rootScope, $scope, $http) {

    $scope.currentPage = "orders";

    $scope.loadOrders = function() {
        $http.get(requestPath + '/orders')
            .then(function onSuccess(response) {
                $scope.orders = response.data;
                console.log(response);
            }, function onError(response) {
                console.log(response);
            });
    }

    $rootScope.registerOnLogin($scope.loadOrders);
    $rootScope.registerOnLogout(function() {
        delete($scope.orders);
    });
});