@Protocol
Feature: Convert order to picture processor protocol

  The photo booth embed a picture processor that is responsible for processing the pictures taken by users.
  In order to communicate with it, a specific protocol needs to be used.
  This protocol follows the format : "colorimetry of the picture";"format of the picture"

  Scenario Outline: translate picture orders into a specific protocol for the picture processor
    Given a "<colorimetry>" "<format>" picture order
    When an order is converted into picture processor protocol
    Then the converted order should be "<expectedInstructions>"

    Examples:
      | colorimetry     | format   | expectedInstructions |
      | BLACK_AND_WHITE | MINI     | BW;M                 |
      | COLOR           | IDENTITY | C;I                  |
      | COLOR           | PORTRAIT | C;P                  |
      | VINTAGE         | PORTRAIT | V;P                  |