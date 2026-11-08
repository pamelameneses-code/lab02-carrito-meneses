package com.tecsup.rickymorty.ui.lista

import androidx.lifecycle.viewModelScope
import com.tecsup.rickymorty.data.repository.ItemRepository
import com.tecsup.rickymorty.domain.model.Item
import com.tecsup.rickymorty.notification.Notificador
import com.tecsup.rickymorty.ui.UiState
import java.io.IOException
import java.net.SocketTimeoutException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ListaViewModelTest {
    private val personaje = Item(1, "Rick Sánchez", "Humano")
    private lateinit var vm: ListaViewModel

    @Before fun preparar() = Dispatchers.setMain(StandardTestDispatcher())

    @After fun limpiar() {
        if (::vm.isInitialized) vm.viewModelScope.cancel()
        Dispatchers.resetMain()
    }

    @Test fun cargaExitosaMuestraLaListaYNotificaUnaVez() = runTest {
        val repo = RepositorioFalso(Result.success(listOf(personaje)))
        val notificador = NotificadorFalso()
        vm = ListaViewModel(repo, notificador)

        assertEquals(UiState.Cargando, vm.estado.value)
        advanceUntilIdle()

        assertEquals(UiState.Exito(listOf(personaje)), vm.estado.value)
        assertEquals(listOf("Se guardaron 1 elementos"), notificador.avisos)
        assertEquals(1, repo.consultas)
    }

    @Test fun sinConexionConCacheConservaLosDatosYNoNotifica() = runTest {
        val repo = RepositorioFalso(Result.failure(IOException()), listOf(personaje))
        val notificador = NotificadorFalso()
        vm = ListaViewModel(repo, notificador)
        advanceUntilIdle()

        assertEquals(UiState.Exito(listOf(personaje)), vm.estado.value)
        assertNotNull(vm.aviso.value)
        assertTrue(notificador.avisos.isEmpty())
    }

    @Test fun sinCacheMuestraErrorYPermiteRecuperarseAlReintentar() = runTest {
        val repo = RepositorioFalso(Result.failure(IOException()))
        val notificador = NotificadorFalso()
        vm = ListaViewModel(repo, notificador)
        advanceUntilIdle()

        assertTrue(vm.estado.value is UiState.Error)
        assertNull(vm.aviso.value)
        assertTrue(notificador.avisos.isEmpty())

        repo.respuesta = Result.success(listOf(personaje))
        vm.refrescar()
        advanceUntilIdle()

        assertEquals(UiState.Exito(listOf(personaje)), vm.estado.value)
        assertEquals(2, repo.consultas)
        assertEquals(1, notificador.avisos.size)
    }

    @Test fun unaRespuestaVaciaTerminaLaCarga() = runTest {
        vm = ListaViewModel(RepositorioFalso(Result.success(emptyList())), NotificadorFalso())
        advanceUntilIdle()

        assertEquals(UiState.Exito(emptyList<Item>()), vm.estado.value)
    }

    @Test fun buscarPorNombreSinTildesNoVuelveADescargar() = runTest {
        val morty = Item(2, "Morty Smith", "Humano")
        val repo = RepositorioFalso(Result.success(listOf(personaje, morty)))
        vm = ListaViewModel(repo, NotificadorFalso())
        advanceUntilIdle()

        vm.buscar("SANCHEZ")
        advanceUntilIdle()
        assertEquals(UiState.Exito(listOf(personaje)), vm.resultados.value)

        vm.buscar("inexistente")
        advanceUntilIdle()
        assertEquals(UiState.Exito(emptyList<Item>()), vm.resultados.value)

        vm.buscar("")
        advanceUntilIdle()
        assertEquals(UiState.Exito(listOf(personaje, morty)), vm.resultados.value)
        assertEquals(1, repo.consultas)
    }

    @Test fun tiempoDeEsperaAgotadoDaUnMensajeDistintoAlDeConexion() = runTest {
        val repo = RepositorioFalso(Result.failure(SocketTimeoutException()))
        vm = ListaViewModel(repo, NotificadorFalso())
        advanceUntilIdle()

        assertEquals(
            UiState.Error("El servidor tardó demasiado en responder. Intenta nuevamente."),
            vm.estado.value
        )
    }

    private class NotificadorFalso : Notificador {
        val avisos = mutableListOf<String>()
        override fun mostrar(titulo: String, texto: String) { avisos.add(texto) }
    }

    private class RepositorioFalso(
        var respuesta: Result<List<Item>>,
        cache: List<Item> = emptyList()
    ) : ItemRepository {
        var consultas = 0
        override val items = MutableStateFlow(cache)
        override val favoritos = items.map { lista -> lista.filter { it.esFavorito } }
        override suspend fun refrescar(): Result<Int> {
            consultas++
            return respuesta.map { items.value = it; it.size }
        }
        override suspend fun obtenerDetalle(id: Int) = runCatching { items.value.first { it.id == id } }
        override suspend fun alternarFavorito(id: Int) {
            items.value = items.value.map { if (it.id == id) it.copy(esFavorito = !it.esFavorito) else it }
        }
        override suspend fun cantidadGuardada() = items.value.size
        override suspend fun cantidadFavoritos() = items.value.count { it.esFavorito }
        override suspend fun limpiarCache() { items.value = emptyList() }
    }
}
