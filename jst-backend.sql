/*******************************************************************************
   Create database
********************************************************************************/
drop table users;
drop table dishes;





/*******************************************************************************
   Create Tables
********************************************************************************/

CREATE TABLE USERS
(
    users_id                NUMBER           NOT NULL,
    first_name              VARCHAR2(20)     NOT NULL,
    last_name               VARCHAR2(20)     NOT NULL,
    username                VARCHAR2(20)     NOT NULL UNIQUE,
    password                VARCHAR2(20)     NOT NULL,
    email                   VARCHAR2(20)     NOT NULL UNIQUE,
    
     CONSTRAINT PK_Customers PRIMARY KEY(users_id )
);

CREATE TABLE DISHES 
(
    dish_id                     NUMBER,                     
    dish_name                   VARCHAR2(30),
    dish_description            VARCHAR2(500),
    dish_price                  DOUBLE PRECISION,
    dish_picture                BLOB,

    CONSTRAINT PK_dish_id PRIMARY KEY(dish_id)
);

/*******************************************************************************
   Create Foreign Keys
********************************************************************************/


/*******************************************************************************
   Populate Tables
********************************************************************************/

INSERT INTO inventory (dish_id, dish_name, dish_description, dish_price, dish_picture)
    VALUES (1, 'Applewood Smoked Bacon', 'Cured bacon smoked with Applewood', 9.99, "https://s3.amazonaws.com/k-craft-bacon/bacon-pics/applewood+bacon.jpg");



INSERT INTO users 
VALUES (1, 'admin', 'user', 'admin', 'password', 'admin@gmail.com' );

commit;
