@Price, @Colorimetry, @Format
Feature: Validate prices

  In order to let a user take a picture, the correct amount of money is given to the photo booth.

  Scenario: Reject picture command if required price is not provided

    Given a "BLACK_AND_WHITE" "IDENTITY" picture with a price of 3 euros
    When 2 euros are given to the photo booth
    And the photobooth processed the picture command
    Then the photo booth should reject it and displayed "not enough money provided : 2.0"

  Scenario: Allow picture command if required price is provided

    Given a "BLACK_AND_WHITE" "IDENTITY" picture with a price of 3 euros
    When 2 euros are given to the photo booth
    And the photobooth processed the picture command
    Then the photo booth should allow the photo taking