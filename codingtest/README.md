# SKIDATA Coding Test


This is a small coding test.
The project contains a small TelephoneBook Service that can be used over REST APIs.

Some basic functionality is already implemented and can be used to check if the setup is correct. The manual testing chapter has some additional details about this.

However, there are still some points open to implement.
These Points are described in the Requirement section.


## Prerequisits
Install:
* JDK 17 or 21 for example from https://adoptium.net/de/download/
* Maven

## Building the project
To build the project "at home":
`mvn clean test`

To build the project within SKIDATA network, deactivate the `skidata` profile:
`mvn clean test -P\!skidata`

## Running the project
To run the project
`mvn spring-boot:run`

## CI Pipeline
Currently, not activated as `generic-maven-java-17` is missing.

## Manual Testing
Testing the REST API can be done using Postman(https://www.postman.com/downloads/) or Insomnia (https://insomnia.rest/download)

For Postman a free Account is required.

Insomnia can be used without Registration.

For Insomnia the file `Insomnia_codingtest.json` in the root folder of the project can be imported into a Scratch Pad.

## Requirements

- The REST calls for update and delete are existing in the controller but are missing their implementation. Implementation needs to be done.
- Currently, it is possible to search by first or last name or phoneNumber. In addition, there should be one find method that searches all three fields at once.
- The new find method should be case-insensitive and be able to find persons when only a part of the names or numbers are entered. 
- There is a method to add a telephone number to a person. This works if the person with the given UUID exists. If the person does not exist it still returns HTTP 200 without content. This should be changed to return HTTP 404 with an error-message that the Person wasn't found. 
- A method to remove a phone number by its ID from a Person exists in the controller and needs to be implemented.
- Additionally, you can always point out changes that you would make to improve the existing code. 
- Bonus: Add basic HTTP-Auth to the calls so only a given authorized user can view/edit the data.
