package com.example.inventarioordenadores2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.inventarioordenadores2.Ventanas.VentanaEditar
import com.example.inventarioordenadores2.Ventanas.VentanaVerLista
import com.example.inventarioordenadores2.data.OrdenadorDatabase
import com.example.inventarioordenadores2.data.OrdenadorRepository
import com.example.inventarioordenadores2.shared.OrdenadorViewModel
import com.example.inventarioordenadores2.ui.theme.InventarioOrdenadores2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InventarioOrdenadores2Theme {

            }
        }
    }
}
@Composable
fun gestorVetana(modifier: Modifier){
    val context = LocalContext.current
    val repositorio = OrdenadorRepository(OrdenadorDatabase.getDatabase(context).ordenadorDao())
    val factory = object : androidx.lifecycle.ViewModelProvider.Factory{
        override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
            return OrdenadorViewModel(repositorio) as T
        }
    }
    val ordenadorViewModel: OrdenadorViewModel = viewModel(factory = factory)
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "ver"){
        composable(
            route = "editor/{id}",
            arguments = listOf(
                navArgument("id"){type = NavType.IntType}
            )
        ) {  backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 0
            VentanaEditar(navController, modifier, ordenadorViewModel, id)
        }
        composable("ver") {VentanaVerLista(navController, modifier, ordenadorViewModel)  }
    }

}