drop database if exists romeidb;

create database romeidb;

use romeidb;

create table Users(
    UserID int not null AUTO_INCREMENT,
    FirstName varchar(20) not null,
    LastName varchar(20) not null,
    UserName varchar(20) not null,
    Password varchar(20) not null,
    inDB bit not null default 1,
    primary key(UserID)
);

create table Suppliers(
    SupplierID int not null AUTO_INCREMENT,
    ContactName varchar(20) not null,
    Phone varchar(20) not null,
    inDB bit not null default 1,
    primary key(SupplierID)
);

create table Products(
    ProductID int not null AUTO_INCREMENT,
    SupplierID int not null,
    ProductName varchar(20) not null,
    inDB bit not null default 1,
    primary key(ProductID),
    foreign key (SupplierID) references Suppliers(SupplierID)
);

create table ProductPrices(
    ProductPriceID int not null AUTO_INCREMENT,
    ProductID int not null,
    Price decimal(19, 4) not null,
    inDB bit not null default 1,
    primary key(ProductPriceID),
    foreign key (ProductID) references Products(ProductID)
);

create table Registers(
    RegisterID int not null AUTO_INCREMENT,
    UserID int not null,
    RegisterDate date not null,
    ProductPriceID int not null,
    InitialStock int not null,
    FinalStock int not null,
    QuantitySold int not null,
    CashSale decimal(19, 4) not null,
    primary key(RegisterID),
    foreign key (UserID) references Users(UserID),
    foreign key (ProductPriceID) references ProductPrices(ProductPriceID)
);

create table AddedOrRemovedStock(
    AddedOrRemovedStockID int not null AUTO_INCREMENT,
    RegisterID int not null,
    Quantity int not null,
    primary key(AddedOrRemovedStockID),
    foreign key (RegisterID) references Registers(RegisterID)
);

insert into
    Suppliers(ContactName, Phone)
values
    ('Sra. Rosa', '098749824');

insert into
    Suppliers(ContactName, Phone)
values
    ('Don Ricardo', '098749824');

insert into
    Products(ProductName, SupplierID)
values
    ('Pantalon', 1);

insert into
    Products(ProductName, SupplierID)
values
    ('Short', 2);

insert into
    ProductPrices(Price, ProductID)
values
    (10, 1);

insert into
    ProductPrices(Price, ProductID)
values
    (13, 1);

insert into
    ProductPrices(Price, ProductID)
values
    (6, 2);

insert into
    Users(FirstName, LastName, UserName, Password)
values
    ('Nathaly', 'Pino', 'npino', '12345');

insert into
    Users(FirstName, LastName, UserName, Password)
values
    ('Laila', 'Alvear', 'lalvear', '54321');

insert into
    Users(FirstName, LastName, UserName, Password)
values
    ('Alejandro', 'Mena', 'rmena', '6789');

insert into
    Registers(
        UserID,
        RegisterDate,
        ProductPriceID,
        InitialStock,
        FinalStock,
        QuantitySold,
        CashSale
    )
values
    (1, '2017-08-15', 1, 40, 36, 4, 40);

insert into
    Registers(
        UserID,
        RegisterDate,
        ProductPriceID,
        InitialStock,
        FinalStock,
        QuantitySold,
        CashSale
    )
values
    (1, '2017-08-15', 2, 45, 44, 1, 13);

insert into
    Registers(
        UserID,
        RegisterDate,
        ProductPriceID,
        InitialStock,
        FinalStock,
        QuantitySold,
        CashSale
    )
values
    (1, '2017-08-15', 3, 40, 34, 6, 36);
