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
