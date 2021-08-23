$eshop.controller('singleOrderController', function($rootScope, $scope, $http, $routeParams) {

    $scope.getOrder = function() {
        $http.get(requestPath + '/orders/' + $routeParams.orderId)
            .then(function onSuccess(response) {
                $scope.order = response.data;
                console.log(response);
            }, function onError(response) {
                console.log(response);
            });
    }

    $scope.getOrder();
});