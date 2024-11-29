package com.example.warehouseemployee

import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.warehouseemployee.data.classes.Task
import com.example.warehouseemployee.domain.auth.AuthenticationRepository
import com.example.warehouseemployee.domain.messages.MessagesRepository
import com.example.warehouseemployee.domain.task.TaskRepository
import com.example.warehouseemployee.domain.user.WorkerRepository
import com.example.warehouseemployee.presentation.screens.auth.AuthorizationViewModel
import com.example.warehouseemployee.presentation.screens.messages.MessagesViewModel
import com.example.warehouseemployee.presentation.screens.tasks.TasksWorkerViewModel
import io.mockk.mockk
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    lateinit var authVM: AuthorizationViewModel
    lateinit var taskVM: TasksWorkerViewModel
    lateinit var messageVM: MessagesViewModel

    @Test
    fun TestMethod_AuthorizationViewModel_onPhoneChange() {
        val mockWorkerRepository =
            mockk<WorkerRepository>(relaxed = true) // Также заменяем WorkerRepository на mock
        val mockAuthRepository =
            mockk<AuthenticationRepository>(relaxed = true) // Также заменяем WorkerRepository на mock
        authVM = AuthorizationViewModel(mockAuthRepository, mockWorkerRepository)
        authVM.onPhoneChange("+79290509325")
        assertEquals("+79290509325", authVM.phoneValue)
    }

    @Test
    fun TestMethod_AuthorizationViewModel_onPasswordChange() {
        val mockWorkerRepository =
            mockk<WorkerRepository>(relaxed = true) // Также заменяем WorkerRepository на mock
        val mockAuthRepository =
            mockk<AuthenticationRepository>(relaxed = true) // Также заменяем WorkerRepository на mock
        authVM = AuthorizationViewModel(mockAuthRepository, mockWorkerRepository)
        authVM.onPasswordChange("123466")
        assertEquals("123466", authVM.passwordValue)
    }

    @Test
    fun TestProperty_AuthorizationViewModel_NavigateToValue() {
        val mockWorkerRepository =
            mockk<WorkerRepository>(relaxed = true) // Также заменяем WorkerRepository на mock
        val mockAuthRepository =
            mockk<AuthenticationRepository>(relaxed = true) // Также заменяем WorkerRepository на mock
        authVM = AuthorizationViewModel(mockAuthRepository, mockWorkerRepository)
        assertEquals(null, authVM.navigateToValue)
    }

    @Test
    fun TestProperty_TasksWorkerViewModel_TaskListValuee() {
        val mockTaskRepository =
            mockk<TaskRepository>(relaxed = true) // Также заменяем WorkerRepository на mock
        taskVM = TasksWorkerViewModel(mockTaskRepository)
        assertEquals(listOf<Task>(), taskVM.taskListValue)

    }

    @Test
    fun TestProperty_MessagesViewModel_isErrorValue() {
        val mockMessageRepository =
            mockk<MessagesRepository>(relaxed = true) // Также заменяем WorkerRepository на mock
        messageVM = MessagesViewModel(mockMessageRepository)
        assertEquals(false, messageVM.isErrorValue)

    }

}