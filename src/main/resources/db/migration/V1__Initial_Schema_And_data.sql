CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

DROP TABLE IF EXISTS permissions CASCADE;
CREATE TABLE permissions (
    permission_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    permission_name VARCHAR(100) NOT NULL,
    permission_key VARCHAR(100) UNIQUE NOT NULL 
);

DROP TABLE IF EXISTS roles CASCADE;
CREATE TABLE roles (
    role_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    role_name VARCHAR(30) NOT NULL,
    role_key VARCHAR(100) UNIQUE NOT NULL
);

DROP TABLE IF EXISTS users CASCADE;
CREATE TABLE users (
    user_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    email VARCHAR(30) UNIQUE NOT NULL,
    password VARCHAR(64) DEFAULT NULL,
    status CHAR(1) DEFAULT '0',
    del_flag CHAR(1) DEFAULT '0'
);

DROP TABLE IF EXISTS role_permissions CASCADE;
CREATE TABLE role_permissions (
    role_id UUID NOT NULL,
    permission_id UUID NOT NULL,
    PRIMARY KEY (role_id, permission_id),
    FOREIGN KEY (role_id) REFERENCES roles (role_id) ON DELETE CASCADE,
    FOREIGN KEY (permission_id) REFERENCES permissions (permission_id) ON DELETE CASCADE
);

DROP TABLE IF EXISTS user_roles CASCADE;
CREATE TABLE user_roles (
    user_id UUID NOT NULL,
    role_id UUID NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles (role_id) ON DELETE CASCADE
);

DROP TABLE IF EXISTS sims CASCADE;
CREATE TABLE sims (
    sim_id UUID PRIMARY KEY DEFAULT gen_random_uuid(), 
    sim_phone_number VARCHAR(20) NOT NULL UNIQUE,
    sim_status INT NOT NULL,
    sim_selling_price INT,
    sim_dealer_price INT,
    sim_import_price INT,
    note TEXT,
    description TEXT,
    created_at TIMESTAMP WITHOUT TIME ZONE,
    updated_at TIMESTAMP WITHOUT TIME ZONE
);

DO $$
DECLARE
    ADMIN_UID UUID := 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11';
    SELLER_UID UUID := 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a12';
    BUYER_UID UUID := 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a13';
    SELLER_BUYER_UID UUID := 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a14';
    
    ADMIN_RID UUID := 'b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b11';
    SELLER_RID UUID := 'b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b12';
    BUYER_RID UUID := 'b0eebc99-9c0b-4ef8-bb6d-6bb9bd380b13';
BEGIN
    INSERT INTO roles (role_id, role_name, role_key) VALUES 
        (ADMIN_RID, 'Quản trị viên', 'ADMIN'),
        (SELLER_RID, 'Người bán', 'SELLER'),
        (BUYER_RID, 'Người mua', 'BUYER');

    INSERT INTO permissions (permission_name, permission_key) VALUES 
        ('Quản lý sản phẩm', 'seller:product:manage'),
        ('Xem đơn hàng của Shop', 'seller:order:view'),
        ('Phản hồi đánh giá', 'seller:review:reply'),
        ('Tạo mã giảm giá của shop', 'seller:voucher:create'),
        ('Xem sản phẩm', 'buyer:product:manage'),
        ('Thêm vào giỏ hàng', 'buyer:cart:add'),
        ('Thực hiện thanh toán', 'buyer:order:checkout'),
        ('Viết đánh giá sản phẩm', 'buyer:review:create'),
        ('Quản lý tài khoản người dùng', 'admin:user:manage'),
        ('Quản lý danh mục', 'admin:category:manage'),
        ('Xem báo cáo', 'admin:report:view'),
        ('Kiểm duyệt sản phẩm', 'admin:product:moderate');

    INSERT INTO users (user_id, email, password, status, del_flag) VALUES 
        (ADMIN_UID, 'admin@gmail.com', '$2b$10$aFCsDS3JNrk3lR/mopRPROvACMVX4rj8v0QTpalcFbuUc9YuYqwFu', '0', '0'),
        (SELLER_UID, 'shop1@gmail.com', '$2b$10$aFCsDS3JNrk3lR/mopRPROvACMVX4rj8v0QTpalcFbuUc9YuYqwFu', '0', '0'),
        (BUYER_UID, 'nguyenvana@gmail.com', '$2b$10$aFCsDS3JNrk3lR/mopRPROvACMVX4rj8v0QTpalcFbuUc9YuYqwFu', '0', '0'),
        (SELLER_BUYER_UID, 'combo@gmail.com', '$2b$10$aFCsDS3JNrk3lR/mopRPROvACMVX4rj8v0QTpalcFbuUc9YuYqwFu', '0', '0');

    INSERT INTO user_roles (user_id, role_id) VALUES 
        (ADMIN_UID, ADMIN_RID),
        (SELLER_UID, SELLER_RID),
        (BUYER_UID, BUYER_RID),
        (SELLER_BUYER_UID, SELLER_RID),
        (SELLER_BUYER_UID, BUYER_RID);
    
    INSERT INTO role_permissions (role_id, permission_id)
    SELECT SELLER_RID, permission_id FROM permissions WHERE permission_key IN (
        'seller:product:manage', 'seller:order:view', 'seller:review:reply', 'seller:voucher:create'
    );
    
    INSERT INTO role_permissions (role_id, permission_id)
    SELECT BUYER_RID, permission_id FROM permissions WHERE permission_key IN (
        'buyer:product:manage', 'buyer:cart:add', 'buyer:order:checkout', 'buyer:review:create'
    );
    
    INSERT INTO role_permissions (role_id, permission_id)
    SELECT ADMIN_RID, permission_id FROM permissions WHERE permission_key LIKE 'admin:%';
END;
$$ LANGUAGE plpgsql;
