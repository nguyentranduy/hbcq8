import { ip, port, getUserFromSessionStorage, handleLoginSuccess } from '../../constant/globals.js';
const url = `http://${ip}:${port}/api/v1/user/update`;
var app = angular.module('profileApp', []);
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

app.controller('profileController', function ($http, $scope) {
    $scope.currentUser = getUserFromSessionStorage();
    console.log($scope.currentUser);
    $scope.updateProfile = function () {
        console.log("Sending request to login...");
        // Gửi yêu cầu POST tới API
        $http.post(url, $scope.currentUser)
            .then(function (response) {
                if (response.status == 200) {
                    console.log('Cập nhật thành công!', response.data);
                    handleLoginSuccess(response.data);
                    $scope.currentUser = response.data;
                    // Hiển thị Toast sau khi cập nhật thành công
                    var toastEl = document.getElementById('liveToast');
                    var toast = new bootstrap.Toast(toastEl);
                    toast.show();
                }

            })
            .catch(function (error) {
                // Đăng nhập thất bại
                console.error('Lỗi cập nhật:', error);
                if (error.status === -1) {
                    console.error("Không thể kết nối đến server. Kiểm tra xem server có đang chạy không và đảm bảo URL chính xác.");
                } else if (error.status === 500) {
                    $scope.errorMessage = 'Lỗi server';
                }
                else {
                    console.error('Chi tiết lỗi:', error.data ? error.data.message : 'Lỗi không xác định');
                }
            });

    };
});

