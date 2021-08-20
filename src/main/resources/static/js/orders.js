$eshop.controller('ordersController', function($rootScope, $scope, $http) {

    $scope.currentPage = "orders";

    $scope.removeOrders = function() {
        if ($scope.orders) {
            delete($scope.orders);
        }
    }

    $scope.loadOrders = function() {
        $scope.removeOrders();
        $http.get(requestPath + '/orders')
            .then(function onSuccess(response) {
                $scope.orders = response.data;
                console.log(response);
            }, function onError(response) {
                console.log(response);
            });
    }

    $rootScope.registerOnLogin($scope.loadOrders);
    $rootScope.registerOnLogout($scope.removeOrders);

    if ($rootScope.isAuthenticated()) {
        $scope.loadOrders();
    }
});