DROP SCHEMA PUBLIC CASCADE;
CREATE TABLE counter (
  counterId varchar(100) NOT NULL,
  entityId varchar(100),
  entityType varchar(255) NOT NULL,
  entityCount int NOT NULL,
  PRIMARY KEY (counterId));
CREATE TABLE user (
  userId varchar(100) NOT NULL,
  name varchar(30) NOT NULL,
  surname varchar(30) NOT NULL,
  birthday datetime NOT NULL,
  email varchar(30) NOT NULL,
  PRIMARY KEY (userId));
CREATE TABLE auditorium (
  auditoriumId varchar(100) NOT NULL,
  name varchar(30) NOT NULL,
  seats int NOT NULL,
  vipSeats varchar(255) NOT NULL,
  PRIMARY KEY (auditoriumId));
CREATE TABLE event (
  eventId varchar(100) NOT NULL,
  name varchar(30) NOT NULL,
  price decimal NOT NULL,
  rating varchar(10) NOT NULL,
  startTime datetime NOT NULL,
  endTime datetime NOT NULL,
  auditoriumId varchar(100),
  PRIMARY KEY (eventId),
  CONSTRAINT FK_EVENT_AUDITORIUM FOREIGN KEY (auditoriumId) REFERENCES auditorium (auditoriumId));
CREATE TABLE ticket (
  ticketId varchar(100) NOT NULL,
  userId varchar(100),
  eventId varchar(100) NOT NULL,
  seatNumber int NOT NULL,
  price decimal NOT NULL,
  PRIMARY KEY (ticketId),
  CONSTRAINT FK_TICKET_EVENT FOREIGN KEY (eventId) REFERENCES event (eventId));