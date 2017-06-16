app.controller('loginCtrl', function ($rootScope, $scope, $http, $location) {

    $scope.boardView = function () {
        $location.path("/board");
    };

    $scope.logIn = function (username, password) {

        $rootScope.authHeader = 'Basic ' + window.btoa(username + ':' + password);

        $http.defaults.headers.common['Authorization'] = $rootScope.authHeader;

        $http.get('http://localhost:8080/')
            .then(function onSuccess() {
                $rootScope.loggedUser = "true";
                $scope.boardView();
            }).catch(function onError(response) {
            alert("BAD CREDENTIALS")
        });

    }

});
