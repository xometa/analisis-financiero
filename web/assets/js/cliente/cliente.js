/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//variables
let buttonModalClient = query.id("btn-addclient"),
        modalTitle = query.id("tittle-modal"),
        buttonAction = query.id('btn-action'),
        buttonCancel = query.id('btn-cancel');

buttonModalClient.onclick = (e) => {
    modalTitle.innerHTML = "Nuevo cliente";

    $("#modal-clientenatural").modal("show");
}
buttonCancel.onclick = (e) => {

    $("#modal-clientenatural").modal("hide");
}


