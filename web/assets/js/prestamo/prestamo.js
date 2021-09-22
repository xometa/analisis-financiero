/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


//variables
let buttonModalUsuario = query.id("btn-addprestamo"),
        modalTitle = query.id("tittle-modal"),
        buttonAction = query.id('btn-action'),
        buttonCancel = query.id('btn-cancel');
let contenedor = query.id('listado-prestamos'),
        cuerpomodal = query.id('cuerpo-modal');

let tituloEliminar = query.id('titulo-eliminar'),
        botonEliminar = query.id('mbtn-eliminar');

let formularioUsuario = query.id('formPrestamo');


function mostrar(id) {
    console.log(id);
    if (id == "ninguno") {
        $("#div_id_natural").hide();
        $("#div_id_fiador").hide();
        $("#div_id_juridico").hide();
    } else if (id == "natural") {
        $("#div_id_natural").show();
        $("#div_id_fiador").show();
        $("#div_id_juridico").hide();
    } else
    if (id == "juridico") {
        $("#div_id_juridico").show();
        $("#div_id_natural").hide();
        $("#div_id_fiador").hide();
    }
}

function verlistass(id) {
    console.log(id);
    if (id == "Natural") {
        listadoPrestamosNaturales();
    } else if (id == "Juridico") {
        listadoPrestamosJuridicos();
    }
}

//validando numeros
$(function () {

    $('.validanumericos').keypress(function (e) {
        if (isNaN(this.value + String.fromCharCode(e.charCode)))
            return false;
    })
            .on("cut copy paste", function (e) {
                e.preventDefault();
            });

});


function calcularGanancia() {


    $('.validanumericos').keypress(function (e) {
        if (isNaN(this.value + String.fromCharCode(e.charCode)))
            return false;
    }).on("cut copy paste", function (e) {
        e.preventDefault();
    });

    let numcuotas = query.id("numcuotas");
    let monto = query.id("monto");
    numcuotas.addEventListener("keyup", function (e) {
        let actual = e.target.value;
        if (isNaN(actual) || parseInt(actual) <= 0) {
            query.toast("Número de cuotas incorrecto", "El número de cuotas ingresadas es incorrecto.", "warning");
            e.target.value = "";
            query.id("cuotames").value = 0;
            return;
        }
        calcularCuotaMensual();

    }, false);

    monto.addEventListener("keyup", function (e) {
        let actual = e.target.value;
        if (isNaN(actual) || parseInt(actual) <= 0) {
            query.toast("Monto incorrecto", "El monto ingesado es incorrecto.", "warning");
            e.target.value = "";
            query.id("cuotames").value = 0;
            return;
        }
        calcularCuotaMensual();

    }, false);

}

function calcularCuotaMensual() {
    let tipo = query.id('tprestamo');
    let porcentaje = tipo.selectedOptions[0].getAttribute("data-porcentaje");
    let monto = parseFloat(query.id("monto").value);
    let ct = parseFloat(query.id("numcuotas").value);

    //obteniendo el porcentaje
    if (parseFloat(porcentaje) <= 0 || isNaN(porcentaje)) {
        query.toast("Campos vacíos", "Seleccione el tipo de prestamo, para obtener la cuota mensual.", "warning");
        query.id("cuotames").value = 0;
        query.id('tprestamo').focus();
        return;
    }

    if (isNaN(monto) || parseFloat(monto) <= 0) {
        query.id("monto").focus();
        query.id("cuotames").value = 0;
        return;
    }

    if (isNaN(ct) || parseFloat(ct) <= 0) {
        query.id("cuotames").value = "";
        query.id("cuotames").focus();
        return;
    }
    console.log(porcentaje);
    let intereses = (parseFloat(porcentaje) / 100) * monto;
    console.log(intereses);
    let total = intereses + monto;//prestamo + intereses
    let cuota1 = (total / ct);
    let cuota = cuota1.toFixed(2)

    query.id("cuotames").value = cuota;
}
//llamado de las funciones
//listadoPrestamosNaturales();

function listadoPrestamosNaturales() {
    query.get({
        url: "ajax/prestamos/listadoprestamosNaturales.jsp",
        method: 'get',
        success: function (data) {
            contenedor.innerHTML = data;
        },
        fail: function () {
            query.toast("Error", "El listado de prestamos no se puede visualizar.", "error");
        }
    });
}

function listadoPrestamosJuridicos() {
    query.get({
        url: "ajax/prestamos/listadoprestamosJuridicos.jsp",
        method: 'get',
        success: function (data) {
            contenedor.innerHTML = data;
        },
        fail: function () {
            query.toast("Error", "El listado de prestamos no se puede visualizar.", "error");
        }
    });
}

function recuperarCuerpoModal(id) {
    let data = {
        id: id
    }
    query.get({
        url: "ajax/prestamos/modalprestamo.jsp",
        method: 'get',
        data: data,
        success: function (data) {
            cuerpomodal.innerHTML = data;
            $("#modal-prestamo").modal("show");
            calcularGanancia();
        },
        fail: function () {
            query.toast("Error", "El formulario no se puede visualizar.", "error");
        }
    });
}

function iconoModalBoton(tittle) {
    if (tittle.textContent == "Nuevo Prestamo") {
        if (buttonAction.childNodes[0].classList.contains("mdi-border-color")) {
            buttonAction.childNodes[0].classList.remove("mdi-border-color");
        }
        buttonAction.childNodes[0].classList.add("mdi-content-save");
        buttonAction.childNodes[1].textContent = " Guardar";
    }
}

buttonModalUsuario.onclick = (e) => {
    cleanData();
    modalTitle.innerHTML = "Nuevo Prestamo";
    iconoModalBoton(modalTitle);
    recuperarCuerpoModal(0);
}
buttonCancel.onclick = (e) => {
    cleanData();
    $("#modal-prestamo").modal("hide");
}

//para editar y eliminar un registro
contenedor.onclick = function (e) {
    let actual = e.target;
    let Id = 0;

    //para modificar el estado del usuario
    if (actual.matches("input[type=checkbox]")) {
        let estado = 0;
        Id = actual.getAttribute("data-idempleado");
        if (actual.checked) {
            estado = 1;
        } else {
            estado = 0;
        }
        if (!query.validate(Id, "int") && !isNaN(estado) && estado >= 0 && estado <= 1) {
            modificarEstado({id: parseInt(Id), estado: estado, accion: "modificarestado"});
            //options({ Id: parseInt(Id), Status }, "EditStatus", "Estado de la categoría", "");
        } else {
            query.toast("Información alterada", "La información del servidor ha sido alterada.", "error");
        }
    }
}


formularioUsuario.onsubmit = function (e) {
    console.log("entro al onsubmit")
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

    if (query.validate(query.id('monto').value, "float")) {
        query.toast("Campos vacíos", "Seleccione el empleado", "warning");
        return;
    }



    //mandando via ajax los registros
    if (!query.validate(accion, "string")) {
        formData = new FormData(this);
        formData.append("accion", accion);
        query.ajax({
            url: "ServletPrestamo",
            content: "application/x-www-form-urlencoded",
            method: "post",
            data: formData,
            success: function (datos) {
                let {texto, icono, objeto} = JSON.parse(datos);
                query.toast("Registrar Prestamo", texto, icono);
                if (objeto != "null") {
                    //verlistass(objeto);
                    $("#modal-prestamo").modal("hide");
                }
            },
            fail: function () {
                query.toast("Error", "La petición solicitada ha fallado.", "error");
            }
        });
    } else {
        query.toast("Registro de Prestamo", "Los datos del servidor han sido alterados.", "error");
    }
}


function cleanData() {
    query.resetForm(formularioUsuario);
}
