USE old_system_db;

CREATE TABLE `user` (
    `login` varchar(255) PRIMARY KEY
);

CREATE TABLE `client` (
                          `guid` varchar(255) PRIMARY KEY,
                          `agency` varchar(255) NOT NULL,
                          `firstName` varchar(255) NOT NULL,
                          `lastName` varchar(255) NOT NULL,
                          `status` varchar(255) NOT NULL,
                          `dob` timestamp NOT NULL,
                          `createdDateTime` timestamp NOT NULL
);

CREATE TABLE `client_note` (
                               `guid` varchar(255) PRIMARY KEY,
                               `comments` varchar(4000),
                               `createdDateTime` timestamp NOT NULL,
                               `modifiedDateTime` timestamp NOT NULL,
                               `datetime` timestamp NOT NULL,
                               `loggedUser` varchar(255),
                               `clientGuid` varchar(255) NOT NULL
);

ALTER TABLE `client_note` ADD FOREIGN KEY (`loggedUser`) REFERENCES `user` (`login`);
ALTER TABLE `client_note` ADD FOREIGN KEY (`clientGuid`) REFERENCES `client` (`guid`);

INSERT INTO `client` (`guid`, `agency`, `firstName`, `lastName`, `status`, `dob`, `createdDateTime`)
VALUES
    ('123e4567-e89b-12d3-a456-426614174000', 'Agency1', 'John', 'Doe', 'Active', '1990-01-01 00:00:00', '2023-10-01 12:00:00'),
    ('123e4567-e89b-12d3-a456-426614174001', 'Agency1', 'Jane', 'Smith', 'Active', '1985-05-15 00:00:00', '2023-10-01 12:05:00'),
    ('123e4567-e89b-12d3-a456-426614174002', 'Agency2', 'Alice', 'Johnson', 'Inactive', '1995-08-20 00:00:00', '2023-10-01 12:10:00'),
    ('123e4567-e89b-12d3-a456-426614174003', 'Agency3', 'Bob', 'Brown', 'Active', '1980-12-30 00:00:00', '2023-10-01 12:15:00'),
    ('123e4567-e89b-12d3-a456-426614174004', 'Agency3', 'John', 'Doe2', 'Active', '1990-01-01 00:00:00', '2023-10-01 12:20:00');

INSERT INTO `user` (`login`)
VALUES
    ('user1'),
    ('user2'),
    ('user3');

INSERT INTO `client_note` (`guid`, `comments`, `createdDateTime`, `modifiedDateTime`, `datetime`, `loggedUser`, `clientGuid`)
VALUES
    ('456e7890-e89b-12d3-a456-426614174000', 'Initial note for John Doe', '2023-10-01 12:05:00', '2023-10-01 12:05:00', '2023-10-01 12:05:00', 'user1', '123e4567-e89b-12d3-a456-426614174000'),
    ('456e7890-e89b-12d3-a456-426614174001', 'Initial note for Jane Smith', '2023-10-01 12:05:00', '2023-10-01 12:05:00', '2023-10-01 12:05:00', 'user1', '123e4567-e89b-12d3-a456-426614174001'),
    ('456e7890-e89b-12d3-a456-426614174002', 'Initial note for Alice Johnson', '2023-10-01 12:10:00', '2023-10-01 12:10:00', '2023-10-01 12:10:00', 'user2', '123e4567-e89b-12d3-a456-426614174002'),
    ('456e7890-e89b-12d3-a456-426614174003', 'Initial note for Bob Brown', '2023-10-01 12:15:00', '2023-10-01 12:15:00', '2023-10-01 12:15:00', 'user2', '123e4567-e89b-12d3-a456-426614174003'),
    ('456e7890-e89b-12d3-a456-426614174004', 'Second note for John Doe', '2023-10-05 12:05:00', '2023-10-05 12:05:00', '2023-10-05 12:05:00', 'user1', '123e4567-e89b-12d3-a456-426614174000'),
    ('456e7890-e89b-12d3-a456-426614174005', 'Third note for John Doe', '2023-11-01 12:05:00', '2023-11-01 12:05:00', '2023-11-01 12:05:00', 'user1', '123e4567-e89b-12d3-a456-426614174000'),
    ('456e7890-e89b-12d3-a456-426614174006', 'Initial note for John Doe2', '2023-11-20 12:05:00', '2023-11-20 12:05:00', '2023-11-20 12:05:00', 'user2', '123e4567-e89b-12d3-a456-426614174004');
