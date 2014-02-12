# 1. Introduction
Nowadays many applications expose business functionality as REST services.  These services
can be consumed not only within the enterprise where they were created but from outside as
well because REST is platform-agnostic and almost every development platform currently
provides some way of consuming REST services.  When creating REST services it is common to
incorporate some sort of security for every REST API endpoint.  This ensures that each API
endpoint is only invoked by clients that are authorised and intended to use that endpoint.
This prevents information leakage and malicious use of the application by internal and
external users.

Spring Security is a flexible framework for implementing security requirements for web
based applications.  It is therefore not uncommon for developers to use Spring Security to
enforce security restrictions on REST API endpoints.  One of the challenges encountered
when using Spring Security to secure REST API endpoints stems from the requirement to
support stateless REST clients.  In this context, *stateless* means that the client does
not provide any information with REST requests that would allow the server to determine the
identity of the user associated with the client.  This is a challenge because Spring Security
(and any other security framework) requires some sort of state information in order to
authenticate and authorize application users.  In the absence of an in-built state
transmission mechanism, a custom mechanism needs to be evolved to support stateless REST
and similar scenarios.

# 2. Overview
This application uses the following architecture to demonstrate how the default Spring
Security configuration can be used to secure a standard web application and another
configuration to secure a stateless REST API.

     ===================       ===================
    |                   |     |                   |
    | W e b   L a y e r |     | A P I   L a y e r |
    |                   |     |                   |
     ===================       ===================

     =============================================
    |                                             |
    |   B u s i n e s s   L o g i c   L a y e r   |
    |                                             |
     =============================================

The *Business Logic* layer is responsible for performing all business operations.  It is
secured using Spring Security annotations. The *Web* and *API* layers call the *Business
Logic* layer as and when required.  This means, both these layers need to authenticate
users correctly and consistently so that users can use either of these layers to get access
to the data and business functionality provided by the *Business Logic* layer.

# 3. Design
The *Web* layer uses the default Spring Security configuration and takes advantage of form
login capability provided by Spring Security.  The following configuration for the *Web*
layer enables this configuration.

     <security:http access-denied-page="/" auto-config="true" use-expressions="true">
       <security:form-login />
     </security:http>

With this configuration, Spring Security uses HTTP sessions to store user credentials in
between requests.

The *API* layer cannot use the default configuration because all communication between this
layer and its clients has been assumed to be stateless.  Spring Security configuration for
this layer is therefore slightly more involved and is shown below.

     <bean class="org.example.api.security.APIAuthenticationEntryPoint" id="apiAuthenticationEntryPoint" />
     <bean class="org.example.api.security.EhcacheSecurityContextRepository" id="apiSecurityContextRepository" />
     <security:authentication-manager alias="authenticationManager" erase-credentials="false">
       <security:authentication-provider ref="authenticationProvider" />
     </security:authentication-manager>
     <security:http auto-config="true" create-session="stateless" entry-point-ref="apiAuthenticationEntryPoint" security-context-repository-ref="apiSecurityContextRepository" use-expressions="true" />

The class `APIAuthenticationEntryPoint` simply rejects all requests that are unauthenticated
but were expected to be.  This ensures that unauthenticated users cannot invoke API endpoints.
The class `EhcacheSecurityContextRepository` leverages the modular architecture of Spring
Security to store authentication information in an expirable Ehcache.  It implements the
Spring Security interface `SecurityContextRepository`, whose other implementation
`HttpSessionSecurityContextRepository` is the default implementation used by Spring Security.
Of course, it is not mandatory to use Ehcache.  Any other caching solution could be used to
store user credentials in between REST calls.

#4. Running the application
The following pre-requisites apply to this application.

1. Java Development Kit (JDK) 6.0 or higher;
1. Apache Maven 3.0.4 or higher.

Once these have been installed and the code checked out, the `web` application can be run
as `mvn clean package tomcat7:run -pl common,data,domain,service,transfer,web`.  This starts an
embedded Tomcat instance on local port `8888`.  The application can then be accessed using
any web browser on [http://localhost:8888](http://localhost:8888).  When accessed for the first
time, the application will present a login screen with instructions on logging in.
Successfully logging in as an *Admin* user provides access to a list of users for the system.
This functionality is not accessible to normal users (try accessing it as a normal user).

Similarly, the `api` application can be run as
`mvn clean package tomcat7:run -pl api,common,data,domain,service,transfer`.  This starts
an embedded Tomcat instance on local port `9999`.  The application can then be accessed
using a REST client, such as the *Postman* extension for Google Chrome on
`http://localhost:9999`.  There are two REST endpoints - `http://localhost:9999/authenticate`
to authenticate clients and `http://localhost:9999/users` to access the user list.
First, make a *POST* request to `http://localhost:9999/authenticate` with two form parameters
`username` (same as the one used for the web application) and `password` (set to *password*
for all users).  Note the text token returned by this call.  Then, make a *GET* request to
`http://localhost:9999/users`, including the value of the text token retrieved on the
previous call as an HTTP header `X-API-TOKEN`.  This should return the list of users similar
to how it was displayed for the web application.

# 5. License
This sample application and its associated source code in its entirety is being made
available under the following licensing terms.

    Copyright (C) 2014

    Permission is hereby granted, free of charge, to any person obtaining a copy of
    this software and associated documentation files (the "Software"), to deal in the
    Software without restriction, including without limitation the rights to use, copy,
    modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
    and to permit persons to whom the Software is furnished to do so, subject to the
    following conditions:

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
    INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
    PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
    HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
    CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
    OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
