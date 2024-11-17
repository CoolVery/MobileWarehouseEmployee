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
object InfoTaskUnloadingDestination : Destination {
    override val route = "infotaskunloading"
    override val title = "Infotaskunloading"
    const val worker = "worker"
    const val currentTask = "currentTask"
    const val themeUI = "themeUI"
    val arguments = listOf(
        navArgument(name = InfoTaskLoadingDestination.worker) {
            type = NavType.StringType
        },
        navArgument(name = InfoTaskLoadingDestination.currentTask) {
            type = NavType.StringType
        },
        navArgument(name = themeUI) {
            type = NavType.StringType
        }
    )
}