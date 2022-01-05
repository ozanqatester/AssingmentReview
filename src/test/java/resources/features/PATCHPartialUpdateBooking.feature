@PatchUpdatePartialBooking

Feature: POST CreateBooking

  Scenario Outline: Update Partial booking in the API
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
      | Update Booking for Test1 | 200    | PatchUpdateBooking | 26 | Test1     | Data1    | PATCHUpdateBooking |