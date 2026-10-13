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

## Test chức năng tài khoản

Backend sử dụng session login và CSRF. Có thể mở trang login bằng trình duyệt,
sau đó dùng Thunder Client hoặc Postman để gọi các API `GET`, `PUT` và `DELETE`.
Phải giữ cookie session sau khi đăng nhập.

### 1. Khởi động ứng dụng

Chạy ứng dụng:

```powershell
.\gradlew.bat bootRun
```

Hoặc chạy `BookStoreBackendApplication.java` trong IDE.

Mở trang đăng nhập:

```text
http://localhost:8080/login
```

### 2. Lấy CSRF token

Trước khi gửi request `PUT` hoặc `DELETE`, gọi:

```http
GET http://localhost:8080/api/auth/csrf
```

Lưu lại giá trị `token` trong response và gửi nó trong header của request thay đổi dữ liệu:

```text
X-CSRF-TOKEN: <token>
```

Thunder Client/Postman phải dùng cùng cookie session với request login.

### 3. Test tài khoản cá nhân bằng CUSTOMER

Đăng nhập tại `http://localhost:8080/login` bằng:

```text
Username: an.nguyen
Password: Password@123
```

#### Xem thông tin tài khoản cá nhân

```http
GET http://localhost:8080/api/accounts/me
```

Kết quả mong đợi: `200 OK`, response chỉ chứa thông tin của `an.nguyen` và
không chứa password.

#### Cập nhật thông tin cá nhân

```http
PUT http://localhost:8080/api/accounts/me
Content-Type: application/json
X-CSRF-TOKEN: <token>
```

Body:

```json
{
    "fullName": "An Nguyen Updated",
    "email": "an.updated@example.com",
    "phoneNumber": "0909123456",
    "shippingAddress": "Ho Chi Minh City"
}
```

Kết quả mong đợi: `200 OK`. Gọi lại `GET /api/accounts/me` để kiểm tra dữ liệu đã đổi.

#### Kiểm tra CUSTOMER không được quản lý tài khoản khác

```http
GET http://localhost:8080/api/accounts
GET http://localhost:8080/api/accounts/search?keyword=an
```

Kết quả mong đợi: `403 Forbidden`.

### 4. Test quản lý tài khoản bằng ADMIN

Đăng xuất rồi đăng nhập lại bằng:

```text
Username: admin.quan
Password: Password@123
```

#### Xem danh sách tài khoản

```http
GET http://localhost:8080/api/accounts
```

Kết quả mong đợi: `200 OK` và danh sách tài khoản.

#### Tìm kiếm tài khoản

```http
GET http://localhost:8080/api/accounts/search?keyword=an
```

Keyword được tìm trong `username`, `email` và `fullName`.

Kết quả mong đợi: `200 OK` và các tài khoản phù hợp.

#### Xem thông tin một tài khoản

Thay `{id}` bằng id lấy từ kết quả danh sách:

```http
GET http://localhost:8080/api/accounts/{id}
```

Kết quả mong đợi: `200 OK`.

#### Cập nhật một tài khoản

```http
PUT http://localhost:8080/api/accounts/{id}
Content-Type: application/json
X-CSRF-TOKEN: <token>
```

Body:

```json
{
    "fullName": "Customer Updated",
    "email": "customer.updated@example.com",
    "phoneNumber": "0911222333",
    "shippingAddress": "Da Nang"
}
```

Kết quả mong đợi: `200 OK`.

#### Xóa hoặc vô hiệu hóa một tài khoản

```http
DELETE http://localhost:8080/api/accounts/{id}
X-CSRF-TOKEN: <token>
```

Nếu ứng dụng dùng xóa mềm bằng trường `enabled`, kết quả mong đợi là `204 No Content`
và tài khoản đó không thể đăng nhập lại.

### 5. Các trường hợp cần kiểm tra thêm

| Trường hợp | Kết quả mong đợi |
|---|---|
| Chưa đăng nhập gọi `/api/accounts/me` | `401 Unauthorized` hoặc chuyển tới trang login |
| CUSTOMER gọi `/api/accounts` | `403 Forbidden` |
| CUSTOMER gọi `/api/accounts/{id}` | `403 Forbidden` |
| Email đã tồn tại khi cập nhật | `409 Conflict` |
| Số điện thoại đã tồn tại khi cập nhật | `409 Conflict` |
| Thiếu `fullName`, `email`, `phoneNumber` hoặc `shippingAddress` | `400 Bad Request` |
| Email sai định dạng | `400 Bad Request` |
| ADMIN gọi `/api/accounts/search` | `200 OK` |
| Xóa tài khoản đã bị vô hiệu hóa | Không được làm mất dữ liệu đơn hàng |

### 6. Chạy test tự động

Chạy toàn bộ test bằng PowerShell:

```powershell
.\gradlew.bat clean test
```

Kết quả thành công:

```text
BUILD SUCCESSFUL
```