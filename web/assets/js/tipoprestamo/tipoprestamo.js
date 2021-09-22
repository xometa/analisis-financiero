/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

let contenedor = query.id('listado-tipoprestamos'),
        cuerpomodal = query.id('cuerpo-modal'),
        buttonModalTprestamo = query.id('btn-addtipoprestamo'),
        modalTitle = query.id("titulo-modal"),
        buttonAction = query.id('btn-action'),
        buttonCancel = query.id('btn-cancel'),
        formTipoPrestamo = query.id('formTipoPrestamo');

//de la modal eliminar
let tituloEliminar = query.id('titulo-eliminar'),
        botonEliminar = query.id('mbtn-eliminar');
//llamado de la lista
listadoTiposPrestamos();

function listadoTiposPrestamos() {
    query.get({
        url: "ajax/tipoprestamo/listadotipoprestamos.jsp",
        method: 'get',
        success: function (data) {
            contenedor.innerHTML = data;
        },
        fail: function () {
            query.toast("Error", "El listado de tipos de prestamos no se puede visualizar.", "error");
        }
    });
}

function recuperarCuerpoModal(id) {
    let data = {
        id: id
    }
    query.get({
        url: "ajax/tipoprestamo/modaltipoprestamo.jsp",
        method: 'get',
        data: data,
        success: function (data) {
            cuerpomodal.innerHTML = data;
            $("#modal-tipoprestamo").modal("show");
        },
        fail: function () {
            query.toast("Error", "El formulario no se puede visualizar.", "error");
        }
    });
}

function iconoModalBoton(tittle) {
    if (tittle.textContent == "Nuevo Tipo de Prestamo") {
        if (buttonAction.childNodes[0].classList.contains("mdi-border-color")) {
            buttonAction.childNodes[0].classList.remove("mdi-border-color");
        }
        buttonAction.childNodes[0].classList.add("mdi-content-save");
        buttonAction.childNodes[1].textContent = " Guardar";
    } else if (tittle.textContent == "Modificar Tipo de Prestamo") {
        if (buttonAction.childNodes[0].classList.contains("mdi-content-save")) {
            buttonAction.childNodes[0].classList.remove("mdi-content-save");
        }
        buttonAction.childNodes[0].classList.add("mdi-border-color");
        buttonAction.childNodes[1].textContent = " Modificar";
    }
}

buttonModalTprestamo.onclick = (e) => {
    modalTitle.innerHTML = "Nuevo Tipo de Prestamo";
    iconoModalBoton(modalTitle);
    recuperarCuerpoModal(0)
}
buttonCancel.onclick = (e) => {

    $("#modal-tipoprestamo").modal("hide");
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
        Id = actual.getAttribute("data-id");
        if (!query.validate(Id, "int") && !query.validate(Id, "numbers")) {
            acciones({id: Id, accion: "editar"});
        } else {
            query.toast("Alteración de información", "Los datos del servidro han sido alterados.", "error");
        }
    } else if (actual.matches('span[id=eliminar]') || actual.classList.contains('btn-danger')) {
        if (actual.nodeName == "BUTTON") {
            actual = actual.firstChild;
        } else {
            actual = actual;
        }
        Id = actual.getAttribute("data-id");
        if (!query.validate(Id, "int") && !query.validate(Id, "numbers")) {
            tituloEliminar.innerHTML = "Eliminar tipo de prestamo";
            $("#modalEliminarActivo").modal("show");
            botonEliminar.onclick = (e) => {
                acciones({id: parseInt(Id), accion: "eliminar"});
            }
        } else {
            query.toast("Alteración de información", "Los datos del servidro han sido alterados.", "error");
        }
    }
}

function acciones(data) {
    if (!query.validate(data.accion, "string")) {
        if (data.accion == "editar") {
            modalTitle.innerHTML = "Modificar Tipo de Prestamo";
            iconoModalBoton(modalTitle);
            recuperarCuerpoModal(data.id);
        } else if (data.accion == "eliminar") {
            query.post({
                url: "ServletTipoPrestamo",
                content: "application/x-www-form-urlencoded",
                data: data,
                success: function (datos) {
                    let {titulo, texto, icono, objeto} = JSON.parse(datos);
                    query.toast(titulo, texto, icono);
                    if (objeto != "null") {
                        listadoTiposPrestamos();
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

//guardando el tipo de prestamo
formTipoPrestamo.onsubmit = function (e) {
    e.preventDefault();
    let accion = "";
    let formData;
    if (query.isUndefinedNull(query.id("id"))) {
        accion = "guardar";
    } else {
        accion = "editar";
    }

//validación de  todos los campos
    if (query.emptyElements(this)) {
        query.toast("Campos vacíos", "Complete la información solicitada en el formulario.", "warning");
        return;
    }

    if (query.validate(query.id('tipoprestamo').value, "string")) {
        query.toast("Campos vacíos", "Ingrese el nombre del tipo de prestamo", "warning");
        return;
    }

    if (query.validate(query.id('porcentaje').value, "string")) {
        query.toast("Campos vacíos", "Ingrese el porcentaje del tipo de prestamo", "warning");
        return;
    }

    //mandando via ajax los registros
    if (!query.validate(accion, "string")) {
        formData = new FormData(this);
        formData.append("accion", accion);
        query.ajax({
            url: "ServletTipoPrestamo",
            content: "application/x-www-form-urlencoded",
            method: "post",
            data: formData,
            success: function (data) {
                let {titulo, texto, icono, objeto} = JSON.parse(data);
                query.toast(titulo, texto, icono);
                if (objeto != "null") {
                    listadoTiposPrestamos();
                    $("#modal-tipoprestamo").modal("hide");
                }
            },
            fail: function () {
                query.toast("Error", "La petición solicitada ha fallado.", "error");
            }
        });
    } else {
        query.toast("Registro tipo de prestamo", "Los datos del servidor han sido alterados.", "error");
    }
}

