package com.example.warehouseemployee.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.warehouseemployee.data.classes.Cell
import com.example.warehouseemployee.data.classes.Task
import com.example.warehouseemployee.data.classes.Worker
import com.example.warehouseemployee.presentation.navigathion.AuthorizationDestination
import com.example.warehouseemployee.presentation.navigathion.InfoCellDestination
import com.example.warehouseemployee.presentation.navigathion.InfoTaskLoadingDestination
import com.example.warehouseemployee.presentation.navigathion.MessagesDestination
import com.example.warehouseemployee.presentation.navigathion.OptimalPlanDestination
import com.example.warehouseemployee.presentation.navigathion.TasksWorkerDestination
import com.example.warehouseemployee.presentation.navigathion.VisitingWorkersDestination
import com.example.warehouseemployee.presentation.screens.auth.Authorization
import com.example.warehouseemployee.presentation.screens.infocell.InfoCell
import com.example.warehouseemployee.presentation.screens.infotask.InfoTaskLoading
import com.example.warehouseemployee.presentation.screens.messages.Messages
import com.example.warehouseemployee.presentation.screens.optimalplan.OptimalPlan
import com.example.warehouseemployee.presentation.screens.tasks.TasksWorker
import com.example.warehouseemployee.presentation.screens.visitingworkers.VisitingWorkers
import com.example.warehouseemployee.ui.theme.ThemeMode
import com.example.warehouseemployee.ui.theme.WarehouseEmployeeTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.withTimeoutOrNull
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            WarehouseEmployeeTheme {
                val navController = rememberNavController()
                val currentBackStack by navController.currentBackStackEntryAsState()
                val currentDestination = currentBackStack?.destination
                NavHost(
                    navController,
                    startDestination = AuthorizationDestination.route,
                ) {
                    composable(AuthorizationDestination.route) {
                        Authorization(
                            navController = navController
                        )
                    }
                    //Механизм в том, что все муршруты - это route/аргумент1/аргумент2
                    //Если аргумент - объект, то это Json строка.
                    //При вызове функции экрана, объект десериализуется в объект
                    //Также в зависимости от темы передается ее название
                    composable(
                        route = "${TasksWorkerDestination.route}/{${TasksWorkerDestination.worker}}/{${TasksWorkerDestination.themeUI}}",
                        arguments = TasksWorkerDestination.arguments) { navBackStackEntry ->
                        val worker = navBackStackEntry.arguments!!.getString(TasksWorkerDestination.worker)
                        val themeUI = navBackStackEntry.arguments!!.getString(TasksWorkerDestination.themeUI)
                        if (worker != null) {
                            if (themeUI == "null") {
                                TasksWorker(
                                    worker = Json.decodeFromString<Worker>(worker),
                                    navController = navController,
                                    themeModeCurrent = null
                                )
                            }
                            else {
                                if (themeUI == "Dark") {
                                    TasksWorker(
                                        worker = Json.decodeFromString<Worker>(worker),
                                        navController = navController,
                                        themeModeCurrent = ThemeMode.Dark
                                    )
                                }
                                else {
                                    TasksWorker(
                                        worker = Json.decodeFromString<Worker>(worker),
                                        navController = navController,
                                        themeModeCurrent = ThemeMode.Light
                                    )
                                }
                            }
                        }
                    }
                    composable(
                        route = "${VisitingWorkersDestination.route}/{${VisitingWorkersDestination.worker}}",
                        arguments = VisitingWorkersDestination.arguments) { navBackStackEntry ->
                        val worker = navBackStackEntry.arguments!!.getString(VisitingWorkersDestination.worker)
                        if (worker != null) {
                            VisitingWorkers(
                                worker = Json.decodeFromString<Worker>(worker),
                                navController = navController
                            )
                        }
                    }
                    composable(
                        route = "${InfoTaskLoadingDestination.route}/{${InfoTaskLoadingDestination.worker}}/{${InfoTaskLoadingDestination.currentTask}}/{${InfoTaskLoadingDestination.themeUI}}",
                        arguments = InfoTaskLoadingDestination.arguments) { navBackStackEntry ->

                        val worker =
                            navBackStackEntry.arguments!!.getString(InfoTaskLoadingDestination.worker)
                        val currentTask =
                            navBackStackEntry.arguments!!.getString(InfoTaskLoadingDestination.currentTask)
                        val themeUI =
                            navBackStackEntry.arguments!!.getString(InfoTaskLoadingDestination.themeUI)
                        if (worker != null && currentTask != null) {
                            if (themeUI == "Dark") {

                                InfoTaskLoading(
                                    worker = Json.decodeFromString<Worker>(worker),
                                    currentTask = Json.decodeFromString<Task>(currentTask),
                                    navController = navController,
                                    themeUI = ThemeMode.Dark
                                )
                            }
                            else {
                                InfoTaskLoading(
                                    worker = Json.decodeFromString<Worker>(worker),
                                    currentTask = Json.decodeFromString<Task>(currentTask),
                                    navController = navController,
                                    themeUI = ThemeMode.Light
                                )
                            }
                        }
                    }
                    composable(
                        route = "${InfoCellDestination.route}/{${InfoCellDestination.themeUI}}/{${InfoCellDestination.cell}}/{${InfoCellDestination.worker}}/{${InfoCellDestination.task}}",
                        arguments = InfoCellDestination.arguments) { navBackStackEntry ->
                        val themeUI = navBackStackEntry.arguments!!.getString(InfoCellDestination.themeUI)
                        val cell = navBackStackEntry.arguments!!.getString(InfoCellDestination.cell)
                        val worker = navBackStackEntry.arguments!!.getString(InfoCellDestination.worker)
                        val task = navBackStackEntry.arguments!!.getString(InfoCellDestination.task)
                        if (themeUI == "Dark") {
                            InfoCell(
                                navController = navController,
                                themeUI = ThemeMode.Dark,
                                cell = Json.decodeFromString<Cell>(cell!!),
                                worker = Json.decodeFromString<Worker>(worker!!),
                                task = Json.decodeFromString<Task>(task!!)
                            )
                        }
                        else {
                            InfoCell(
                                navController = navController,
                                themeUI = ThemeMode.Light,
                                cell = Json.decodeFromString<Cell>(cell!!),
                                worker = Json.decodeFromString<Worker>(worker!!),
                                task = Json.decodeFromString<Task>(task!!)

                            )
                        }
                    }
                    composable(
                        route = "${MessagesDestination.route}/{${MessagesDestination.sendWorker}}/{${MessagesDestination.recipientWorker}}/{${MessagesDestination.task}}/{${MessagesDestination.themeUI}}",
                        arguments = MessagesDestination.arguments) { navBackStackEntry ->
                        val sendWorker = navBackStackEntry.arguments!!.getString(MessagesDestination.sendWorker)
                        val recipientWorker = navBackStackEntry.arguments!!.getString(MessagesDestination.recipientWorker)
                        val task = navBackStackEntry.arguments!!.getString(MessagesDestination.task)
                        val themeUI = navBackStackEntry.arguments!!.getString(MessagesDestination.themeUI)
                        if (themeUI == "Dark") {
                            if (task == "null") {
                                Messages(
                                    navController = navController,
                                    sendWorker = Json.decodeFromString<Worker>(sendWorker!!),
                                    recipientWorker = Json.decodeFromString<Worker>(recipientWorker!!),
                                    themeUI = ThemeMode.Dark,
                                    task = null
                                )
                            }
                            else {
                                Messages(
                                    navController = navController,
                                    sendWorker = Json.decodeFromString<Worker>(sendWorker!!),
                                    recipientWorker = Json.decodeFromString<Worker>(recipientWorker!!),
                                    themeUI = ThemeMode.Dark,
                                    task = Json.decodeFromString<Task>(task!!)
                                )
                            }
                        }
                        else {
                            if (task == "null") {
                                Messages(
                                    navController = navController,
                                    sendWorker = Json.decodeFromString<Worker>(sendWorker!!),
                                    recipientWorker = Json.decodeFromString<Worker>(recipientWorker!!),
                                    themeUI = ThemeMode.Light,
                                    task = null
                                )
                            }
                            else {
                                Messages(
                                    navController = navController,
                                    sendWorker = Json.decodeFromString<Worker>(sendWorker!!),
                                    recipientWorker = Json.decodeFromString<Worker>(recipientWorker!!),
                                    themeUI = ThemeMode.Light,
                                    task = Json.decodeFromString<Task>(task!!)
                                )
                            }
                        }
                    }
                    composable(
                        route = "${OptimalPlanDestination.route}/{${OptimalPlanDestination.worker}}/{${OptimalPlanDestination.task}}/{${OptimalPlanDestination.themeUI}}",
                        arguments = OptimalPlanDestination.arguments) { navBackStackEntry ->
                        val worker =
                            navBackStackEntry.arguments!!.getString(OptimalPlanDestination.worker)
                        val task =
                            navBackStackEntry.arguments!!.getString(OptimalPlanDestination.task)
                        val themeUI =
                            navBackStackEntry.arguments!!.getString(OptimalPlanDestination.themeUI)
                        if (themeUI == "Dark") {
                            OptimalPlan(
                                navController = navController,
                                worker = Json.decodeFromString<Worker>(worker!!),
                                task = Json.decodeFromString<Task>(task!!),
                                themeUI = ThemeMode.Dark
                            )
                        } else {
                            OptimalPlan(
                                navController = navController,
                                worker = Json.decodeFromString<Worker>(worker!!),
                                task = Json.decodeFromString<Task>(task!!),
                                themeUI = ThemeMode.Light
                            )
                        }
                    }
                }
            }
        }
    }
}




