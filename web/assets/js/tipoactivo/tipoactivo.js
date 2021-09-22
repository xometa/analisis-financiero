/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//variables
let buttonModalTipo = query.id("btn-addtipo"),
        modalTitle = query.id("titulo-modal"),
        buttonAction = query.id('btn-action'),
        buttonCancel = query.id('btn-cancel');
let contenedor = query.id('listado-tipos'),
        cuerpomodal = query.id('cuerpo-modal');

let tituloEliminar = query.id('titulo-eliminar'),
        botonEliminar = query.id('mbtn-eliminar');

let formTipo = query.id('formTipo');

listadoTipo();

function listadoTipo() {
    query.get({
        url: "ajax/tipoactivo/listadotipoactivo.jsp",
        method: 'get',
        success: function (data) {
            contenedor.innerHTML = data;
        },
        fail: function () {
            query.toast("Error", "El listado de tipos de activos no se puede visualizar.", "error");
        }
    });
}

function recuperarCuerpoModal(id) {
    let data = {
        id: id
    }
    query.get({
        url: "ajax/tipoactivo/modaltipoactivo.jsp",
        method: 'get',
        data: data,
        success: function (data) {
            cuerpomodal.innerHTML = data;
            $("#modal-tipo").modal("show");
        },
        fail: function () {
            query.toast("Error", "El formulario no se puede visualizar.", "error");
        }
    });
}

function iconoModalBoton(tittle) {
    if (tittle.textContent == "Nuevo tipo de activo") {
        if (buttonAction.childNodes[0].classList.contains("mdi-border-color")) {
            buttonAction.childNodes[0].classList.remove("mdi-border-color");
        }
        buttonAction.childNodes[0].classList.add("mdi-content-save");
        buttonAction.childNodes[1].textContent = " Guardar";
    } else if (tittle.textContent == "Modificar tipo de activo") {
        if (buttonAction.childNodes[0].classList.contains("mdi-content-save")) {
            buttonAction.childNodes[0].classList.remove("mdi-content-save");
        }
        buttonAction.childNodes[0].classList.add("mdi-border-color");
        buttonAction.childNodes[1].textContent = " Modificar";
    }
}

buttonModalTipo.onclick = (e) => {
    modalTitle.innerHTML = "Nuevo tipo de activo";
    iconoModalBoton(modalTitle);
    recuperarCuerpoModal(0)
}

buttonCancel.onclick = (e) => {
    $("#modal-tipo").modal("hide");
}

contenedor.onclick = function (e) {
    let actual = e.target;
    let Id = 0;
    if (actual.matches('span[id=editar]') || actual.classList.contains('btn-warning')) {
        if (actual.nodeName == "BUTTON") {
            actual = actual.firstChild;
        } else {
            actual = actual;
        }
        Id = actual.getAttribute("data-idtipo");
        if (!query.validate(Id, "int") && !query.validate(Id, "numbers")) {
            acciones(Id, "editar")
        } else {
            query.toast("Alteración de información", "Los datos del servidor han sido alterados.", "error");
        }
    } else if (actual.matches('span[id=eliminar]') || actual.classList.contains('btn-danger')) {
        if (actual.nodeName == "BUTTON") {
            actual = actual.firstChild;
        } else {
            actual = actual;
        }
        Id = actual.getAttribute("data-idtipo");
        if (!query.validate(Id, "int") && !query.validate(Id, "numbers")) {
            tituloEliminar.innerHTML = "Eliminar tipo de activo";
            $("#modalEliminarTipo").modal("show");
            botonEliminar.onclick = (e) => {
                acciones(parseInt(Id), "eliminar");
            }
        } else {
            query.toast("Alteración de información", "Los datos del servidor han sido alterados.", "error");
        }
    }
}

function acciones(Id, accion) {
    if (!query.validate(accion, "string")) {
        if (accion == "editar") {
            modalTitle.innerHTML = "Modificar tipo de activo";
            iconoModalBoton(modalTitle);
            recuperarCuerpoModal(Id);
        } else if (accion == "eliminar") {
            query.post({
                url: "ServletTipoActivo",
                content: "application/x-www-form-urlencoded",
                data: {id: Id, accion: "eliminar"},
                success: function (data) {
                    let {texto, icono, objeto} = JSON.parse(data);
                    query.toast("Eliminación de tipo de activo", texto, icono);
                    if (objeto != null) {
                        listadoTipo();
                    }
                },
                fail: function () {
                    query.toast("Error en el servidor", "La petición del servidor ha fallado.", "error");
                }
            });
        }
    } else {
        query.toast("Alteración de información", "La informacion del servidor ha sido alterada.", "error");
    }
}

formTipo.onsubmit = function (e) {
    e.preventDefault();
    let accion = "";
    let formData;
    if (query.isUndefinedNull(query.id("id"))) {
        accion = "guardar";
    } else {
        accion = "editar";
    }

    if (query.emptyElements(this)) {
        query.toast("Campos vacíos", "Complete la información solicitada en el formulario.", "warning");
        return;
    }

    if (query.validate(query.id('nombre').value, "string")) {
        query.toast("Campos vacíos", "Ingrese el nombre del tipo de activo", "warning");
        return;
    }
    
    if (query.validate(query.id('codigo').value, "string")) {
        query.toast("Campos vacíos", "Ingrese el código del tipo de activo", "warning");
        return;
    }
    
    if (!query.id('tangible').checked && !query.id('intangible').checked) {
        query.toast("Campos vacíos", "Seleccione el tipo de activo", "warning");
        return;
    }

    if (!query.validate(accion, "string")) {
        formData = new FormData(this);
        formData.append("accion", accion);
        query.ajax({
            url: "ServletTipoActivo",
            content: "application/x-www-form-urlencoded",
            method: "post",
            data: formData,
            success: function (data) {
                let {texto, icono, objeto} = JSON.parse(data);
                query.toast("Registrar tipo de activo", texto, icono);
                if (objeto != "null") {
                    listadoTipo();
                    $("#modal-tipo").modal("hide");
                }
            },
            fail: function () {
                query.toast("Error", "La petición solicitada ha fallado.", "error");
            }
        });
    } else {
        query.toast("Registro de tipo de activo", "Los datos del servidor han sido alterados.", "error");
    }
}
