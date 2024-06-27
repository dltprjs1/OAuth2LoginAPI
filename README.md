Start DATE : 2024 - 06 - 03

END DATE : 2024 - 06 - 27

Language : JAVA

DB : MSSQL

TECH : Spring, JWT, OAuth2.0, Spring Security, JPA 

Social NetWork : Kakao, Naver

Connected ProJect : NodeJS, React --> http://localhost:3000/

--------------------------------------------------------------

Table : <br/>
  AUTUTRADING_USER  --> user_id(primary key), password, email, type, role <br/>
  AUTUTRADING_CERTIFIACTION   --> user_id(primary key), email, certification_number <br/>
  
--------------------------------------------------------------

###Check Duplicated ID <br/>
URL: http://localhost:4040/api/v1/auth/id-check <br/>
Method: POST <br/>
Type: JSON <br/>
data:  <br/>
{ <br/>
  "id" : "user_id" <br/>
} <br/>

--------------------------------------------------------------

Send Certification Code by Email ( you should change the email in application.properties before this work ) <br/>
URL: http://localhost:4040/api/v1/auth/email-certification <br/>
Method: POST <br/>
Type: JSON <br/>
Data: <br/>
{ <br/> 
  "id" : "user_id", <br/> 
  "email" : "user_email" <br/>
} <br/>

--------------------------------------------------------------

Check certification code <br/>
URL: http://localhost:4040/api/v1/auth/check-certification <br/>
Method: POST <br/>
Type: JSON <br/>
Data: <br/>
{ <br/>
  "id" : "user_id", <br/>
  "email" : "user_email", <br/>
  "certificationNumber" : "code" <br/>
} <br/>

--------------------------------------------------------------

Sign in <br/>
URL: http://localhost:4040/api/v1/auth/sign-in <br/>
Method: POST <br/>
Type: JSON <br/>
Data: <br/>
{ <br/>
  "id" : "user_id", <br/>
  "password" : "password" <br/>
} <br/>

--------------------------------------------------------------

Sign up <br/>
URL: http://localhost:4040/api/v1/auth/sign-up <br/>
Method: POST <br/>
Type: JSON <br/>
Data:  <br/>
{ <br/>
  "id" : "user_id", <br/>
  "password" : "password", <br/>
  "email" : "user_email", <br/>
  "certificationNumber" : "code" <br/>
}<br/>

--------------------------------------------------------------

Social Network Sign in<br/>
<br/>
Kakao Login URL: http://localhost:4040/api/v1/auth/oauth2/kakao<br/>
<br/>
Naver Login URL: http://localhost:4040/api/v1/auth/oauth2/naver<br/>
<br/>

--------------------------------------------------------------
