Feature: the version can be retrieved

  Scenario: Client make call hello
    When Client call api cucumber hello
    Then Client receives status code of 200
    And Client receives hello