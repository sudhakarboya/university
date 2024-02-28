create table if not exists professor(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name varchar(250),
    department varchar(250)
);
create table if not exists course(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name varchar(250),
    credits INT,
    professorId INT,
    FOREIGN KEY(professorId) REFERENCES professor(id)
);
create table if not exists student(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name varchar(250),
    email varchar(250)

);
create table if not exists course_student(
    courseId INT,
    studentId INT,
    FOREIGN KEY(courseId) REFERENCES course(id),
    FOREIGN KEY(studentId) REFERENCES student(id)
);