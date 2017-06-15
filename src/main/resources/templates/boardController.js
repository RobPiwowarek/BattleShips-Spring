// TODO: different behaviour for get/post success/error

var app = angular.module('myApp', ["ngRoute"]);

app.config(function ($routeProvider) {
    $routeProvider
        .when("/", {
            templateUrl: "loginPage.html"
        })
        .when("/board", {
            templateUrl: "board.html"
        });

})

app.run(function ($rootScope, $location) {

    // register listener to watch route changes
    $rootScope.$on("$locationChangeStart", function (event, next, current) {
        if ($rootScope.loggedUser == null) {
            // no logged user, we should be going to #login
            if (next.templateUrl == "loginPage.html") {
                // already going to #login, no redirect needed
            } else {
                // not going to #login, we should redirect now
                $location.path("/");
            }
        }
    });
});

app.controller('boardCtrl', function ($rootScope, $scope, $http) {

    $scope.updateBoard = function (fieldList) {
        $scope.boardData = fieldList;

        var i;

        for (i = 0; i < fieldList.length; i++) {
            document.getElementsByTagName("td")[fieldList[i].y * 6 + fieldList[i].x].style.backgroundColor = fieldList[i].color;
        }
    };

    $scope.getFields = function () {
        $http.defaults.headers.common['Authorization'] = $rootScope.authHeader;

        $http.get('http://localhost:8080').then(function (response) {

            //alert(response.data.length)
            console.log(response.data)
            $scope.updateBoard(response.data);
        });
    }

    $scope.post = function (x, y) {
        $http.defaults.headers.common['Authorization'] = $rootScope.authHeader;

        $http.post('http://localhost:8080', ({"x": x, "y": y})).then(function (response) {
            $scope.getFields();
        });
    }

    $scope.getFields();
});

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

