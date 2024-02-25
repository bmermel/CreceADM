$(document).ready(function () {
    // Manejar el envío del formulario
    $('#uploadForm').submit(function (event) {
        // Evitar que se envíe el formulario de manera predeterminada
        event.preventDefault();

        // Obtener el archivo seleccionado
        var fileInput = $('#imageInput')[0];
        var file = fileInput.files[0];

        // Verificar si se seleccionó un archivo
        if (!file) {
            alert("Por favor, seleccione un archivo.");
            return;
        }



        // Crear un objeto FormData para enviar el archivo y el ID del edificio
        var formData = new FormData();
        formData.append('image', file);
        
        // Realizar la solicitud AJAX para cargar el archivo
        $.ajax({
            url: 'http://localhost:8080/file/fileSystem/1',
            type: 'POST',
            data: formData,
            processData: false,
            contentType: false,
            crossDomain: true, // Agregar esta línea
            success: function (data) {
                // Manejar la respuesta exitosa (puedes mostrar un mensaje o redirigir, según tus necesidades)
                console.log("Archivo cargado exitosamente:", data);
            },
            error: function (xhr, status, error) {
                // Manejar errores (puedes mostrar un mensaje de error)
                console.error("Error al cargar el archivo:", error);
            }
        });
    });
});
