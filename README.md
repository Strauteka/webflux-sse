# Webflux SSE notify
Run:  
`mvn spring-boot:run`  

Browser:  
 
[Watch messages: text/event-stream v1](http://127.0.0.1:8080/sse/v1)  
[Watch messages: text/event-stream v2](http://127.0.0.1:8080/sse/v2)  
[Publish message:](http://127.0.0.1:8080/sse/publish/Some%20Simple%20Message)  

Curl command sse:
`curl  -i -N --http2 -H "Accept:text/event-stream" http://127.0.0.1:8080/sse/v2`

Curl command get:
`curl -X GET -i -H "Content-Type: application/json" "http://127.0.0.1:8080/sse/publish/Some%20Simple%20Message"`

