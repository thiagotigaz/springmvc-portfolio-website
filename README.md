# springmvc-supercloud-website

This repository is used to demonstrate the usage of Spring Boot, thymeleaf and spring data Jpa. 
It also includes bootstrap and jquery.

If you would like to deploy it as a container, the Dockerfile is also available. PS: database is not dockerized.

To run the development database you can set $DATABASE_DATA var and run the following command:

place where you would like to store database data on your system. eg:

`export DATABASE_DATA=/Users/thiago/Documents/pgdata` 

`docker run --name postgres -d -p 5432:5432 -v $DATABASE_DATA:/var/lib/postgresql/data/pgdata -e 
PGDATA=/var/lib/postgresql/data/pgdata -e POSTGRES_PASSWORD=admin postgres:latest`

Here you can find examples for following technology:

* Template structure using thymeleaf 3.x
* File upload using Spring's MultipartFile class
* Spring messages bundle using ResourceBundleMessageSource
* Spring Email configuration using JavaMailSender
* Spring Data JPA basic CRUD management using PagingAndSortingRepository
* Spring JPA Auditors using Spring's AuditorAware interface

This project is online at http://supercloud.com.br
