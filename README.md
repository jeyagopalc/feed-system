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


