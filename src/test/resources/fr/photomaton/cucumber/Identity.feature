Feature: Identity pictures

  A photomaton can only take identity pictures that respect state standards.

  Scenario: a user can know if the taken picture respect the standard imposed by the identity format

    Given an identity picture is taken by the photomaton
    And the picture does not respect identity picture standard
    When the picture is being processed by the picture processor
    Then the photomaton should reject it and displayed "This picture does not respect identity picture standard"

  Scenario: a user can't choose a vintage identity picture

    Given a "VINTAGE" "IDENTITY" picture order
    When the photomaton processed the picture
    Then the photomaton should reject it and displayed "the picture combination IDENTITY VINTAGE is not allowed"
