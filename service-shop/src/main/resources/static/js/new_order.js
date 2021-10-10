$eshop.controller('newOrderController', function($rootScope, $scope, $http, $location) {

    $scope.initOrder = function() {
        $http({
            url: requestPath + '/carts',
            method: 'GET'
        }).then(function(response) {
            console.log(response);
            $scope.orderData = {
                sessionId: $rootScope.user.sessionId,
                items: response.data.items,
                sumTotal: response.data.sumTotal,
                address : {
                    country: 'RU'
                }
            }
        });
    }

    $scope.createOrder = function() {
        $http.post(requestPath + '/orders', $scope.orderData)
            .then(function onSuccess(response) {
                alert("Order successfully created.");
                if ($rootScope.isAuthenticated()) {
                    $location.path('/orders');
                } else {
                    $location.path('/');
                }
            }, function onError(response) {
                console.log(response);
                $scope.errors = response.data.errors;
            });
    }

    $scope.initOrder();
});