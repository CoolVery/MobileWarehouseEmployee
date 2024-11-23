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
    const val themeUI = "themeUI"
    val arguments = listOf(navArgument(name = worker) {
        type = NavType.StringType
    },
        navArgument(name = themeUI) {
            type = NavType.StringType
        } )
}

object VisitingWorkersDestination : Destination {
    override val route = "visitingworkers"
    override val title = "Visitingworkers"
    const val worker = "worker"
    val arguments = listOf(navArgument(name = worker) {
        type = NavType.StringType
    })
}
object InfoTaskLoadingDestination : Destination {
    override val route = "infotaskloading"
    override val title = "Infotaskloading"
    const val worker = "worker"
    const val currentTask = "currentTask"
    const val themeUI = "themeUI"
    val arguments = listOf(
        navArgument(name = worker) {
            type = NavType.StringType
        },
        navArgument(name = currentTask) {
            type = NavType.StringType
        },
        navArgument(name = themeUI) {
            type = NavType.StringType
        }
    )
}

object InfoCellDestination : Destination {
    override val route = "infocell"
    override val title = "Infocell"
    const val cell = "cell"
    const val themeUI = "themeUI"
    const val worker = "worker"
    const val task = "task"
    val arguments = listOf(
        navArgument(name = cell) {
            type = NavType.StringType
        },
        navArgument(name = themeUI) {
            type = NavType.StringType
        },
        navArgument(name = worker) {
            type = NavType.StringType
        },
        navArgument(name = task) {
            type = NavType.StringType
        }
    )
}

object MessagesDestination : Destination {
    override val route = "messages"
    override val title = "Messages"
    const val sendWorker = "sendWorker"
    const val recipientWorker = "recipientWorker"
    const val themeUI = "themeUI"
    const val task = "task"

    val arguments = listOf(
        navArgument(name = sendWorker) {
            type = NavType.StringType
        },
        navArgument(name = recipientWorker) {
            type = NavType.StringType
        },
        navArgument(name = themeUI) {
            type = NavType.StringType
        },
        navArgument(name = task) {
            type = NavType.StringType
        },
    )
}

object OptimalPlanDestination : Destination {
    override val route = "optimalplan"
    override val title = "Otimalplan"
    const val themeUI = "themeUI"
    const val worker = "worker"
    const val task = "task"

    val arguments = listOf(
        navArgument(name = themeUI) {
            type = NavType.StringType
        },
        navArgument(name = worker) {
            type = NavType.StringType
        },
        navArgument(name = task) {
            type = NavType.StringType
        },
    )
}