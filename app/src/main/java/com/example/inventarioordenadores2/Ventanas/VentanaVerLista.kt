package com.example.inventarioordenadores2.Ventanas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.inventarioordenadores2.MyLog
import com.example.inventarioordenadores2.data.Ordenador
import com.example.inventarioordenadores2.shared.OrdenadorViewModel
import kotlinx.coroutines.launch


@Composable
fun VentanaVerLista(navController: NavController, modifier: Modifier, viewModel: OrdenadorViewModel)
{
    val ordenadores by viewModel.ordenadorFiltradas.collectAsState()

    val scope = rememberCoroutineScope()

    var ordenadorAEliminar by remember { mutableStateOf<Ordenador?>(null) }

    //Tu callback actual, ahora solo prepara el diologo
    val  onDeleteClick: (Ordenador) -> Unit = {ordenadorSelecionado ->
        ordenadorAEliminar = ordenadorSelecionado
    }
    //Diologo de confirmacion
    if (ordenadorAEliminar !=null){
        MyLog.d("Se va a eliminar")
        MyLog.d("id ordenador: ${ordenadorAEliminar!!.id}")
        val ordenadorEliminarId = ordenadorAEliminar!!.id
        AlertDialog(
            onDismissRequest = {ordenadorAEliminar = null},
            title = { Text("Eliminar libro") },
            text = {Text("seguro que deseas eliminar ${ordenadorAEliminar!!.identificador} ? Esta accion no se puede deshacer")},
            confirmButton = {
                TextButton({
                    scope.launch {
                        viewModel.eliminarOrdenador(ordenadorEliminarId)
                    }
                    ordenadorAEliminar = null
                }) { Text("Eliminar")}
            },
            dismissButton = {
                TextButton({ordenadorAEliminar = null}) {
                    Text("Cancelar")
                }
            }
        )

    }
    Column(Modifier.fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Text("VentanaVer")
        Button({viewModel.insertarDatosPrueba()}) {
            Text("Insertar datos de prueba")
        }
        Row(Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly) {
            Button({
                MyLog.d("Boton Crear")
                navController.navigate("crear")
            }) { Text("Añadir libro")}
            Button({
                MyLog.d("Boton aplicarFiltros")
                viewModel.aplicarOrdenadores(viewModel.filtrarIdentificador, viewModel.filtrarModelo)
            }) {Text("Aplicar Filtros") }
        }
        OutlinedTextField(
            value = viewModel.filtrarIdentificador,
            onValueChange = {nuevaId ->
                viewModel.onFiltroIdentificadorChanged(nuevaId)},
            label = {Text("Identificador")}
        )
        OutlinedTextField(
            value = viewModel.filtrarModelo,
            onValueChange = {newModelo -> viewModel.onFIltrarModeloChanged(newModelo)},
            label = {Text("Modelo")}
        )
        LazyColumn(modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
            item {
                Row(
                    Modifier.fillMaxWidth()
                        .padding()
                ) { }
            }
        }
    }

}