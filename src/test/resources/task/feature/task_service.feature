Feature: TaskService

  Scenario: Add task and it appears in the list test
    Given Init repo
    When I add task
    Then It has the same name and priority


  Scenario: Complete the task
    Given Init repo
    When I add and  complete the task
    Then Task has completed property

  Scenario: Sort by name
    Given Init repo
    When I add a list of task
    Then Tasks are sorted by name

  Scenario: Sort by deadline
    Given Init repo
    When I add a list of task
    Then Tasks are sorted by priority


  Scenario: Size changing when adding or removing the task
    Given Init repo
    Then Task list is empty
    When I add task
    Then Task list size equals to 1
    When I remove task
    Then Task list is empty
