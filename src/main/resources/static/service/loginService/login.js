import { ip, port , handleLoginSuccess} from '../../constant/globals.js';
// Khai báo ứng dụng AngularJS
var app = angular.module('loginApp', []);
const url = `http://${ip}:${port}/api/v1/login`;

// Khai báo controller
app.controller('LoginController', function ($scope, $http) {
    // Biến lưu trữ thông tin người dùng
    $scope.user = {};
    // Hàm xử lý khi submit form
    $scope.submitForm = function () {
        console.log("Sending request to login...");
        // Gửi yêu cầu POST tới API
        $http.post(url, $scope.user)
            .then(function (response) {
                // Đăng nhập thành công
                if (response.status == 200) {
                    console.log('Đăng nhập thành công:', response.data);
                    // Chuyển hướng tới trang chính sau khi đăng nhập thành công
                    handleLoginSuccess(response.data);
                    window.location.href = '/home';
                }

            })
            .catch(function (error) {
                // Đăng nhập thất bại
                console.error('Lỗi đăng nhập:', error);
                if (error.status === -1) {
                    console.error("Không thể kết nối đến server. Kiểm tra xem server có đang chạy không và đảm bảo URL chính xác.");
                } else if (error.status === 401) {
                    $scope.errorMessage = 'Tên đăng nhập hoặc mật khẩu không chính xác';
                }
                 else {
                    console.error('Chi tiết lỗi:', error.data ? error.data.message : 'Lỗi không xác định');
                }
            });
    };
});
