# invoicify
Invoicify is tool created by Team Alpha for contractors to bill companies for services.It contains brief details of Product and Services bought. It works as follows
Invoice is created by contractor when a goods or services is purchased by Client Companty
Items can be added for services rendered
Invoice is sent to company for payment


1. [Main Page](https://github.com/padhuashok/invoicify/projects)
2. Tools (to-do)
3. Features (to-do)


## Tools

The following is a list of tools associated with the project .

* C1 - Gradle
* C2 - GitHub
* C3 - Docker
* C4 - Heroku

## Features

Invoicyfy has detailed indepth documentation of all endpoints usedin project

It supports Docker profile to run in different environments.

It runs in a containerized enviroment and used Postgres as DB. 

##**Build Section**

Before starting the build, Copy dos2unix file to root folder 
once copied, issue following command
dos2unix gradlew
Here is end point used by Heroku  for application healthcheck
https://invoicifyalpha.herokuapp.com/healthcheck


##**EndPoints**

GET Company endpoint: "/company"

POST Company endpoint: "/company"

PUT Company endpoint: "/company/{id}"

GET HealthCheck endpoint: "/healthcheck"

POST InvoiceItems endpoint: "/invoice/items"

POST Invoice endpoint: "/invoice"

DELETE Invoice endpoint: "/invoice"

GET Invoice By Invoice Number endpoint: "/invoice/{invoiceNumber}"
