angular.module('eshop', ['ngStorage']).controller('indexController', function($scope, $http, $localStorage) {
    const rootPath = 'http://' + location.host;
    const requestPath = rootPath + '/api/v1';

    $scope.minPrice = 0.01;
    $scope.maxPrice = 0.01;
    $scope.user = {};

    $scope.getSession = function() {
        $http({
            url: rootPath + '/session',
            method: 'GET',
            params: {}
        }).then(function(response) {
            $http.defaults.headers.common['X-SESSION'] = response.data;
            $localStorage.activeSession = response.data
            $scope.user.session = response.data;
            console.log(response);
        });
    }

    $scope.loadProducts = function(pageIndex) {
        $http({
            url: requestPath + '/products',
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
            url: requestPath + '/products/filter',
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
            url: requestPath + '/products/' + id,
            method: 'DELETE'
        }).then(function(response) {
            $scope.loadProducts($scope.page.number);
        });
    };

    $scope.loadCart = function() {
        console.log($http.defaults.headers.common)
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
        });
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

    $scope.mergeCart = function(session) {
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

    $scope.createOrder = function() {
        $http({
            url: requestPath + '/orders',
            method: 'POST'
        }).then(function(response) {
            $scope.loadCart();
            if ($scope.user.authenticated) {
                $scope.loadOrders();
            }
        });
    }

    $scope.loadOrders = function() {
        $http.get(requestPath + '/orders')
            .then(function onSuccess(response) {
                $scope.orders = response.data;
            }, function onError(response) {
                console.log(response);
            });
    }

    $scope.clearUser = function () {
        delete $localStorage.activeUser;
        $http.defaults.headers.common.Authorization = '';
        $scope.user.authenticated = false;
        $scope.user.authorities = null;
        $scope.user.username = null;
        $scope.user.password = null;
    };

    $scope.doLogout = function() {
        $scope.clearUser();
        $scope.getSession();
        $scope.loadCart();
    }

    $scope.doLogin = function() {
        $http
            .post(rootPath + '/login', $scope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $localStorage.activeUser = {
                        firstname: response.data.firstname,
                        lastname: response.data.lastname,
                        token: response.data.token,
                        session: response.data.session,
                        authorities: response.data.authorities
                    };
                    var session = $scope.user.session;
                    $http.defaults.headers.common['X-SESSION'] = response.data.session;
                    $scope.user.firstname = response.data.firstname;
                    $scope.user.lastname = response.data.lastname;
                    $scope.user.session = response.data.session;
                    $scope.user.authorities = response.data.authorities;
                    $scope.user.authenticated = true;
                    $localStorage.activeSession = null;
                    $scope.user.username = null;
                    $scope.user.password = null;

                    $scope.mergeCart(session);
                    $scope.loadOrders();
                }
            }, function errorCallback(response) {
                console.log(response);
            });
    }

    $scope.checkLogin = function() {
        $http
            .get(rootPath + '/login')
            .then(function onSuccess(response) {
                $http.defaults.headers.common['X-SESSION'] = $localStorage.activeUser.session;
                $scope.user.firstname = $localStorage.activeUser.firstname;
                $scope.user.lastname = $localStorage.activeUser.lastname;
                $scope.user.session = $localStorage.activeUser.session;
                $scope.user.authorities = $localStorage.activeUser.authorities;
                $scope.user.authenticated = true;
                $scope.loadOrders();
            }, function onError(response) {
                $scope.clearUser();
                $scope.getSession();
            });
    }

    $scope.hasPermission = function(name) {
        if ($scope.user.authorities == null) {
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

    if ($localStorage.activeUser) {
        $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.activeUser.token;
        $scope.checkLogin();
    } else if ($localStorage.activeSession) {
        $http.defaults.headers.common['X-SESSION'] = $localStorage.activeSession;
        $scope.user.session = $localStorage.activeSession;
    } else {
        $scope.getSession();
    }

    $scope.loadProducts(0);
    $scope.loadCart();
});