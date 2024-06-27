Start DATE : 2024 - 06 - 03

END DATE : 2024 - 06 - 27

Language : JAVA

DB : MSSQL

TECH : Spring, JWT, OAuth2.0, Spring Security, JPA 

Social NetWork : Kakao, Naver

Connected ProJect : NodeJS, React --> http://localhost:3000/

--------------------------------------------------------------

Table : 
  AUTUTRADING_USER  --> user_id(primary key), password, email, type, role
  AUTUTRADING_CERTIFIACTION   --> user_id(primary key), email, certification_number
  
--------------------------------------------------------------

Check Duplicated ID
URL: http://localhost:4040/api/v1/auth/id-check
Method: POST
Type: JSON
data: 
{
  "id" : "user_id"
}

--------------------------------------------------------------

Send Certification Code by Email ( you should change the email in application.properties before this work )
URL: http://localhost:4040/api/v1/auth/email-certification
Method: POST
Type: JSON
Data:
{
  "id" : "user_id",
  "email" : "user_email"
}

--------------------------------------------------------------

Check certification code
URL: http://localhost:4040/api/v1/auth/check-certification
Method: POST
Type: JSON
Data:
{
  "id" : "user_id",
  "email" : "user_email",
  "certificationNumber" : "code"
}

--------------------------------------------------------------

Sign in
URL: http://localhost:4040/api/v1/auth/sign-in
Method: POST
Type: JSON
Data:
{
  "id" : "user_id",
  "password" : "password"
}

--------------------------------------------------------------

Sign up
URL: http://localhost:4040/api/v1/auth/sign-up
Method: POST
Type: JSON
Data: 
{
  "id" : "user_id",
  "password" : "password",
  "email" : "user_email",
  "certificationNumber" : "code"
}

--------------------------------------------------------------

Social Network Sign in

Kakao Login URL: http://localhost:4040/api/v1/auth/oauth2/kakao

Naver Login URL: http://localhost:4040/api/v1/auth/oauth2/naver

--------------------------------------------------------------
