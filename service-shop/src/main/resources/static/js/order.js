$eshop.controller('singleOrderController', function($rootScope, $scope, $http, $routeParams) {

    $scope.getOrder = function() {
        $http.get(requestPath + '/orders/' + $routeParams.orderId)
            .then(function onSuccess(response) {
                $scope.order = response.data;
                if (response.data.status == 'NEW') {
                    $scope.renderPaymentButtons();
                }
                console.log(response);
            }, function onError(response) {
                console.log(response);
            });
    }

    $scope.renderPaymentButtons = function() {
        paypal.Buttons({
            // Call your server to set up the transaction
            createOrder: function(data, actions) {
                return fetch(requestPath + '/paypal/create/' + $scope.order.id, {
                    method: 'post',
                    headers: {
                        'content-type': 'application/json'
                    }
                }).then(function(response) {
                    return response.text();
                });
            },

            // Call your server to finalize the transaction
            onApprove: function(data, actions) {
                return fetch(requestPath + '/paypal/capture/' + data.orderID, {
                    method: 'post',
                    headers: {
                        'content-type': 'application/json'
                    }
                }).then(function(response) {
                   response.text().then(msg => alert(msg));
                   $scope.getOrder();
                });
            },

            onCancel: function (data) {
                console.log("Order canceled: " + data);
            },

            onError: function (err) {
                console.log(err);
            }
        }).render('#paypal-buttons');
    }

    $scope.getOrder();
});