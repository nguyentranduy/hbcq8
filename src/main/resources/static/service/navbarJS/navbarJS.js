import { ip, port , getUserFromSessionStorage} from '../../constant/globals.js';
const url = `http://${ip}:${port}/api/v1/logout`;
// Định nghĩa module AngularJS
var app = angular.module('homeApp', []);

// Định nghĩa controller 'navbarController'
app.controller('navbarController', function ($http,$scope) {

    // Cập nhật biến 'currentUser' dựa trên trạng thái đăng nhập
    $scope.currentUser = getUserFromSessionStorage();

    // Hàm đăng xuất
    $scope.logout = function () {
        // Gọi API để xóa session
        $http.get(url)  // Thay '/your-api-endpoint' bằng URL thực tế của API
            .then(function (response) {
                // Xóa thông tin người dùng khỏi sessionStorage sau khi API trả về thành công
                sessionStorage.removeItem('currentUser');
                // Cập nhật lại biến 'currentUser' để ẩn menu
                $scope.currentUser = null;
                window.location.href = '/login';
            })
            .catch(function (error) {
                // Xử lý lỗi nếu có vấn đề xảy ra khi gọi API
                console.error('Error during logout:', error);
            });
    };
});
