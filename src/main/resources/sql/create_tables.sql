use gallery;

# 상품 테이블 생성
create table items
(
    id           int auto_increment primary key,
    name         varchar(50)  not null,
    img_path     varchar(100) not null,
    price        int          not null,
    discount_per int          not null,
    created      datetime     not null default current_timestamp
);

desc items;

INSERT INTO items (name, img_path, price, discount_per)
VALUES ('Starry', '/img/001.jpg', 10000000, 5)
     , ('Seascape', '/img/002.jpg', 20000000, 10)
     , ('Arles', '/img/003.jpg', 30000000, 15)
     , ('Mountain', '/img/004.jpg', 40000000, 20)
     , ('Provence', '/img/005.jpg', 50000000, 25)
     , ('Houses', '/img/006.jpg', 60000000, 30);

commit;

# 회원 테이블 생성
create table members
(
    id       int auto_increment primary key,
    name     varchar(50)  not null,
    login_id varchar(50)  not null unique,
    login_pw varchar(100) not null,
    created  datetime     not null default current_timestamp()
);

# 비밀번호에 해싱을 적용하기 위한 속성 추가
alter table members add login_pw_salt char(16) not null after login_pw;
alter table members modify login_pw char(64) not null;
desc members;

insert into members (name, login_id, login_pw, created)
values ('member1', 'member1@test.com', '1111', current_timestamp);

commit;

# 장바구니 테이블 생성
create table carts
(
    id        int auto_increment primary key,
    member_id int                                  not null,
    item_id   int                                  not null,
    created   datetime default current_timestamp() not null
);
commit;

# 주문 테이블 생성
create table orders
(
    id          int auto_increment primary key,
    member_id   int                                  not null,
    name        varchar(50)                          not null,
    address     varchar(500)                         not null,
    payment     varchar(10)                          not null,
    card_number varchar(16)                          null,
    amount      bigint                               not null,
    created     datetime default current_timestamp() not null
);

# 주문 상품 테이블 생성
create table order_items
(
    id       int auto_increment primary key,
    order_id int                                  not null,
    item_id  int                                  not null,
    created  datetime default current_timestamp() not null
);

# 카드번호 암호화를 위한 준비
# 주문/주문상품 테이블에 저장된 기존 데이터 삭제
truncate table orders;
truncate table order_items;
# 주문 테이블의 카드번호 속성의 최대 길이를 50으로 상향
alter table orders modify card_number varchar(50) null;

# 토큰 차단 테이블: 사용자 로그아웃 시, 로그아웃한 사용자의 리프레시 토큰을 저장
create table blocks
(
    # 리프레시 토큰의 값을 id로 저장
    id      int auto_increment primary key,
    token   varchar(250)                         not null,
    created datetime default current_timestamp() not null
);
commit;

truncate table blocks;