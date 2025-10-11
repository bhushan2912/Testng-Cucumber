@Regression
Feature: Dmart cookies print


  Scenario: Print cookies and prices from D-mart kalyan
    Given I have Url of dmart website
    And I select city as a kalyan and expect in dropdown list as 'Gandhar Nagar'
    When I search as 'chocolates' in search field
    #Then Print all cookies and prices
    


