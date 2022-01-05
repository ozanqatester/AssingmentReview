@GetBookingApi
Feature: GetBooking - Booking ID

   @GetBookingbyId
        Scenario Outline: 1)Returns a specific booking based upon the booking id provided
        Given Testing environment
        When I pass headers
          |Accept    | application/json|
        And I pass PathParametres
          |bookingId |<id>|
        And I perform GET operation "<resourceName>"
        Then I should get response "<status>" 
        And response content Type is json
        And response Body contains
          |firstname        |
          |lastname         |
          |totalprice       |
          |depositpaid      |
          |additionalneeds  |

        Examples:
          |status| resourceName |id   |
          | 200  | GetBooking   | 7   |
          | 200  | GetBooking   | 8   |
          | 200  | GetBooking   | 17  |
        
        
        
        
        @GetBookingbyNames
        Scenario Outline: 2)Returns a specific booking based upon the First and Last Name provided
        Given Testing environment
        When I pass headers
          |Accept    | application/json|
        And I pass queryParametres
          |firstname|<firstname>|
          |lastname |<lastname> |
        And I perform GET operation "<resourceName>"
        Then I should get response "<status>" 
        And response content Type is json
        And response Body contains
          |bookingid|

        Examples:
          |status| resourceName |firstname|lastname|
          | 200  | GetBookingId | Test1   |Data      |
          
        @GetBookingbyAllIds  
        Scenario Outline: 3) Returns a specific booking based upon All ids
        Given Testing environment
        When I pass headers
          |Accept    | application/json|
        And I perform GET operation "<resourceName>"
        Then I should get response "<status>" 
        And response content Type is json
        And response Body contains
          |bookingid|

        Examples:
          |status| resourceName |
          | 200  | GetBookingId |
          
          
          @GetBookingbyCheckinAndOut
        Scenario Outline: 4)Returns a specific booking based upon time provided
        Given Testing environment
        When I pass headers
          |Accept    | application/json|
        And I pass queryParametres
          |checkin  |<checkin>  |
          |checkout |<checkout> |
        And I perform GET operation "<resourceName>"
        Then I should get response "<status>" 
        And response content Type is json
        And response Body contains
          |bookingid|

        Examples:
         |scenario                             |status| resourceName |checkin    |checkout  |
         |Get Id for both checkin and checkout | 200  | GetBookingId |2018-11-01 |2021-09-25|
         |Get Id for only checkin              | 200  | GetBookingId |2018-09-01 |          |
         |Get Id for only checkout             | 200  | GetBookingId |2018-04-01 |2021-07-01|