@Effects
Feature: Apply Picture Effects

  @Vintage, @Portrait
  Scenario: apply a sepia effect when ordering a vintage colorimetry

    Given a "VINTAGE" "PORTRAIT" picture command
    When the picture processor processed the picture
    Then a sepia effect should be apply to the picture

  @Color, @Identity
  Scenario: display four times the picture when ordering an identity format

    Given a "COLOR" "IDENTITY" picture command
    When the picture processor processed the picture
    Then the picture should be displayed four times

  @BlackAndWhite, @Mini
  Scenario: display sixteen times the picture when ordering an mini format

    Given a "BLACK_AND_WHITE" "MINI" picture command
    When the picture processor processed the picture
    Then the picture should be displayed 16 times
    And a black and white effect should by apply to the picture