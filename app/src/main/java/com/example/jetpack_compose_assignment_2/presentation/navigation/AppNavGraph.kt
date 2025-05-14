package com.example.todoapp.presentation.navigation

import TodoDetailScreen
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.jetpack_compose_assignment_2.domain.repository.TodoRepository
import com.example.todoapp.presentation.detail.TodoDetailViewModel
import com.example.todoapp.presentation.list.TodoDetailViewModelFactory
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

    NavHost(navController = navController, startDestination = "todo_list") {
        composable("todo_list") {
            val factory =  TodoDetailViewModelFactory(repository)
            val viewModel: TodoListViewModel = viewModel(factory = factory)

            TodoListScreen(viewModel = viewModel, onTodoClick = { todoId ->
                navController.navigate("todo_detail/$todoId")
            })
        }

        composable(
            "todo_detail/{todoId}",
            arguments = listOf(navArgument("todoId") { type = NavType.IntType })
        ) { backStackEntry ->
            val todoId = backStackEntry.arguments?.getInt("todoId") ?: return@composable
            val factory = TodoDetailViewModelFactory(repository)
            val viewModel: TodoDetailViewModel = viewModel(factory = factory)

            TodoDetailScreen(todoId = todoId, viewModel = viewModel, onBack = {
                navController.popBackStack()
            })
        }
    }
}
