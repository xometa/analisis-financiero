/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//variables
let buttonModalDepartamento = query.id("btn-adddepartamento"),
        modalTitle = query.id("tittle-modal"),
        buttonAction = query.id('btn-action'),
        buttonCancel = query.id('btn-cancel');
let contenedor = query.id('listado-departamentos'),
        cuerpomodal = query.id('cuerpo-modal');

let tituloEliminar = query.id('titulo-eliminar'),
        botonEliminar = query.id('mbtn-eliminar');

let formDepartamento = query.id('formDepartamento');

listadoDepartamento();

function listadoDepartamento() {
    query.get({
        url: "ajax/departamento/listadodepartamento.jsp",
        method: 'get',
        success: function (data) {
            contenedor.innerHTML = data;
        },
        fail: function () {
            query.toast("Error", "El listado de departamentos no se puede visualizar.", "error");
        }
    });
}

function recuperarCuerpoModal(id) {
    let data = {
        id: id
    }
    query.get({
        url: "ajax/departamento/modaldepartamento.jsp",
        method: 'get',
        data: data,
        success: function (data) {
            cuerpomodal.innerHTML = data;
            $("#modal-departamento").modal("show");
        },
        fail: function () {
            query.toast("Error", "El formulario no se puede visualizar.", "error");
        }
    });
}

function iconoModalBoton(tittle) {
    if (tittle.textContent == "Nuevo departamento") {
        if (buttonAction.childNodes[0].classList.contains("mdi-border-color")) {
            buttonAction.childNodes[0].classList.remove("mdi-border-color");
        }
        buttonAction.childNodes[0].classList.add("mdi-content-save");
        buttonAction.childNodes[1].textContent = " Guardar";
    } else if (tittle.textContent == "Modificar departamento") {
        if (buttonAction.childNodes[0].classList.contains("mdi-content-save")) {
            buttonAction.childNodes[0].classList.remove("mdi-content-save");
        }
        buttonAction.childNodes[0].classList.add("mdi-border-color");
        buttonAction.childNodes[1].textContent = " Modificar";
    }
}

buttonModalDepartamento.onclick = (e) => {
    modalTitle.innerHTML = "Nuevo departamento";
    iconoModalBoton(modalTitle);
    recuperarCuerpoModal(0)
}

buttonCancel.onclick = (e) => {
    $("#modal-departamento").modal("hide");
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
        Id = actual.getAttribute("data-iddepartamento");
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
        Id = actual.getAttribute("data-iddepartamento");
        if (!query.validate(Id, "int") && !query.validate(Id, "numbers")) {
            tituloEliminar.innerHTML = "Eliminar departamento";
            $("#modalEliminarDepartamento").modal("show");
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
            modalTitle.innerHTML = "Modificar departamento";
            iconoModalBoton(modalTitle);
            recuperarCuerpoModal(Id);
        } else if (accion == "eliminar") {
            query.post({
                url: "ServletDepartamento",
                content: "application/x-www-form-urlencoded",
                data: {id: Id, accion: "eliminar"},
                success: function (data) {
                    let {texto, icono, objeto} = JSON.parse(data);
                    query.toast("Eliminación de departamento", texto, icono);
                    if (objeto != null) {
                        listadoDepartamento();
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

formDepartamento.onsubmit = function (e) {
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
        query.toast("Campos vacíos", "Ingrese el código del departamento", "warning");
        return;
    }

    if (query.validate(query.id('nombre').value, "string")) {
        query.toast("Campos vacíos", "Ingrese el nombre del departamento", "warning");
        return;
    }

    if (!query.validate(accion, "string")) {
        formData = new FormData(this);
        formData.append("accion", accion);
        query.ajax({
            url: "ServletDepartamento",
            content: "application/x-www-form-urlencoded",
            method: "post",
            data: formData,
            success: function (data) {
                let {texto, icono, objeto} = JSON.parse(data);
                query.toast("Registrar departamento", texto, icono);
                if (objeto != "null") {
                    listadoDepartamento();
                    $("#modal-departamento").modal("hide");
                }
            },
            fail: function () {
                query.toast("Error", "La petición solicitada ha fallado.", "error");
            }
        });
    } else {
        query.toast("Registro de departamento", "Los datos del servidor han sido alterados.", "error");
    }
}
