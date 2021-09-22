/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//variables
let buttonModalJuridico = query.id("btn-addjuridico"),
        modalTitle = query.id("titulo-modal"),
        buttonAction = query.id('btn-action'),
        btnAction = query.id('btn-accionbg'),
        btnActionER = query.id('btn-accioner'),
        buttonCancel = query.id('btn-cancel'),
        btnCancel = query.id('btn-cleanbg'),
        btnCancelER = query.id('btn-cleaner');
let contenedor = query.id('listado-juridicos'),
        cuerpomodal = query.id('cuerpo-modal');

let tituloEliminar = query.id('titulo-eliminar'),
        botonEliminar = query.id('mbtn-eliminar');

let formularioJuridico = query.id('formJuridico');

//para el estado y balance
let contenedorBalance = query.id('listadoBalance'), contenedorEstado = query.id('listadoEstado');

let idJuridico = 0, idBalance = 0, idEstado = 0;
let formBalance = query.id('formBalance'),
        formResultado = query.id('formEstado');

//llamado de las funciones
listadoJuridicos();

function listadoJuridicos() {
    query.get({
        url: "ajax/juridico/listadojuridicos.jsp",
        method: 'get',
        success: function (data) {
            contenedor.innerHTML = data;
        },
        fail: function () {
            query.toast("Error", "El listado de fiadores no se puede visualizar.", "error");
        }
    });
}

function listadoBGER(data, pagina, elemento) {
    query.get({
        url: "ajax/juridico/" + pagina + ".jsp",
        method: 'get',
        data: data,
        success: function (data) {
            elemento.innerHTML = data;
        },
        fail: function () {
            query.toast("Error", "La información no se puede visualizar.", "error");
        }
    });
}

function recuperarCuerpoModal(id) {
    let data = {
        id: id
    }
    query.get({
        url: "ajax/juridico/modaljuridico.jsp",
        method: 'get',
        data: data,
        success: function (data) {
            cuerpomodal.innerHTML = data;
            $("#modal-cjuridico").modal("show");
        },
        fail: function () {
            query.toast("Error", "El formulario no se puede visualizar.", "error");
        }
    });
}

function iconoModalBoton(tittle) {
    if (tittle.textContent == "Nuevo Cliente Jurídico") {
        if (buttonAction.childNodes[0].classList.contains("mdi-border-color")) {
            buttonAction.childNodes[0].classList.remove("mdi-border-color");
        }
        buttonAction.childNodes[0].classList.add("mdi-content-save");
        buttonAction.childNodes[1].textContent = " Guardar";
    } else if (tittle.textContent == "Modificar Cliente Jurídico") {
        if (buttonAction.childNodes[0].classList.contains("mdi-content-save")) {
            buttonAction.childNodes[0].classList.remove("mdi-content-save");
        }
        buttonAction.childNodes[0].classList.add("mdi-border-color");
        buttonAction.childNodes[1].textContent = " Modificar";
    } else {
        if (tittle.textContent == "Guardar Balance") {
            if (btnAction.childNodes[0].classList.contains("mdi-border-color")) {
                btnAction.childNodes[0].classList.remove("mdi-border-color");
            }
            btnAction.childNodes[0].classList.add("mdi-content-save");
            btnAction.childNodes[1].textContent = " Guardar";
        } else if (tittle.textContent == "Modificar Balance") {
            if (btnAction.childNodes[0].classList.contains("mdi-content-save")) {
                btnAction.childNodes[0].classList.remove("mdi-content-save");
            }
            btnAction.childNodes[0].classList.add("mdi-border-color");
            btnAction.childNodes[1].textContent = " Modificar";
        } else if (tittle.textContent == "Guardar Estado") {
            if (btnActionER.childNodes[0].classList.contains("mdi-border-color")) {
                btnActionER.childNodes[0].classList.remove("mdi-border-color");
            }
            btnActionER.childNodes[0].classList.add("mdi-content-save");
            btnActionER.childNodes[1].textContent = " Guardar";
        } else if (tittle.textContent == "Modificar Estado") {
            if (btnActionER.childNodes[0].classList.contains("mdi-content-save")) {
                btnActionER.childNodes[0].classList.remove("mdi-content-save");
            }
            btnActionER.childNodes[0].classList.add("mdi-border-color");
            btnActionER.childNodes[1].textContent = " Modificar";
        }
    }
}

buttonModalJuridico.onclick = (e) => {
    modalTitle.innerHTML = "Nuevo Cliente Jurídico";
    iconoModalBoton(modalTitle);
    recuperarCuerpoModal(0)
}
buttonCancel.onclick = (e) => {

    $("#modal-cjuridico").modal("hide");
}

btnCancel.onclick = (e) => {
    //idfiador = 0;
    idBalance = 0;
    cleanData();
    modalTitle.innerHTML = "Guardar Balance";
    iconoModalBoton(modalTitle);
    //$("#modal-ineg").modal("hide");
}

btnCancelER.onclick = (e) => {
    idEstado = 0;
    cleanDataER();
    modalTitle.innerHTML = "Guardar Estado";
    iconoModalBoton(modalTitle);
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
        Id = actual.getAttribute("data-idjuridico");
        if (!query.validate(Id, "int") && !query.validate(Id, "numbers")) {
            acciones({id: Id, accion: "editar"});
        } else {
            query.toast("Alteración de información", "Los datos del servidro han sido alterados.", "error");
        }
    } else if (actual.matches('i[id=eliminar]') || actual.classList.contains('btn-danger')) {
        if (actual.nodeName == "BUTTON") {
            actual = actual.firstChild;
        } else {
            actual = actual;
        }
        Id = actual.getAttribute("data-idjuridico");
        if (!query.validate(Id, "int") && !query.validate(Id, "numbers")) {
            tituloEliminar.innerHTML = "Eliminar cliente jurídico";
            $("#modalEliminarFiador").modal("show");
            botonEliminar.onclick = (e) => {
                acciones({id: parseInt(Id), accion: "eliminar"});
            }
        } else {
            query.toast("Alteración de información", "Los datos del servidro han sido alterados.", "error");
        }
    } else if (actual.matches('i[id=balance]') || actual.classList.contains('btn-info')) {
        if (actual.nodeName == "BUTTON") {
            actual = actual.firstChild;
        } else {
            actual = actual;
        }
        Id = actual.getAttribute("data-idjuridico");
        if (!query.validate(Id, "int") && !query.validate(Id, "numbers")) {
            cleanData();
            idJuridico = parseInt(Id);
            listadoBGER({id: idJuridico}, "balance", contenedorBalance, "#modal-balance");
            $("#modal-balance").modal("show");
        } else {
            query.toast("Alteración de información", "Los datos del servidro han sido alterados.", "error");
        }
    } else if (actual.matches('i[id=estado]') || actual.classList.contains('btn-primary')) {
        if (actual.nodeName == "BUTTON") {
            actual = actual.firstChild;
        } else {
            actual = actual;
        }
        Id = actual.getAttribute("data-idjuridico");
        if (!query.validate(Id, "int") && !query.validate(Id, "numbers")) {
            cleanDataER();
            idJuridico = parseInt(Id);
            listadoBGER({id: idJuridico}, "estado", contenedorEstado);
            $("#modal-estado").modal("show");
        } else {
            query.toast("Alteración de información", "Los datos del servidro han sido alterados.", "error");
        }
    }
}

contenedorBalance.onclick = function (e) {
    let actual = e.target;
    let Id = 0;
    let data = {};
    if (actual.matches('i[id=eliminar-balance]')) {
        Id = actual.parentNode.getAttribute("data-idbalance");
        if (!query.validate(Id, "int") && !query.validate(Id, "numbers")) {
            cleanData();
            acciones({id: parseInt(Id), accion: "eliminarBalance"});
        } else {
            query.toast("Alteración de información", "Los datos del servidro han sido alterados.", "error");
        }
    } else if (actual.matches('i[id=editar-balance]')) {
        data = query.getAttributes(actual.parentNode);
        if (!query.validate(data.idbalance, "int") && !query.validate(data.idbalance, "numbers")) {
            data.accion = "editarBalance";
            acciones(data);
        } else {
            query.toast("Alteración de información", "Los datos del servidro han sido alterados.", "error");
        }
    }
}

contenedorEstado.onclick = function (e) {
    let actual = e.target;
    let Id = 0;
    let data = {};
    if (actual.matches('i[id=eliminar-estado]')) {
        Id = actual.parentNode.getAttribute("data-idestado");
        if (!query.validate(Id, "int") && !query.validate(Id, "numbers")) {
            cleanDataER();
            acciones({id: parseInt(Id), accion: "eliminarEstado"});
        } else {
            query.toast("Alteración de información", "Los datos del servidro han sido alterados.", "error");
        }
    } else if (actual.matches('i[id=editar-estado]')) {
        data = query.getAttributes(actual.parentNode);
        if (!query.validate(data.idestado, "int") && !query.validate(data.idestado, "numbers")) {
            data.accion = "editarEstado";
            acciones(data);
        } else {
            query.toast("Alteración de información", "Los datos del servidro han sido alterados.", "error");
        }
    }
}

function acciones(data) {
    if (!query.validate(data.accion, "string")) {
        if (data.accion == "editar") {
            modalTitle.innerHTML = "Modificar Cliente Jurídico";
            iconoModalBoton(modalTitle);
            recuperarCuerpoModal(data.id);
        } else if (data.accion == "eliminar" || data.accion == "eliminarBalance" ||
                data.accion == "eliminarEstado") {
            query.post({
                url: "ServletJuridico",
                content: "application/x-www-form-urlencoded",
                data: data,
                success: function (datos) {
                    let {titulo, texto, icono, objeto} = JSON.parse(datos);
                    query.toast(titulo, texto, icono);
                    if (isNaN(objeto)) {
                        if (objeto != "null") {
                            listadoJuridicos();
                        }
                    } else {
                        if (data.accion == "eliminarBalance") {
                            listadoBGER({id: parseInt(objeto)}, "balance", contenedorBalance);
                        } else {
                            console.log(objeto);
                            listadoBGER({id: parseInt(objeto)}, "estado", contenedorEstado);
                        }
                    }
                },
                fail: function () {
                    query.toast("Error en el servidor", "La petición del servidor ha fallado.", "error");
                }
            });
        } else if (data.accion == "editarBalance") {
            idBalance = parseInt(data.idbalance);
            query.id('cuenta').value = data.cuenta;
            query.id('monto').value = data.monto;
            query.id('anio').value = data.anio;
            if (data.clasificacion === "Activo corriente") {
                query.id('clasificacion').selectedIndex = 1;
            } else if (data.clasificacion === "Activo no corriente") {
                query.id('clasificacion').selectedIndex = 2;
            } else if (data.clasificacion === "Pasivo corriente") {
                query.id('clasificacion').selectedIndex = 3;
            } else if (data.clasificacion === "Pasivo no corriente") {
                query.id('clasificacion').selectedIndex = 4;
            } else if (data.clasificacion === "Patrimonio") {
                query.id('clasificacion').selectedIndex = 5;
            } else {
                query.id('clasificacion').selectedIndex = 0;
            }
            modalTitle.innerHTML = "Modificar Balance";
            iconoModalBoton(modalTitle);
        } else if (data.accion == "editarEstado") {
            idEstado = parseInt(data.idestado);
            console.log(idEstado);
            query.id('cuentaer').value = data.cuenta;
            query.id('montoer').value = data.monto;
            query.id('anioer').value = data.anio;
            modalTitle.innerHTML = "Modificar Estado";
            iconoModalBoton(modalTitle);
        }
    } else {
        query.toast("Alteración de información", "La informacion del servidor ha sido alterada.", "error");
    }
}

formularioJuridico.onsubmit = function (e) {
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

//validación de la información del representante de la entidad
    if (query.validate(query.id('dui').value, "string")) {
        query.toast("Campos vacíos", "Ingrese el DUI del representante", "warning");
        return;
    }

    if (query.validate(query.id('nombre').value, "string")) {
        query.toast("Campos vacíos", "Ingrese el nombre del representante", "warning");
        return;
    }

    if (query.validate(query.id('apellido').value, "string")) {
        query.toast("Campos vacíos", "Ingrese el apellido del representante", "warning");
        return;
    }

    if (!query.id('masculino').checked && !query.id('femenino').checked) {
        query.toast("Campos vacíos", "Seleccione el sexo del representante", "warning");
        return;
    }

    if (query.validate(query.id('fechanacimiento').value, "string")) {
        query.toast("Campos vacíos", "Ingrese la fecha de nacimiento del representante", "warning");
        return;
    }

    if (query.validate(query.id('telefono').value, "string")) {
        query.toast("Campos vacíos", "Ingrese el # de teléfono del representante", "warning");
        return;
    }

    if (query.validate(query.id('direccion').value, "string")) {
        query.toast("Campos vacíos", "Ingrese la dirección del representante", "warning");
        return;
    }

    if (query.validate(query.id('estadocivil').value, "string")) {
        query.toast("Campos vacíos", "Seleccione el estado civil del representante", "warning");
        return;
    }

//validación de los datos de la empresa
    if (query.validate(query.id('codigo').value, "string")) {
        query.toast("Campos vacíos", "Ingrese el código de la institución", "warning");
        return;
    }

    if (query.validate(query.id('empresa').value, "string")) {
        query.toast("Campos vacíos", "Ingrese el nombre de la empresa", "warning");
        return;
    }

    if (query.validate(query.id('telefonoempresa').value, "string")) {
        query.toast("Campos vacíos", "Ingrese el # de télefono de la empresa", "warning");
        return;
    }

    if (query.validate(query.id('direccionempresa').value, "string")) {
        query.toast("Campos vacíos", "Ingrese la dirección de la empresa", "warning");
        return;
    }

    //mandando via ajax los registros
    if (!query.validate(accion, "string")) {
        formData = new FormData(this);
        formData.append("accion", accion);
        formData.append("tipo", "Juridico");
        query.ajax({
            url: "ServletJuridico",
            content: "application/x-www-form-urlencoded",
            method: "post",
            data: formData,
            success: function (data) {
                let {texto, icono, objeto} = JSON.parse(data);
                query.toast("Registrar cliente jurídico", texto, icono);
                if (objeto != "null") {
                    listadoJuridicos();
                    $("#modal-cjuridico").modal("hide");
                }
            },
            fail: function () {
                query.toast("Error", "La petición solicitada ha fallado.", "error");
            }
        });
    } else {
        query.toast("Registro de fiador", "Los datos del servidor han sido alterados.", "error");
    }
}

formBalance.onsubmit = function (e) {
    e.preventDefault();
    let accion = "";
    let formData;
    let anioer = query.id('anio').value;
    let factual = new Date();
    if (idBalance == 0) {
        accion = "guardarBalance";
    } else {
        accion = "editarBalance";
    }

//validación de  todos los campos
    if (query.emptyElements(this)) {
        query.toast("Campos vacíos", "Complete la información solicitada en el formulario.", "warning");
        return;
    }

//validación de la información del representante de la entidad
    if (query.validate(query.id('cuenta').value, "string")) {
        query.toast("Campos vacíos", "Ingrese el nombre de la cuenta", "warning");
        return;
    }

    if (query.validate(query.id('monto').value, "string")) {
        query.toast("Campos vacíos", "Ingrese el monto de la cuenta", "warning");
        return;
    }
    
    if (isNaN(query.id('monto').value) || parseFloat(query.id('monto').value) <= 0) {
        query.toast("Monto incorrecto", "El monto ingresado de la cuenta es incorrecto.", "warning");
        return;
    }

    if (query.validate(query.id('clasificacion').value, "string")) {
        query.toast("Campos vacíos", "Ingrese la clasificación de la cuenta", "warning");
        return;
    }

    if (parseInt(anioer) > parseInt(factual.getFullYear())) {
        query.toast("Año incorrecto", "El año ingresado supera al año actual.", "warning");
        return;
    }

    //mandando via ajax los registros
    if (!query.validate(accion, "string")) {
        formData = new FormData(this);
        formData.append("accion", accion);
        formData.append("idjuridico", idJuridico);
        if (idBalance > 0) {
            formData.append("id", idBalance);
        }
        query.ajax({
            url: "ServletJuridico",
            content: "application/x-www-form-urlencoded",
            method: "post",
            data: formData,
            success: function (data) {
                let {titulo, texto, icono, objeto} = JSON.parse(data);
                if (icono === "success") {
                    cleanData();
                }
                query.toast(titulo, texto, icono);
                if (!isNaN(objeto)) {
                    listadoBGER({id: parseInt(objeto)}, "balance", contenedorBalance);
                }
            },
            fail: function () {
                query.toast("Error", "La petición solicitada ha fallado.", "error");
            }
        });
    } else {
        query.toast("Registro de fiador", "Los datos del servidor han sido alterados.", "error");
    }
}


function cleanData() {
    idBalance = 0;
    query.id('cuenta').value = "";
    query.id('monto').value = "";
    query.id('anio').value = "";
    query.id('clasificacion').selectedIndex = 0;
    modalTitle.innerHTML = "Guardar Balance";
    iconoModalBoton(modalTitle);
}

formResultado.onsubmit = function (e) {
    e.preventDefault();
    let accion = "";
    let formData;
    let anioer = query.id('anioer').value;
    let factual = new Date();
    if (idEstado == 0) {
        accion = "guardarEstado";
    } else {
        accion = "editarEstado";
    }

//validación de  todos los campos
    if (query.emptyElements(this)) {
        query.toast("Campos vacíos", "Complete la información solicitada en el formulario.", "warning");
        return;
    }

//validación de la información del representante de la entidad
    if (query.validate(query.id('cuentaer').value, "string")) {
        query.toast("Campos vacíos", "Ingrese el nombre de la cuenta", "warning");
        return;
    }

    if (query.validate(query.id('montoer').value, "string")) {
        query.toast("Campos vacíos", "Ingrese el monto de la cuenta", "warning");
        return;
    }

    if (isNaN(query.id('montoer').value) || parseFloat(query.id('montoer').value) <= 0) {
        query.toast("Monto incorrecto", "El monto ingresado de la cuenta es incorrecto.", "warning");
        return;
    }

    if (query.validate(query.id('anioer').value, "string")) {
        query.toast("Campos vacíos", "Ingrese el año a registrar la cuenta.", "warning");
        return;
    }

    if (parseInt(anioer) > parseInt(factual.getFullYear())) {
        query.toast("Año incorrecto", "El año ingresado supera al año actual.", "warning");
        return;
    }
    //mandando via ajax los registros
    if (!query.validate(accion, "string")) {
        formData = new FormData(this);
        formData.append("accion", accion);
        formData.append("idjuridico", idJuridico);
        if (idEstado > 0) {
            formData.append("id", idEstado);
        }
        query.ajax({
            url: "ServletJuridico",
            content: "application/x-www-form-urlencoded",
            method: "post",
            data: formData,
            success: function (data) {
                let {titulo, texto, icono, objeto} = JSON.parse(data);
                if (icono === "success") {
                    cleanDataER();
                }
                query.toast(titulo, texto, icono);
                if (!isNaN(objeto)) {
                    listadoBGER({id: parseInt(objeto)}, "estado", contenedorEstado);
                }
            },
            fail: function () {
                query.toast("Error", "La petición solicitada ha fallado.", "error");
            }
        });
    } else {
        query.toast("Registro de fiador", "Los datos del servidor han sido alterados.", "error");
    }
}

function cleanDataER() {
    idEstado = 0;
    query.id('cuentaer').value = "";
    query.id('montoer').value = "";
    query.id('anioer').value = "";
    modalTitle.innerHTML = "Guardar Estado";
    iconoModalBoton(modalTitle);
}

$(document).ready(function () {
    $('#anioer').datepicker({
        format: " yyyy",
        viewMode: "years",
        minViewMode: "years"
    });
    $('#anio').datepicker({
        format: " yyyy",
        viewMode: "years",
        minViewMode: "years"
    });
});