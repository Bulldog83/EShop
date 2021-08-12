$eshop.controller('ordersController', function($rootScope, $scope, $http) {

    $scope.currentPage = "orders";

    $scope.loadOrders = function() {
        $http.get(requestPath + '/orders')
            .then(function onSuccess(response) {
                $scope.orders = response.data;
            }, function onError(response) {
                console.log(response);
            });
    }

    if ($rootScope.user.authenticated) {
        $scope.loadOrders();
    }
});