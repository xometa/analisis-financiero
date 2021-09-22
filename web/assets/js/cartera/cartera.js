/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

let contenedor = query.id('data-body'),
        formCartera = query.id('formCartera'),
        cartera = new Array();

listadoClientes();

function listadoClientes() {
    contenedor.innerHTML = "";
    query.get({
        url: "ajax/cartera/listadocarteraclientes.jsp",
        method: 'get',
        success: function (data) {
            contenedor.innerHTML = data;
            //$("#basic-datatable").DataTable();
        },
        fail: function () {
            query.toast("Error", "El listado de clientes no se puede visualizar.", "error");
        }
    });
}

function dataTable() {
    $("#basic-datatable").DataTable().destroy();
    $("#basic-datatable").DataTable(
            {
                keys: !0,
                language: {
                    paginate: {
                        "first": "<i class='uil-angle-double-left'>",
                        "last": "<i class='uil-angle-double-right'>",
                        previous: "<i class='mdi mdi-chevron-left'>",
                        next: "<i class='mdi mdi-chevron-right'>"
                    },
                    "emptyTable": "No hay registros",
                    "info": "Mostrando _START_ a _END_ de _TOTAL_ registros",
                    "infoEmpty": "Mostrando 0 a 0 de 0 registros",
                    "infoFiltered": "(Filtrando de _MAX_ registros totales)",
                    "infoPostFix": "",
                    "thousands": ",",
                    "lengthMenu": "Mostrar _MENU_ registros",
                    "loadingRecords": "Cargando...",
                    "processing": "Procesando...",
                    "search": "Buscar:",
                    "zeroRecords": "Sin resultados encontrados"
                },
                drawCallback: function () {
                    $(".dataTables_paginate > .pagination").addClass("pagination-rounded")
                },
                "lengthMenu": [[5, 10, 25, 50, 100, -1], [5, 10, 25, 50, 100, "Todo"]]
            }
    );
}


contenedor.onclick = function (e) {
    let actual = e.target;
    let id = 0;
    let elemet;
    if (actual.matches('input[type=checkbox]')) {
        elemet = actual.parentNode.parentNode.parentNode;
        if (actual.checked) {
            id = parseInt(elemet.getAttribute('data-id'));
            agregarCliente(parseInt(id));
        } else {
            id = parseInt(elemet.getAttribute('data-id'));
            removerCliente(parseInt(id));
        }
    }
}

function agregarCliente(id) {
    if (buscarCliente(id) == null) {
        cartera.push(id);
    }
}

//verifica si el producto ya esta asignado en la lista
function buscarCliente(id) {
    for (let k = 0; k < cartera.length; k++) {
        if (cartera[k] === id) {
            return cartera[k];
        }
    }
    return null;
}

//quitando un producto de la lista
function removerCliente(id) {
    let element = null;
    let position = -1;
    if (cartera.length > 0) {
        for (let i = 0; i < cartera.length; i++) {
            if (cartera[i] == id) {
                element = cartera[i];
                break;
            } else {
                element = null;
            }
        }
        if (element != null) {
            position = cartera.indexOf(element);
            if (position > -1) {
                cartera.splice(position, 1);
            }
        }
    }
}

formCartera.onsubmit = function (e) {
    e.preventDefault();

    if (!query.validate(query.id("idsucursal").value, "string")) {
        if (query.validate(query.id("idsucursal").value, "numbers")) {
            query.toast("Advertencia", "La información de la sucursal seleccionada ha sido alterada.", "warning");
            return;
        }
    }

    if (query.validate(query.id("idsucursal").value, "int")) {
        query.toast("Campos vacíos", "Seleccione una sucursal.", "warning");
        return;
    }

    if (!query.validate(query.id("idasesor").value, "string")) {
        if (query.validate(query.id("idasesor").value, "numbers")) {
            query.toast("Advertencia", "La información del asesor seleccionado ha sido alterada.", "warning");
            return;
        }
    }

    if (query.validate(query.id("idasesor").value, "int")) {
        query.toast("Campos vacíos", "Seleccione un asesor, para la cartera.", "warning");
        return;
    }

    if (cartera.length <= 0) {
        query.toast("Seleccionar clientes", "No se han seleccionado clientes, para la cartera de la sucursal.", "warning");
        return;
    }

    let data = {
        'clientes[]': cartera,
        'idsucursal': parseInt(query.id("idsucursal").value),
        'idasesor': parseInt(query.id("idasesor").value),
        'accion': "guardar"
    }
    $.ajax({
        url: "ServletCartera",
        type: "POST",
        data: data,
        success: function (data) {
            console.log(data);
            let {titulo, texto, icono, objeto} = JSON.parse(data);
            query.toast(titulo, texto, icono);
            if (objeto != "null") {
                listadoClientes();
                limpiar();
            }
        }, fail: function () {
            query.toast("Error", "La petición solicitada ha fallado.", "error");
        }
    });
}

function limpiar() {
    query.resetForm(formCartera);
    cartera=new Array();
}
