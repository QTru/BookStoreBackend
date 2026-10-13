-- Sample data for the BookStore database (MariaDB).
-- Run AFTER the app has started once, so Hibernate has created the tables.
-- All accounts use the password: Password@123  (stored as BCrypt hashes)
-- Titles/authors/publishers are real; ISBNs, page counts and prices are made-up sample values.

USE bookstoredb;
SET NAMES utf8mb4;

-- Optional reset (uncomment to wipe existing data first):
-- SET FOREIGN_KEY_CHECKS = 0;
-- TRUNCATE order_items; TRUNCATE orders; TRUNCATE books_authors; TRUNCATE books_categories;
-- TRUNCATE books_publishers; TRUNCATE books; TRUNCATE authors; TRUNCATE publishers;
-- TRUNCATE categories; TRUNCATE accounts;
-- SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO accounts (id, username, password, full_name, email, phone_number, shipping_address, role, creation_date) VALUES
('3bc2eb58-9124-5456-91f6-b9fcb58c475c', 'admin.quan', '$2b$10$N78xsNarpGuYGnEJyyuE5Ogk6stUPod8C7MAYBz1.2mGitZsfLe2G', 'Trần Minh Quân', 'admin_quan@example.com', '0901000001', '25 Lê Đức Thọ, Phường 6, Quận Gò Vấp, TP. Hồ Chí Minh', 'ADMIN', '2026-07-01 09:00:00'),
('fdb40490-2a6e-5f41-bab1-8692ea543da9', 'admin.ha', '$2b$10$N78xsNarpGuYGnEJyyuE5Ogk6stUPod8C7MAYBz1.2mGitZsfLe2G', 'Lê Thu Hà', 'admin_ha@example.com', '0901000002', '88 Nguyễn Thị Minh Khai, Quận 3, TP. Hồ Chí Minh', 'ADMIN', '2026-07-01 09:05:00'),
('fab1508b-82f1-59b2-bd2f-6a81f8eac6ad', 'an.nguyen', '$2b$10$N78xsNarpGuYGnEJyyuE5Ogk6stUPod8C7MAYBz1.2mGitZsfLe2G', 'Nguyễn Văn An', 'an_nguyen@example.com', '0901000003', '12 Nguyễn Văn Nghi, Phường 5, Quận Gò Vấp, TP. Hồ Chí Minh', 'CUSTOMER', '2026-07-20 10:12:00'),
('add4b8a1-e586-505a-80a0-ad225118ca11', 'bich.pham', '$2b$10$N78xsNarpGuYGnEJyyuE5Ogk6stUPod8C7MAYBz1.2mGitZsfLe2G', 'Phạm Thị Bích', 'bich_pham@example.com', '0901000004', '47 Phan Văn Trị, Phường 12, Quận Bình Thạnh, TP. Hồ Chí Minh', 'CUSTOMER', '2026-07-25 14:30:00'),
('de2067c6-57fa-53ce-af58-6215f9138539', 'long.vo', '$2b$10$N78xsNarpGuYGnEJyyuE5Ogk6stUPod8C7MAYBz1.2mGitZsfLe2G', 'Võ Hoàng Long', 'long_vo@example.com', '0901000005', '103 Cách Mạng Tháng 8, Quận 10, TP. Hồ Chí Minh', 'CUSTOMER', '2026-08-02 08:45:00'),
('d20e09ce-2726-539d-831d-c97d902f4673', 'mai.dang', '$2b$10$N78xsNarpGuYGnEJyyuE5Ogk6stUPod8C7MAYBz1.2mGitZsfLe2G', 'Đặng Ngọc Mai', 'mai_dang@example.com', '0901000006', '9 Trường Chinh, Phường Tân Hưng Thuận, Quận 12, TP. Hồ Chí Minh', 'CUSTOMER', '2026-08-10 19:20:00'),
('5573856c-25f2-532a-9d8c-bd3e220c1f45', 'khanh.bui', '$2b$10$N78xsNarpGuYGnEJyyuE5Ogk6stUPod8C7MAYBz1.2mGitZsfLe2G', 'Bùi Quốc Khánh', 'khanh_bui@example.com', '0901000007', '56 Quang Trung, Phường 10, Quận Gò Vấp, TP. Hồ Chí Minh', 'CUSTOMER', '2026-08-18 11:00:00'),
('ea91357b-6703-55aa-a16f-996bf1a6adc4', 'nguyen.hoang', '$2b$10$N78xsNarpGuYGnEJyyuE5Ogk6stUPod8C7MAYBz1.2mGitZsfLe2G', 'Hoàng Thảo Nguyên', 'nguyen_hoang@example.com', '0901000008', '210 Lý Thường Kiệt, Quận Tân Bình, TP. Hồ Chí Minh', 'CUSTOMER', '2026-09-01 16:40:00'),
('6f99a588-0441-5aae-ab23-023656b982c7', 'hung.ly', '$2b$10$N78xsNarpGuYGnEJyyuE5Ogk6stUPod8C7MAYBz1.2mGitZsfLe2G', 'Lý Gia Hưng', 'hung_ly@example.com', '0901000009', '34 Nguyễn Huệ, Quận 1, TP. Hồ Chí Minh', 'CUSTOMER', '2026-09-12 13:15:00'),
('5e575e58-6629-57db-b811-0c9215b12d79', 'tam.phan', '$2b$10$N78xsNarpGuYGnEJyyuE5Ogk6stUPod8C7MAYBz1.2mGitZsfLe2G', 'Phan Thanh Tâm', 'tam_phan@example.com', '0901000010', '71 Đường số 7, Phường Bình Trị Đông, Quận Bình Tân, TP. Hồ Chí Minh', 'CUSTOMER', '2026-09-20 09:50:00');

INSERT INTO authors (id, name) VALUES
('7e98d6f7-3a2f-51d6-92d8-c5aefd05d773', 'Tô Hoài'),
('1a51c3ef-f379-5fe8-bf06-bbbb45d48658', 'Vũ Trọng Phụng'),
('4bd7cbb1-534d-56f2-8863-5003a3b5ae68', 'Ngô Tất Tố'),
('165d9bc1-971a-5a84-bdab-99290757851e', 'Paulo Coelho'),
('ff69d611-e84d-5b9d-b9fd-847a66d077fe', 'Yuval Noah Harari'),
('dadf8d8f-7850-5b48-b084-9d823f6fdf8f', 'Robert C. Martin'),
('35ce8019-13f3-592c-bc1c-e714a244dac6', 'Joshua Bloch'),
('bfd398ac-453c-503b-b2e8-733c7878d40e', 'Nguyễn Nhật Ánh'),
('ba1594de-456c-5018-bf81-b0c45d93fd4b', 'Andrew Hunt'),
('39a94740-160c-5927-b9e6-aa9862154c97', 'David Thomas');

INSERT INTO publishers (id, name) VALUES
('642eee00-b93d-51dd-885a-c10be806efb3', 'NXB Kim Đồng'),
('dfdca535-405e-5f80-b15f-c69eaa42b58b', 'NXB Văn Học'),
('2550b831-d083-5a3a-a251-19fc7974ddbb', 'NXB Trẻ'),
('632ecb70-1556-5133-a045-b82a7821280f', 'NXB Tri Thức'),
('2958c9af-50bb-59fc-98f0-39c38eda5b12', 'Prentice Hall'),
('dcf861aa-86a6-50e8-83a1-38fbf5c1feb2', 'Addison-Wesley');

INSERT INTO categories (id, name) VALUES
('ee4a3f6b-7a97-54af-957a-3f93d875b55a', 'Văn học Việt Nam'),
('febb29d1-ede7-52dd-add2-9883ec0a7bdb', 'Văn học nước ngoài'),
('489db7a8-91a1-5334-ae67-f25ad7f61371', 'Thiếu nhi'),
('6e6bb766-f566-5905-84a1-eccf49141ed2', 'Lịch sử & Khoa học'),
('0b899372-5630-52de-b473-4315cd43bdc8', 'Công nghệ thông tin'),
('22853472-71d0-5721-a24f-d3ec86945668', 'Kinh điển');

INSERT INTO books (id, isbn, title, cover_image_url, page_count, description, price, version, discount_percentage, start_discount_date, end_discount_date, stock_quantity, creation_date) VALUES
('b8ebed4f-c5f9-582d-8fa8-190503de1d50', '9786041000018', 'Dế Mèn Phiêu Lưu Ký', 'https://picsum.photos/seed/book1/400/600', 164, 'Cuộc phiêu lưu của chú dế Mèn qua thế giới loài vật, một tác phẩm thiếu nhi kinh điển của văn học Việt Nam.', 85000, 0, 0, NULL, NULL, 60, '2026-07-15 08:00:00'),
('05b764fd-c9d8-5706-a44e-e0274bffafa5', '9786041000025', 'Số Đỏ', 'https://picsum.photos/seed/book2/400/600', 330, 'Tiểu thuyết trào phúng nổi tiếng phơi bày xã hội thành thị Việt Nam những năm 1930.', 95000, 0, 10, '2026-09-01 00:00:00', '2026-12-31 23:59:59', 45, '2026-07-15 08:00:00'),
('1d4f9001-0a9f-5183-bada-e9220b7e39ff', '9786041000032', 'Tắt Đèn', 'https://picsum.photos/seed/book3/400/600', 200, 'Bức tranh chân thực về đời sống người nông dân dưới ách sưu thuế, qua hình ảnh chị Dậu.', 70000, 0, 0, NULL, NULL, 40, '2026-07-15 08:00:00'),
('b5be592b-f665-5362-90a2-f47821a2754d', '9786041000049', 'Nhà Giả Kim', 'https://picsum.photos/seed/book4/400/600', 228, 'Hành trình của chàng chăn cừu Santiago đi tìm kho báu và ý nghĩa của giấc mơ.', 79000, 0, 0, NULL, NULL, 80, '2026-07-15 08:00:00'),
('1fe1315e-bc03-5b15-bba1-590a8c29827c', '9786041000056', 'Sapiens: Lược Sử Loài Người', 'https://picsum.photos/seed/book5/400/600', 560, 'Lịch sử loài người từ thời săn bắt hái lượm đến thời đại công nghệ.', 189000, 0, 20, '2026-10-01 00:00:00', '2026-10-31 23:59:59', 30, '2026-07-15 08:00:00'),
('1de99335-8ee7-5348-87b4-8f5d473c91cb', '9786041000063', 'Clean Code', 'https://picsum.photos/seed/book6/400/600', 464, 'Cẩm nang viết mã nguồn sạch, dễ đọc và dễ bảo trì.', 450000, 0, 15, '2026-08-15 00:00:00', '2026-09-30 23:59:59', 25, '2026-07-15 08:00:00'),
('c2408ddf-507d-537d-a583-e6154e74194a', '9786041000070', 'Effective Java', 'https://picsum.photos/seed/book7/400/600', 412, 'Tập hợp các thực hành tốt nhất để viết mã Java hiệu quả.', 520000, 0, 0, NULL, NULL, 20, '2026-07-15 08:00:00'),
('4d7ddcb9-4506-5580-9dff-c15342f04ff9', '9786041000087', 'Cho Tôi Xin Một Vé Đi Tuổi Thơ', 'https://picsum.photos/seed/book8/400/600', 208, 'Những câu chuyện tuổi thơ trong veo, hồn nhiên được kể bằng giọng văn dí dỏm.', 108000, 0, 5, '2026-08-01 00:00:00', '2026-12-31 23:59:59', 70, '2026-07-15 08:00:00'),
('8437cf11-dd56-5cf7-a166-1738dcd21f42', '9786041000094', 'Mắt Biếc', 'https://picsum.photos/seed/book9/400/600', 300, 'Câu chuyện tình đơn phương của Ngạn dành cho Hà Lan ở làng Đo Đo.', 99000, 0, 0, NULL, NULL, 55, '2026-07-15 08:00:00'),
('3db1ca4f-77e9-573c-80a5-fd39c5f49a5a', '9786041000100', 'The Pragmatic Programmer', 'https://picsum.photos/seed/book10/400/600', 352, 'Những lời khuyên thực tế giúp lập trình viên làm việc chuyên nghiệp hơn.', 480000, 0, 0, NULL, NULL, 15, '2026-07-15 08:00:00');

INSERT INTO books_authors (book_id, author_id) VALUES
('b8ebed4f-c5f9-582d-8fa8-190503de1d50', '7e98d6f7-3a2f-51d6-92d8-c5aefd05d773'),
('05b764fd-c9d8-5706-a44e-e0274bffafa5', '1a51c3ef-f379-5fe8-bf06-bbbb45d48658'),
('1d4f9001-0a9f-5183-bada-e9220b7e39ff', '4bd7cbb1-534d-56f2-8863-5003a3b5ae68'),
('b5be592b-f665-5362-90a2-f47821a2754d', '165d9bc1-971a-5a84-bdab-99290757851e'),
('1fe1315e-bc03-5b15-bba1-590a8c29827c', 'ff69d611-e84d-5b9d-b9fd-847a66d077fe'),
('1de99335-8ee7-5348-87b4-8f5d473c91cb', 'dadf8d8f-7850-5b48-b084-9d823f6fdf8f'),
('c2408ddf-507d-537d-a583-e6154e74194a', '35ce8019-13f3-592c-bc1c-e714a244dac6'),
('4d7ddcb9-4506-5580-9dff-c15342f04ff9', 'bfd398ac-453c-503b-b2e8-733c7878d40e'),
('8437cf11-dd56-5cf7-a166-1738dcd21f42', 'bfd398ac-453c-503b-b2e8-733c7878d40e'),
('3db1ca4f-77e9-573c-80a5-fd39c5f49a5a', 'ba1594de-456c-5018-bf81-b0c45d93fd4b'),
('3db1ca4f-77e9-573c-80a5-fd39c5f49a5a', '39a94740-160c-5927-b9e6-aa9862154c97');

INSERT INTO books_categories (book_id, category_id) VALUES
('b8ebed4f-c5f9-582d-8fa8-190503de1d50', 'ee4a3f6b-7a97-54af-957a-3f93d875b55a'),
('b8ebed4f-c5f9-582d-8fa8-190503de1d50', '489db7a8-91a1-5334-ae67-f25ad7f61371'),
('05b764fd-c9d8-5706-a44e-e0274bffafa5', 'ee4a3f6b-7a97-54af-957a-3f93d875b55a'),
('05b764fd-c9d8-5706-a44e-e0274bffafa5', '22853472-71d0-5721-a24f-d3ec86945668'),
('1d4f9001-0a9f-5183-bada-e9220b7e39ff', 'ee4a3f6b-7a97-54af-957a-3f93d875b55a'),
('1d4f9001-0a9f-5183-bada-e9220b7e39ff', '22853472-71d0-5721-a24f-d3ec86945668'),
('b5be592b-f665-5362-90a2-f47821a2754d', 'febb29d1-ede7-52dd-add2-9883ec0a7bdb'),
('1fe1315e-bc03-5b15-bba1-590a8c29827c', '6e6bb766-f566-5905-84a1-eccf49141ed2'),
('1de99335-8ee7-5348-87b4-8f5d473c91cb', '0b899372-5630-52de-b473-4315cd43bdc8'),
('c2408ddf-507d-537d-a583-e6154e74194a', '0b899372-5630-52de-b473-4315cd43bdc8'),
('4d7ddcb9-4506-5580-9dff-c15342f04ff9', 'ee4a3f6b-7a97-54af-957a-3f93d875b55a'),
('4d7ddcb9-4506-5580-9dff-c15342f04ff9', '489db7a8-91a1-5334-ae67-f25ad7f61371'),
('8437cf11-dd56-5cf7-a166-1738dcd21f42', 'ee4a3f6b-7a97-54af-957a-3f93d875b55a'),
('3db1ca4f-77e9-573c-80a5-fd39c5f49a5a', '0b899372-5630-52de-b473-4315cd43bdc8');

INSERT INTO books_publishers (book_id, publisher_id) VALUES
('b8ebed4f-c5f9-582d-8fa8-190503de1d50', '642eee00-b93d-51dd-885a-c10be806efb3'),
('05b764fd-c9d8-5706-a44e-e0274bffafa5', 'dfdca535-405e-5f80-b15f-c69eaa42b58b'),
('1d4f9001-0a9f-5183-bada-e9220b7e39ff', 'dfdca535-405e-5f80-b15f-c69eaa42b58b'),
('b5be592b-f665-5362-90a2-f47821a2754d', 'dfdca535-405e-5f80-b15f-c69eaa42b58b'),
('1fe1315e-bc03-5b15-bba1-590a8c29827c', '632ecb70-1556-5133-a045-b82a7821280f'),
('1de99335-8ee7-5348-87b4-8f5d473c91cb', '2958c9af-50bb-59fc-98f0-39c38eda5b12'),
('c2408ddf-507d-537d-a583-e6154e74194a', 'dcf861aa-86a6-50e8-83a1-38fbf5c1feb2'),
('4d7ddcb9-4506-5580-9dff-c15342f04ff9', '2550b831-d083-5a3a-a251-19fc7974ddbb'),
('8437cf11-dd56-5cf7-a166-1738dcd21f42', '2550b831-d083-5a3a-a251-19fc7974ddbb'),
('3db1ca4f-77e9-573c-80a5-fd39c5f49a5a', 'dcf861aa-86a6-50e8-83a1-38fbf5c1feb2');

INSERT INTO orders (id, full_name, shipping_address, status, payment_method, subtotal, discount, total, creation_date, account_id) VALUES
('5d482472-5874-568c-842e-f67e2e9e7de6', 'Nguyễn Văn An', '12 Nguyễn Văn Nghi, Phường 5, Quận Gò Vấp, TP. Hồ Chí Minh', 'DELIVERED', 'CASH', 315000, 10800, 304200, '2026-08-05 10:30:00', 'fab1508b-82f1-59b2-bd2f-6a81f8eac6ad'),
('8f6e11e9-0624-5d9d-ae28-a4cfe3cd6352', 'Phạm Thị Bích', '47 Phan Văn Trị, Phường 12, Quận Bình Thạnh, TP. Hồ Chí Minh', 'DELIVERED', 'DIGITAL_WALLET', 164000, 0, 164000, '2026-08-12 15:10:00', 'add4b8a1-e586-505a-80a0-ad225118ca11'),
('0a2a3728-b974-57d0-ae9c-13a7edab83cc', 'Võ Hoàng Long', '103 Cách Mạng Tháng 8, Quận 10, TP. Hồ Chí Minh', 'DELIVERED', 'DIGITAL_WALLET', 970000, 67500, 902500, '2026-08-20 09:25:00', 'de2067c6-57fa-53ce-af58-6215f9138539'),
('e5aaf20d-9496-544c-9ce0-94dc9aa39bc2', 'Đặng Ngọc Mai', '9 Trường Chinh, Phường Tân Hưng Thuận, Quận 12, TP. Hồ Chí Minh', 'DELIVERED', 'CASH', 189000, 0, 189000, '2026-09-02 18:00:00', 'd20e09ce-2726-539d-831d-c97d902f4673'),
('a001fae7-8b63-59d3-84ae-4eac5c1b64b7', 'Bùi Quốc Khánh', '56 Quang Trung, Phường 10, Quận Gò Vấp, TP. Hồ Chí Minh', 'DELIVERED', 'CASH', 343000, 14900, 328100, '2026-09-10 12:40:00', '5573856c-25f2-532a-9d8c-bd3e220c1f45'),
('9b23ddcd-c230-5fe3-83b2-2579c6918f0c', 'Hoàng Thảo Nguyên', '210 Lý Thường Kiệt, Quận Tân Bình, TP. Hồ Chí Minh', 'DELIVERING', 'DIGITAL_WALLET', 480000, 0, 480000, '2026-10-08 20:05:00', 'ea91357b-6703-55aa-a16f-996bf1a6adc4'),
('1a83bee0-a154-50d0-8efa-e66f8b135329', 'Lý Gia Hưng', '34 Nguyễn Huệ, Quận 1, TP. Hồ Chí Minh', 'PROCESSING', 'CASH', 283000, 0, 283000, '2026-10-11 08:15:00', '6f99a588-0441-5aae-ab23-023656b982c7'),
('0154eb18-1291-53cb-81c7-44ac2522b9d1', 'Phan Thanh Tâm', '71 Đường số 7, Phường Bình Trị Đông, Quận Bình Tân, TP. Hồ Chí Minh', 'CANCELLED', 'DIGITAL_WALLET', 450000, 67500, 382500, '2026-09-25 21:30:00', '5e575e58-6629-57db-b811-0c9215b12d79'),
('cda933fa-83c0-5d62-b1e7-a9304d3b6f83', 'Nguyễn Thị Lan', '15 Pasteur, Phường Bến Nghé, Quận 1, TP. Hồ Chí Minh', 'DELIVERING', 'DIGITAL_WALLET', 268000, 37800, 230200, '2026-10-09 11:50:00', 'fab1508b-82f1-59b2-bd2f-6a81f8eac6ad'),
('b3a44465-6edb-5f7c-9adc-045f36ff7c2c', 'Phạm Thị Bích', '47 Phan Văn Trị, Phường 12, Quận Bình Thạnh, TP. Hồ Chí Minh', 'PROCESSING', 'DIGITAL_WALLET', 575000, 9500, 565500, '2026-10-12 16:20:00', 'add4b8a1-e586-505a-80a0-ad225118ca11');

INSERT INTO order_items (book_id, order_id, quantity, unit_price, discount_percentage, amount) VALUES
('4d7ddcb9-4506-5580-9dff-c15342f04ff9', '5d482472-5874-568c-842e-f67e2e9e7de6', 2, 108000, 5, 216000),
('8437cf11-dd56-5cf7-a166-1738dcd21f42', '5d482472-5874-568c-842e-f67e2e9e7de6', 1, 99000, 0, 99000),
('b8ebed4f-c5f9-582d-8fa8-190503de1d50', '8f6e11e9-0624-5d9d-ae28-a4cfe3cd6352', 1, 85000, 0, 85000),
('b5be592b-f665-5362-90a2-f47821a2754d', '8f6e11e9-0624-5d9d-ae28-a4cfe3cd6352', 1, 79000, 0, 79000),
('1de99335-8ee7-5348-87b4-8f5d473c91cb', '0a2a3728-b974-57d0-ae9c-13a7edab83cc', 1, 450000, 15, 450000),
('c2408ddf-507d-537d-a583-e6154e74194a', '0a2a3728-b974-57d0-ae9c-13a7edab83cc', 1, 520000, 0, 520000),
('1fe1315e-bc03-5b15-bba1-590a8c29827c', 'e5aaf20d-9496-544c-9ce0-94dc9aa39bc2', 1, 189000, 0, 189000),
('05b764fd-c9d8-5706-a44e-e0274bffafa5', 'a001fae7-8b63-59d3-84ae-4eac5c1b64b7', 1, 95000, 10, 95000),
('1d4f9001-0a9f-5183-bada-e9220b7e39ff', 'a001fae7-8b63-59d3-84ae-4eac5c1b64b7', 2, 70000, 0, 140000),
('4d7ddcb9-4506-5580-9dff-c15342f04ff9', 'a001fae7-8b63-59d3-84ae-4eac5c1b64b7', 1, 108000, 5, 108000),
('3db1ca4f-77e9-573c-80a5-fd39c5f49a5a', '9b23ddcd-c230-5fe3-83b2-2579c6918f0c', 1, 480000, 0, 480000),
('8437cf11-dd56-5cf7-a166-1738dcd21f42', '1a83bee0-a154-50d0-8efa-e66f8b135329', 2, 99000, 0, 198000),
('b8ebed4f-c5f9-582d-8fa8-190503de1d50', '1a83bee0-a154-50d0-8efa-e66f8b135329', 1, 85000, 0, 85000),
('1de99335-8ee7-5348-87b4-8f5d473c91cb', '0154eb18-1291-53cb-81c7-44ac2522b9d1', 1, 450000, 15, 450000),
('b5be592b-f665-5362-90a2-f47821a2754d', 'cda933fa-83c0-5d62-b1e7-a9304d3b6f83', 1, 79000, 0, 79000),
('1fe1315e-bc03-5b15-bba1-590a8c29827c', 'cda933fa-83c0-5d62-b1e7-a9304d3b6f83', 1, 189000, 20, 189000),
('05b764fd-c9d8-5706-a44e-e0274bffafa5', 'b3a44465-6edb-5f7c-9adc-045f36ff7c2c', 1, 95000, 10, 95000),
('3db1ca4f-77e9-573c-80a5-fd39c5f49a5a', 'b3a44465-6edb-5f7c-9adc-045f36ff7c2c', 1, 480000, 0, 480000);