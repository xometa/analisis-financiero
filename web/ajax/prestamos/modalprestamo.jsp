<%-- 
    Document   : modalprestamo
    Created on : 16-ene-2021, 04:40:34
    Author     : BONIFACIO
--%>

<%@page import="models.ModeloJuridico"%>
<%@page import="entities.Cjuridico"%>
<%@page import="models.ModeloPersonaNatural"%>
<%@page import="entities.Tipoprestamo"%>
<%@page import="models.ModeloTipoPrestamo"%>
<%@page import="entities.Persona"%>
<%@page import="java.util.ArrayList"%>
<%@page import="entities.Usuario"%>
<%@page import="models.ModeloUsuario"%>
<%@page import="models.ModeloEmpleado"%>
<%@page import="entities.Cnatural"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>

<%

    ModeloTipoPrestamo mtp = new ModeloTipoPrestamo();
    ModeloPersonaNatural mpn = new ModeloPersonaNatural();
    ModeloJuridico mj = new ModeloJuridico();

    ArrayList<Tipoprestamo> tiposPrestamos = (ArrayList<Tipoprestamo>) mtp.listadoTipoPrestamos();
    ArrayList<Persona> clientes = (ArrayList<Persona>) mpn.listadoClientesSinCreditoActivo();//esta lista tiene que venir filtrada por si posee un prestamo activo o si se le ha aplicado el refinanciamiento
    ArrayList<Persona> fiadores = (ArrayList<Persona>) mpn.listadoFiadoresSinCreditoActivo();//esta lista tiene que venir filtrada para que el fiador no este en otro prestamo activo 
    ArrayList<Cjuridico> juridicos = (ArrayList<Cjuridico>) mj.lstClientesJuridicosSinCreditoActivo();//esta lista tiene que venir filtrada por si posee un prestamo activo o si se le ha aplicado el refinanciamiento

%>





<div class="form-group px-3">
    <label class="font-weight-bold">Tipo de cliente<span class="text-danger">*</span></label>
    <div class="input-group">
        <div class="input-group-prepend">
            <div class="input-group-text">
                <span class="uil-archive"></span>
            </div>
        </div>
        <select id="tcliente" name="tcliente" class="custom-select form-control" required onChange="mostrar(this.value);">
            <option value="ninguno">--- Seleccionar opción ---</option>
            <option value="natural" >Natural</option>
            <option value="juridico">Jurídico</option>
        </select>
    </div>
</div>
<div class="form-group px-3">
    <label class="font-weight-bold">Tipo de prestamo<span class="text-danger">*</span></label>
    <div class="input-group">
        <div class="input-group-prepend">
            <div class="input-group-text">
                <span class="uil-archive"></span>
            </div>
        </div>
        <select id="tprestamo" name="tprestamo" class="custom-select form-control" required>
            <option value="0" data-porcentaje="0">--- Seleccionar opción ---</option>            
            <% if (tiposPrestamos.size() > 0) {
                    for (Tipoprestamo tpm : tiposPrestamos) {%>
            <option value="<%= tpm.getId()%>" data-porcentaje="<%= tpm.getPorcentaje()%>"> <%=tpm.getTipo()%></option>

            <% }
                }%>
        </select>
    </div>
</div>


<div class="form-group px-3" id="div_id_natural" style="display: none;">
    <label class="font-weight-bold">Cliente Natural<span class="text-danger">*</span></label>
    <div class="input-group">
        <div class="input-group-prepend">
            <div class="input-group-text">
                <span class="uil-archive"></span>
            </div>
        </div>
        <select id="natural" name="natural" class="custom-select form-control" required>
            <option value="0">--- Seleccionar opción ---</option>            
            <%if (clientes.size() > 0) {
                    for (Persona c : clientes) {%>
            <option value="<%= c.getId()%>"><%=c.getNombre() + " " + c.getApellido()%></option>
            <% }
                }%>
        </select>
    </div>
</div>

<div class="form-group px-3" id="div_id_fiador" style="display: none;">
    <label class="font-weight-bold">Fiador<span class="text-danger">*</span></label>
    <div class="input-group">
        <div class="input-group-prepend">
            <div class="input-group-text">
                <span class="uil-archive"></span>
            </div>
        </div>
        <select id="fiador" name="fiador" class="custom-select form-control" required>
            <option value="0">--- Seleccionar opción ---</option>            
            <%if (fiadores.size() > 0) {
                    for (Persona f : fiadores) {%>
            <option value="<%= f.getId()%>"><%= f.getNombre() + " " + f.getApellido()%></option>
            <%      }
                }%>
        </select>
    </div>
</div>
<div class="form-group px-3" id="div_id_juridico" style="display: none;">
    <label class="font-weight-bold">Cliente Jurídico<span class="text-danger">*</span></label>
    <div class="input-group">
        <div class="input-group-prepend">
            <div class="input-group-text">
                <span class="uil-archive"></span>
            </div>
        </div>
        <select id="juridico" name="juridico" class="custom-select form-control" required>
            <option value="0">--- Seleccionar opción ---</option>            
            <%if (juridicos.size() > 0) {
                    for (Cjuridico j : juridicos) {%>
            <option value="<%= j.getId()%>"><%=j.getEmpresa()%></option>
            <% }
                }%>
        </select>
    </div>
</div>


<div class="form-group px-3">
    <label class="font-weight-bold">Monto<span class="text-danger">*</span></label>
    <div class="input-group">
        <div class="input-group-prepend">
            <div class="input-group-text">
                <span class="uil-archive"></span>
            </div>
        </div>
        <input  type="number" class="validanumericos form-control" id="monto" name="monto" min="10" >
    </div>
</div>

<div class="form-group px-3">
    <label class="font-weight-bold">Número de cuotas<span class="text-danger">*</span></label>
    <div class="input-group">
        <div class="input-group-prepend">
            <div class="input-group-text">
                <span class="uil-archive"></span>
            </div>
        </div>
        <input type="number" class="validanumericos form-control"  id="numcuotas" name="numcuotas" min="1" max="120"  >
    </div>
</div>

<div class="form-group px-3">
    <label class="font-weight-bold">Cuota mensual<span class="text-danger">*</span></label>
    <div class="input-group">
        <div class="input-group-prepend">
            <div class="input-group-text">
                <span class="uil-archive"></span>
            </div>
        </div>
        <input type="number" class="validanumericos form-control" step="any" id="cuotames" name="cuotames" min="10" readonly >
    </div>
</div>


