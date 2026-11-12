USE NAMMA_OORU_STUDIO;

-- 6. LOGIN_USER TABLE
-- =========================================================

-- This table stores login credentials for all user types.
-- It allows Admin, Customer and Photographer accounts.
CREATE TABLE LOGIN_USER (

    -- Unique ID for every login account.
    User_ID INT PRIMARY KEY AUTO_INCREMENT,

    -- Username used during login.
    -- UNIQUE prevents two users from having the same username.
    Username VARCHAR(50) NOT NULL UNIQUE,

    -- Password used during login.
    Password VARCHAR(100) NOT NULL,

    -- Identifies the type of account.
    Role VARCHAR(20) NOT NULL,

    -- Links a customer account to the CUSTOMER table.
    -- NULL because Admin and Photographer accounts may not use it.
    Customer_ID INT NULL,

    -- Links a photographer account to the PHOTOGRAPHER table.
    -- NULL because Admin and Customer accounts may not use it.
    Photographer_ID INT NULL,

    -- Only these three account types are allowed.
    CHECK (
        Role IN ('ADMIN', 'CUSTOMER', 'PHOTOGRAPHER')
    ),

    -- Connect Customer_ID with CUSTOMER.
    FOREIGN KEY (Customer_ID)
        REFERENCES CUSTOMER(Customer_ID),

    -- Connect Photographer_ID with PHOTOGRAPHER.
    FOREIGN KEY (Photographer_ID)
        REFERENCES PHOTOGRAPHER(Photographer_ID)
);