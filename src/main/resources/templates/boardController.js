function error(data) {
    alert("Error sending message");
}

function success(response) {
    alert("Success");
}

var app = angular.module('myApp', ["ngRoute"]);

app.config(function ($routeProvider) {
    $routeProvider
        .when("/", {
            templateUrl : "loginPage.html"
        })
        .when("/board",{
        templateUrl: "board.html"
    });

})

app.controller('boardCtrl', function($rootScope, $scope, $http) {

    $scope.updateBoard = function (fieldList) {
        $scope.boardData = fieldList;

        var i;

        for (i = 0; i < fieldList.length; i++) {
            document.getElementsByTagName("td")[fieldList[i].y*6+fieldList[i].x].style.backgroundColor = fieldList[i].color;
        }
    };

    $scope.getFields = function() {
        $http.defaults.headers.common['Authorization'] = $rootScope.authHeader;

        $http.get('http://localhost:8080').then(function (response) {

            alert(response.data.length)
            console.log(response.data)
            $scope.updateBoard(response.data);
        });
    }

    $scope.post = function(x, y){
        $http.defaults.headers.common['Authorization'] = $rootScope.authHeader;

        $http.post('http://localhost:8080', ({"x" : x, "y" : y})).then(function(response){
        $scope.getFields();
        });
    }

    $scope.getFields();
});

app.controller('loginCtrl', function($rootScope, $scope, $http, $location) {

    $scope.boardView = function() {
        $location.path("/board");
    };

     $scope.logIn = function(username, password) {

        $rootScope.authHeader = 'Basic ' + window.btoa(username + ':' + password);

        $http.defaults.headers.common['Authorization'] = $rootScope.authHeader;

        $http.get('http://localhost:8080/login').then(function() {
            $scope.boardView();
        });

    }


});

