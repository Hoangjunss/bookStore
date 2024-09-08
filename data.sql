INSERT INTO role VALUES (1, 'admin'),
                        (2, 'user')

INSERT INTO payment_status VALUES (1, 'receive'),
                                (2, 'online')

INSERT INTO order_status VALUES (1, 'pending'),
                            (2, 'approved'),
                            (3, 'delivering')

INSERT INTO inventory_status VALUES (1, 'completed'),
                                    (2, 'cancel')

INSERT INTO `user` (id) VALUES (1) 

INSERT INTO category VALUES (1, 'horrified'),
                            (2, 'study'),
                            (3, 'comic')

-- Chèn dữ liệu vào bảng product
INSERT INTO product(id, author, language, page_count, price, price_sale, publication_date, quantity, title, id_category, url) VALUES
                    (1, 'Kevin Chen', 'Tiếng Việt', 413, 130000, NULL, '2023-01-01', 100, 'Vùng Đất Quỷ Tha Ma Bắt', 1, 'url_1'),
                    (2, 'Thảo Trang', 'Tiếng Việt', 544, 170000, NULL, '2022-01-01', 100, 'Tết Ở Làng Địa Ngục', 1, 'url_2'),
                    (3, 'Emma Hạ My', 'Tiếng Việt', 332, 168000, NULL, '2024-01-01', 100, 'Tổng Đài Kể Chuyện Lúc 0h', 1, 'url_3'),
                    (4, 'Tống Ngọc', 'Tiếng Việt', 200, 105000, NULL, '2024-01-01', 100, 'Giếng Độc', 1, 'url_4'),
                    (5, 'Lư Lạp Lạp', 'Tiếng Việt', 200, 94000, NULL, '2023-01-01', 100, 'Người Dọn Dẹp Hiện Trường Án Mạng', 1, 'url_5'),
                    (6, 'Emma Hạ My', 'Tiếng Việt', 264, 102000, NULL, '2023-01-01', 100, 'Sĩ Số Lớp Vắng 0', 1, 'url_6'),
                    (7, 'Doo Vandenis', 'Tiếng Việt', 316, 169000, NULL, '2023-01-01', 100, '17 Âm 1', 1, 'url_7'),
                    (8, 'Thảo Trang', 'Tiếng Việt', 400, 185000, NULL, '2023-01-01', 100, 'Ngủ Cùng Người Chết', 1, 'url_8'),
                    (9, 'Linh Lan', 'Tiếng Việt', 250, 268000, NULL, '2024-01-01', 100, 'Trung Hoa Bách Quỷ Lục', 1, 'url_9'),
                    (10, 'Thục Linh', 'Tiếng Việt', 488, 189000, NULL, '2024-01-01', 100, 'Rừng Than Khóc', 1, 'url_10');

-- Chèn dữ liệu vào bảng product
INSERT INTO product(id, author, language, page_count, price, price_sale, publication_date, quantity, title, id_category, url) VALUES
(11, 'Lê Bích', 'Tiếng Việt', 162, 90000, NULL, '2022-01-01', 100, 'Đời Về Cơ Bản Là Buồn Cười (Tái Bản 2022)', 3, 'url_11'),
(12, 'Vương Hồng Sển', 'Tiếng Việt', 280, 215000, NULL, '2024-01-01', 100, 'Chuyện Cười Cổ Nhân', 3, 'url_12'),
(13, 'Minh Phong', 'Tiếng Việt', 323, 105000, NULL, '2020-01-01', 100, 'Cơ Hội Đổi Đời', 3, 'url_13'),
(14, 'Lê Minh Quốc', 'Tiếng Việt', 288, 85000, NULL, '2017-01-01', 100, 'Lắt Léo Tiếng Việt', 3, 'url_14'),
(15, 'Song Hà', 'Tiếng Việt', 400, 150000, NULL, '2018-02-01', 100, 'Những Chuyện Bựa Thời Sinh Viên', 3, 'url_15'),
(16, 'Lê Văn Nghĩa', 'Tiếng Việt', 225, 85000, NULL, '2021-01-01', 100, 'Điệp Viên Không Không Thấy Và Đại Văn Mỗ', 3, 'url_16'),
(17, 'Tam Vũ', 'Tiếng Việt', 218, 35000, NULL, '2018-01-01', 100, 'Bỗng Dưng Thành Khổng Lồ', 3, 'url_17'),
(18, 'Lê Văn Nghĩa', 'Tiếng Việt', 223, 85000, NULL, '2021-01-01', 100, 'Điệp Viên Không Không Thấy Và Nhà Thơ Thần Giáng', 3, 'url_18'),
(19, 'Nhiều tác giả', 'Tiếng Việt', 155, 22000, NULL, '2010-01-01', 100, 'Ba Giai Tú Xuất & Truyện Thủ Thiệm', 3, 'url_19'),
(20, 'Hoa Sơn', 'Tiếng Việt', 176, 79000, NULL, '2023-01-01', 100, 'Những Mẫu Chuyện Hài Hước Trong Kinh Doanh Của Người Do Thái', 3, 'url_20');

-- Chèn dữ liệu vào bảng product
INSERT INTO product(id, author, language, page_count, price, price_sale, publication_date, quantity, title, id_category, url) VALUES
(21, 'Kang Ah', 'Dịch về tiếng Việt', 224, 210000, NULL, '2022-01-01', 100, 'Chú Mèo Rụng Lông Không Có Điểm Dừng', 2, 'url_21'),
(22, 'Quỳnh Lưu, Chu Ân', 'Tiếng Việt', 192, 115000, NULL, '2024-01-01', 100, 'Nếu Có Người Yêu, Em Thích…', 2, 'url_22'),
(23, 'Lê Trọng Nghĩa', 'Tiếng Việt', 116, 89000, NULL, '2024-01-01', 100, '9H Ta Hỏi', 2, 'url_23'),
(24, 'Đạo Quang', 'Tiếng Việt', 114, 55000, NULL, '2021-01-01', 100, 'Truyện Tranh - Kinh Pháp Hoa - Tập 1', 2, 'url_24'),
(25, 'Nguyễn Phước Minh Mẫn', 'Tiếng Việt', 149, 52000, NULL, '2021-01-01', 100, 'Truyện Tranh Đức Phật Thích Ca - Từ Xuất Gia Đến Hành Đạo - Tập 2', 2, 'url_25'),
(26, 'Nguyễn Phước Minh Mẫn', 'Tiếng Việt', 126, 45000, NULL, '2021-01-01', 100, 'Truyện Tranh Đức Phật Thích Ca - Từ Xuất Gia Đến Hành Đạo - Tập 3', 2, 'url_26'),
(27, 'Quỳnh Lưu, Chu Ân', 'Tiếng Việt', 192, 115000, NULL, '2024-01-01', 100, 'Nếu Có Người Yêu, Em Thích… - Tặng Kèm Chữ Ký Tác Giả', 2, 'url_27'),
(28, 'Minh Họa: Nonchan, An Băng', 'Tiếng Việt', 34, 45000, NULL, '2024-01-01', 100, 'Ai Đã Gõ Vào Thân Cây? - Chùm Truyện Ngắn Tạo Cảm Hứng Cho Em Làm Văn', 2, 'url_28'),
(29, 'Astrid Lindgren', 'Tiếng Việt', 224, 59000, NULL, '2024-01-01', 100, 'Lotta Bướng Như Lừa', 2, 'url_29'),
(30, 'Xiao Pin', 'Dịch sang tiếng Việt', 160, 58000, NULL, '2024-01-01', 100, 'Truyện Tranh Lớp Học Xả Stress - Con Gái Không Dễ Bắt Nạt Đâu', 2, 'url_30');
