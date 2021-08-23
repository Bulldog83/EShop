const rootPath = 'http://' + location.host;
const requestPath = rootPath + '/api/v1';

(function ($localStorage) {
    'use strict';

    angular
        .module('eshop', ['ngRoute', 'ngStorage'])
        .config(config)
        .run(run);

    function config($routeProvider) {
        $routeProvider
            .when('/', {
                templateUrl: '/html/products.html',
                controller: 'productsController'
            })
            .when('/cart', {
                templateUrl: '/html/cart.html',
                controller: 'cartController'
            })
            .when('/orders', {
                templateUrl: '/html/orders.html',
                controller: 'ordersController'
            })
            .when('/orders/new', {
                templateUrl: '/html/new_order.html',
                controller: 'newOrderController'
            })
            .when('/orders/:orderId', {
                templateUrl: '/html/order.html',
                controller: 'singleOrderController'
            })
            .when('/register', {
                templateUrl: '/html/registration.html',
                controller: 'registerController'
            })
            .otherwise({
                redirectTo: '/'
            });
    }

    function run($rootScope, $http, $localStorage, $window) {
        $rootScope.user = {};

        $rootScope.onLoginActions = [];
        $rootScope.onLogoutActions = [];

        $rootScope.registerOnLogin = function(action) {
            $rootScope.onLoginActions.push(action);
        }

        $rootScope.registerOnLogout = function(action) {
            $rootScope.onLogoutActions.push(action);
        }

        $rootScope.doOnLogin = function() {
            $rootScope.onLoginActions.forEach(function(action, idx, array) {
                action();
            });
        }

        $rootScope.doOnLogout = function() {
            $rootScope.onLogoutActions.forEach(function(action, idx, array) {
                action();
            });
        }

        $rootScope.getSession = function() {
           $http({
               url: rootPath + '/session',
               method: 'GET',
               params: {}
           }).then(function(response) {
               $http.defaults.headers.common['X-SESSION'] = response.data;
               $localStorage.activeSession = response.data
               $rootScope.user.sessionId = response.data;
               console.log(response);
           });
        }

        $rootScope.mergeCart = function(session) {
            $http({
                url: requestPath + '/carts/merge',
                method: 'PUT',
                params: {
                    session: session
                }
            });
        }

        $rootScope.clearUser = function () {
            delete $localStorage.activeUser;
            delete $rootScope.authToken;
            delete $rootScope.user.authorities;
            delete $rootScope.user.username;
            delete $rootScope.user.password;

            $http.defaults.headers.common.Authorization = '';
        };

        $rootScope.doLogout = function() {
            $rootScope.clearUser();
            $rootScope.getSession();
            $rootScope.doOnLogout();
        }

        $rootScope.updateDisplayName = function() {
            if ($rootScope.user.firstname && $rootScope.user.lastname) {
                $rootScope.user.displayName = $rootScope.user.firstname + ' ' + $rootScope.user.lastname;
            } else if ($rootScope.user.firstname) {
                $rootScope.user.displayName = $rootScope.user.firstname;
            } else if ($rootScope.user.lastname) {
                $rootScope.user.displayName = $rootScope.user.lastname;
            } else {
                $rootScope.user.displayName = $rootScope.user.username;
            }
            $localStorage.activeUser.displayName = $rootScope.user.displayName;
        }

        $rootScope.doLogin = function() {
            $http
                .post(rootPath + '/login', $rootScope.user)
                .then(function successCallback(response) {
                    if (response.data.token) {
                        $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                        $http.defaults.headers.common['X-SESSION'] = response.data.session;
                        $localStorage.activeUser = {
                            firstname: response.data.firstname,
                            lastname: response.data.lastname,
                            token: response.data.token,
                            session: response.data.session,
                            authorities: response.data.authorities
                        };
                        delete($localStorage.activeSession);
                        delete($rootScope.user.password);

                        var session = $rootScope.user.sessionId;
                        $rootScope.authToken = response.data.token;
                        $rootScope.user.firstname = response.data.firstname;
                        $rootScope.user.lastname = response.data.lastname;
                        $rootScope.user.sessionId = response.data.session;
                        $rootScope.user.authorities = response.data.authorities;

                        $rootScope.updateDisplayName();
                        $rootScope.mergeCart(session);
                        $rootScope.doOnLogin();
                    }
                }, function errorCallback(response) {
                    console.log(response);
                });
        }

        $rootScope.checkLogin = function() {
            $http
                .get(rootPath + '/login')
                .then(function onSuccess(response) {
                    $rootScope.authToken = $localStorage.activeUser.token;
                    $rootScope.user.firstname = $localStorage.activeUser.firstname;
                    $rootScope.user.lastname = $localStorage.activeUser.lastname;
                    $rootScope.user.sessionId = $localStorage.activeUser.session;
                    $rootScope.user.authorities = $localStorage.activeUser.authorities;
                    $rootScope.user.displayName = $localStorage.activeUser.displayName;
                    $rootScope.doOnLogin();
                }, function onError(response) {
                    $rootScope.clearUser();
                    $rootScope.getSession();
                });
        }

        $rootScope.goRegister = function() {
            $window.location = '/#!/register';
        }

        $rootScope.hasPermission = function(name) {
            if (typeof $rootScope.user.authorities === 'undefined') {
                return false;
            }
            return $rootScope.user.authorities.includes('ALL') ||
                   $rootScope.user.authorities.includes(name);
        }

        $rootScope.isAuthenticated = function() {
            return typeof $rootScope.authToken !== 'undefined';
        }

        if ($localStorage.activeUser) {
            $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.activeUser.token;
            $http.defaults.headers.common['X-SESSION'] = $localStorage.activeUser.session;
            $rootScope.checkLogin();
        } else if ($localStorage.activeSession) {
            $http.defaults.headers.common['X-SESSION'] = $localStorage.activeSession;
            $rootScope.user.sessionId = $localStorage.activeSession;
        } else {
            $rootScope.getSession();
        }
    }
})();

$eshop = angular.module('eshop');