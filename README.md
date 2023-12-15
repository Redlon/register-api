
# Register API

API RESTful for USER creation with JWT and OpenAPi 3.0


## Tech Stack

**Server:** Spring Boot

**ORM:** Hibernate

**Database:** H2



## Run Locally

Clone the project

```bash
  git clone https://github.com/Redlon/register-api.git
```

Go to the project directory

```bash
  cd my-project
```

Install dependencies

```bash
  ./gradlew build
```

Start the server

```bash
  ./gradlew bootRun

```


## Get JWT
With the app running the first step is get a JWT. One way to get it is with Postman, sending this request

```http
  POST /token
```
In this request is important to add the credential, to do this you need to go to **Authorization** tab, select **Type: Basic Auth** and use the next credential(This credential can be change inside **SecurityConfig.java**):

| Username | Password    |
| :--------| :-------    |
| `test`   | `password`  |


## Create new User
### Using Postman
The request needed to create a new user is
```http
  POST /register
```
This time you have to add the bearer token that you got in the previous step, to do this you need to go to **Authorization** tab, select **Type: Bearer Token** and paste it. Also need to add body to this request, for example:

```json
{
    "name": "Juan Rodriguez",
    "email": "juan@rodriguez.org",
    "password": "#Hunter2",
    "phones": [{
        "number": "1234567",
        "citycode": "1",
        "countrycode": "57"
    }]
}
```


### Using Swagger
With the app running go to 
```http
  http://localhost:8080/swagger-ui/index.html
```

Here you need to authorize Swagger giving it the Bearer Token created. In the upper-right corner of the page is the button **Authorize**, paste the JWT in the pop-up and authorize. Without this step you can enter Swagger and see the content, but you can not execute any request inside. Now is possible to try register out, inluding the same body used in postman, swagger return the next response

```json
{
  "uuid": "b80c8225-8b6b-4dba-a496-ad85680a1352",
  "created": "2023-12-14T23:24:34.7678202",
  "modified": "2023-12-14T23:24:34.7678202",
  "lastLogin": "2023-12-14T23:24:34.7678202",
  "token": "Bearer Token",
  "isActive": true
}
```
## API Reference

#### Register new user
```http
  POST /register
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `name`    | `string` | **Required**. Name of the new user |
| `email`   | `string` | **Required**. Mail of the new user |
| `password`| `string` | **Required**. Password of the new user |
| `phones`  | `JSON`   |  List of phone objects, include number, cityCode and countryCode  |




## Password configuration
The pasword configuration is found in **UserService** method **validation**. Here you can find both regex used to check email and password when a new user is created. The regex that check the password is
`^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$` this modular regex is easy to change and the rules that is checking are:

-  ^  &emsp;&emsp; &emsp; &emsp; &emsp; &emsp; &emsp;  # start-of-string
-  (?=.*[0-9]) &emsp; &emsp; &emsp; &ensp; &nbsp;      # a digit must occur at least once
-  (?=.*[a-z]) &emsp; &emsp; &emsp; &ensp; &nbsp;      # a lower case letter must occur at least once
-  (?=.*[A-Z]) &emsp; &emsp; &emsp; &ensp; &nbsp;      # an upper case letter must occur at least once
-  (?=.*[@#$%^&+=])&emsp;   # a special character must occur at least once
-  (?=\S+$)  &emsp; &emsp; &emsp; &emsp; &ensp;        # no whitespace allowed in the entire string
-  .{8,} &emsp; &emsp; &emsp; &emsp; &emsp; &ensp; &ensp;          # anything, at least eight places though
-  $    &emsp; &emsp; &emsp; &emsp; &emsp; &emsp; &ensp; &ensp;              # end-of-string

If needed this regex can be change at any time adding, removing or redefining any rule or the whole string.