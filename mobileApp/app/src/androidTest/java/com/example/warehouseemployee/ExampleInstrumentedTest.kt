package com.example.warehouseemployee

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.warehouseemployee.data.classes.Task
import com.example.warehouseemployee.data.classes.Worker
import com.example.warehouseemployee.domain.auth.AuthenticationRepository
import com.example.warehouseemployee.domain.auth.AuthenticationRepositoryImpl
import com.example.warehouseemployee.domain.user.WorkerRepository
import com.example.warehouseemployee.domain.user.WorkerRepositoryImpl
import com.example.warehouseemployee.presentation.navigathion.AuthorizationDestination
import com.example.warehouseemployee.presentation.navigathion.TasksWorkerDestination
import com.example.warehouseemployee.presentation.screens.auth.Authorization
import com.example.warehouseemployee.presentation.screens.auth.AuthorizationViewModel
import com.example.warehouseemployee.presentation.screens.tasks.TasksWorker
import com.example.warehouseemployee.ui.theme.ThemeMode
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.FlowType
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.realtime.Realtime
import io.github.jan.supabase.storage.Storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.time.Duration.Companion.seconds

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class UiTest {

    @get:Rule
    val composeTestRule =
        createComposeRule()

    val client = createSupabaseClient(
        supabaseUrl = BuildConfig.SUPABASE_URL,
        supabaseKey = BuildConfig.SUPABASE_ANON_KEY
    ) {
        install(Postgrest)
        install(Auth) {
            flowType = FlowType.PKCE
            scheme = "app"
            host = "supabase.com"
        }
        install(Storage)
        install(Realtime)
    }

    @Test
    fun useAppContext() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.warehouseemployee", appContext.packageName)
    }

    lateinit var authVM: AuthorizationViewModel

    @Test
    fun Authorization_IsDisplayed() {
        composeTestRule.setContent {
            val auth: Auth = client.auth
            val postgrest: Postgrest = client.postgrest
            val workerRep: WorkerRepository = WorkerRepositoryImpl(postgrest)
            val authRep: AuthenticationRepository = AuthenticationRepositoryImpl(auth)
            authVM = AuthorizationViewModel(authRep, workerRep)
            TestNavigation(AuthorizationDestination.route)
        }
        composeTestRule.onNodeWithText("Авторизация").assertIsDisplayed()
    }

    @Test
    fun Authorization_SignIn_To_TaskWorker() {
        composeTestRule.setContent {
            val auth: Auth = client.auth
            val postgrest: Postgrest = client.postgrest
            val workerRep: WorkerRepository = WorkerRepositoryImpl(postgrest)
            val authRep: AuthenticationRepository = AuthenticationRepositoryImpl(auth)
            authVM = AuthorizationViewModel(authRep, workerRep)
            authVM.phoneValue = "+79290509325"
            authVM.passwordValue = "123466"
            TestNavigation(AuthorizationDestination.route)

        }
        composeTestRule.onNodeWithText("Вход").performClick()
        composeTestRule.onNodeWithText("Сообщения").isDisplayed()
    }

    @Test
    fun Authorization_SignIn_To_VisitingWorkers() {
        composeTestRule.setContent {
            val auth: Auth = client.auth
            val postgrest: Postgrest = client.postgrest
            val workerRep: WorkerRepository = WorkerRepositoryImpl(postgrest)
            val authRep: AuthenticationRepository = AuthenticationRepositoryImpl(auth)
            authVM = AuthorizationViewModel(authRep, workerRep)
            authVM.phoneValue = "+79290509319"
            authVM.passwordValue = "123460"
            TestNavigation(AuthorizationDestination.route)

        }
        composeTestRule.onNodeWithText("Вход").performClick()
        composeTestRule.onNodeWithText("К задачам").isDisplayed()
    }

    @Test
    fun Authorization_Toast_EmptyTextFolder_IsDisplayed() {
        composeTestRule.setContent {
            val auth: Auth = client.auth
            val postgrest: Postgrest = client.postgrest
            val workerRep: WorkerRepository = WorkerRepositoryImpl(postgrest)
            val authRep: AuthenticationRepository = AuthenticationRepositoryImpl(auth)
            authVM = AuthorizationViewModel(authRep, workerRep)
            TestNavigation(AuthorizationDestination.route)
        }
        composeTestRule.onNodeWithText("Вход").performClick()
        composeTestRule.onNodeWithText("Поля не должны быть пустыми").isDisplayed()
    }

    @Test
    fun Authorization_Toast_IncorrectSignIn_IsDisplayed() {
        composeTestRule.setContent {
            val auth: Auth = client.auth
            val postgrest: Postgrest = client.postgrest
            val workerRep: WorkerRepository = WorkerRepositoryImpl(postgrest)
            val authRep: AuthenticationRepository = AuthenticationRepositoryImpl(auth)
            authVM = AuthorizationViewModel(authRep, workerRep)
            authVM.phoneValue = "+3219"
            authVM.passwordValue = "123460"
            TestNavigation(AuthorizationDestination.route)
        }
        composeTestRule.onNodeWithText("Вход").performClick()
        composeTestRule.onNodeWithText("Неверный телефон или пароль").isDisplayed()
    }

    @Composable
    fun TestNavigation(start: String) {
        val controller = rememberNavController()
        val work = Worker(
            id = 0,
            idRole = 0,
            lastName = "",
            firstName = "",
            patronymic = "",
            idWorker = "",
            idWarehouse = 0
        )
        NavHost(
            navController = controller,
            startDestination = start
        ) {

            composable(AuthorizationDestination.route) {
                Authorization(controller, authVM)
            }

//            composable(TasksWorkerDestination.route) {
//                TasksWorker(work, controller, ThemeMode.Dark)
//            }

//            composable(NavigationRoutes.REGIST) {
//                Register(controller, registViewModel)
//            }
//
//            composable(NavigationRoutes.TASKS) {
//                Tasks(controller, pullToRefreshState, tasksViewModel)
//                barsIsVisible.value = true
//            }

        }
    }
}

//@RunWith(AndroidJUnit4::class)
//class UnitSupabaseTest {
//    @get:Rule
//    val composeTestRule =
//        createComposeRule()
//
//    lateinit var authVM: AuthorizationViewModel
//
//    val client = createSupabaseClient(
//        supabaseUrl = BuildConfig.SUPABASE_URL,
//        supabaseKey = BuildConfig.SUPABASE_ANON_KEY
//    ) {
//        install(Postgrest)
//        install(Auth) {
//            flowType = FlowType.PKCE
//            scheme = "app"
//            host = "supabase.com"
//        }
//        install(Storage)
//        install(Realtime)
//    }
//    @Test
//    fun test() = runBlocking {
//
//        val auth: Auth = client.auth
//        val postgrest: Postgrest = client.postgrest
//        val workerRep: WorkerRepository = WorkerRepositoryImpl(postgrest)
//        val authRep: AuthenticationRepository = AuthenticationRepositoryImpl(auth)
//        authVM = AuthorizationViewModel(authRep, workerRep)
//        authVM.phoneValue = "+79290509319"
//        authVM.passwordValue = "123460"
//
//            authVM.onSignIn()
//            delay(10.seconds)
//            assertEquals("S62bfff1c-5b34-4b5d-9847-9e85a49f1e05", authVM.isErrorValue)
//
//
//    }
//}