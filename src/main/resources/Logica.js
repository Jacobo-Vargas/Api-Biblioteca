// Logica.js

// Función para obtener la lista de libros desde la API y actualizar la tabla en HTML
function obtenerLibros() {
    fetch('http://localhost:8080/apiLibros')
        .then(response => response.json())
        .then(libros => {
            const tablaLibros = document.getElementById('tablaLibros');

            // Limpiar la tabla antes de agregar los nuevos datos
            tablaLibros.innerHTML = '<tr><th>ID</th><th>Título</th><th>Autor</th><th>Año</th><th>Género</th><th>Acciones</th></tr>';

            libros.forEach(libro => {
                const fila = `<tr>
                                    <td>${libro.id}</td>
                                    <td>${libro.titulo}</td>
                                    <td>${libro.autor}</td>
                                    <td>${libro.anio}</td>
                                    <td>${libro.genero}</td>
                                    <td>
                                        <button onclick="mostrarFormularioEditar(${libro.id})">Editar</button>
                                        <button onclick="eliminarLibro(${libro.id})">Eliminar</button>
                                    </td>
                                </tr>`;
                tablaLibros.innerHTML += fila;
            });
        })
        .catch(error => console.error('Error al obtener la lista de libros:', error));
}

// Función para agregar un nuevo libro a la API
function agregarLibro(event) {
    event.preventDefault();

    const nuevoLibro = {
        titulo: document.getElementById('titulo').value,
        autor: document.getElementById('autor').value,
        anio: parseInt(document.getElementById('anio').value),
        genero: document.getElementById('genero').value
    };

    fetch('http://localhost:8080/apiLibros', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(nuevoLibro),
    })
        .then(response => response.json())
        .then(libro => {
            console.log('Libro agregado:', libro);
            // Limpiar campos y ocultar formulario
            limpiarCamposFormulario();
            ocultarFormularioAgregar();
            // Actualizar la lista de libros
            obtenerLibros();
        })
        .catch(error => console.error('Error al agregar el libro:', error));
}

// Función para mostrar el formulario de agregar
function mostrarFormularioAgregar() {
    const formularioAgregar = document.getElementById('formularioAgregar');
    formularioAgregar.style.display = 'block';
}

// Función para ocultar el formulario de agregar
function ocultarFormularioAgregar() {
    const formularioAgregar = document.getElementById('formularioAgregar');
    formularioAgregar.style.display = 'none';
}

// Función para limpiar los campos del formulario de agregar
function limpiarCamposFormulario() {
    document.getElementById('titulo').value = '';
    document.getElementById('autor').value = '';
    document.getElementById('anio').value = '';
    document.getElementById('genero').value = '';
}

// Función para mostrar el formulario de edición con los datos del libro seleccionado
// Función para mostrar el formulario de edición con los datos del libro seleccionado
function mostrarFormularioEditar(id) {
    fetch(`http://localhost:8080/apiLibros/${id}`)
        .then(response => response.json())
        .then(libro => {
            var formularioEdicion = document.getElementById('formularioEdicion');

            // Preencher el formulario con los datos del libro
            document.getElementById('tituloEditar').value = libro.titulo;
            document.getElementById('autorEditar').value = libro.autor;
            document.getElementById('anioEditar').value = libro.anio;
            document.getElementById('generoEditar').value = libro.genero;

            // Mostrar el formulario en la interfaz gráfica
            formularioEdicion.style.display = 'block';

            // Agregar un evento al botón "Guardar cambios"
            var guardarCambiosBtn = formularioEdicion.querySelector('button[type="submit"]');
            guardarCambiosBtn.addEventListener('click', function (event) {
                // Llamada a la función para guardar cambios
                guardarCambiosLibro(event, id);
            });
        })
        .catch(error => console.error('Error al obtener datos del libro:', error));
}

// Función para guardar los cambios de un libro en la API
function guardarCambiosLibro(event, id) {
    event.preventDefault();

    // Obtener los nuevos valores del formulario
    var nuevoTitulo = document.getElementById('tituloEditar').value;
    var nuevoAutor = document.getElementById('autorEditar').value;
    var nuevoAnio = document.getElementById('anioEditar').value;
    var nuevoGenero = document.getElementById('generoEditar').value;

    // Lógica para enviar los cambios a la API y actualizar el libro
    fetch(`http://localhost:8080/apiLibros/${id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            titulo: nuevoTitulo,
            autor: nuevoAutor,
            anio: nuevoAnio,
            genero: nuevoGenero,
        }),
    })
        .then(response => response.json())
        .then(libroActualizado => {
            console.log('Libro actualizado:', libroActualizado);
            // Puedes ocultar el formulario después de la actualización si es necesario
            document.getElementById('formularioEdicion').style.display = 'none';
            // Actualizar la lista de libros
            obtenerLibros();
        })
        .catch(error => console.error('Error al actualizar el libro:', error));
}

// Función para eliminar un libro de la API
function eliminarLibro(id) {
    fetch(`http://localhost:8080/apiLibros/${id}`, {
        method: 'DELETE',
    })
        .then(response => {
            if (response.ok) {
                console.log('Libro eliminado con éxito.');
                // Actualizar la lista de libros después de eliminar
                obtenerLibros();
            } else {
                console.error('Error al eliminar el libro. Código de estado:', response.status);
            }
        })
        .catch(error => console.error('Error al procesar la solicitud:', error));
}

// Cargar la lista de libros al cargar la página
window.onload = obtenerLibros;
