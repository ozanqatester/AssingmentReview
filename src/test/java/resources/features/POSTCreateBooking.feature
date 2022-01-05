@PostCreateBooking

Feature: Create booking 
        Scenario Outline: Creates a new booking in the API
        Given Testing environment
        When I pass headers
          |Content-Type | application/json|
        And I pass body as "<jsonFileName>"
          |firstname       | <firstname>     |
          |lastname        | <lastname>      |
          |totalprice      | <totalprice>    |
          |depositpaid     | <depositpaid>   |
          |checkin         | <checkin>       |
          |checkout        | <checkout>      |
          |additionalneeds |<additionalneeds>|
        And I perform POST operation "<resourceName>"
        Then I should get response "<status>" 
        And response Body contains
        |bookingid|
        
        Examples:
        | scenario                |status| resourceName     |firstname|lastname|checkin   |checkout  |totalprice|depositpaid|additionalneeds|jsonFileName     |
        |CreateBooking for Test1 |200   |PostCreateBooking |Test1   |Data    |2018-03-01|2020-09-01|123       |true       |RoomService    |PostCreateBooking|
        |CreateBooking for Test2  |200   |PostCreateBooking |Test2    |Data   |2018-04-01|2021-07-01|123       |true       |ExtraBed       |PostCreateBooking|
        |Create Booking for Test3 |200   |PostCreateBooking |Test3    |Data      |2018-09-01|2021-12-01|123       |true       |Coffe          |PostCreateBooking|
        |Create Booking for Test4  |200   |PostCreateBooking |Test4     |Data   |2018-11-01|2021-09-25|123       |true       |Towel          |PostCreateBooking|
        
        
        