Feature: Prices of picture

  In order to let a user take a picture, the correct amount of money is given to the photo booth.

  Scenario Outline: Each type of picture has a a different price that needs to be paid in order to be taken

    Given the price of a "<colorimetry>" "<format>" is "<picture price>" euros
    When more euros than the price of the wanted picture is given to the photo booth
    Then the photo booth should allow the photo taking

    Examples:
      | colorimetry     | format   | picture price |
      | COLOR           | PORTRAIT | 1             |
      | BLACK_AND_WHITE | PORTRAIT | 3             |
      | COLOR           | IDENTITY | 3             |
      | VINTAGE         | PORTRAIT | 1,5           |
      | VINTAGE         | MINI     | 4,5           |
      | BLACK_AND_WHITE | MINI     | 4             |
      | COLOR           | MINI     | 4             |

  Scenario: If the amount of money given to the photo booth is not enough comparing to the picture price, then the picture can't be taken

    Given a picture with a certain price
    When not enough euros is given to the photo booth
    Then the photo booth should reject it and displayed "not enough money provided : 0.0"
