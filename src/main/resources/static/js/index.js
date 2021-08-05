angular.module('eshop', []).controller('indexController', function($scope, $http) {
    const root = 'http://' + location.host + '/api/v1';

    $scope.minPrice = 0.01;
    $scope.maxPrice = 0.01;
    $scope.session = null
    $scope.authenticated = false;

    $scope.getSessionToken = function() {
        $http({
            url: root + '/token',
            method: 'GET',
            params: {}
        }).then(function(response) {
            $scope.sessionToken = response.data;
            $scope.session = response.data.token;
            $http.defaults.headers.post[response.data.headerName] = response.data.token;
            $http.defaults.headers.put[response.data.headerName] = response.data.token;
            console.log(response);
        });
    }

    $scope.getCurrentUser = function() {
        $http({
            url: root + '/users/current',
            method: 'GET',
            params: {}
        }).then(function(response) {
            if (response.status == 200) {
                $scope.user = response.data;
                $scope.authenticated = true;
            } else {
                $scope.authenticated = false;
            }
        }).catch(function(err) {
            console.log(err);
        });
    }

    $scope.loadProducts = function(pageIndex) {
        $http({
            url: root + '/products',
            method: 'GET',
            params: {
                page: pageIndex + 1
            }
        }).then(function(response) {
            $scope.filtered = false;
            $scope.page = response.data;
            $scope.updatePages();
            console.log(response);
        });
    };

    $scope.applyFilter = function(pageIndex) {
        $http({
            url: root + '/products/filter',
            method: 'GET',
            params: {
                min: $scope.minPrice,
                max: $scope.maxPrice,
                page: pageIndex + 1
            }
        }).then(function(response) {
            $scope.filtered = true;
            $scope.page = response.data;
            $scope.updatePages();
        });
    };

    $scope.deleteProduct = function(idx, id) {
        $http({
            url: root + '/products/' + id,
            method: 'DELETE',
            headers: {
                [$scope.sessionToken.headerName]: $scope.sessionToken.token
            },
            params: {}
        }).then(function(response) {
            $scope.loadProducts($scope.page.number);
        });
    };

    $scope.loadCart = function() {
        $http({
            url: root + '/carts',
            method: 'GET',
            params: {
                session: $scope.session
            }
        }).then(function(response) {
            $scope.cart = response.data;
            $scope.session = response.data.session;
            console.log(response);
        });
    }

    $scope.addToCart = function(id) {
        $http({
            url: root + '/carts/add/' + id,
            method: 'PUT',
            params: {
                session: $scope.session
            }
        }).then(function(response) {
            $scope.loadCart();
        });
    }

    $scope.removeFromCart = function(id) {
        $http({
            url: root + '/carts/remove/' + id,
            method: 'PUT',
            params: {
                session: $scope.session
            }
        }).then(function(response) {
            $scope.loadCart();
        });
    }

    $scope.deleteFromCart = function(id) {
        $http({
            url: root + '/carts/' + id,
            method: 'DELETE',
            params: {
                session: $scope.session
            }
        }).then(function(response) {
            $scope.loadCart();
        });
    }

    $scope.createOrder = function() {
        $http({
            url: root + '/orders',
            method: 'POST',
            params: {
                session: $scope.session
            }
        }).then(function(response) {
            $scope.loadCart();
        });
    }

    $scope.doLogout = function() {
        $http({
            url: '/logout',
            method: 'POST',
            params: {
                [$scope.sessionToken.parameterName]: $scope.sessionToken.token
            }
        }).then(function(response) {
            $scope.authenticated = false;
            $scope.user = null;
        });
    }

    $scope.hasPermission = function(name) {
        if ($scope.user == null) {
            return false;
        }
        return $scope.user.authorities.includes('ALL') ||
               $scope.user.authorities.includes(name);
    }

    $scope.updatePages = function() {
        $scope.pages = [];
        for(var i = 0; i < $scope.page.totalPages; i++) {
            $scope.pages[i] = i;
        }
    }

    $scope.getSessionToken();
    $scope.getCurrentUser();
    $scope.loadProducts(0);
    $scope.loadCart();
});