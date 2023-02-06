package steps

import data.Priority
import data.Task
import data.TasksRepositoryMemory
import io.cucumber.java8.En
import io.kotest.matchers.comparables.shouldBeEqualComparingTo
import io.kotest.matchers.shouldBe
import kotlin.properties.Delegates
import kotlin.test.assertEquals

class TaskServiceSteps: En {

    init {
        lateinit var repository: TasksRepositoryMemory
        var id by Delegates.notNull<Int>()
        val testTask = Task(1, "simple task", Priority.LOW)
        val listOfTasks = mutableListOf(
            Task(1, "Task3", Priority.HIGH),
            Task(2, "Task2", Priority.LOW),
            Task(3, "Task1", Priority.MEDIUM)
        )

        Given("Init repo") {
            repository = TasksRepositoryMemory()
        }

        When("I add task") {
            repository.addTask(testTask)
        }

        When("I add and  complete the task") {
            id = repository.addTask(testTask)
            repository.completeTask(id)

        }

        When("I add a list of task") {
            listOfTasks.forEach {
                repository.addTask(it)
            }
        }

        Then("Tasks are sorted by name") {
            listOfTasks.sortByDescending { it.name }
            repository.tasks.first().name shouldBeEqualComparingTo listOfTasks.first().name
            repository.tasks.last().name shouldBeEqualComparingTo listOfTasks.last().name
        }


        Then("Tasks are sorted by priority") {
            listOfTasks.sortByDescending { it.priority }
            repository.tasks.first().priority shouldBeEqualComparingTo listOfTasks.first().priority
            repository.tasks.last().priority shouldBeEqualComparingTo listOfTasks.last().priority
        }


        Then("It has the same name and priority") {
            assertEquals(testTask.name, repository.getTasks().last().name)
            assertEquals(testTask.priority, repository.getTasks().last().priority)

        }

        Then("Task has completed property") {
                repository.getTasks().first { it.id == id }.completed shouldBe true
                repository.getTasks(true).size shouldBeEqualComparingTo 1

        }


    }
}