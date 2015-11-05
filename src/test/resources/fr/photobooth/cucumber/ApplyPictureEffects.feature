@Effects
Feature: Apply Picture Effects

  @Color, @Identity
  Scenario: display four times the picture when ordering an identity format

    Given a "COLOR" "IDENTITY" picture order
    When the picture processor processed the picture
    Then the picture should be displayed four times