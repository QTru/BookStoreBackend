## Chạy thử backend

1. Login MariaDB.
2. Chạy BookStoreBackendApplication.java để Hibernate tạo các bảng.
3. Với database mới, chạy `testdata.sql` để thêm dữ liệu mẫu.
4. Với database đã có bảng accounts nhưng chưa có cột enabled,
   chạy `database/01_account_enabled.sql` trong lần thiết lập đầu tiên.
5. Khởi động lại ứng dụng.

### Tài khoản mẫu

Chỉ dùng cho phát triển và kiểm thử với dữ liệu trong `testdata.sql`:

| Vai trò | Username | Password |
|---|---|---|
| ADMIN | admin.quan | Password@123 |
| CUSTOMER | an.nguyen | Password@123 |

Đăng nhập tại http://localhost:8080/login.
Sau đó kiểm tra http://localhost:8080/api/accounts.

Không sử dụng tài khoản user và mật khẩu sinh trong console nữa.