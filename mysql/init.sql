CREATE DATABASE IF NOT EXISTS management_system;
use management_system;

create table brand
(
    id       bigint auto_increment
        primary key,
    name     varchar(32)  not null,
    logo_url varchar(255) null,
    constraint brand_name
        unique (name)
);

create table category1
(
    id   bigint auto_increment
        primary key,
    name varchar(32) not null
);

create table category2
(
    id           bigint auto_increment
        primary key,
    name         varchar(32) not null,
    category1_id bigint      not null
);

create table category3
(
    id           bigint auto_increment
        primary key,
    name         varchar(32) not null,
    category2_id bigint      not null
);

create table menu
(
    id          bigint auto_increment
        primary key,
    menuName    varchar(32) not null,
    code        varchar(50) not null,
    pid         bigint      not null,
    level       tinyint     not null,
    create_time datetime    null,
    update_time datetime    null
);

create table properties
(
    id             bigint auto_increment
        primary key,
    prop_name      varchar(32) not null,
    category_id    bigint      not null,
    category_level bigint      not null
);

create table properties_value
(
    id         bigint auto_increment
        primary key,
    value_name varchar(32) not null,
    prop_id    bigint      not null
);

create table role
(
    id          bigint auto_increment
        primary key,
    role_name   varchar(32) not null,
    create_time datetime    null,
    update_time datetime    null,
    constraint role_pk
        unique (role_name)
);

create table role_menu_relation
(
    id      bigint auto_increment
        primary key,
    menu_id bigint not null,
    role_id bigint not null
)
    comment 'many to many relation';

create table sale_attr
(
    id                bigint auto_increment
        primary key,
    spu_id            bigint      not null,
    base_sale_attr_id int         not null,
    sale_attr_name    varchar(32) not null
);

create table sale_attr_value
(
    id                   bigint auto_increment
        primary key,
    spu_id               bigint      not null,
    base_sale_attr_id    int         not null,
    sale_attr_value_name varchar(32) not null,
    sale_attr_name       varchar(32) null
);

create table sku
(
    id                        bigint auto_increment
        primary key,
    sku_name                  varchar(32)  not null,
    spu_id                    bigint       not null,
    is_available              int          not null,
    price                     int          null,
    weight                    int          null,
    description               varchar(300) null,
    sku_default_img           varchar(255) null,
    sku_properties_value_list json         null,
    sku_sale_attr_value_list  json         null,
    category1_id              bigint       not null,
    category2_id              bigint       not null,
    category3_id              bigint       not null
);

create table spu
(
    id           bigint auto_increment
        primary key,
    spu_name     varchar(32)  not null,
    description  varchar(300) null,
    category3_id bigint       not null,
    brand_id     bigint       null
);

create table spu_image
(
    id       bigint auto_increment
        primary key,
    spu_id   bigint       not null,
    img_name varchar(100) null,
    img_url  varchar(255) not null
);

create table user
(
    id          bigint auto_increment
        primary key,
    username    varchar(32)  not null,
    password    varchar(32)  not null,
    avatar      varchar(255) null,
    name        varchar(32)  null,
    create_time datetime     null,
    update_time datetime     null,
    constraint username
        unique (username)
);

create table user_role_relation
(
    id      bigint auto_increment
        primary key,
    user_id bigint not null,
    role_id bigint not null
);

-- initial data
INSERT INTO brand (name, logo_url) VALUES
('nina', 'http://localhost:9000/management-system/44cb4864-44fe-4478-874e-dc3e8b8a6384.png'),
('蓮', 'http://localhost:9000/management-system/ac148587-549b-4f10-bd07-2a90badba6b9.png'),
('yukiyuki', 'http://localhost:9000/management-system/d9ff1bd1-6adc-44cd-91f0-84efa976cc5e.png'),
('yuuki', 'http://localhost:9000/management-system/851ed8fa-73c4-408c-896f-0ecc6628dcf4.png'),
('riri', 'http://localhost:9000/management-system/9d68a585-a203-41ca-a979-a43269c4c7eb.png');

INSERT INTO category1 (name) VALUES
('Phone'),
('Laptop'),
('Graphic Card'),
('CPU'),
('RAM'),
('SSD'),
('Book'),
('Game');

INSERT INTO category2 (name, category1_id) VALUES
('apple', 1),
('samsung', 1),
('dell', 2),
('rog', 2),
('nvdia', 3),
('amd', 3),
('intel', 4),
('amd', 4),
('light novel', 7),
('comic', 7),
('gal game', 8),
('fps', 8),
('ddr4', 5),
('samsung', 6);

INSERT INTO category3 (name, category2_id) VALUES
('iphone', 1),
('ipad', 1),
('galaxy', 2),
('tab', 2),
('gaming', 3),
('office', 3),
('gaming', 4),
('super gaming', 4),
('rtx50 series', 5),
('rtx40 series', 5),
('404', 6),
('i7', 7),
('i9', 7),
('r7', 8),
('r9', 8),
('make heroine', 9),
('isekai', 9),
('kimetsu', 10),
('yuzusoft', 11),
('madosoft', 11),
('apex', 12),
('16gb', 13),
('32gb', 13),
('1tb', 14),
('2tb', 14);

INSERT INTO menu (menuName, code, pid, level, create_time, update_time) VALUES
('All Menu', 'all', 0, 1, '2025-01-29 17:09:29', '2025-01-29 17:09:31'),
('Permission Administration', 'permission', 1, 2, '2025-01-29 17:10:45', '2025-01-29 17:10:47'),
('User Administration', 'user', 2, 3, '2025-01-29 17:11:33', '2025-01-29 17:11:34'),
('Role Administration', 'role', 2, 3, '2025-01-29 17:11:59', '2025-01-29 17:12:00'),
('Menu Administration', 'menu', 2, 3, '2025-01-29 17:12:27', '2025-01-29 17:12:29'),
('Add User', 'btn.user.add', 3, 4, NULL, '2025-02-08 12:32:49'),
('Delete User', 'btn.user.delete', 3, 4, NULL, '2025-02-08 12:33:31'),
('Goods Administration', 'goods', 1, 2, '2025-01-30 15:47:28', '2025-01-30 15:47:28'),
('Brand Administration', 'brand', 8, 3, '2025-01-30 15:47:55', '2025-01-30 15:47:55'),
('Properties Administration', 'properties', 8, 3, '2025-01-30 15:48:12', '2025-01-30 15:48:12'),
('SPU Administration', 'spu', 8, 3, '2025-01-30 15:48:32', '2025-01-30 15:48:32'),
('SKU Administration', 'sku', 8, 3, '2025-01-30 15:49:00', '2025-01-30 15:49:00'),
('Add Role', 'btn.role.add', 4, 4, NULL, '2025-02-08 12:33:43'),
('Add Menu', 'btn.menu.add', 5, 4, NULL, '2025-02-08 12:34:31'),
('Add Brand', 'btn.brand.add', 9, 4, NULL, '2025-02-08 12:35:16'),
('Add Properties', 'btn.properties.add', 10, 4, NULL, '2025-02-08 12:36:22'),
('Add SPU', 'btn.spu.add', 11, 4, NULL, '2025-02-08 12:36:56'),
('Add SKU', 'btn.sku.add', 12, 4, NULL, '2025-02-08 12:37:43'),
('Edit Menu', 'btn.menu.edit', 5, 4, NULL, '2025-02-08 12:34:41'),
('Edit User', 'btn.user.edit', 3, 4, '2025-02-08 12:33:26', '2025-02-08 12:33:26'),
('Edit Role', 'btn.role.edit', 4, 4, '2025-02-08 12:33:58', '2025-02-08 12:33:58'),
('Delete Role', 'btn.role.delete', 4, 4, '2025-02-08 12:34:16', '2025-02-08 12:34:16'),
('Delete Menu', 'btn.menu.delete', 5, 4, '2025-02-08 12:34:57', '2025-02-08 12:34:57'),
('Edit Brand', 'btn.brand.edit', 9, 4, '2025-02-08 12:35:30', '2025-02-08 12:35:30'),
('Delete Brand', 'btn.brand.delete', 9, 4, '2025-02-08 12:35:49', '2025-02-08 12:35:49'),
('Edit Properties', 'btn.properties.edit', 10, 4, '2025-02-08 12:36:34', '2025-02-08 12:36:34'),
('Delete Properties', 'btn.properties.delete', 10, 4, '2025-02-08 12:36:46', '2025-02-08 12:36:46'),
('Edit SPU', 'btn.spu.edit', 11, 4, '2025-02-08 12:37:10', '2025-02-08 12:37:10'),
('Delete SPU', 'btn.spu.delete', 11, 4, '2025-02-08 12:37:34', '2025-02-08 12:37:34'),
('Edit SKU', 'btn.sku.edit', 12, 4, '2025-02-08 12:37:53', '2025-02-08 12:37:53'),
('Delete SKU', 'btn.sku.delete', 12, 4, NULL, '2025-02-08 12:49:20'),
('Distribute Acess', 'btn.role.distribute', 4, 4, '2025-02-08 13:07:47', '2025-02-08 13:07:47'),
('Distribute Role', 'btn.user.distribute', 3, 4, '2025-02-08 13:08:30', '2025-02-08 13:08:30'),
('Change SKU Status', 'btn.sku.change', 12, 4, '2025-02-08 15:18:12', '2025-02-08 15:18:12');

INSERT INTO properties (prop_name, category_id, category_level) VALUES
('storage', 1, 1),
('storage', 5, 3),
('RAM', 5, 3),
('storage', 4, 3);

INSERT INTO properties_value (value_name, prop_id) VALUES
('1tb', 1),
('2tb', 1),
('512gb', 4),
('1tb', 4),
('2tb', 4),
('1tb', 8),
('2tb', 8),
('16gb', 5),
('32gb', 5),
('8gb', 5);

INSERT INTO role (role_name, create_time, update_time) VALUES
('admin', '2025-01-27 15:04:44', '2025-01-27 15:04:46'),
('hr', '2025-01-28 11:06:42', '2025-01-28 11:06:42'),
('visitor', '2025-01-28 11:06:48', '2025-01-28 11:06:48'),
('boss', '2025-01-28 11:07:07', '2025-01-28 11:07:07'),
('employee', '2025-01-28 11:07:21', '2025-01-28 11:07:21');

INSERT INTO role_menu_relation (menu_id, role_id) VALUES
(1, 1),
(2, 1),
(3, 1),
(4, 1),
(5, 1),
(6, 1),
(7, 1),
(8, 1),
(9, 1),
(10, 1),
(11, 1),
(12, 1),
(13, 1),
(14, 1),
(15, 1),
(16, 1),
(17, 1),
(18, 1),
(19, 1),
(20, 1),
(21, 1),
(22, 1),
(23, 1),
(24, 1),
(25, 1),
(26, 1),
(27, 1),
(28, 1),
(29, 1),
(30, 1),
(31, 1),
(32, 1),
(33, 1),
(34, 1);

INSERT INTO sale_attr (spu_id, base_sale_attr_id, sale_attr_name) VALUES
(2, 1, 'color'),
(6, 2, 'version'),
(1, 1, 'color'),
(1, 2, 'version'),
(1, 3, 'size'),
(7, 2, 'version'),
(7, 1, 'color'),
(7, 3, 'size');

INSERT INTO sale_attr_value (spu_id, base_sale_attr_id, sale_attr_value_name, sale_attr_name) VALUES
(2, 1, 'black', 'color'),
(6, 2, '222', 'version'),
(1, 1, 'white', 'color'),
(1, 2, '1', 'version'),
(1, 2, '2', 'version'),
(1, 3, '10kg', 'size'),
(1, 3, '20kg', 'size'),
(7, 2, 'asdads', 'version'),
(7, 1, '222', 'color'),
(7, 3, '111', 'size');

INSERT INTO sku (sku_name, spu_id, is_available, price, weight, description, sku_default_img, sku_properties_value_list, sku_sale_attr_value_list, category1_id, category2_id, category3_id) VALUES
('test', 1, 1, 12, 12, 'this is a sku test', 'http://localhost:9000/management-system/7a5e1784-7af1-4f77-8b2e-8f9f22b28987.png', '[{"valueName": "1tb", "propertiesName": "storage"}]', '[{"valueName": "white", "saleAttrName": "color"}, {"valueName": "2", "saleAttrName": "version"}, {"valueName": "10kg", "saleAttrName": "size"}]', 1, 1, 1);

INSERT INTO spu (spu_name, description, category3_id, brand_id) VALUES
('test 1', 'testing data', 1, 3),
('test 2', 'this is a test data', 2, 4),
('test2', 'testing', 1, 4);

INSERT INTO spu_image (spu_id, img_name, img_url) VALUES
(2, '6.png', 'http://localhost:9000/management-system/e1ccc605-e3dc-4a9a-9fe6-0ebe3057056c.png'),
(6, '1.png', 'http://localhost:9000/management-system/ee0fe7ed-9e4f-4b43-9527-540bd9d55202.png'),
(1, 'test', 'http://localhost:9000/management-system/9d68a585-a203-41ca-a979-a43269c4c7eb.png'),
(1, '1.png', 'http://localhost:9000/management-system/7a5e1784-7af1-4f77-8b2e-8f9f22b28987.png'),
(7, '1.png', 'http://localhost:9000/management-system/dbe6270b-d0f3-42fa-aa97-6c840dc883ed.png');

INSERT INTO user (username, password, avatar, name, create_time, update_time) VALUES
('admin', '202cb962ac59075b964b07152d234b70', 'https://pbs.twimg.com/profile_images/1825774787853275136/9N7OWDBI_400x400.jpg', 'admin', '2025-01-24 21:33:44', '2025-01-26 20:38:38');

INSERT INTO user_role_relation (user_id, role_id) VALUES
(1, 1);