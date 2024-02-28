insert into professor(name,department) values('John Smith','Computer Science');
insert into professor(name,department) values('Mary Johnson','Physics');
insert into professor(name,department) values('David Lee','Mathematics');

insert into course(name,credits,professorId) values('Introduction to Programming',3,1);
insert into course(name,credits,professorId) values('Quantum Mechanics',4,2);
insert into course(name,credits,professorId) values('Calculus',4,3);

insert into student(name,email) values('Alice Johnson','alice@example.com');
insert into student(name,email) values('Bob Davis','bob@example.com');
insert into student(name,email) values('Eva Wilson','eva@example.com');

insert into course_student(courseId,studentId) values(1,1);
insert into course_student(courseId,studentId) values(1,2);
insert into course_student(courseId,studentId) values(2,2);
insert into course_student(courseId,studentId) values(2,3);
insert into course_student(courseId,studentId) values(3,1);
insert into course_student(courseId,studentId) values(3,3);

