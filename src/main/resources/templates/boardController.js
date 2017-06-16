app.controller('boardCtrl', function ($rootScope, $scope, $http, $interval) {

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

            console.log(response.data)
            $scope.updateBoard(response.data);
        });
    }

    $scope.post = function (x, y) {
        $http.defaults.headers.common['Authorization'] = $rootScope.authHeader;

        $http.post('http://localhost:8080', ({"x": x, "y": y})).then(function (response) {
            $scope.getFields()
        });

    }

    $scope.connect = function() {
        var socket = new SockJS('http://localhost:8080/ws');
        $scope.stompClient = Stomp.over(socket);
        $scope.stompClient.connect({}, function (frame) {
            console.log('Connected: ' + frame);
            $scope.stompClient.subscribe('/topic/greetings', function (greeting) {
                $scope.getFields()
            });

        });
    }

    $scope.connect();
    $scope.getFields();
});



