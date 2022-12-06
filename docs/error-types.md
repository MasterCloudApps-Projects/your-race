
## TCP/IP connection errors


### ECONNREFUSED 

Connection Refused.

The host refused service. This happens when a server process is not present at the requested address. 

### ETIMEDOUT 
Connection timeout. 
No connection established in specified time. This happens when the destination host is down or when problems in the network result in lost transmissions. 

### ECONNRESET 
Connection is reset by a partner. The server unexpectedly closed the connection and the request to the server was not fulfilled. 



## HTTP errors

### 500 Internal Server Error

The 500 (Internal Server Error) status code indicates that the server encountered un unexpected condition that prevented it from fulfilling the request.


### 503 Service Unavailable

The 503 (Service Unavailable) status code indicates that the server    is currently unable to handle the request due to a temporary overload    or scheduled maintenance, which will likely be alleviated after some    delay.  The server MAY send a Retry-After header field (Section 7.1.3) to suggest an appropriate amount of time for the client to wait before retrying the request.

Note: The existence of the 503 status code does not imply that a server has to use it when becoming overloaded. Some servers might simply refuse the connection.

### 504 Gateway Timeout

The 504 (Gateway Timeout) status code indicates that the server, while acting as a gateway or proxy, did not receive a timely response from an upstream server it needed to access in order to complete the request.


## References

https://www.rfc-editor.org/rfc/rfc7231#section-6.3.3

https://docs.oracle.com/cd/E19455-01/806-1017/sockets-9/index.html
https://support.postman.com/hc/en-us/articles/6346313956887-Fixing-an-ECONNRESET-error

