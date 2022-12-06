import data.Priority
import data.Task
import data.TasksRepositoryMemory
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


class Tests {

   lateinit var repository: TasksRepositoryMemory
            @BeforeEach
    fun beforeMethod() {
        repository = TasksRepositoryMemory()
    }
    @Test
    fun addTaskAndItAppearsInTheListTest() {
        repository.addTask(Task(name = "first", priority = Priority.MEDIUM))
        val name = repository.getTasks().last().name
        val priority = repository.getTasks().last().priority

        assertEquals("first", name)
        assertEquals(Priority.MEDIUM, priority)
    }

    @Test
    fun completeTaskAndFilterCompleted() {
        repository.addTask(Task(name = "second", priority = Priority.MEDIUM))
        val id = repository.getTasks().last().id ?: 0
        repository.completeTask(id)
        val name = repository.getTasks(true).first { it.id?.equals(id) == true }.name

        assertEquals("second", name)
    }

    @Test
    fun sortTasksByNameAndByPriority() {
        val expectedTasks = mutableListOf<Task>(
            Task(name = "Add task", priority = Priority.LOW),
            Task(name = "Delete report", priority = Priority.MEDIUM),
            Task(name = "Create report", priority = Priority.LOW),
            Task(name = "Create report", priority = Priority.HIGH))

        repository.addTask(expectedTasks[0])
        repository.addTask(expectedTasks[1])
        repository.addTask(expectedTasks[2])
        val tasks = repository.getTasks()


        assertEquals(expectedTasks[0].name, tasks[0].name)
        assertEquals(expectedTasks[3].name, tasks[1].name)
        assertEquals(expectedTasks[2].name, tasks[2].name)
        assertEquals(expectedTasks[1].name, tasks[3].name)
    }

    @AfterEach
    fun afterTest() {
        repository.getTasks().forEach{
            it.id?.let { it1 -> repository.deleteTask(it1) }
        }
    }

}