/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

let contenedor = query.id('listado-activos'),
        infoAmortizacion = query.id('amortizacion-activofijo'),
        plantilla = query.id('vacio'),
        idactivo = 0;

infoAmortizacion.innerHTML = plantilla.innerHTML;

//llamado de las funciones
listadoActivo();

function listadoActivo() {
    query.get({
        url: "ajax/activofijo/listadotangible.jsp",
        method: 'get',
        data: {tipo: "Intangible"},
        success: function (data) {
            contenedor.innerHTML = data;
            selection();
        },
        fail: function () {
            query.toast("Error", "El listado de activos fijos no se puede visualizar.", "error");
        }
    });
}

function obtenerAmortizacion(id) {
    query.get({
        url: "ajax/activofijo/infoamortizacion.jsp",
        method: 'get',
        data: {id: id},
        success: function (data) {
            infoAmortizacion.innerHTML = data;
        },
        fail: function () {
            query.toast("Error", "La amortizacion del activo fijo no se puede visualizar.", "error");
        }
    });
}

//click en el contenedor
contenedor.onclick = function (e) {
    let current = e.target;
    let id = 0;
    if (current.matches('td')) {
        id = current.parentNode.getAttribute('data-id');
        if (!query.validate(id, "int") && !query.validate(id, "numbers")) {
            obtenerAmortizacion(parseInt(id))
        } else {
            query.toast("Alteración de información", "Los datos del servidor han sido alterados.", "error");
        }
    }
}

function selection() {
    let row = contenedor.querySelectorAll('tr');

    row.forEach(tr => {
        tr.addEventListener("click", function (e) {
            let current = e.target;
            //limpiamos todas las filas
            row.forEach(tr => {
                if (tr.classList.contains('alert-success')) {
                    tr.classList.remove('alert-success');
                }
            });
            if (current.nodeName == "TD") {
                current = current.parentNode;
            }
            if (current.getAttribute("data-id") != null) {
                idactivo = parseInt(current.getAttribute("data-id"));
                current.classList.add('alert-success');
            } else {
                query.toast("Error", "Los precios del producto no se pueden visualizar.", "error");
            }
        }, false);

        tr.addEventListener("mouseover", function (e) {
            let current = e.target;
            if (current.nodeName == "TD") {
                current = current.parentNode;
            }

            if (idactivo > 0) {

                if (idactivo == parseInt(current.getAttribute('data-id'))) {
                    if (!current.classList.contains('alert-success')) {
                        current.classList.add('alert-success')
                    }
                } else {
                    if (!current.classList.contains('alert-success')) {
                        current.classList.add('alert-success')
                    }
                }
            } else {
                if (!current.classList.contains('alert-success')) {
                    current.classList.add('alert-success')
                }
            }
        }, false);

        //removemos el hover
        tr.addEventListener("mouseout", function (e) {
            let current = e.target;
            if (current.nodeName == "TD") {
                current = current.parentNode;
            }
            if (idactivo > 0) {
                if (idactivo == parseInt(current.getAttribute('data-id'))) {
                    if (!current.classList.contains('alert-success')) {
                        current.classList.add('alert-success')
                    }
                } else {
                    if (current.classList.contains('alert-success')) {
                        current.classList.remove('alert-success')
                    }
                }
            } else {
                if (current.classList.contains('alert-success')) {
                    current.classList.remove('alert-success')
                }
            }
        }, false);

    });
}



