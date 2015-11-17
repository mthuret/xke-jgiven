package fr.photobooth.domain.jgiven;

import fr.photobooth.domain.jgiven.tags.format.Identity;

/*
@Identity
Feature: Validate Identity pictures

  A photo booth can only take identity pictures that respect state standards.

  Scenario: do not take pictures not respecting the identity standard format

    Given an identity picture command
    And the picture does not respect identity picture standard
    When the photobooth processed the picture command
    Then the photo booth should reject it and displayed "This picture does not respect identity picture standard"
    */

@Identity
public class ValidateIdentityPicturesTest {

}