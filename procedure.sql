create database quanlybanhang;
use quanlybanhang;

create table Categories
(
    category_id     int primary key auto_increment,
    category_name   varchar(50) Not null unique,
    category_status Bit Default 1,
    check ( category_status in (0, 1) )

);

create table Products
(
    product_id int primary key auto_increment,
    product_name varchar(20) Not null unique,
    stock Int Not null,
    cost_price double Not null Check(cost_price>0),
    selling_price double Not null Check(selling_price>0),
    created_at Datetime default CURRENT_TIMESTAMP,
    category_id int Not Null,
    foreign key (category_id) references Categories(category_id)

);

delimiter &&
create procedure Proc_GetAllCategories()
begin
    select category_id, category_name, category_status
        from Categories
            where category_status = 1;
end &&
delimiter &&;

delimiter &&
create procedure Proc_AddCategory(
    in categoryName varchar(50),
    in categoryStatus bit
)
begin
    insert into Categories (category_name, category_status)
        value (categoryName,categoryStatus);
end &&
delimiter &&;

delimiter &&
create procedure Proc_UpdateCategory(
    in categoryId int,
    in categoryName varchar(50),
    in categoryStatus bit
)
begin
    update Categories set category_name = categoryName, category_status = categoryStatus
    where category_id = categoryId;
end &&
delimiter &&;

delimiter &&
create procedure Proc_DeleteCategory(
    in categoryId int
)
begin
    update Categories set category_status = 0
    where category_id = categoryId;
end &&
delimiter &&;

delimiter &&
create procedure Proc_CountProductsByCategory()
begin
    select c.category_id, c.category_name, count(p.product_id) as product_count
        from Categories c
    left join Products P on c.category_id = P.category_id
    where c.category_status = 1
    group by c.category_id, c.category_name ;
end &&
delimiter &&;

delimiter &&
create procedure Proc_GetAllProduct()
begin
    select product_id, product_name, stock, cost_price, selling_price, created_at, category_id
    from Products
    where category_id is not null;
end &&
delimiter &&;

delimiter &&
create procedure Proc_AddProduct(
    in p_product_name varchar(20),
    in p_stock int,
    in p_cost_price double,
    in p_selling_price double,
    in p_created_at DATETIME,
    in p_category_id int
)
begin
    insert into products(product_name, stock, cost_price, selling_price, created_at, category_id)
        value (p_product_name, p_stock, p_cost_price, p_selling_price, p_created_at, p_category_id);
end &&
delimiter &&;