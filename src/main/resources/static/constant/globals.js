const ip = "localhost";
const port = "8080";
// Sau khi đăng nhập thành công và nhận thông tin người dùng
function handleLoginSuccess(user) {
    // Lưu thông tin người dùng vào sessionStorage
    sessionStorage.setItem('currentUser', JSON.stringify(user));
}

// Để lấy thông tin người dùng từ sessionStorage
function getUserFromSessionStorage() {
    const user = sessionStorage.getItem('currentUser');
    return user ? JSON.parse(user) : null;
}

function checkUserLoggedIn() {
    var user = sessionStorage.getItem('currentUser');
    console.log(user);
    return user ? JSON.parse(user) : null;
}
export { ip, port , handleLoginSuccess, getUserFromSessionStorage, checkUserLoggedIn};