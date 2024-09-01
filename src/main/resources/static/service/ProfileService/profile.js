import { ip, port, getUserFromSessionStorage } from '../../constant/globals.js';
const url = 'http://${ip}:${port}/api/v1/updateProfile';
var app = angular.module('profileApp', []);
app.controller('profileController', function ($http, $scope) {
    $scope.currentUser = getUserFromSessionStorage();
});