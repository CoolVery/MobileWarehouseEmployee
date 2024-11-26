package com.example.warehouseemployee.presentation.navigathion

import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.warehouseemployee.data.classes.Worker
/**
 * Класс Пути экрана
 *
 * @param route пусть к экрану
 * @param title название экрана
 *
 */
interface Destination {
    val route: String
    val title: String
}
/**
 * Путь к экрану Авторизации
 */
object AuthorizationDestination : Destination {
    override val route = "authorization"
    override val title = "Authorization"
}
/**
 * Путь к экрану Задач
 *
 * @param worker рабочий
 * @param themeUI тема приложения
 * @param arguments перечисление и определение типов параметров пути
 *
 */
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
/**
 * Путь к экрану Работники на смене
 *
 * @param worker рабочий
 * @param arguments перечисление и определение типов параметров пути
 *
 */
object VisitingWorkersDestination : Destination {
    override val route = "visitingworkers"
    override val title = "Visitingworkers"
    const val worker = "worker"
    val arguments = listOf(navArgument(name = worker) {
        type = NavType.StringType
    })
}
/**
 * Путь к экрану Инофрмация задачи
 *
 * @param worker рабочий
 * @param themeUI тема приложения
 * @param currentTask задача, которую надо посмотреть
 * @param arguments перечисление и определение типов параметров пути
 *
 */
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
/**
 * Путь к экрану Инофрмация ячейки
 *
 * @param worker рабочий
 * @param themeUI тема приложения
 * @param task задача, из которой взяата ячейка
 * @param cell ячейка скалада, которую надо посмотреть
 * @param arguments перечисление и определение типов параметров пути
 *
 */
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
/**
 * Путь к экрану Инофрмация ячейки
 *
 * @param sendWorker рабочий, который отправляет сообщения
 * @param recipientWorker рабочий, который получает сообщения
 * @param themeUI тема приложения
 * @param task Если перешли из экрана Информация задачи - Объект задачи, если из экрана Задач - null
 * @param arguments перечисление и определение типов параметров пути
 *
 */
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

/**
 * Путь к экрану Инофрмация ячейки
 *
 * @param worker рабочий
 * @param themeUI тема приложения
 * @param task задача из которой перешли
 * @param arguments перечисление и определение типов параметров пути
 *
 */
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