Feature: Picture Processor Protocol

  The photomaton embed a picture processor that is responsible for processing the pictures taken by users. In order to communicate with it, a specific protocol needs to be used.

  This protocol follows the format : "colorimetry of the picture";"format of the picture"

  Scenario Outline: picture orders are translated into a specific protocol for the picture processor
    Given a "<colorimetry>" "<format>" picture order
    When an order is converted into picture processor protocol
    Then the converted order should be "<picture processor instructions>"

    Examples:
      | colorimetry     | format   | picture processor instructions |
      | COLOR           | IDENTITY | C;I                            |
      | BLACK_AND_WHITE | MINI     | BW;M                           |
      | COLOR           | PORTRAIT | C;P                            |
      | VINTAGE         | PORTRAIT | V;P                            |


