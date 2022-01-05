@DeleteBooking
Feature: DELETE Booking
        Scenario Outline: Update Partial booking in the API
        Given Testing environment
        When I pass headers
          |Content-Type | application/json|
        And I pass PathParametres
          |bookingId |<id>|
        And I set Authorisation token  
        And I perform DELETE operation "<resourceName>"
        Then I should get response "<status>" 
        
        Examples:
        | scenario                 |status| resourceName  | id   |
        |Delete Booking for Johnny |201   |DeleteBooking  |  3   |