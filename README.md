## Personalized Image viewer :

# Idea: Personalized Image Viewer App to Upload,View,Download,Delete and Commenting Images for the users.

Tech Stack : 
            Flutter (App Development)
            Spring Boot (API Development)
            Postgresql (DataBase)
Development in Progress.

# Flutter: (In Progress)
Flutter is Google’s portable UI toolkit for building beautiful, natively-compiled applications for mobile, web, and desktop from a single codebase.
+Fast Development
+Expressive and Flexible UI
+Native Performance
Link: https://flutter.dev/


# Postgresql DataBase (Completed):
PostgreSQL, also known as Postgres, is a free and open-source relational database management system emphasizing extensibility and technical standards compliance. It is designed to handle a range of workloads, from single machines to data warehouses or Web services with many concurrent users.

For my Use Case i have created 1 Table
Table : ImageDetails
Columns : id ,title,description,image_path,compressed_path

I used to store details in Database and i have store images and compressed images in LocalDrive. 


# API :(Completed)
A RESTful API is an application program interface (API) that uses HTTP requests to GET, PUT, POST and DELETE data.

A RESTful API -- also referred to as a RESTful web service -- is based on representational state transfer (REST) technology, an architectural style and approach to communications often used in web services development.

For my use Case i have Developed 8 APIS as follows:

1: GET : /api/login : TO Verify username and password for Login of application.
2: GET : /api/images : To Retrieve all images From DataBase.
3: GET : /api/images/count : To Retrieve Images Count From DataBase.
4: POST : /api/image : TO insert Image Details in DataBase and store image in LocalDrive.
5: PUT : /api/image : To Update Image Details and image if present. IfNotPresent create Image.
6: GET : /api/image/{id} : To Retreive ImageDetails of particular ID from Database.
7: DELETE : /api/image/{id} : To Delete ImageDetails of particular Id from Database as well as from LocalDrive.
8: GET : /api/image : To Server Large Image to Client.


To Deploy Change application properties :

//PORT
server.port=8080
//PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
//Image Store Properties
image.uploadDir = C:\\Users\\Pictures\\Spring_Rest\\images_storage\\main_image
image.templateDir= C:\\Users\\Pictures\\Spring_Rest\\images_storage\\compressed_image

Command : mvn clean compile install spring-boot:run

It will boot the application and will be serving APIS with PORT 8080.
FrontEnd APP is in Development Phase.Will be Updating....        
