CREATE DATABASE curriculums;

USE curriculums;

CREATE TABLE cvv_contact (
                            contact_id INT AUTO_INCREMENT PRIMARY KEY,
                            name VARCHAR(255),
                            lastname VARCHAR(255),
                            image VARCHAR(255),
                            occupation VARCHAR(255),
                            mobile VARCHAR(20),
                            email VARCHAR(255),
                            linkedin VARCHAR(255),
                            location VARCHAR(255),
                            extra varchar(255)
);

CREATE TABLE cvv_academies (
                            academy_id INT AUTO_INCREMENT PRIMARY KEY,
                            contact_id INT,
                            name VARCHAR(255),
                            entity VARCHAR(255),
                            location VARCHAR(255),
                            year INT,
                            FOREIGN KEY (contact_id) REFERENCES cvv_contact(contact_id)
);

CREATE TABLE cvv_contact_skills (
                            cskill_id INT AUTO_INCREMENT PRIMARY KEY,
                            contact_id INT,
                            skill_id INT,
                            value INT,
                            FOREIGN KEY (contact_id) REFERENCES cvv_contact(contact_id),
                            FOREIGN KEY (skill_id) REFERENCES cvv_skills(skill_id)
);

CREATE TABLE cvv_courses (
                            course_id INT AUTO_INCREMENT PRIMARY KEY,
                            contact_id INT,
                            name VARCHAR(255),
                            duration VARCHAR(50),
                            position VARCHAR(255),
                            FOREIGN KEY (contact_id) REFERENCES cvv_contact(contact_id)
);

CREATE TABLE cvv_experiences (
                            experience_id INT AUTO_INCREMENT PRIMARY KEY,
                            contact_id INT,
                            name VARCHAR(255),
                            duration VARCHAR(50),
                            entity VARCHAR(255),
                            location VARCHAR(255),
                            year INT,
                            position VARCHAR(255),
                            FOREIGN KEY (contact_id) REFERENCES cvv_contact(contact_id)
);

CREATE TABLE cvv_languages (
                            lang_id INT AUTO_INCREMENT PRIMARY KEY,
                            contact_id INT,
                            spanish VARCHAR(50),
                            english VARCHAR(50),
                            french VARCHAR(50),
                            FOREIGN KEY (contact_id) REFERENCES cvv_contact(contact_id)
);

CREATE TABLE cvv_skills (
                            skill_id INT AUTO_INCREMENT PRIMARY KEY,
                            name VARCHAR(255)
);