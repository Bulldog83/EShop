$eshop.controller('singleOrderController', function($rootScope, $scope, $http) {

    $scope.initOrder = function() {
        $http({
            url: requestPath + '/carts',
            method: 'GET'
        }).then(function(response) {
            console.log(response);
            console.log($rootScope.user.session);
            $scope.orderData = {
                sessionId: $rootScope.user.session,
                items: response.data.items,
                sumTotal: response.data.sumTotal
            }
        });
    }

    $scope.createOrder = function() {
        $http.post(requestPath + '/orders', $scope.orderData)
            .then(function onSuccess(response) {
                alert("Order successfully created.");
                if ($rootScope.isAuthenticated()) {
                    window.location = '/orders';
                } else {
                    window.location = '/';
                }
            }, function onError(response) {
                console.log(response);
                $scope.errors = response.data.errors;
            });
    }

    $scope.initOrder();
});