-- Drop tables if they exist to avoid conflicts
DROP TABLE IF EXISTS payments;
DROP TABLE IF EXISTS dependents;
DROP TABLE IF EXISTS baggage;
DROP TABLE IF EXISTS tickets;
DROP TABLE IF EXISTS flight_crew;
DROP TABLE IF EXISTS aircraft_maintenance_log;
DROP TABLE IF EXISTS flights;
DROP TABLE IF EXISTS aircraft;
DROP TABLE IF EXISTS passengers;
DROP TABLE IF EXISTS tsa;
DROP TABLE IF EXISTS airport_staff;
DROP TABLE IF EXISTS lounges;
DROP TABLE IF EXISTS terminals;
DROP TABLE IF EXISTS airlines;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS ticket_classes;
DROP TABLE IF EXISTS airports;


-- Create tables based on the ER diagram

-- Users table for authentication
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL
);

-- Airlines table
CREATE TABLE airlines (
    airline_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

-- Terminals table
CREATE TABLE terminals (
    terminal_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    location VARCHAR(100) NOT NULL
);

-- Lounges table
CREATE TABLE lounges (
    lounge_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    location VARCHAR(100) NOT NULL,
    loyalty_id VARCHAR(50),
    terminal_id BIGINT,
    FOREIGN KEY (terminal_id) REFERENCES terminals(terminal_id)
);

-- Airport Staff table
CREATE TABLE airport_staff (
    staff_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    role VARCHAR(50) NOT NULL,
    terminal_id BIGINT,
    FOREIGN KEY (terminal_id) REFERENCES terminals(terminal_id)
);

-- TSA table
CREATE TABLE tsa (
    personal_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    clearance_level VARCHAR(50) NOT NULL,
    checkpoint_number INT NOT NULL,
    terminal_id BIGINT,
    FOREIGN KEY (terminal_id) REFERENCES terminals(terminal_id)
);

-- Passengers table
CREATE TABLE passengers (
    passenger_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    phone VARCHAR(20),
    loyalty_id VARCHAR(50)
);

-- Aircraft table
CREATE TABLE aircraft (
    aircraft_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    type VARCHAR(50) NOT NULL,
    capacity INT NOT NULL,
    airline_id BIGINT,
    FOREIGN KEY (airline_id) REFERENCES airlines(airline_id)
);

CREATE TABLE airports (
    airport_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    city VARCHAR(100),
    country VARCHAR(100),
    code VARCHAR(10) UNIQUE -- e.g., DFW, LAX
);

-- Flights table
CREATE TABLE flights (
    flight_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    origin_airport_id BIGINT,
    destination_airport_id BIGINT,
    departure_time DATETIME NOT NULL,
    arrival_time DATETIME NOT NULL,
    status VARCHAR(50) NOT NULL,
    airline_id BIGINT,
    aircraft_id BIGINT,
    FOREIGN KEY (airline_id) REFERENCES airlines(airline_id),
    FOREIGN KEY (aircraft_id) REFERENCES aircraft(aircraft_id),
    FOREIGN KEY (origin_airport_id) REFERENCES airports(airport_id),
    FOREIGN KEY (destination_airport_id) REFERENCES airports(airport_id)
);

-- Flight Crew table
CREATE TABLE flight_crew (
    crew_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    role VARCHAR(50) NOT NULL,
    flight_id BIGINT,
    airline_id BIGINT,
    FOREIGN KEY (flight_id) REFERENCES flights(flight_id),
    FOREIGN KEY (airline_id) REFERENCES airlines(airline_id)
);

-- Aircraft Maintenance Log table
CREATE TABLE aircraft_maintenance_log (
    log_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    aircraft_id BIGINT,
    flight_id BIGINT,
    maintenance_date DATE,
    status VARCHAR(50), -- e.g., DONE, PENDING
    FOREIGN KEY (aircraft_id) REFERENCES aircraft(aircraft_id),
    FOREIGN KEY (flight_id) REFERENCES flights(flight_id)
);

CREATE TABLE ticket_classes (
    class_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50), -- First, Business, Economy
    benefits TEXT
);

-- Tickets table
CREATE TABLE tickets (
    ticket_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    seat VARCHAR(10) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    booked_status BOOLEAN NOT NULL DEFAULT FALSE,
    passenger_id BIGINT,
    flight_id BIGINT,
    class_id BIGINT,
    FOREIGN KEY (class_id) REFERENCES ticket_classes(class_id),
    FOREIGN KEY (passenger_id) REFERENCES passengers(passenger_id),
    FOREIGN KEY (flight_id) REFERENCES flights(flight_id)
);

-- Baggage table
CREATE TABLE baggage (
    baggage_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    location VARCHAR(100),
    status VARCHAR(50) NOT NULL,
    passenger_id BIGINT,
    flight_id BIGINT,
    FOREIGN KEY (passenger_id) REFERENCES passengers(passenger_id),
    FOREIGN KEY (flight_id) REFERENCES flights(flight_id)
);

-- Dependents table
CREATE TABLE dependents (
    dependent_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    phone VARCHAR(20),
    loyalty_id VARCHAR(50),
    passenger_id BIGINT,
    ticket_id BIGINT,
    FOREIGN KEY (passenger_id) REFERENCES passengers(passenger_id),
    FOREIGN KEY (ticket_id) REFERENCES tickets(ticket_id)
);

-- Payments table
CREATE TABLE payments (
    payment_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    date DATE NOT NULL,
    paid_status BOOLEAN NOT NULL DEFAULT FALSE,
    ticket_id BIGINT,
    FOREIGN KEY (ticket_id) REFERENCES tickets(ticket_id)
);

-- Insert initial data for terminals
INSERT INTO terminals (name, location) VALUES 
    ('Terminal A', 'North Wing'),
    ('Terminal B', 'South Wing');

-- Insert initial data for airlines
INSERT INTO airlines (name) VALUES 
    ('SkyHigh Airlines'),
    ('Global Airways');

-- Insert lounges (one per terminal)
INSERT INTO lounges (location, loyalty_id, terminal_id) VALUES 
    ('Terminal A - Level 3', 'GOLD', 1),
    ('Terminal B - Level 2', 'GOLD', 2);

-- Insert airports
INSERT INTO airports (name, city, country, code) VALUES
('DFW International', 'Dallas', 'USA', 'DFW'),
('LAX International', 'Los Angeles', 'USA', 'LAX'),
('JFK International', 'New York', 'USA', 'JFK'),
('ORD International', 'Chicago', 'USA', 'ORD'),
('ATL International', 'Atlanta', 'USA', 'ATL');

-- Insert ticket classes
INSERT INTO ticket_classes (name, benefits) VALUES
('Economy', 'Standard seating, 1 carry-on'),
('Business', 'Premium seating, 2 carry-ons, priority boarding'),
('First Class', 'Luxury seating, lounge access, priority everything');

-- Insert users for authentication
INSERT INTO users (username, password, role) VALUES
('admin', '$2a$10$X7L4FxfZ1J4RX1XZLP.YY.jmvxEg5Q5WdO7.3ZoFiYwI7g9KGi3.q', 'ADMIN'),
('passenger1', '$2a$10$kMmUQR.jEWYDGXK1L1APhe8.CrdHBjlq0j6EbQZ0wOe8tL8ueVxuy', 'PASSENGER'),
('crewmember1', '$2a$10$H5wvnDS9rXmKF6dn3PPjQuLrJuIxHYLXPZtB9hsOdI1d4JQKhKm72', 'CREW'),
('tsa1', '$2a$10$8LGAh.zYg8oE5GCXBexPEOY7gS/a0D3J9YqJfCxKiMM1zUszPP1pa', 'TSA'),
('airline1', '$2a$10$PCRnBJWFEduVe9L.V4dF6eQfJlTpx7l7XuGXh1chGfD44rUr76G6i', 'AIRLINE'),
('staff1', '$2a$10$WvXYDgPQP.x.gVJIJJ7SjeCXCGUZlDy5paF5Nl29NSzfR3NYAkjJC', 'STAFF');

-- Insert aircraft
INSERT INTO aircraft (name, type, capacity, airline_id) VALUES
('Boeing 737-800', 'Narrow-body', 162, 1),
('Airbus A320', 'Narrow-body', 150, 1),
('Boeing 737-700', 'Narrow-body', 143, 2),
('Airbus A319', 'Narrow-body', 124, 2),
('Bombardier CRJ-900', 'Regional', 76, 1),
('Embraer E175', 'Regional', 76, 2);

-- Insert flights (departing every hour, arriving every 1.5 hours)
INSERT INTO flights (origin_airport_id, destination_airport_id, departure_time, arrival_time, status, airline_id, aircraft_id) VALUES
-- SkyHigh Airlines flights
(1, 2, '2025-04-22 08:00:00', '2025-04-22 10:30:00', 'ON TIME', 1, 1),
(1, 3, '2025-04-22 09:00:00', '2025-04-22 12:30:00', 'ON TIME', 1, 2),
(1, 4, '2025-04-22 10:00:00', '2025-04-22 11:45:00', 'ON TIME', 1, 1),
(1, 5, '2025-04-22 11:00:00', '2025-04-22 14:30:00', 'DELAYED', 1, 2),
-- Global Airways flights
(1, 2, '2025-04-22 12:00:00', '2025-04-22 14:30:00', 'ON TIME', 2, 3),
(1, 3, '2025-04-22 13:00:00', '2025-04-22 16:30:00', 'ON TIME', 2, 4),
(1, 4, '2025-04-22 14:00:00', '2025-04-22 15:45:00', 'ON TIME', 2, 3),
(1, 5, '2025-04-22 15:00:00', '2025-04-22 18:30:00', 'ON TIME', 2, 4),
-- SkyHigh arrival flights
(2, 1, '2025-04-22 07:00:00', '2025-04-22 09:30:00', 'LANDED', 1, 1),
(3, 1, '2025-04-22 08:30:00', '2025-04-22 12:00:00', 'LANDED', 1, 2),
-- Global Airways arrival flights
(4, 1, '2025-04-22 10:00:00', '2025-04-22 11:45:00', 'LANDED', 2, 3),
(5, 1, '2025-04-22 11:30:00', '2025-04-22 15:00:00', 'LANDED', 2, 4);

-- Insert passengers
INSERT INTO passengers (name, phone, loyalty_id) VALUES
('John Smith', '214-555-1234', 'SKY123456'),
('Jane Doe', '214-555-5678', 'SKY234567'),
('Robert Johnson', '214-555-9012', 'GLB123456'),
('Mary Williams', '214-555-3456', 'GLB234567'),
('James Brown', '214-555-7890', NULL),
('Patricia Davis', '214-555-2345', NULL),
('Jennifer Wilson', '214-555-6789', 'SKY345678'),
('Michael Moore', '214-555-0123', 'GLB345678'),
('Elizabeth Taylor', '214-555-4567', NULL),
('David Anderson', '214-555-8901', 'SKY456789');

-- Insert tickets
INSERT INTO tickets (seat, price, booked_status, passenger_id, flight_id, class_id) VALUES
-- Flight 1 tickets
('12A', 299.99, TRUE, 1, 1, 1),
('12B', 299.99, TRUE, 2, 1, 1),
('1A', 899.99, TRUE, 3, 1, 3),
('5C', 599.99, TRUE, 4, 1, 2),
-- Flight 2 tickets
('14D', 349.99, TRUE, 5, 2, 1),
('14E', 349.99, TRUE, 6, 2, 1),
('2A', 949.99, TRUE, 7, 2, 3),
('7C', 649.99, TRUE, 8, 2, 2),
-- Flight 3 tickets
('18F', 279.99, TRUE, 9, 3, 1),
('18E', 279.99, TRUE, 10, 3, 1),
-- Flight 5 tickets
('10A', 319.99, TRUE, 1, 5, 1),
('10B', 319.99, TRUE, 2, 5, 1);

-- Insert baggage
INSERT INTO baggage (location, status, passenger_id, flight_id) VALUES
('Terminal A - Belt 1', 'CHECKED_IN', 1, 1),
('Terminal A - Belt 1', 'CHECKED_IN', 1, 1), -- John has 2 bags
('Terminal A - Belt 1', 'CHECKED_IN', 2, 1),
('Aircraft Cargo Hold', 'IN_TRANSIT', 3, 1),
('Terminal A - Belt 1', 'CHECKED_IN', 4, 1),
('Terminal A - Belt 1', 'CHECKED_IN', 5, 2),
('Terminal A - Belt 1', 'CHECKED_IN', 6, 2),
('Terminal A - Belt 1', 'CHECKED_IN', 7, 2),
('Terminal A - Belt 1', 'CHECKED_IN', 8, 2),
('Terminal A - Belt 1', 'CHECKED_IN', 9, 3),
('Terminal A - Belt 1', 'LOST', 10, 3),
('Terminal A - Belt 1', 'CHECKED_IN', 1, 5),
('Terminal A - Belt 1', 'CHECKED_IN', 2, 5);

-- Insert dependents
INSERT INTO dependents (name, phone, loyalty_id, passenger_id, ticket_id) VALUES
('Billy Smith', NULL, 'SKY123457', 1, 1),
('Sarah Doe', NULL, 'SKY234568', 2, 2),
('Tommy Johnson', NULL, NULL, 3, 3),
('Emma Williams', NULL, NULL, 4, 4);

-- Insert payments
INSERT INTO payments (date, paid_status, ticket_id) VALUES
('2025-04-10', TRUE, 1),
('2025-04-10', TRUE, 2),
('2025-04-11', TRUE, 3),
('2025-04-11', TRUE, 4),
('2025-04-12', TRUE, 5),
('2025-04-12', TRUE, 6),
('2025-04-13', TRUE, 7),
('2025-04-13', TRUE, 8),
('2025-04-14', TRUE, 9),
('2025-04-14', TRUE, 10),
('2025-04-15', TRUE, 11),
('2025-04-15', TRUE, 12);

-- Insert flight crew (4 crew members and 2 pilots per flight)
INSERT INTO flight_crew (name, role, flight_id, airline_id) VALUES
-- Flight 1 crew (SkyHigh Airlines)
('Captain James Wilson', 'PILOT', 1, 1),
('First Officer Sarah Lee', 'PILOT', 1, 1),
('Lisa Chen', 'FLIGHT_ATTENDANT', 1, 1),
('Mark Rodriguez', 'FLIGHT_ATTENDANT', 1, 1),
('Karen White', 'FLIGHT_ATTENDANT', 1, 1),
('David Thompson', 'FLIGHT_ATTENDANT', 1, 1),
-- Flight 2 crew (SkyHigh Airlines)
('Captain Richard Harris', 'PILOT', 2, 1),
('First Officer Emily Davis', 'PILOT', 2, 1),
('John Martinez', 'FLIGHT_ATTENDANT', 2, 1),
('Susan Taylor', 'FLIGHT_ATTENDANT', 2, 1),
('Michael Brown', 'FLIGHT_ATTENDANT', 2, 1),
('Jessica Wilson', 'FLIGHT_ATTENDANT', 2, 1),
-- Flight 5 crew (Global Airways)
('Captain Robert Anderson', 'PILOT', 5, 2),
('First Officer Jennifer Lewis', 'PILOT', 5, 2),
('Thomas Clark', 'FLIGHT_ATTENDANT', 5, 2),
('Patricia Moore', 'FLIGHT_ATTENDANT', 5, 2),
('Christopher Hall', 'FLIGHT_ATTENDANT', 5, 2),
('Margaret Young', 'FLIGHT_ATTENDANT', 5, 2);

-- Insert aircraft maintenance log
INSERT INTO aircraft_maintenance_log (aircraft_id, flight_id, maintenance_date, status) VALUES
(1, 1, '2025-04-21', 'DONE'),
(2, 2, '2025-04-21', 'DONE'),
(1, 3, '2025-04-22', 'PENDING'),
(2, 4, '2025-04-22', 'DONE'),
(3, 5, '2025-04-21', 'DONE'),
(4, 6, '2025-04-21', 'DONE');

-- Insert TSA staff (2 checkpoints per terminal as per requirements)
INSERT INTO tsa (name, clearance_level, checkpoint_number, terminal_id) VALUES
('Officer John Davis', 'LEVEL_2', 1, 1),
('Officer Mary Johnson', 'LEVEL_1', 1, 1),
('Officer Robert Wilson', 'LEVEL_3', 2, 1),
('Officer Sarah Brown', 'LEVEL_2', 2, 1),
('Officer James Martin', 'LEVEL_2', 1, 2),
('Officer Patricia Garcia', 'LEVEL_1', 1, 2),
('Officer Thomas Rodriguez', 'LEVEL_3', 2, 2),
('Officer Jennifer Lopez', 'LEVEL_2', 2, 2);

-- Insert airport staff
INSERT INTO airport_staff (name, role, terminal_id) VALUES
('Michael Clark', 'GATE_AGENT', 1),
('Susan Wright', 'GATE_AGENT', 1),
('David Lee', 'BAGGAGE_HANDLER', 1),
('Karen Jackson', 'CUSTOMER_SERVICE', 1),
('Richard White', 'MAINTENANCE', 1),
('Jessica Taylor', 'GATE_AGENT', 2),
('Thomas Anderson', 'GATE_AGENT', 2),
('Patricia Harris', 'BAGGAGE_HANDLER', 2),
('Robert Martin', 'CUSTOMER_SERVICE', 2),
('Jennifer Thompson', 'MAINTENANCE', 2);