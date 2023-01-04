import data.Priority
import data.Task
import data.TasksRepositoryMemory
import io.kotest.core.spec.style.FunSpec
import io.kotest.core.spec.style.BehaviorSpec
import kotlin.test.assertEquals
import assertk.assertThat
import io.kotest.matchers.comparables.shouldBeEqualComparingTo
import io.kotest.matchers.shouldBe


class HomeWork4Functional : FunSpec({
    lateinit var repository: TasksRepositoryMemory
    val testTask = Task(1, "simple task", Priority.LOW)
    val listOfTasks = mutableListOf(
        Task(1, "Task3", Priority.HIGH),
        Task(2, "Task2", Priority.LOW),
        Task(3, "Task1", Priority.MEDIUM)
    )


    beforeTest {
        repository = TasksRepositoryMemory()
    }
    test("Add task and it appears in the list test") {
        repository.addTask(testTask)


        assertEquals(testTask.name, repository.getTasks().last().name)
        assertEquals(testTask.priority, repository.getTasks().last().priority)
    }

    test("Complete the task") {
        val id = repository.addTask(testTask)
        repository.completeTask(id)

        assertEquals(true, repository.getTasks().first { it.id == id }.completed)
    }

    test("Filter the completed tasks") {
        val id = repository.addTask(testTask)
        repository.completeTask(id)

        assertEquals(1, repository.getTasks(true).size)
    }


    test("Sort by name") {
        listOfTasks.forEach {
            repository.addTask(it)
        }
        listOfTasks.sortBy { it.name }

        assertThat(listOfTasks.first().name).equals(repository.tasks.first().name)
        assertThat(listOfTasks.last().name).equals(repository.tasks.last().name)
    }

    test("Sort by deadline") {
        listOfTasks.forEach {
            repository.addTask(it)
        }
        listOfTasks.sortBy { it.priority }

        assertThat(listOfTasks.first().priority).equals(repository.tasks.first().priority)
        assertThat(listOfTasks.last().priority).equals(repository.tasks.last().priority)
    }

    afterAny() {
        repository.getTasks().forEach {
            it.id?.let { it1 -> repository.deleteTask(it1) }
        }
    }


})


class HomeWork4Behavior : BehaviorSpec({


    given("Task repository init") {
        lateinit var repository: TasksRepositoryMemory
        val testTask = Task(1, "simple task", Priority.LOW)
        val listOfTasks = mutableListOf(
            Task(1, "Task3", Priority.HIGH),
            Task(2, "Task2", Priority.LOW),
            Task(3, "Task1", Priority.MEDIUM)
        )

        beforeContainer {
            repository = TasksRepositoryMemory()
        }
        afterContainer {
            repository.getTasks().forEach {
                it.id?.let { it1 -> repository.deleteTask(it1) }
            }
        }

        `when`("Add task") {
            repository.addTask(testTask)


            then("Task is created and appears in the list test") {
                repository.getTasks().last().name  shouldBeEqualComparingTo testTask.name
                repository.getTasks().last().priority  shouldBeEqualComparingTo testTask.priority
            }
        }

        `when`("Complete the task") {
            val id = repository.addTask(testTask)
            repository.completeTask(id)

            then("Task has completed property") {
                repository.getTasks().first { it.id == id }.completed shouldBe true
            }
        }

        `when`("Complete the task") {
            val id = repository.addTask(testTask)
            repository.completeTask(id)

            then("Task has completed property") {
                repository.getTasks().first { it.id == id }.completed shouldBe true
            }

            then("Task can be filtered") {
                repository.getTasks(true).size shouldBeEqualComparingTo 1
            }
        }

        `when`("Sort by name") {
            listOfTasks.forEach {
                repository.addTask(it)
            }
            listOfTasks.sortByDescending { it.name }

            then("Tasks are sorted by name") {
                repository.tasks.first().name shouldBeEqualComparingTo listOfTasks.first().name
                repository.tasks.last().name shouldBeEqualComparingTo listOfTasks.last().name
            }
        }

        `when`("Sort by deadline") {
            listOfTasks.forEach {
                repository.addTask(it)
            }
            listOfTasks.sortByDescending { it.priority }


            then("Tasks are sorted by priority") {
                repository.tasks.first().priority shouldBeEqualComparingTo listOfTasks.first().priority
                repository.tasks.last().priority shouldBeEqualComparingTo listOfTasks.last().priority
            }
        }

    }
})

