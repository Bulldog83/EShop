$eshop.controller('registerController', function($rootScope, $scope, $http, $window) {

       $scope.doRegister = function() {
            var $user = $rootScope.user;
            if ($user.password != $scope.passwordConfirm) {
                alert('Password and confirmation don\'t match.');
                return;
            }
            $http.post(requestPath + '/users/register', $user)
                .then(function onSuccess(response) {
                    alert("You've successfully registered.");
                    $rootScope.doLogin();
                    $window.location = '/';
                }, function onError(response) {
                    console.log(response);
                    $scope.errors = response.data.errors;
                });
       }
});