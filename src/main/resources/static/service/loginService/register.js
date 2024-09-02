import { ip, port, handleLoginSuccess } from '../../constant/globals.js';
// Khai báo ứng dụng AngularJS
var app = angular.module('registerApp', []);
const url = `http://${ip}:${port}/api/v1/register`;
// Khai báo controller
app.controller('registerController', function ($scope, $http, $window) {
    // Biến lưu trữ thông tin người dùng
    $scope.user = {};

    // Hàm xử lý khi submit form
    $scope.submitForm = function () {
        if ($scope.user.password !== $scope.confirmPassword) {
            $scope.errorMessage = 'Mật khẩu xác nhận không khớp';
            return;
        }
        console.log("Sending request to register...");
        // Gửi yêu cầu POST tới API
        $http.post(url, $scope.user)
            .then(function (response) {
                // Đăng ký thành công
                console.log('Đăng ký thành công:', response.data);
                $scope.user = {};
                toast.show();
                handleLoginSuccess(response.data);
                window.location.href = '/home';
                handleLoginSuccess(response.data);
                // Chuyển hướng tới trang đăng nhập sau khi đăng ký thành công
                $window.location.href = '/home';
            })
            .catch(function (error) {
                // Đăng ký thất bại
                console.error('Lỗi đăng ký:', error);
                if (error.status === -1) {
                    console.error("Không thể kết nối đến server. Kiểm tra xem server có đang chạy không và đảm bảo URL chính xác.");
                } else {
                    console.error('Chi tiết lỗi:', error.data ? error.data.message : 'Lỗi không xác định');
                }
            });
    };
});
