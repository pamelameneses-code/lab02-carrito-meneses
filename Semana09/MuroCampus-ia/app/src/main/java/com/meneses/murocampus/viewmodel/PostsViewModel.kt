package com.meneses.murocampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meneses.murocampus.data.PostRepository
import com.meneses.murocampus.data.mensajeDe
import com.meneses.murocampus.model.Comentario
import com.meneses.murocampus.model.NuevoPost
import com.meneses.murocampus.model.Post
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class DetalleUi(val post: Post, val comentarios: List<Comentario>)

class PostsViewModel(private val repo: PostRepository) : ViewModel() {
    private val _lista = MutableStateFlow<UiState<List<Post>>>(UiState.Cargando)
    val lista = _lista.asStateFlow()
    private val _usuarioFiltro = MutableStateFlow<Int?>(null)
    val usuarioFiltro = _usuarioFiltro.asStateFlow()
    private val _busqueda = MutableStateFlow("")
    val busqueda = _busqueda.asStateFlow()

    fun buscar(texto: String) {
        _busqueda.value = texto
    }

    private var cargaLista: Job? = null

    init {
        cargar()
    }

    fun cargar() {
        cargaLista?.cancel()
        cargaLista = viewModelScope.launch {
            _lista.value = UiState.Cargando
            val usuario = _usuarioFiltro.value
            val resultado = if (usuario == null) repo.listar() else repo.porUsuario(usuario)
            _lista.value = resultado.fold(
                onSuccess = { UiState.Exito(it) },
                onFailure = { UiState.Error(mensajeDe(it)) },
            )
        }
    }

    fun filtrar(userId: Int?) {
        _usuarioFiltro.value = userId
        cargar()
    }

    private val _detalle = MutableStateFlow<UiState<DetalleUi>>(UiState.Cargando)
    val detalle = _detalle.asStateFlow()
    private var cargaDetalle: Job? = null

    fun abrir(id: Int) {
        cargaDetalle?.cancel()
        _detalle.value = UiState.Cargando
        cargaDetalle = viewModelScope.launch {
            val post = repo.detalle(id)
            val comentarios = if (post.isSuccess) repo.comentarios(id) else null
            val error = post.exceptionOrNull() ?: comentarios?.exceptionOrNull()
            _detalle.value = if (error != null) {
                UiState.Error(mensajeDe(error))
            } else {
                UiState.Exito(DetalleUi(post.getOrThrow(), requireNotNull(comentarios).getOrThrow()))
            }
        }
    }

    fun cerrarDetalle() {
        cargaDetalle?.cancel()
    }

    private val _publicacion = MutableStateFlow<UiState<Post>?>(null)
    val publicacion = _publicacion.asStateFlow()

    fun publicar(titulo: String, texto: String) {
        if (titulo.isBlank() || texto.isBlank() || _publicacion.value == UiState.Cargando) return
        _publicacion.value = UiState.Cargando
        viewModelScope.launch {
            _publicacion.value = repo.crear(NuevoPost(1, titulo, texto)).fold(
                onSuccess = { UiState.Exito(it) },
                onFailure = { UiState.Error(mensajeDe(it)) },
            )
        }
    }

    fun limpiarPublicacion() {
        if (_publicacion.value != UiState.Cargando) _publicacion.value = null
    }
}
