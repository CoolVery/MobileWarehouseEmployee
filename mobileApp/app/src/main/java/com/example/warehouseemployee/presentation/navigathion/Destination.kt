package com.example.warehouseemployee.presentation.navigathion

import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.warehouseemployee.data.classes.Worker

interface Destination {
    val route: String
    val title: String
}

object AuthorizationDestination : Destination {
    override val route = "authorization"
    override val title = "Authorization"
}

object TasksWorkerDestination : Destination {
    override val route = "tasksworker"
    override val title = "Tasksworker"
    const val worker = "worker"
    val arguments = listOf(navArgument(name = worker) {
        type = NavType.StringType
    })
}

object VisitingWorkersDestination : Destination {
    override val route = "visitingworkers"
    override val title = "Visitingworkers"
    const val worker = "worker"
    val arguments = listOf(navArgument(name = worker) {
        type = NavType.StringType
    })
}