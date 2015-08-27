@Effects
Feature: Picture Effects

  @Vintage, @Portrait
  Scenario: a vintage picture has a sepia effect

    Given a "VINTAGE" "PORTRAIT" picture order
    When the picture processor processed the picture
    Then a sepia effect should be apply to the picture

  @Color, @Identity
  Scenario: when ordering an identity picture, you'll get four of them

    Given a "COLOR" "IDENTITY" picture order
    When the picture processor processed the picture
    Then the picture should be displayed four times

  @BlackAndWhite, @Mini
  Scenario: when ordering a mini picture, you'll get 16 of them

    Given a "BLACK_AND_WHITE" "MINI" picture order
    When the picture processor processed the picture
    Then the picture should be displayed 16 times
    And a black and white effect should by apply to the picture