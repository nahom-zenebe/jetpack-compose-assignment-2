package com.example.todoapp.presentation.navigation

import TodoDetailScreen
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.jetpack_compose_assignment_2.domain.repository.TodoRepository
import com.example.todoapp.presentation.detail.TodoDetailViewModel

import com.example.todoapp.presentation.list.TodoListScreen
import com.example.todoapp.presentation.list.TodoListViewModel


sealed class Screen(val route: String) {
    object TodoList : Screen("todo_list")
    object TodoDetail : Screen("todo_detail/{todoId}") {
        fun createRoute(todoId: Int) = "todo_detail/$todoId"
    }
}

@Composable
fun AppNavGraph(repository: TodoRepository) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.TodoList.route
    ) {
        composable(Screen.TodoList.route) {
            val viewModel: TodoListViewModel = viewModel(
                factory = object : ViewModelProvider.Factory {
                    override fun <T : ViewModel> create(modelClass: Class<T>): T {
                        return TodoListViewModel(repository) as T
                    }
                }
            )
            TodoListScreen(
                viewModel = viewModel,
                onTodoClick = { todoId ->
                    navController.navigate(Screen.TodoDetail.createRoute(todoId))
                }
            )
        }

        composable(
            Screen.TodoDetail.route,
            arguments = listOf(navArgument("todoId") { type = NavType.IntType })
        ) { backStackEntry ->
            val todoId = backStackEntry.arguments?.getInt("todoId") ?: return@composable
            val viewModel: TodoDetailViewModel = viewModel(
                factory = object : ViewModelProvider.Factory {
                    override fun <T : ViewModel> create(modelClass: Class<T>): T {
                        return TodoDetailViewModel(repository) as T
                    }
                }
            )
            TodoDetailScreen(
                todoId = todoId,
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}

