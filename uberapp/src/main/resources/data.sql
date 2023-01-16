insert into users (`ACTIVATED`,`ADDRESS`,`BLOCKED`,`EMAIL`,`NAME`,`PASSWORD`,`PROFILE_PICTURE`,`ROLE`,`SURNAME`,`TELEPHONE_NUMBER`) values (true,'adminLand',false,'admin','admin','$2a$10$0Qt1oivg3MLKpwhDosIsmecVSYyjCC4xZNrSX2/tt1WoPMbVl1yVW','admin.jpg','ADMIN','adminic','+admin');
insert into users (`ACTIVATED`,`ADDRESS`,`BLOCKED`,`EMAIL`,`NAME`,`PASSWORD`,`PROFILE_PICTURE`,`ROLE`,`SURNAME`,`TELEPHONE_NUMBER`) values (true,'bulevar',false,'stefanium@mail.com','stefan','$2a$10$0Qt1oivg3MLKpwhDosIsmecVSYyjCC4xZNrSX2/tt1WoPMbVl1yVW','stefan.jpg','USER','stefanic','+3810641234567');
insert into users (`ACTIVATED`,`ADDRESS`,`BLOCKED`,`EMAIL`,`NAME`,`PASSWORD`,`PROFILE_PICTURE`,`ROLE`,`SURNAME`,`TELEPHONE_NUMBER`) values (true,'livade',false,'vlafa@mail.com','vlafimir','$2a$10$0Qt1oivg3MLKpwhDosIsmecVSYyjCC4xZNrSX2/tt1WoPMbVl1yVW','vlafa.jpg','DRIVER','golosin','+3810641234567');
insert into users (`ACTIVATED`,`ADDRESS`,`BLOCKED`,`EMAIL`,`NAME`,`PASSWORD`,`PROFILE_PICTURE`,`ROLE`,`SURNAME`,`TELEPHONE_NUMBER`) values (true,'liman',false,'rxx@mail.com','rxlja','$2a$10$0Qt1oivg3MLKpwhDosIsmecVSYyjCC4xZNrSX2/tt1WoPMbVl1yVW','rxlja.jpg','USER','rad','+3810639734467');

insert into vehicle_type values (1,150,200,'Standardno');
insert into vehicle_type values (2,175,300,'Luksuzno');
insert into vehicle_type values (3,200,500,'Kombi');

insert into locations (`LATITUDE`,`LONGITUDE`) values (45.4242,15.5555);
insert into locations (`LATITUDE`,`LONGITUDE`) values (45.4242,15.7575);

insert into vehicles (`ALLOWED_BABY_IN_VEHICLE`,`ALLOWED_PET_IN_VEHICLE`,`NUMBER_OF_SEATS`,`REG_PLATES`,`DRIVER_ID`,`LOCATION_ID`,`VEHICLE_TYPE_ID`) values (false,false,3,'NS-420-BL',3,1,1);

/*
insert into routes (`ESTIMATED_TIME_IN_MINUTES`,`LENGHT`,`END_LOCATION_ID`,`START_LOCATION_ID`) values(11,5.5,2,1);
insert into refusals (`REASON`,`TIME`,`USER_ID`) values('iks de', '2023-01-12 20:46:54.033' ,3);
insert into rides (`BABY_IN_VEHICLE`,`END_TIME`,`PANIC`,`PET_IN_VEHICLE`,`START_TIME`,`STATUS`,`DRIVER_ID`,`REFUSAL_ID`,`ROUTE_ID`) values (false,'2023-01-12 20:58:24.037',false,false,'2023-01-12 20:58:24.037','PENDING',3,null,1);
insert into rides_passengers values(1,2);
insert into rides_passengers values(1,4);

*/

