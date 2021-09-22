/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor. estado
 */

//variables
let buttonModalJuridico = query.id("btn-addactivo"),
        modalTitle = query.id("titulo-modal"),
        buttonAction = query.id('btn-action'),
        buttonCancel = query.id('btn-cancel');
let contenedor = query.id('listado-activos'),
        cuerpomodal = query.id('cuerpo-modal');

let tituloEliminar = query.id('titulo-eliminar'),
        botonEliminar = query.id('mbtn-eliminar');

let formActivo = query.id('formActivo');

//para dar de baja a un activo
let btnDarBaja = query.id('btn-darbaja'),
        btnCancelarBajar = query.id('btn-cancelarbaja'),
        formDarBaja = query.id('formDarBaja'),
        tituloDarBaja = query.id('titulo-darbaja'),
        contenedorBaja = query.id('registrarBaja'),
        contenedorInformacion = query.id('verinformacionbaja');

//llamado de las funciones
listadoActivo();

function listadoActivo() {
    query.get({
        url: "ajax/activofijo/listadoactivofijo.jsp",
        method: 'get',
        success: function (data) {
            contenedor.innerHTML = data;
        },
        fail: function () {
            query.toast("Error", "El listado de activos fijos no se puede visualizar.", "error");
        }
    });
}

function recuperarCuerpoModal(id) {
    let data = {
        id: id
    }
    query.get({
        url: "ajax/activofijo/modalactivofijo.jsp",
        method: 'get',
        data: data,
        success: function (data) {
            cuerpomodal.innerHTML = data;
            $("#modal-activofijo").modal("show");
        },
        fail: function () {
            query.toast("Error", "El formulario no se puede visualizar.", "error");
        }
    });
}

function iconoModalBoton(tittle) {
    if (tittle.textContent == "Nuevo Activo Fijo") {
        if (buttonAction.childNodes[0].classList.contains("mdi-border-color")) {
            buttonAction.childNodes[0].classList.remove("mdi-border-color");
        }
        buttonAction.childNodes[0].classList.add("mdi-content-save");
        buttonAction.childNodes[1].textContent = " Guardar";
    } else if (tittle.textContent == "Modificar Activo Fijo") {
        if (buttonAction.childNodes[0].classList.contains("mdi-content-save")) {
            buttonAction.childNodes[0].classList.remove("mdi-content-save");
        }
        buttonAction.childNodes[0].classList.add("mdi-border-color");
        buttonAction.childNodes[1].textContent = " Modificar";
    }
}

buttonModalJuridico.onclick = (e) => {
    modalTitle.innerHTML = "Nuevo Activo Fijo";
    iconoModalBoton(modalTitle);
    recuperarCuerpoModal(0)
}
buttonCancel.onclick = (e) => {

    $("#modal-activofijo").modal("hide");
}

btnCancelarBajar.onclick = (e) => {
    cleanData();
    $("#modal-darbaja").modal("hide");
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
        if (actual.classList.contains("mdi-pencil")) {
            Id = actual.getAttribute("data-idactivo");
            if (!query.validate(Id, "int") && !query.validate(Id, "numbers")) {
                acciones({id: parseInt(Id), accion: "editar"});
            } else {
                query.toast("Alteración de información", "Los datos del servidro han sido alterados.", "error");
            }
        }
    } else if (actual.matches('i[id=eliminar]') || actual.classList.contains('btn-danger')) {
        if (actual.nodeName == "BUTTON") {
            actual = actual.firstChild;
        } else {
            actual = actual;
        }
        Id = actual.getAttribute("data-idactivo");
        if (!query.validate(Id, "int") && !query.validate(Id, "numbers")) {
            tituloEliminar.innerHTML = "Eliminar activo fijo";
            $("#modalEliminarActivo").modal("show");
            botonEliminar.onclick = (e) => {
                acciones({id: parseInt(Id), accion: "eliminar"});
            }
        } else {
            query.toast("Alteración de información", "Los datos del servidro han sido alterados.", "error");
        }
    } else if (actual.matches("input[type=checkbox]")) {
        let estado = 0;
        Id = actual.getAttribute("data-idactivo");
        if (actual.checked) {
            estado = 1;
        } else {
            estado = 0;
        }
        if (!query.validate(Id, "int") && !isNaN(estado) && estado >= 0 && estado <= 1) {
            acciones({id: parseInt(Id), estado: estado, accion: "modificarestado"});
        } else {
            query.toast("Información alterada", "La información del servidor ha sido alterada.", "error");
        }
    } else if (actual.matches('i[id=darbaja]') || actual.classList.contains('btn-primary')) {
        if (actual.nodeName == "BUTTON") {
            actual = actual.firstChild;
        } else {
            actual = actual;
        }
        Id = actual.getAttribute("data-idactivo");
        if (!query.validate(Id, "int") && !query.validate(Id, "numbers")) {
            let nombre = actual.getAttribute("data-nombreactivo");
            cleanData();
            tituloDarBaja.innerHTML = "Dar de baja al activo " + nombre;
            query.id('idactivofijo').value = parseInt(Id);
            ver("Registrarbaja");
            $("#modal-darbaja").modal("show");
        } else {
            query.toast("Alteración de información", "Los datos del servidro han sido alterados.", "error");
        }
    } else if (actual.matches('i[id=verbaja]') || actual.classList.contains('btn-info')) {
        if (actual.nodeName == "BUTTON") {
            actual = actual.firstChild;
        } else {
            actual = actual;
        }
        Id = actual.getAttribute("data-idbaja");
        if (!query.validate(Id, "int") && !query.validate(Id, "numbers")) {
            let nombre = actual.getAttribute("data-nombreactivo");
            cleanData();
            tituloDarBaja.innerHTML = "Información de la baja del activo " + nombre;
            query.id('motivobajaactivo').innerHTML = actual.getAttribute("data-motivo");
            query.id('fechabajaactivo').innerHTML = actual.getAttribute("data-fecha");
            ver("Informacion");
            $("#modal-darbaja").modal("show");
        } else {
            query.toast("Alteración de información", "Los datos del servidro han sido alterados.", "error");
        }
    }
}

function acciones(data) {
    if (!query.validate(data.accion, "string")) {
        if (data.accion == "editar") {
            modalTitle.innerHTML = "Modificar Activo Fijo";
            iconoModalBoton(modalTitle);
            recuperarCuerpoModal(data.id);
        } else if (data.accion === "eliminar" || data.accion === "modificarestado") {
            query.post({
                url: "ServletActivoFijo",
                content: "application/x-www-form-urlencoded",
                data: data,
                success: function (data) {
                    let {titulo, texto, icono, objeto} = JSON.parse(data);
                    query.toast(titulo, texto, icono);
                    if (isNaN(objeto)) {
                        listadoActivo();
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

formActivo.onsubmit = function (e) {
    e.preventDefault();
    let accion = "";
    let formData;
    let fecha_aux = query.id('fechaadquisicion').value.split("-");
    console.log(fecha_aux);
    let fadquisicion = new Date(parseInt(fecha_aux[0]), (parseInt(fecha_aux[1]) - 1), parseInt(fecha_aux[2]));
    let factual = new Date();
    fadquisicion.setHours(0, 0, 0, 0);
    factual.setHours(0, 0, 0, 0)
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
    if (query.validate(query.id('nombre').value, "string")) {
        query.toast("Campos vacíos", "Ingrese el nombre del activo fijo", "warning");
        return;
    }

    if (query.validate(query.id('nombre').value, "string")) {
        query.toast("Campos vacíos", "Ingrese el nombre del representante", "warning");
        return;
    }

    if (query.validate(query.id('descripcion').value, "string")) {
        query.toast("Campos vacíos", "Ingrese la descripción del activo fijo", "warning");
        return;
    }

    if (query.validate(query.id('procedencia').value, "string")) {
        query.toast("Campos vacíos", "Seleccione la procedencia del activo fijo", "warning");
        return;
    }

    if (query.validate(query.id('precio').value, "string")) {
        query.toast("Campos vacíos", "Ingrese el precio del activo fijo", "warning");
        return;
    }

    if (isNaN(query.id('precio').value)) {
        query.toast("Precio inválido", "El precio ingresado es inválido", "warning");
        return;
    }

    if (parseFloat(query.id('precio').value) <= 0) {
        query.toast("Precio inválido", "El precio ingresado debe ser mayor a cero", "warning");
        return;
    }

    if (query.validate(query.id('fechaadquisicion').value, "string")) {
        query.toast("Campos vacíos", "Ingrese la fecha de adquisición del activo fijo", "warning");
        return;
    }

    if (query.validate(query.id('vidautil').value, "int")) {
        query.toast("Campos vacíos", "Ingrese la vida útil del activo fijo", "warning");
        return;
    }

    if (fadquisicion > factual) {
        query.toast("Fecha incorrecta", "La fecha de adquisición del activo fijo, es incorrecta.", "warning");
        return;
    }

    if (!query.validate(query.id("sucursal").value, "string")) {
        if (query.validate(query.id("sucursal").value, "numbers")) {
            query.toast("Advertencia", "La información de la sucursal seleccionada ha sido alterada.", "warning");
            return;
        }
    }

    if (query.validate(query.id("sucursal").value, "int")) {
        query.toast("Campos vacíos", "Seleccione una sucursal.", "warning");
        return;
    }

    if (!query.validate(query.id("departamento").value, "string")) {
        if (query.validate(query.id("departamento").value, "numbers")) {
            query.toast("Advertencia", "La información del departamento seleccionado ha sido alterada.", "warning");
            return;
        }
    }

    if (query.validate(query.id("departamento").value, "int")) {
        query.toast("Campos vacíos", "Seleccione un departamento.", "warning");
        return;
    }

    if (!query.validate(query.id("tipoactivo").value, "string")) {
        if (query.validate(query.id("tipoactivo").value, "numbers")) {
            query.toast("Advertencia", "La información del tipo de activo seleccionado ha sido alterada.", "warning");
            return;
        }
    }

    if (query.validate(query.id("tipoactivo").value, "int")) {
        query.toast("Campos vacíos", "Seleccione un tipo de activo.", "warning");
        return;
    }

    //mandando via ajax los registros
    if (!query.validate(accion, "string")) {
        formData = new FormData(this);
        formData.append("accion", accion);
        query.ajax({
            url: "ServletActivoFijo",
            content: "application/x-www-form-urlencoded",
            method: "post",
            data: formData,
            success: function (data) {
                let {texto, icono, objeto} = JSON.parse(data);
                query.toast("Registrar activo fijo", texto, icono);
                if (objeto != "null") {
                    listadoActivo();
                    $("#modal-activofijo").modal("hide");
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

formDarBaja.onsubmit = function (e) {
    e.preventDefault();
    let accion = "";
    let formData;
    if (!query.validate(query.id("idactivofijo").value, "int") &&
            !query.validate(query.id("idactivofijo").value, "numbers")) {
        accion = "darbajaactivo";
    } else {
        accion = "";
    }

    //validación de  todos los campos
    if (query.emptyElements(this)) {
        query.toast("Campos vacíos", "Complete la información solicitada en el formulario.", "warning");
        return;
    }

    //validación de la información del representante de la entidad
    if (query.validate(query.id('motivo').value, "string")) {
        query.toast("Campos vacíos", "Ingrese el motivo por el cual dara de baja al activo fijo", "warning");
        return;
    }

    if (query.validate(query.id('fecha').value, "string")) {
        query.toast("Campos vacíos", "Ingrese la fecha de baja del activo fijo", "warning");
        return;
    }

    //mandando via ajax los registros
    if (!query.validate(accion, "string")) {
        formData = new FormData(this);
        formData.append("accion", accion);
        formData.append("estado", 0);
        query.ajax({
            url: "ServletActivoFijo",
            content: "application/x-www-form-urlencoded",
            method: "post",
            data: formData,
            success: function (data) {
                let {titulo, texto, icono, objeto} = JSON.parse(data);
                query.toast(titulo, texto, icono);
                if (objeto != "null") {
                    listadoActivo();
                    $("#modal-darbaja").modal("hide");
                }
            },
            fail: function () {
                query.toast("Error", "La petición solicitada ha fallado.", "error");
            }
        });
    } else {
        query.toast("Dar de baja activo fijo", "Los datos del servidor han sido alterados.", "error");
    }
}
function cleanData() {
    query.id('idactivofijo').value = 0;
    query.id('motivo').value = "";
    query.id('fecha').value = "";
}

function ver(opcion) {
    if (opcion === "Informacion") {
        btnDarBaja.style.display = "none";
        if (contenedorBaja.getAttribute('hidden') == null) {
            contenedorBaja.setAttribute('hidden', 'hidden');
        }
        contenedorInformacion.removeAttribute("hidden");
    } else {
        btnDarBaja.style.display = "block";
        if (contenedorInformacion.getAttribute('hidden') == null) {
            contenedorInformacion.setAttribute('hidden', 'hidden');
        }
        contenedorBaja.removeAttribute("hidden");
    }
}

