insert into users values (1,'adminLand','admin','admin','$2a$10$0Qt1oivg3MLKpwhDosIsmecVSYyjCC4xZNrSX2/tt1WoPMbVl1yVW','admin.jpg','ADMIN','adminic','+admin');
insert into users values (2,'bulevar','stefanium@mail.com','stefan','$2a$10$0Qt1oivg3MLKpwhDosIsmecVSYyjCC4xZNrSX2/tt1WoPMbVl1yVW','stefan.jpg','USER','stefanic','+3810641234567');
insert into users values (3,'livade','vlafa@mail.com','vlafimir','$2a$10$0Qt1oivg3MLKpwhDosIsmecVSYyjCC4xZNrSX2/tt1WoPMbVl1yVW','vlafa.jpg','DRIVER','golosin','+3810641234567');
insert into users values (4,'liman','rxx@mail.com','rxlja','$2a$10$0Qt1oivg3MLKpwhDosIsmecVSYyjCC4xZNrSX2/tt1WoPMbVl1yVW','rxlja.jpg','USER','rad','+3810639734467');

insert into vehicle_type values (1,150,200,'Standardno');
insert into vehicle_type values (2,175,300,'Luksuzno');
insert into vehicle_type values (3,200,500,'Kombi');

insert into vehicles values (1,false,false,3,'NS-420-BL',3,1);


insert into locations values(1,69.42,69.42);
insert into locations values(2,42.69,42.69);
insert into routes values(1,11,5.5,2,1);
insert into refusals values(1,'iks de', '2023-01-12 20:46:54.033' ,3);
insert into rides values (1,false,'2023-01-12 20:58:24.037',false,false,'2023-01-12 20:58:24.037','PENDING',3,1,1);
insert into rides_passengers values(1,2);
insert into rides_passengers values(1,4);
