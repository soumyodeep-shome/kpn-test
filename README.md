# kpn-test
Test for KPN
--------------


Clone the repo
Then use the "mvn clean install"
Then use "docker-compose up --build"
Use postman to test as of now I created a spring boot app, in future I plan to handle the input coming via kafka
I was planning on using Flink app, but for a true realtime case I need to set up the kafka and also the Flink needs to be set up properly.
Coming back to testing, you can use postman POST request "http://localhost:8080/business_outages" and "http://localhost:8080/customer_outages"
Do not forget to add the xml string (whatever is in the outages.xml file) in the body of the POST request
I have added two unit tests for the time being
