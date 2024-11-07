use new_system_db;

CREATE TABLE `company_user` (
                                `id` int PRIMARY KEY AUTO_INCREMENT,
                                `login` varchar(255) UNIQUE NOT NULL
);

CREATE TABLE `patient_profile` (
                                   `id` int PRIMARY KEY AUTO_INCREMENT,
                                   `first_name` varchar(255),
                                   `last_name` varchar(255),
                                   `status_id` int NOT NULL
);

CREATE TABLE `patient_note` (
                                `id` int PRIMARY KEY AUTO_INCREMENT,
                                `created_date_time` timestamp NOT NULL,
                                `last_modified_date_time` timestamp NOT NULL,
                                `created_by_user_id` int,
                                `last_modified_by_user_id` int,
                                `note` varchar(4000),
                                `patient_id` int NOT NULL,
                                `old_guid` varchar(255) UNIQUE NOT NULL
);

CREATE TABLE `old_guid` (
                            `id` int PRIMARY KEY AUTO_INCREMENT,
                            `old_client_guid` varchar(255) NOT NULL,
                            `patient_profile_id` int NOT NULL
);

ALTER TABLE `patient_note` ADD FOREIGN KEY (`created_by_user_id`) REFERENCES `company_user` (`id`);

ALTER TABLE `patient_note` ADD FOREIGN KEY (`last_modified_by_user_id`) REFERENCES `company_user` (`id`);

ALTER TABLE `patient_note` ADD FOREIGN KEY (`patient_id`) REFERENCES `patient_profile` (`id`);

ALTER TABLE `old_guid` ADD FOREIGN KEY (`patient_profile_id`) REFERENCES `patient_profile` (`id`);

INSERT INTO `patient_profile` (`first_name`, `last_name`, `status_id`)
VALUES
    ('John', 'Doe', 200),
    ('Jane', 'Smith', 210),
    ('Alice', 'Johnson', 300),
    ('Bob', 'Brown', 230);

INSERT INTO `company_user` (`login`)
VALUES
    ('user1'),
    ('user2');

INSERT INTO `old_guid` (`old_client_guid`, `patient_profile_id`)
VALUES
    ('123e4567-e89b-12d3-a456-426614174000', 1),
    ('123e4567-e89b-12d3-a456-426614174004', 1),
    ('123e4567-e89b-12d3-a456-426614174001', 2),
    ('123e4567-e89b-12d3-a456-426614174002', 3),
    ('123e4567-e89b-12d3-a456-426614174003', 4);