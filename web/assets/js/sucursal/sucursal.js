/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

let buttonModalSucursal = query.id("btn-addsucursal"),
        modalTitle = query.id("tittle-modal"),
        buttonAction = query.id('btn-action'),
        buttonCancel = query.id('btn-cancel');
let contenedor = query.id('listado-sucursales'),
        cuerpomodal = query.id('cuerpo-modal');

let tituloEliminar = query.id('titulo-eliminar'),
        botonEliminar = query.id('mbtn-eliminar');

let formSucursal = query.id('formSucursal');

//listado de clientes naturales y juridicos
let contenedorCliente = query.id('listado-clientes');

listadoSucursal();

function listadoSucursal() {
    query.get({
        url: "ajax/sucursal/listadosucursal.jsp",
        method: 'get',
        success: function (data) {
            contenedor.innerHTML = data;
        },
        fail: function () {
            query.toast("Error", "El listado de las sucursales no se puede visualizar.", "error");
        }
    });
}

function listadoClientes(id) {
    query.get({
        url: "ajax/sucursal/listadoclientessucursal.jsp",
        method: 'get',
        data: {id: parseInt(id)},
        success: function (data) {
            contenedorCliente.innerHTML = data;
            $("#modal-clientes").modal("show");
        },
        fail: function () {
            query.toast("Error", "El listado de los clientes no se puede visualizar.", "error");
        }
    });
}
function recuperarCuerpoModal(id) {
    let data = {
        id: id
    }
    query.get({
        url: "ajax/sucursal/modalsucursal.jsp",
        method: 'get',
        data: data,
        success: function (data) {
            cuerpomodal.innerHTML = data;
            $("#modal-sucursal").modal("show");
        },
        fail: function () {
            query.toast("Error", "El formulario no se puede visualizar.", "error");
        }
    });
}

function iconoModalBoton(tittle) {
    if (tittle.textContent == "Nueva sucursal") {
        if (buttonAction.childNodes[0].classList.contains("mdi-border-color")) {
            buttonAction.childNodes[0].classList.remove("mdi-border-color");
        }
        buttonAction.childNodes[0].classList.add("mdi-content-save");
        buttonAction.childNodes[1].textContent = " Guardar";
    } else if (tittle.textContent == "Modificar sucursal") {
        if (buttonAction.childNodes[0].classList.contains("mdi-content-save")) {
            buttonAction.childNodes[0].classList.remove("mdi-content-save");
        }
        buttonAction.childNodes[0].classList.add("mdi-border-color");
        buttonAction.childNodes[1].textContent = " Modificar";
    }
}

buttonModalSucursal.onclick = (e) => {
    modalTitle.innerHTML = "Nueva sucursal";
    iconoModalBoton(modalTitle);
    recuperarCuerpoModal(0)
}

buttonCancel.onclick = (e) => {
    $("#modal-sucursal").modal("hide");
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
        Id = actual.getAttribute("data-idsucursal");
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
        Id = actual.getAttribute("data-idsucursal");
        if (!query.validate(Id, "int") && !query.validate(Id, "numbers")) {
            tituloEliminar.innerHTML = "Eliminar sucursal";
            $("#modalEliminarSucursal").modal("show");
            botonEliminar.onclick = (e) => {
                acciones(parseInt(Id), "eliminar");
            }
        } else {
            query.toast("Alteración de información", "Los datos del servidor han sido alterados.", "error");
        }
    } else if (actual.matches('span[id=ver]') || actual.classList.contains('btn-info')) {
        if (actual.nodeName == "BUTTON") {
            actual = actual.firstChild;
        } else {
            actual = actual;
        }
        Id = actual.getAttribute("data-idsucursal");
        if (!query.validate(Id, "int") && !query.validate(Id, "numbers")) {
            listadoClientes(parseInt(Id));
        } else {
            query.toast("Alteración de información", "Los datos del servidor han sido alterados.", "error");
        }
    }
}

contenedorCliente.onclick = function (e) {
    let actual = e.target;
    let idcliente = 0, idsucursal = 0, idcartera = 0;
    if (actual.matches('i[id=eliminar-cliente')) {
        idcliente = actual.parentNode.getAttribute("data-idpersona");
        idsucursal = actual.parentNode.getAttribute("data-idsucursal");
        idcartera = actual.parentNode.getAttribute("data-idcartera");
        if (!query.validate(idcliente, "int") && !query.validate(idcliente, "numbers") &&
                !query.validate(idsucursal, "int") && !query.validate(idsucursal, "numbers") &&
                !query.validate(idcartera, "int") && !query.validate(idcartera, "numbers")) {
            query.post({
                url: "ServletCartera",
                content: "application/x-www-form-urlencoded",
                data: {idcliente: idcliente, idcartera: idcartera, idsucursal: idsucursal, accion: "eliminarclientecartera"},
                success: function (data) {
                    let {texto, icono, objeto} = JSON.parse(data);
                    query.toast("Eliminar cliente", texto, icono);
                    if (objeto != null) {
                        if (parseInt(objeto) > 0) {
                            listadoClientes(objeto);
                        } else {
                            $("#modal-clientes").modal("hide");
                            listadoSucursal();
                        }
                    }
                },
                fail: function () {
                    query.toast("Error en el servidor", "La petición del servidor ha fallado.", "error");
                }
            });
        } else {
            query.toast("Alteración de información", "Los datos del servidro han sido alterados.", "error");
        }
    }
}

function acciones(Id, accion) {
    if (!query.validate(accion, "string")) {
        if (accion == "editar") {
            modalTitle.innerHTML = "Modificar sucursal";
            iconoModalBoton(modalTitle);
            recuperarCuerpoModal(Id);
        } else if (accion == "eliminar") {
            query.post({
                url: "ServletSucursal",
                content: "application/x-www-form-urlencoded",
                data: {id: Id, accion: "eliminar"},
                success: function (data) {
                    let {texto, icono, objeto} = JSON.parse(data);
                    query.toast("Eliminación de sucursal", texto, icono);
                    if (objeto != null) {
                        listadoSucursal();
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

formSucursal.onsubmit = function (e) {
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

    if (query.validate(query.id('codigo').value, "string")) {
        query.toast("Campos vacíos", "Ingrese el código de la sucursal", "warning");
        return;
    }

    if (query.validate(query.id('nombre').value, "string")) {
        query.toast("Campos vacíos", "Ingrese el nombre de la sucursal", "warning");
        return;
    }

    if (!query.validate(accion, "string")) {
        formData = new FormData(this);
        formData.append("accion", accion);
        query.ajax({
            url: "ServletSucursal",
            content: "application/x-www-form-urlencoded",
            method: "post",
            data: formData,
            success: function (data) {
                let {texto, icono, objeto} = JSON.parse(data);
                query.toast("Registrar sucursal", texto, icono);
                if (objeto != "null") {
                    listadoSucursal();
                    $("#modal-sucursal").modal("hide");
                }
            },
            fail: function () {
                query.toast("Error", "La petición solicitada ha fallado.", "error");
            }
        });
    } else {
        query.toast("Registro de sucursal", "Los datos del servidor han sido alterados.", "error");
    }
}
