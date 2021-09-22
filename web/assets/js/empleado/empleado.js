/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor. 
 */

//variables
let buttonModalClientNat = query.id("btn-addempleado"),
        modalTitle = query.id("tittle-modal"),
        buttonAction = query.id('btn-action'),
        buttonCancel = query.id('btn-cancel');
let contenedor = query.id('listado-empleados'),
        cuerpomodal = query.id('cuerpo-modal');

let tituloEliminar = query.id('titulo-eliminar'),
        botonEliminar = query.id('mbtn-eliminar');

let formularioClientNat = query.id('formEmpleado'),
        formUsuario = query.id('formUser');

//para los usuarios
let contenedorUsuario = query.id('data-user'),
        contenedorInfUser = query.id('userinformation'),
        userTitle = query.id('user-title'),
        btnUsuario = query.id('user-button');

//llamado de las funciones
listadoClientesNaturales();

function listadoClientesNaturales() {
    query.get({
        url: "ajax/empleado/listadoempleados.jsp",
        method: 'get',
        success: function (data) {
            contenedor.innerHTML = data;
        },
        fail: function () {
            query.toast("Error", "El listado de empleados no se puede visualizar.", "error");
        }
    });
}

function recuperarCuerpoModal(id) {
    let data = {
        id: id
    }
    query.get({
        url: "ajax/empleado/modalempleado.jsp",
        method: 'get',
        data: data,
        success: function (data) {
            cuerpomodal.innerHTML = data;
            $("#modal-empleado").modal("show");
        },
        fail: function () {
            query.toast("Error", "El formulario no se puede visualizar.", "error");
        }
    });
}

function recuperarCuerpoModalUsuario(id, pagina, modal, contenedor) {
    let data = {
        id: id
    }
    query.get({
        url: "ajax/user/" + pagina + ".jsp",
        method: 'get',
        data: data,
        success: function (data) {
            contenedor.innerHTML = data;
            $(modal).modal("show");
        },
        fail: function () {
            query.toast("Error", "El formulario no se puede visualizar.", "error");
        }
    });
}

function iconoModalBoton(tittle) {
    if (tittle.textContent == "Nuevo Empleado") {
        if (buttonAction.childNodes[0].classList.contains("mdi-border-color")) {
            buttonAction.childNodes[0].classList.remove("mdi-border-color");
        }
        buttonAction.childNodes[0].classList.add("mdi-content-save");
        buttonAction.childNodes[1].textContent = " Guardar";
    } else if (tittle.textContent == "Modificar Empleado") {
        if (buttonAction.childNodes[0].classList.contains("mdi-content-save")) {
            buttonAction.childNodes[0].classList.remove("mdi-content-save");
        }
        buttonAction.childNodes[0].classList.add("mdi-border-color");
        buttonAction.childNodes[1].textContent = " Modificar";
    } else {
        if (tittle.textContent == "Agregar usuario") {
            if (btnUsuario.childNodes[0].classList.contains("mdi-border-color")) {
                btnUsuario.childNodes[0].classList.remove("mdi-border-color");
            }
            btnUsuario.childNodes[0].classList.add("mdi-account-plus");
            btnUsuario.childNodes[1].textContent = " Guardar";
        } else if (tittle.textContent == "Modificar usuario") {
            if (btnUsuario.childNodes[0].classList.contains("mdi-account-plus")) {
                btnUsuario.childNodes[0].classList.remove("mdi-account-plus");
            }
            btnUsuario.childNodes[0].classList.add("mdi-border-color");
            btnUsuario.childNodes[1].textContent = " Modificar";
        }
    }
}

buttonModalClientNat.onclick = (e) => {
    modalTitle.innerHTML = "Nuevo Empleado";
    iconoModalBoton(modalTitle);
    recuperarCuerpoModal(0)
}
buttonCancel.onclick = (e) => {
    $("#modal-empleado").modal("hide");
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
        Id = actual.getAttribute("data-idempleado");
        if (!query.validate(Id, "int") && !query.validate(Id, "numbers")) {
            acciones(Id, "editar")
        } else {
            query.toast("Alteración de información", "Los datos del servidro han sido alterados.", "error");
        }
    } else if (actual.matches('i[id=eliminar]') || actual.classList.contains('btn-danger')) {
        if (actual.nodeName == "BUTTON") {
            actual = actual.firstChild;
        } else {
            actual = actual;
        }
        Id = actual.getAttribute("data-idempleado");
        if (!query.validate(Id, "int") && !query.validate(Id, "numbers")) {
            tituloEliminar.innerHTML = "Eliminar empleado";
            $("#modalEliminarEmpleado").modal("show");
            botonEliminar.onclick = (e) => {
                acciones(parseInt(Id), "eliminar");
            }
        } else {
            query.toast("Alteración de información", "Los datos del servidro han sido alterados.", "error");
        }
    } else if (actual.matches('i[class="mdi mdi-account-edit"]') || actual.classList.contains('btn-secondary')) {
        if (actual.nodeName == "BUTTON") {
            actual = actual;
        } else {
            actual = actual.parentNode;
        }
        Id = actual.getAttribute("data-idempleado");
        if (!query.validate(Id, "int") && !query.validate(Id, "numbers")) {
            userTitle.innerHTML = "Modificar usuario";
            iconoModalBoton(userTitle);
            recuperarCuerpoModalUsuario(Id, "modalusuario", "#modal-user", contenedorUsuario);
        } else {
            query.toast("Alteración de información", "Los datos del servidro han sido alterados.", "error");
        }
    } else if (actual.matches('i[class="mdi mdi-account-plus"]') || actual.classList.contains('btn-primary')) {
        if (actual.nodeName == "BUTTON") {
            actual = actual;
        } else {
            actual = actual.parentNode;
        }
        Id = actual.getAttribute("data-idempleado");
        if (!query.validate(Id, "int") && !query.validate(Id, "numbers")) {
            userTitle.innerHTML = "Agregar usuario";
            iconoModalBoton(userTitle);
            recuperarCuerpoModalUsuario(Id, "modalusuario", "#modal-user", contenedorUsuario);
        } else {
            query.toast("Alteración de información", "Los datos del servidro han sido alterados.", "error");
        }
    } else if (actual.matches('i[class="mdi mdi-eye"]') || actual.classList.contains('btn-info')) {
        if (actual.nodeName == "BUTTON") {
            actual = actual;
        } else {
            actual = actual.parentNode;
        }
        Id = actual.getAttribute("data-idempleado");
        if (!query.validate(Id, "int") && !query.validate(Id, "numbers")) {
            recuperarCuerpoModalUsuario(Id, "infousuario", "#modal-informationuser", contenedorInfUser);
        } else {
            query.toast("Alteración de información", "Los datos del servidro han sido alterados.", "error");
        }
    }
}

contenedorInfUser.onclick = function (e) {
    let actual = e.target;
    let Status = 0;
    let Id = 0;
    if (actual.matches('input[type=checkbox]')) {
        if (actual.tagName == "INPUT") {
            Id = actual.getAttribute("data-idusuario");
            if (actual.checked) {
                Status = 1;
            } else {
                Status = 0;
            }
        }
        if (!query.validate(Id, "int") && !query.validate(Id, "numbers")) {
            query.post({
                url: "ServletUsuario",
                content: "application/x-www-form-urlencoded",
                data: {id: Id, estado: Status, accion: "modificarestado"},
                success: function (data) {
                    let {texto, icono} = JSON.parse(data);
                    query.toast("Modificar estado usuario", texto, icono);
                },
                fail: function () {
                    query.toast("Error en el servidor", "La petición del servidor ha fallado al intentar eliminar.", "error");
                }
            });
        } else {
            query.toast("Alteración de información", "Los datos del servidro han sido alterados.", "error");
        }
    } else if (actual.matches('i[class="mdi mdi-delete"]') || actual.classList.contains('btn-danger')) {
        if (actual.nodeName == "BUTTON") {
            actual = actual;
        } else {
            actual = actual.parentNode;
        }
        Id = actual.getAttribute("data-idusuario");
        if (!query.validate(Id, "int") && !query.validate(Id, "numbers")) {
            query.post({
                url: "ServletUsuario",
                content: "application/x-www-form-urlencoded",
                data: {id: Id, accion: "eliminar"},
                success: function (data) {
                    let {texto, icono, objeto} = JSON.parse(data);
                    query.toast("Eliminación de usuario", texto, icono);
                    if (objeto != null) {
                        listadoClientesNaturales();
                        $("#modal-informationuser").modal("hide");
                    }
                },
                fail: function () {
                    query.toast("Error en el servidor", "La petición del servidor ha fallado al intentar eliminar.", "error");
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
            modalTitle.innerHTML = "Modificar Empleado";
            iconoModalBoton(modalTitle);
            recuperarCuerpoModal(Id);
        } else if (accion == "eliminar") {
            query.post({
                url: "ServletEmpleado",
                content: "application/x-www-form-urlencoded",
                data: {id: Id, accion: "eliminar"},
                success: function (data) {
                    let {texto, icono, objeto} = JSON.parse(data);
                    query.toast("Eliminación de Empleado", texto, icono);
                    if (objeto != null) {
                        listadoClientesNaturales();
                    }
                },
                fail: function () {
                    query.toast("Error en el servidor", "La petición del servidor ha fallado al intentar eliminar.", "error");
                }
            });
        }
    } else {
        query.toast("Alteración de información", "La informacion del servidor ha sido alterada.", "error");
    }
}

formularioClientNat.onsubmit = function (e) {
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
    } else {
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

    //mandando via ajax los registros
    if (!query.validate(accion, "string")) {
        formData = new FormData(this);
        formData.append("accion", accion);
        formData.append("tipo", "Empleado");//tipo de persona
        query.ajax({
            url: "ServletEmpleado",
            content: "application/x-www-form-urlencoded",
            method: "post",
            data: formData,
            success: function (data) {
                let {texto, icono, objeto} = JSON.parse(data);
                query.toast("Registrar empleado", texto, icono);
                console.log(objeto);
                if (objeto != "null") {
                    listadoClientesNaturales();
                    $("#modal-empleado").modal("hide");
                }
            },
            fail: function () {
                query.toast("Error", "La petición solicitada ha fallado.", "error");
            }
        });
    } else {
        query.toast("Registro de Empleado", "Los datos del servidor han sido alterados.", "error");
    }
}

formUsuario.onsubmit = function (e) {
    e.preventDefault();
    let accion = "";
    let formData;
    let User_Id = 0;
    if (query.isUndefinedNull(query.id("idusuario"))) {
        accion = "guardar";
    } else {
        accion = "editar";
        User_Id = query.id("idusuario").value;
    }

    if (query.emptyElements(this)) {
        query.toast("Campos vacíos", "Complete la información solicitada en el formulario.", "warning");
        return;
    }

    if (query.validate(query.id('usuario').value, "string")) {
        query.toast("Campos vacíos", "Ingrese un nombre de usuario.", "warning");
        return;
    }

    //validación de la contraseña
    if (!query.validate(User_Id, "int") && !query.validate(User_Id, "numbers")) {
        if (!query.validate(query.id("clave").value, "string")) {
            let password = query.id("clave").value;
            if (password.length < 8) {
                query.toast("Contraseña no segura", "La contraseña debe contener como minímo 8 carácteres.", "warning");
                return;
            }
        }
    } else {
        if (query.validate(query.id("clave").value, "string")) {
            query.toast("Campos vacíos", "Ingrese la contraseña del usuario.", "warning");
            return;
        }
        let password = query.id("clave").value;
        if (password.length < 8) {
            query.toast("Contraseña no segura", "La contraseña debe contener como minímo 8 carácteres.", "warning");
            return;
        }
    }

    if (query.validate(query.id('correo').value, "string")) {
        query.toast("Campos vacíos", "Ingrese un correó eléctronico.", "warning");
        return;
    }

    if (!query.validate(query.id("correo").value, "string")) {
        if (query.validate(query.id("correo").value, "email")) {
            query.toast("Correo inválido", "El correo ingresado es incorrecto.", "warning");
            return;
        }
    }

    if (query.validate(query.id('nivel').value, "string")) {
        query.toast("Campos vacíos", "Seleccione el nivel de acceso, para el usuario.", "warning");
        return;
    }

    if (!query.validate(accion, "string")) {
        formData = new FormData(this);
        formData.append("accion", accion);
        query.ajax({
            url: "ServletUsuario",
            content: "application/x-www-form-urlencoded",
            method: "post",
            data: formData,
            success: function (data) {
                let {texto, icono, objeto} = JSON.parse(data);
                query.toast("Registrar usuario", texto, icono);
                if (objeto != "null") {
                    listadoClientesNaturales();
                    $("#modal-user").modal("hide");
                }
            },
            fail: function () {
                query.toast("Error", "La petición solicitada ha fallado.", "error");
            }
        });
    } else {
        query.toast("Registro de Empleado", "Los datos del servidor han sido alterados.", "error");
    }
}

