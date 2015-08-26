Feature: Identity pictures

  A photo booth can only take identity pictures that respect state standards.

  Scenario: a user can know if the taken picture respect the standard imposed by the identity format

    Given an identity picture is taken by the photo booth
    And the picture does not respect identity picture standard
    When the picture is being processed by the picture processor
    Then the photo booth should reject it and displayed "This picture does not respect identity picture standard"

  Scenario: a user can't choose a vintage identity picture

    Given a "VINTAGE" "IDENTITY" picture order
    When the photo booth processed the picture
    Then the photo booth should reject it and displayed "the picture combination IDENTITY VINTAGE is not allowed"
