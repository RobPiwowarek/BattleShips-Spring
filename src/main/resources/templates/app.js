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