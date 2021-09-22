/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//variables
let buttonModalClient = query.id("btn-addfiador"),
        modalTitle = query.id("tittle-modal"),
        buttonAction = query.id('btn-action'),
        btnDisponibilidad = query.id('btn-acciondispo'),
        buttonCancel = query.id('btn-cancel'),
        btnCancel = query.id('btn-cleanineg'),
        tituloDispo = query.id('titulo-dispo');

let contenedor = query.id('listado-fiadores'),
        cuerpomodal = query.id('cuerpo-modal');

let tituloEliminar = query.id('titulo-eliminar'),
        botonEliminar = query.id('mbtn-eliminar');

let formularioFiador = query.id('formFiador'),
        formIngresos = query.id('formIng');

let contenedorIngresos = query.id('listado-ingresos'),
        titulotabla = query.id('titulo-tabla'),
        contenedorDiferencia = query.id('diferenciaInEg');

let idfiador = 0, iddispo = 0, tipoSeleccionado = "";

//llamado de las funciones
listadoFiadores();

function listadoFiadores() {
    query.get({
        url: "ajax/clientenatural/listadoclientesnaturales.jsp",
        method: 'get',
        success: function (data) {
            contenedor.innerHTML = data;
        },
        fail: function () {
            query.toast("Error", "El listado de clientes naturales no se puede visualizar.", "error");
        }
    });
}

function listadoIngresos(data) {
    console.log(data);
    query.get({
        url: "ajax/clientenatural/ingresos.jsp",
        method: 'get',
        data: data,
        success: function (data) {
            contenedorIngresos.innerHTML = data;
        },
        fail: function () {
            query.toast("Error", "El listado de ingresos no se puede visualizar.", "error");
        }
    });
}

function listadoEgresos(data) {
    query.get({
        url: "ajax/clientenatural/egresos.jsp",
        method: 'get',
        data: data,
        success: function (data) {
            contenedorIngresos.innerHTML = data;
        },
        fail: function () {
            query.toast("Error", "El listado de egresos no se puede visualizar.", "error");
        }
    });
}

function listadoDiferencia(data) {
    query.get({
        url: "ajax/clientenatural/diferencia.jsp",
        method: 'get',
        data: data,
        success: function (data) {
            contenedorDiferencia.innerHTML = data;
        },
        fail: function () {
            query.toast("Error", "El cálculo de la diferencia entre ingresos y egresos no se puede visualizar.", "error");
        }
    });
}

function recuperarCuerpoModal(id) {
    let data = {
        id: id
    }
    query.get({
        url: "ajax/clientenatural/modalclientenatural.jsp",
        method: 'get',
        data: data,
        success: function (data) {
            cuerpomodal.innerHTML = data;
            $("#modal-fiador").modal("show");
        },
        fail: function () {
            query.toast("Error", "El formulario no se puede visualizar.", "error");
        }
    });
}

function iconoModalBoton(tittle) {
    if (tittle.textContent == "Nuevo Cliente Natural") {
        if (buttonAction.childNodes[0].classList.contains("mdi-border-color")) {
            buttonAction.childNodes[0].classList.remove("mdi-border-color");
        }
        buttonAction.childNodes[0].classList.add("mdi-content-save");
        buttonAction.childNodes[1].textContent = " Guardar";
    } else if (tittle.textContent == "Modificar Cliente Natural") {
        if (buttonAction.childNodes[0].classList.contains("mdi-content-save")) {
            buttonAction.childNodes[0].classList.remove("mdi-content-save");
        }
        buttonAction.childNodes[0].classList.add("mdi-border-color");
        buttonAction.childNodes[1].textContent = " Modificar";
    } else {
        if (tittle.textContent == "Nuevo Ingreso" || tittle.textContent == "Nuevo Egreso") {
            if (btnDisponibilidad.childNodes[0].classList.contains("mdi-border-color")) {
                btnDisponibilidad.childNodes[0].classList.remove("mdi-border-color");
            }
            btnDisponibilidad.childNodes[0].classList.add("mdi-content-save");
            btnDisponibilidad.childNodes[1].textContent = " Guardar";
        } else if (tittle.textContent == "Modificar Ingreso" || tittle.textContent == "Modificar Egreso") {
            if (btnDisponibilidad.childNodes[0].classList.contains("mdi-content-save")) {
                btnDisponibilidad.childNodes[0].classList.remove("mdi-content-save");
            }
            btnDisponibilidad.childNodes[0].classList.add("mdi-border-color");
            btnDisponibilidad.childNodes[1].textContent = " Modificar";
        }
    }
}

buttonModalClient.onclick = (e) => {
    modalTitle.innerHTML = "Nuevo Cliente Natural";
    iconoModalBoton(modalTitle);
    recuperarCuerpoModal(0)
}
buttonCancel.onclick = (e) => {

    $("#modal-fiador").modal("hide");
}

btnCancel.onclick = (e) => {
//idfiador = 0;
    iddispo = 0;
    cleanData();
    modalTitle.innerHTML = "Guardar Disponibilidad";
    iconoModalBoton(modalTitle);
    //$("#modal-ineg").modal("hide");
}

//para editar y eliminar un registro
contenedor.onclick = function (e) {
    let actual = e.target;
    let Id = 0;
    if (actual.matches('i[id=editar]') || actual.classList.contains('btn-warning')) {
        if (actual.nodeName == "BUTTON") {
            actual = actual.firstChild;
        } else {
            actual = actual;
        }
        Id = actual.getAttribute("data-idfiador");
        if (!query.validate(Id, "int") && !query.validate(Id, "numbers")) {
            acciones({id: parseInt(Id), accion: "editar"})
        } else {
            query.toast("Alteración de información", "Los datos del servidro han sido alterados.", "error");
        }
    } else if (actual.matches('i[id=eliminar]') || actual.classList.contains('btn-danger')) {
        if (actual.nodeName == "BUTTON") {
            actual = actual.firstChild;
        } else {
            actual = actual;
        }
        Id = actual.getAttribute("data-idfiador");
        if (!query.validate(Id, "int") && !query.validate(Id, "numbers")) {
            tituloEliminar.innerHTML = "Eliminar Cliente Natural";
            $("#modalEliminarFiador").modal("show");
            botonEliminar.onclick = (e) => {
                acciones({id: parseInt(Id), accion: "eliminar"});
            }
        } else {
            query.toast("Alteración de información", "Los datos del servidro han sido alterados.", "error");
        }
    } else if (actual.matches('i[id=ing]') || actual.classList.contains('btn-info')) {
        if (actual.nodeName == "BUTTON") {
            actual = actual;
        } else {
            actual = actual.parentElement;
        }
        Id = actual.getAttribute("data-idfiador");
        if (!query.validate(Id, "int") && !query.validate(Id, "numbers")) {
            cleanData();
            idfiador = parseInt(Id);
            tipoSeleccionado = "Ingreso";
            titulotabla.innerHTML = "Listado de ingresos";
            tituloDispo.innerHTML = "Nuevo Ingreso";
            listadoIngresos({id: idfiador});
            $("#modal-in").modal("show");
        } else {
            query.toast("Alteración de información", "Los datos del servidro han sido alterados.", "error");
        }
    } else if (actual.matches('i[id=egr]') || actual.classList.contains('btn-primary')) {
        if (actual.nodeName == "BUTTON") {
            actual = actual;
        } else {
            actual = actual.parentElement;
        }
        Id = actual.getAttribute("data-idfiador");
        if (!query.validate(Id, "int") && !query.validate(Id, "numbers")) {
            cleanData();
            idfiador = parseInt(Id);
            tipoSeleccionado = "Egreso";
            titulotabla.innerHTML = "Listado de egresos";
            tituloDispo.innerHTML = "Nuevo Egreso";
            listadoEgresos({id: idfiador});
            $("#modal-in").modal("show");
        } else {
            query.toast("Alteración de información", "Los datos del servidro han sido alterados.", "error");
        }
    } else if (actual.matches('i[id=ver]') || actual.classList.contains('btn-secondary')) {
        if (actual.nodeName == "BUTTON") {
            actual = actual;
        } else {
            actual = actual.parentElement;
        }
        Id = actual.getAttribute("data-idfiador");
        if (!query.validate(Id, "int") && !query.validate(Id, "numbers")) {
            listadoDiferencia({id: parseInt(Id)});
            $("#modal-ingresosegresos").modal("show");
        } else {
            query.toast("Alteración de información", "Los datos del servidro han sido alterados.", "error");
        }
    }
}

contenedorIngresos.onclick = function (e) {
    let actual = e.target;
    let Id = 0;
    let data = {};
    if (actual.matches('i[id=eliminar-ingreso]')) {
        Id = actual.parentNode.getAttribute("data-id");
        if (!query.validate(Id, "int") && !query.validate(Id, "numbers")) {
            acciones({iddisponibilidad: parseInt(Id), tipo: "Ingreso", accion: "eliminarDisponibilidad"});
        } else {
            query.toast("Alteración de información", "Los datos del servidro han sido alterados.", "error");
        }
    } else if (actual.matches('i[id=editar-ingreso]')) {
        data = query.getAttributes(actual.parentNode);
        if (!query.validate(data.id, "int") && !query.validate(data.id, "numbers")) {
            data.accion = "editarDisponibilidad";
            acciones(data);
        } else {
            query.toast("Alteración de información", "Los datos del servidro han sido alterados.", "error");
        }
    } else if (actual.matches('i[id=eliminar-egreso]')) {
        Id = actual.parentNode.getAttribute("data-id");
        if (!query.validate(Id, "int") && !query.validate(Id, "numbers")) {
            acciones({iddisponibilidad: parseInt(Id), tipo: "Egreso", accion: "eliminarDisponibilidad"});
        } else {
            query.toast("Alteración de información", "Los datos del servidro han sido alterados.", "error");
        }
    } else if (actual.matches('i[id=editar-egreso]')) {
        data = query.getAttributes(actual.parentNode);
        if (!query.validate(data.id, "int") && !query.validate(data.id, "numbers")) {
            data.accion = "editarDisponibilidad";
            acciones(data);
        } else {
            query.toast("Alteración de información", "Los datos del servidro han sido alterados.", "error");
        }
    }
}

function acciones(data) {
    if (!query.validate(data.accion, "string")) {
        if (data.accion == "editar") {
            modalTitle.innerHTML = "Modificar Cliente Natural";
            iconoModalBoton(modalTitle);
            recuperarCuerpoModal(data.id);
        } else if (data.accion == "eliminar" || data.accion === "eliminarDisponibilidad") {
            query.post({
                url: "ServletPersonaNatural",
                content: "application/x-www-form-urlencoded",
                data: data,
                success: function (datos) {
                    let {titulo, texto, icono, objeto} = JSON.parse(datos);
                    query.toast(titulo, texto, icono);
                    if (objeto != null) {
                        if (!isNaN(objeto)) {
                            if (data.tipo === "Ingreso") {
                                listadoIngresos({id: parseInt(objeto)});
                            } else {
                                listadoEgresos({id: parseInt(objeto)});
                            }
                        } else {
                            listadoFiadores();
                        }
                    }
                },
                fail: function () {
                    query.toast("Error en el servidor", "La petición del servidor ha fallado.", "error");
                }
            });
        } else if (data.accion == "editarDisponibilidad") {
            iddispo = parseInt(data.id);
            query.id('descripcion').value = data.descripcion;
            query.id('monto').value = data.monto;
            if (tipoSeleccionado === "Ingreso") {
                tituloDispo.innerHTML = "Modificar Ingreso";
            } else {
                tituloDispo.innerHTML = "Modificar Egreso";
            }
            iconoModalBoton(tituloDispo);
        }
    } else {
        query.toast("Alteración de información", "La informacion del servidor ha sido alterada.", "error");
    }
}

formularioFiador.onsubmit = function (e) {
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
        query.toast("Campos vacíos", "Ingrese el código del cliente natural", "warning");
        return;
    }

    if (query.validate(query.id('dui').value, "string")) {
        query.toast("Campos vacíos", "Ingrese el DUI del cliente natural", "warning");
        return;
    }

    if (query.validate(query.id('nombre').value, "string")) {
        query.toast("Campos vacíos", "Ingrese el nombre del cliente natural", "warning");
        return;
    }

    if (query.validate(query.id('apellido').value, "string")) {
        query.toast("Campos vacíos", "Ingrese el apellido del cliente natural", "warning");
        return;
    }

    if (!query.id('masculino').checked && !query.id('femenino').checked) {
        query.toast("Campos vacíos", "Seleccione el sexo del cliente natural", "warning");
        return;
    }

    if (query.validate(query.id('fechanacimiento').value, "string")) {
        query.toast("Campos vacíos", "Ingrese la fecha de nacimiento del cliente natural", "warning");
        return;
    }else {
        var fechaActual = new Date();
        var anioActual = fechaActual.getFullYear();
        var x = document.getElementById("fechanacimiento");
        let fecha = new Date(x.value);
        var anioNacim = fecha.getFullYear();
        if (anioActual - anioNacim < 18) {
            query.toast("Fecha no valida", "Esta persona no es mayor de edad", "warning");
            return;
        }
    }

    if (query.validate(query.id('telefono').value, "string")) {
        query.toast("Campos vacíos", "Ingrese el # de teléfono del cliente natural", "warning");
        return;
    }

    if (query.validate(query.id('direccion').value, "string")) {
        query.toast("Campos vacíos", "Ingrese la dirección del cliente natural", "warning");
        return;
    }

    if (query.validate(query.id('estadocivil').value, "string")) {
        query.toast("Campos vacíos", "Seleccione el estado civil del cliente natural", "warning");
        return;
    }

    if (query.validate(query.id('lugartrabajo').value, "string")) {
        query.toast("Campos vacíos", "Ingrese el lugar de trabajo del cliente natural", "warning");
        return;
    }

//mandando via ajax los registros
    if (!query.validate(accion, "string")) {
        formData = new FormData(this);
        formData.append("accion", accion);
        formData.append("tipo", "Natural");
        query.ajax({
            url: "ServletPersonaNatural",
            content: "application/x-www-form-urlencoded",
            method: "post",
            data: formData,
            success: function (data) {
                let {texto, icono, objeto} = JSON.parse(data);
                query.toast("Registrar cliente natural", texto, icono);
                console.log(objeto);
                if (objeto != "null") {
                    listadoFiadores("ajax/clientenatural/listadoclientesnaturales.jsp", null, contenedor);
                    $("#modal-fiador").modal("hide");
                }
            },
            fail: function () {
                query.toast("Error", "La petición solicitada ha fallado.", "error");
            }
        });
    } else {
        query.toast("Registro de Cliente Natural", "Los datos del servidor han sido alterados.", "error");
    }
}

formIngresos.onsubmit = function (e) {
    e.preventDefault();
    let accion = "";
    let formData;
    if (iddispo == 0) {
        accion = "guardarDisponibilidad";
    } else {
        accion = "editarDisponibilidad";
    }

    if (query.emptyElements(this)) {
        query.toast("Campos vacíos", "Complete la información solicitada en el formulario.", "warning");
        return;
    }

    if (query.validate(query.id('descripcion').value, "string")) {
        query.toast("Campos vacíos", "Ingrese una descripción", "warning");
        return;
    }

    if (query.validate(query.id('monto').value, "string")) {
        query.toast("Campos vacíos", "Ingrese el monto de la descripción", "warning");
        return;
    }

    if (query.validate(tipoSeleccionado, "string")) {
        query.toast("Campos vacíos", "Seleccione el tipo de operación a registrar", "warning");
        return;
    }

    if (!query.validate(accion, "string")) {
        formData = new FormData(this);
        formData.append("accion", accion);
        formData.append("idfiador", idfiador);
        formData.append("tipo", tipoSeleccionado);
        if (iddispo > 0) {
            formData.append("id", iddispo);
        }
        query.ajax({
            url: "ServletPersonaNatural",
            content: "application/x-www-form-urlencoded",
            method: "post",
            data: formData,
            success: function (data) {
                let {titulo, texto, icono, objeto} = JSON.parse(data);
                query.toast(titulo, texto, icono);
                if (!isNaN(objeto)) {
                    if (tipoSeleccionado === "Ingreso") {
                        listadoIngresos({id: parseInt(objeto)});
                    } else {
                        listadoEgresos({id: parseInt(objeto)});
                    }
                    cleanData();
                }
            },
            fail: function () {
                query.toast("Error", "La petición solicitada ha fallado.", "error");
            }
        });
    } else {
        query.toast("Registro de Cliente Natural", "Los datos del servidor han sido alterados.", "error");
    }
}

function cleanData() {
    iddispo = 0;
    query.id('descripcion').value = "";
    query.id('monto').value = "";
    if (tipoSeleccionado === "Ingreso") {
        tituloDispo.innerHTML = "Nuevo Ingreso";
    } else {
        tituloDispo.innerHTML = "Nuevo Egreso";
    }
    iconoModalBoton(tituloDispo);
}