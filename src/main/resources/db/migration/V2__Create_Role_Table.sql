
DROP TABLE IF EXISTS `ROLE`;


CREATE TABLE `role` (
                        `id` INT NOT NULL AUTO_INCREMENT,
                        `name` VARCHAR(255) NOT NULL,
                        `description` TEXT,
                        PRIMARY KEY (`id`)
);

INSERT INTO ROLE (name, description) VALUES
                                         ('USER', 'A basic user with limited access'),
                                         ('EMPLOYEE', 'Employee with extended permission'),
                                         ('OWNER', 'System owner with full access');