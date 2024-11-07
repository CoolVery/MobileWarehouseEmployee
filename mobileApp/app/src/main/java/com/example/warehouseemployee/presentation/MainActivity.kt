package com.example.warehouseemployee.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.warehouseemployee.data.classes.Worker
import com.example.warehouseemployee.presentation.navigathion.AuthorizationDestination
import com.example.warehouseemployee.presentation.navigathion.TasksWorkerDestination
import com.example.warehouseemployee.presentation.navigathion.VisitingWorkersDestination
import com.example.warehouseemployee.presentation.screens.auth.Authorization
import com.example.warehouseemployee.presentation.screens.tasks.TasksWorker
import com.example.warehouseemployee.presentation.screens.visitingworkers.VisitingWorkers
import com.example.warehouseemployee.ui.theme.WarehouseEmployeeTheme
import dagger.hilt.android.AndroidEntryPoint
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
                    composable(
                        route = "${TasksWorkerDestination.route}/{${TasksWorkerDestination.worker}}",
                        arguments = TasksWorkerDestination.arguments) { navBackStackEntry ->
                        val worker = navBackStackEntry.arguments!!.getString(TasksWorkerDestination.worker)
                        if (worker != null) {
                            TasksWorker(
                                worker = Json.decodeFromString<Worker>(worker),
                                navController = navController
                            )
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
                }
            }
        }
    }
}

