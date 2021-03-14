# Feed System

## Architecture
![image](https://user-images.githubusercontent.com/18320796/111068293-3c14a180-84ee-11eb-9ab2-7b5d55249eed.png)

### ELB(Elastic Load Balancer)
  1.	Distributes server loads to multiple server instances.
  2.	Maintains the status of all services. 
  3.  The ELB sends the snapshot of all services status to API Gateway. 
  4.	This snapshot is periodically updated every 10 seconds.

### API Gateway:
  1.	It is the single entry point for all client requests. It routes client requests to appropriate services.
  2.	All API requests are authenticated using JWT token. 
  3.	When the client requests come, it checks the snapshot to decide which server instance it has to route.

### Post Service:
  1.	Process the client request to check for article duplicattion. 
  2.	Persists the articles to MongoDB and Elastic Search.

### User service:
  1.	Creates new user(Registration)
  2.	Handles the User login
  3.	Get User details
  4.	Update the User details
  5.	Delete the User details.

### Feed Service:
  1.	Receives the search request from client.
  2.	Retrieves the top ranked articles and server to the client.
  3.	User activity is first retrieved from “User activity Service” and used to query Elastic search for top ranked user feed articles

### User Activity Service:
  1.	Calculates the user feed article on-demand.
  2.	Stores the user activities(article view, user searches, users favourite categories, likes/dislikes the article etc) in Kafka topic
  3.	The user activities are continuously aggregated from user interactions that flow into Kafka stream application.
  4.	Using Kafka stream to personalize the user feed.

### Elastic Search:
  1.	Scalable and distributed search engine
  2.	Used to search artticles efficiently.

### Database Collections
#### Users collection
![image](https://user-images.githubusercontent.com/18320796/111069094-72075500-84f1-11eb-9c62-f5a898129db2.png)

#### Article collection
![image](https://user-images.githubusercontent.com/18320796/111069207-070a4e00-84f2-11eb-90ed-7f04bfcd89aa.png)

# API's
	All pagination query parameter are optional. The default values will be used if not provided.

	page – page number. Expected values are positive integers. Default page number is 0.
	pageSize – Number of item in each page. Default values is 20
	sortBy – Field to sort. This will be different for every API
	sortType – ASC or DESC. The default value is different for each API
	fields – any of the fields present in the response.
	query – String with multiple key value pairs
	mode – truncated or full. The default value is full

## Post Article
### Request:
  Request Type	:  POST,
  URL		:  /api/post/articles?tags=social,water
  
#### Headers: 
  * Content-Type: multipart/form-data
  * Accept: multipart/form-data
  * Data: file: abcd.pdf

### Response:
#### When the request is correct with authorized user
  200 OK [abcd.pdf] uploaded successfully

#### When user is not authorized
  401 Unauthorized

## Get Articles
### Request:
  Request Type	:  GET,
  URL: /api/post/articles?page=0&pageSize=20&sortBy=uploadedDate&sortType=ASC&fields=uploadedDate,content&query=”userId:james”&mode=full
  Default sortType: ASC
  Default sortBy: uploadedDate

### Headers: 
  * Content-Type: application/json
  * Accept: application/json

### Response:
#### When the request is correct with authorized user
  {
      “pageInfo”: {
	    “currentPage”: 0,
	    “numberOfElementInCurrentPage”: 2,
	    “completeResultsetSize”: 2,
	    “pageSize”: 2
     },
    “articles”: [
	    {“userId”: “James”, “articleId”: “ddgrhtadwregr”, “content”: “”, “uploadedDate”: “”, “likes”: 20, “unlikes”: 5},
      {“userId”: “James”, “articleId”: “nvntbntbn”, “content”: “”, “uploadedDate”: “”, “likes”: 40, “unlikes”: 10},
    ]	
  }

  200 OK

#### When user is not authorized
  401 Unauthorized
  
## Auto complete suggestions
### Request:
  Request Type	:  GET,
  URL: /api/post/articles/search?keywords=socil, wat

### Response:
#### When the request is correct with authorized user
  {
      [“social”, “water”]
  }
  200 OK 

#### When user is not authorized
  401 Unauthorized



