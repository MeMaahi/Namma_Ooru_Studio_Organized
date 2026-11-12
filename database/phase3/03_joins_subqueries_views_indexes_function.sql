-- NAMMA OORU STUDIO | PHASE 3
-- Joins, subqueries, view, indexes, stored function and transaction examples.
USE NAMMA_OORU_STUDIO;

-- 1. JOIN: booking details with customer, photographer and package.
SELECT b.Booking_ID, c.Customer_Name, p.Photographer_Name,
       pkg.Package_Name, pkg.Price, b.Wedding_Date, b.Venue
FROM BOOKING b
JOIN CUSTOMER c ON b.Customer_ID = c.Customer_ID
JOIN PHOTOGRAPHER p ON b.Photographer_ID = p.Photographer_ID
JOIN PACKAGE pkg ON b.Package_ID = pkg.Package_ID
ORDER BY b.Wedding_Date;

-- 2. Customers with more bookings than the average number of bookings per customer.
SELECT c.Customer_ID, c.Customer_Name, COUNT(b.Booking_ID) AS Booking_Count
FROM CUSTOMER c
JOIN BOOKING b ON c.Customer_ID = b.Customer_ID
GROUP BY c.Customer_ID, c.Customer_Name
HAVING COUNT(b.Booking_ID) > (
    SELECT AVG(customer_booking_count)
    FROM (
        SELECT COUNT(*) AS customer_booking_count
        FROM CUSTOMER c2
        LEFT JOIN BOOKING b2 ON c2.Customer_ID = b2.Customer_ID
        GROUP BY c2.Customer_ID
    ) AS booking_counts
);

-- 3. Packages priced above the average package price.
SELECT Package_ID, Package_Name, Price
FROM PACKAGE
WHERE Price > (SELECT AVG(Price) FROM PACKAGE);

-- 4. View to reuse a joined booking summary.
CREATE OR REPLACE VIEW Booking_Summary AS
SELECT b.Booking_ID, c.Customer_Name, c.Phone AS Customer_Phone,
       p.Photographer_Name, pk.Package_Name, pk.Price,
       b.Wedding_Date, b.Venue
FROM BOOKING b
JOIN CUSTOMER c ON b.Customer_ID = c.Customer_ID
JOIN PHOTOGRAPHER p ON b.Photographer_ID = p.Photographer_ID
JOIN PACKAGE pk ON b.Package_ID = pk.Package_ID;

-- Use the view:
SELECT * FROM Booking_Summary ORDER BY Wedding_Date;

-- 5. Indexes to support common joins and lookups.
CREATE INDEX idx_booking_customer ON BOOKING(Customer_ID);
CREATE INDEX idx_booking_photographer ON BOOKING(Photographer_ID);
CREATE INDEX idx_booking_package ON BOOKING(Package_ID);
CREATE INDEX idx_payment_booking ON PAYMENT(Booking_ID);

-- 6. EXPLAIN: inspect the query execution plan.
EXPLAIN
SELECT b.Booking_ID, c.Customer_Name
FROM BOOKING b
JOIN CUSTOMER c ON b.Customer_ID = c.Customer_ID
WHERE c.Customer_ID = 1;

-- 7. Stored function: number of bookings for one customer.
DELIMITER //
CREATE FUNCTION GetCustomerBookingCount(p_customer_id INT)
RETURNS INT
READS SQL DATA
BEGIN
    DECLARE booking_count INT DEFAULT 0;
    SELECT COUNT(*) INTO booking_count
    FROM BOOKING
    WHERE Customer_ID = p_customer_id;
    RETURN booking_count;
END //
DELIMITER ;

-- Use the function (replace 1 with an existing Customer_ID):
SELECT GetCustomerBookingCount(1) AS Booking_Count;

