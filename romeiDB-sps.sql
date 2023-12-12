SET sql_mode = '';

DELIMITER $
    create procedure getCurrentStock() begin drop table if exists t1;
    create temporary table t1 as(
        select
            p.ProductName,
            pp.Price,
            pp.ProductPriceID
        from
            Products p,
            ProductPrices pp
        where
            p.ProductId = pp.ProductID
            and pp.inDB = 1
            and p.inDB = 1
    );

    drop table if exists t2;

    create temporary table t2 as(
        select
            p.ProductName,
            pp.Price,
            r.FinalStock
        from
            Registers r,
            ProductPrices pp,
            Products p
        where
            r.ProductPriceID = pp.ProductPriceID
            and pp.ProductID = p.ProductID
            and (
                r.RegisterDate = (
                    select
                        max(RegisterDate)
                    from
                        Registers
                )
            )
            and pp.inDB = 1
            and p.inDB = 1
    );

    select
        distinct t1.ProductName,
        t1.Price,
        ifnull(t2.FinalStock, 0) as CurrentStock,
        t1.ProductPriceID
    from
        t1
        left join t2 on t1.ProductName = t2.ProductName
    group by
        t1.ProductName,
        t1.Price;

    end $ 
DELIMITER ;

DELIMITER $
    create procedure insertAORS(in quantityIn int) begin
    set
        @rID = (
            select
                max(RegisterID)
            from
                Registers
        );

    insert into
        AddedOrRemovedStock(RegisterID, Quantity)
    values
        (@rID, quantityIn);

    end $ 
DELIMITER ;

DELIMITER $
    create procedure insertProduct(
        in contactNameIn varchar(20),
        in productNameIn varchar(20),
        in priceIn int
    ) begin
    set
        @sID = (
            select
                supplierID
            from
                Suppliers
            where
                contactName = contactNameIn
        );

    insert into
        Products(SupplierID, ProductName)
    values
        (@sID, productNameIn);

    set
        @pID = (
            select
                max(ProductID)
            from
                Products
        );

    insert into
        ProductPrices(Price, ProductID)
    values
        (priceIn, @pID);

    end $ 
DELIMITER ;

DELIMITER $
    create procedure getCurrentProducts() begin
    select
        p.ProductID,
        p.ProductName,
        pp.ProductPriceID,
        pp.Price,
        s.SupplierID,
        s.ContactName
    from
        Products p,
        ProductPrices pp,
        Suppliers s
    where
        p.ProductID = pp.ProductID
        and s.SupplierID = p.SupplierID
        and pp.inDB = 1
        and p.inDB = 1
        and s.inDB = 1;

    end $ 
DELIMITER ;

DROP PROCEDURE IF EXISTS getregisterinfoinrange;
DELIMITER $
    create procedure getregisterinfoinrange(
        IN _productpriceID INT,
        IN _datefrom date,
        IN _dateto date
    ) begin
    select
        r.RegisterDate,
        r.QuantitySold,
        r.CashSale
    from
        Registers r,
        ProductPrices pp
    where
        _productpriceID = r.ProductPriceID
        and r.ProductPriceID = pp.ProductPriceID
        and (
            r.RegisterDate between _datefrom and _dateto
        )
        and pp.inDB = 1;

    END $
DELIMITER ;

DROP PROCEDURE IF EXISTS getProductWithPrice;
DELIMITER $
    create procedure getProductWithPrice(
        in productNameIn varchar(20),
        in priceIn decimal(19, 4)
    ) begin
    select
        p.` ProductName `,
        pp.` Price `
    from
        Products p,
        ProductPrices pp
    where
        p.` ProductID ` = pp.` ProductID `
        and p.ProductName = productNameIn
        and pp.Price = priceIn
        and pp.inDB = 1
        and p.inDB = 1;

    END $ 
DELIMITER ;

DELIMITER $
    create procedure setStateProduct(
        in inDBIn bit,
        in productPriceIdIn int,
        in productIdIn int
    ) begin
    update
        ProductPrices
    set
        inDB = inDBIn
    where
        ProductPriceID = productPriceIdIn;

    update
        Products
    set
        inDB = inDBIn
    where
        ProductID = productIdIn;

    end $ 
DELIMITER ;

DELIMITER $
    create procedure updateProduct(
        in contactNameIn varchar(20),
        in productNameIn varchar(20),
        in priceIn int,
        in productIDIn int,
        in productPriceIDIn int
    ) begin
    set
        @sID = (
            select
                supplierID
            from
                Suppliers
            where
                contactName = contactNameIn
        );

    update
        Products
    set
        SupplierID = @sID,
        ProductName = productNameIn
    where
        productID = productIDIn;

    update
        ProductPrices
    set
        Price = priceIn
    where
        productPriceID = productPriceIDIn;

    end $ 
DELIMITER ;

DELIMITER $
    create procedure updateSupplier(
        in contactNameIn varchar(20),
        in phoneIn varchar(20),
        in supplierIDIn int
    ) begin
    update
        Suppliers
    set
        ContactName = contactNameIn,
        Phone = phoneIn
    where
        supplierID = supplierIDIn;

    end $ 
DELIMITER ;

DELIMITER $
    create procedure setStateSupplier(in inDBIn bit, in supplierIDIn int) begin
    update
        Suppliers
    set
        inDB = inDBIn
    where
        SupplierID = supplierIDIn;

    end $
DELIMITER ;

DELIMITER $
    create procedure getUserInfo(
        in userIDIn int,
        in firstNameIn varchar(20),
        in lastNameIn varchar(20),
        in userNameIn varchar(20),
        in passwordIn varchar(20)
    ) begin
    select
        UserID,
        FirstName,
        LastName,
        UserName,
        Password
    from
        users
    where
        UserID = userIDIn
        and FirstName = firstNameIn
        and LastName = lastNameIn
        and UserName = userNameIn
        and Password = passwordIn
        and inDB = 1;

    end $ 
DELIMITER ;

DELIMITER $
    create procedure updateUser(
        in firstNameIn varchar(20),
        in lastNameIn varchar(20),
        in userNameIn varchar(20),
        in passwordIn varchar(20),
        in userIDIn int
    ) begin
    update
        Users
    set
        FirstName = firstNameIn,
        LastName = lastNameIn,
        UserName = userNameIn,
        Password = passwordIn
    where
        UserID = userIDIn;

    end $ 
DELIMITER ;

DELIMITER $
    create procedure setStateUser(in inDBIn bit, in userIDIn int) begin
    update
        Users
    set
        inDB = inDBIn
    where
        UserID = userIDIn;

    end $ 
DELIMITER ;