-- =========================================================
-- NAMMA OORU STUDIO
-- Wedding Photography Management System
-- Database Setup
-- =========================================================


-- Create the database if it does not already exist.
CREATE DATABASE IF NOT EXISTS NAMMA_OORU_STUDIO;


-- Select the database that the application will use.
USE NAMMA_OORU_STUDIO;


-- =========================================================
-- 1. CUSTOMER TABLE
-- =========================================================

-- Create a table to store customer information.
CREATE TABLE CUSTOMER (

    -- Unique ID for every customer.
    Customer_ID INT PRIMARY KEY AUTO_INCREMENT,

    -- Full name of the customer.
    Customer_Name VARCHAR(100) NOT NULL,

    -- Customer phone number.
    -- UNIQUE prevents duplicate phone numbers.
    Phone VARCHAR(15) NOT NULL UNIQUE,

    -- Customer email address.
    Email VARCHAR(100) UNIQUE
);


-- =========================================================
-- 2. PHOTOGRAPHER TABLE
-- =========================================================

-- Create a table to store photographer information.
CREATE TABLE PHOTOGRAPHER (

    -- Unique ID for every photographer.
    Photographer_ID INT PRIMARY KEY AUTO_INCREMENT,

    -- Full name of the photographer.
    Photographer_Name VARCHAR(100) NOT NULL,

    -- Photographer phone number.
    Phone VARCHAR(15) NOT NULL UNIQUE
);


-- =========================================================
-- 3. PACKAGE TABLE
-- =========================================================

-- Create a table to store photography packages.
CREATE TABLE PACKAGE (

    -- Unique ID for every package.
    Package_ID INT PRIMARY KEY AUTO_INCREMENT,

    -- Name of the photography package.
    Package_Name VARCHAR(100) NOT NULL UNIQUE,

    -- Price of the package.
    -- CHECK ensures that price is greater than zero.
    Price DECIMAL(10,2) NOT NULL CHECK (Price > 0)
);


-- =========================================================
-- 4. BOOKING TABLE
-- =========================================================

-- Create a table to store wedding bookings.
CREATE TABLE BOOKING (

    -- Unique ID for every booking.
    Booking_ID INT PRIMARY KEY AUTO_INCREMENT,

    -- ID of the customer who made the booking.
    Customer_ID INT NOT NULL,

    -- ID of the photographer assigned to the booking.
    Photographer_ID INT NOT NULL,

    -- ID of the selected photography package.
    Package_ID INT NOT NULL,

    -- Date of the wedding.
    Wedding_Date DATE NOT NULL,

    -- Location where the wedding photography will take place.
    Venue VARCHAR(200) NOT NULL,

    -- Connect the booking to the CUSTOMER table.
    FOREIGN KEY (Customer_ID)
        REFERENCES CUSTOMER(Customer_ID),

    -- Connect the booking to the PHOTOGRAPHER table.
    FOREIGN KEY (Photographer_ID)
        REFERENCES PHOTOGRAPHER(Photographer_ID),

    -- Connect the booking to the PACKAGE table.
    FOREIGN KEY (Package_ID)
        REFERENCES PACKAGE(Package_ID)
);


-- =========================================================
-- 5. PAYMENT TABLE
-- =========================================================

-- Create a table to store payment information.
CREATE TABLE PAYMENT (

    -- Unique ID for every payment.
    Payment_ID INT PRIMARY KEY AUTO_INCREMENT,

    -- Booking associated with the payment.
    -- UNIQUE represents the 1:1 relationship in the proposal.
    Booking_ID INT NOT NULL UNIQUE,

    -- Amount paid by the customer.
    Amount DECIMAL(10,2) NOT NULL CHECK (Amount > 0),

    -- Date on which the payment was made.
    Payment_Date DATE NOT NULL,

    -- Current payment status.
    Payment_Status VARCHAR(30) NOT NULL,

    -- Connect the payment to the BOOKING table.
    FOREIGN KEY (Booking_ID)
        REFERENCES BOOKING(Booking_ID)
);


-- =========================================================
