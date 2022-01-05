@RegressionTest
Feature: Creating a Booking and Fetch all the Ids followed by partial Update and Delete

  Scenario Outline: 1)Creates a new booking in the API
    Given Testing environment
    When I pass headers
      | Content-Type | application/json |
    And I pass body as "<jsonFileName>"
      | firstname       | <firstname>       |
      | lastname        | <lastname>        |
      | totalprice      | <totalprice>      |
      | depositpaid     | <depositpaid>     |
      | checkin         | <checkin>         |
      | checkout        | <checkout>        |
      | additionalneeds | <additionalneeds> |
    And I perform POST operation "<resourceName>"
    Then I should get response "<status>"
    And response Body contains
      | bookingid |

    Examples:
      | scenario                 | status | resourceName      | firstname | lastname | checkin    | checkout   | totalprice | depositpaid | additionalneeds | jsonFileName      |
      | CreateBooking for Test1  | 200    | PostCreateBooking | Test2     | Data2    | 2018-05-01 | 2020-11-01 | 123        | true        | CotExtra        | PostCreateBooking |
      | CreateBooking for Test2  | 200    | PostCreateBooking | Test1     | Data1    | 2019-04-21 | 2021-07-21 | 123        | true        | ExtraPillow     | PostCreateBooking |
      | Create Booking for Test3 | 200    | PostCreateBooking | Test4     | Data4    | 2019-12-01 | 2021-04-01 | 123        | true        | CoffeeMaker     | PostCreateBooking |
      | Create Booking for Test4 | 200    | PostCreateBooking | Test3     | Data3    | 2021-01-18 | 2021-09-15 | 123        | true        | Breakfast       | PostCreateBooking |
      | Create Booking for Test5 | 200    | PostCreateBooking | Test3     | Data3    | 2020-11-18 | 2021-09-15 | 123        | true        | Slippers        | PostCreateBooking |


  Scenario Outline: 2)Returns a specific booking based upon the First and Last Name provided
    Given Testing environment
    When I pass headers
      | Accept | application/json |
    And I pass queryParametres
      | firstname | <firstname> |
      | lastname  | <lastname>  |
    And I perform GET operation "<resourceName>"
    Then I should get response "<status>"
    And response content Type is json
    And response Body contains
      | bookingid |

    Examples:
      | status | resourceName | firstname | lastname |
      | 200    | GetBookingId | Test1     | Data1    |
      | 200    | GetBookingId | Test2     | Data2    |
      | 200    | GetBookingId | Test3     | Data3    |
      | 200    | GetBookingId | Test4     | Data4    |

  Scenario Outline: 3)Returns a specific booking based upon time provided
    Given Testing environment
    When I pass headers
      | Accept | application/json |
    And I pass queryParametres
      | checkin  | <checkin>  |
      | checkout | <checkout> |
    And I perform GET operation "<resourceName>"
    Then I should get response "<status>"
    And response content Type is json
    And response Body contains
      | bookingid |

    Examples:
      | scenario                             | status | resourceName | checkin    | checkout   |
      | Get Id for both checkin and checkout | 200    | GetBookingId | 2018-11-01 | 2021-09-25 |
      | Get Id for only checkin              | 200    | GetBookingId | 2019-04-21 | 2021-07-21 |
      | Get Id for only checkout             | 200    | GetBookingId | 2020-11-18 | 2021-09-15 |


  Scenario Outline:  4) Update Partial booking in the API
    Given Testing environment
    When I pass headers
      | Content-Type | application/json |
      | Accept       | application/json |
    And I pass PathParametres
      | bookingId | <id> |
    And I set Authorisation token
    And I pass body as "<jsonFileName>"
      | firstname | <firstname> |
      | lastname  | <lastname>  |
    And I perform PATCH operation "<resourceName>"
    Then I should get response "<status>"
    And response Body contains
      | firstname |

    Examples:
      | scenario                 | status | resourceName       | id | firstname | lastname | jsonFileName       |
      | Update Booking for Test1 | 200    | PatchUpdateBooking | 30 | Test2     | Data2    | PATCHUpdateBooking |
      | Update Booking for Test1 | 200    | PatchUpdateBooking | 44 | Test3     | Data3    | PATCHUpdateBooking |
      | Update Booking for Test1 | 200    | PatchUpdateBooking | 59 | Test4     | Data4    | PATCHUpdateBooking |

  Scenario Outline: 5)Returns a specific booking based upon the booking id provided
    Given Testing environment
    When I pass headers
      | Accept | application/json |
    And I pass PathParametres
      | bookingId | <id> |
    And I perform GET operation "<resourceName>"
    Then I should get response "<status>"
    And response content Type is json
    And response Body contains
      | firstname       |
      | lastname        |
      | totalprice      |
      | depositpaid     |
      | additionalneeds |

    Examples:
      | status | resourceName | id |
      | 200    | GetBooking   | 30 |
      | 200    | GetBooking   | 44 |
      | 200    | GetBooking   | 59 |


  Scenario Outline: 6) Delete booking in the API
    Given Testing environment
    When I pass headers
      | Content-Type | application/json |
    And I pass PathParametres
      | bookingId | <id> |
    And I set Authorisation token
    And I perform DELETE operation "<resourceName>"
    Then I should get response "<status>"

    Examples:
      | scenario                 | status | resourceName  | id |
      | Delete Booking for Test1 | 201    | DeleteBooking | 43 |
      | Delete Booking for Test1 | 201    | DeleteBooking | 9  |
      | Delete Booking for Test1 | 201    | DeleteBooking | 32 |

  Scenario Outline: 7) Returns a specific booking based upon All ids
    Given Testing environment
    When I pass headers
      | Accept | application/json |
    And I perform GET operation "<resourceName>"
    Then I should get response "<status>"
    And response content Type is json
    And response Body doesnot contains
      | 9  |
      | 32 |
    Examples:
      | status | resourceName |
      | 200    | GetBookingId |
        