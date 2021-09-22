<%-- 
    Document   : modalfiador
    Created on : Dec 29, 2020, 10:34:27 AM
    Author     : jsaul
--%>

<%@page import="process.GeneraCodigos"%>
<%@page import="entities.Cnatural"%>
<%@page import="models.ModeloFiador"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    int id = Integer.parseInt(request.getParameter("id"));
    ModeloFiador mf = new ModeloFiador();
    GeneraCodigos cg = new GeneraCodigos();
    int valor = cg.autoincrementableFiador(mf.listadoFiadores());
    String codigo = cg.codigo("FIA", valor);
    Cnatural fiador;
    fiador = mf.recuperaFiador(id);

%>

<%    if (fiador != null) {
%>
<input type="hidden" class="form-control" id="id" name="id" value="<%=fiador.getId()%>">
<input type="hidden" class="form-control" id="idpersona" name="idpersona" value="<%=fiador.getPersona().getId()%>">
<%
    }
%>

<div class="form-group px-3" style="<%=(fiador == null) ? "display:block" : "display:none"%>">
    <label class="font-weight-bold">Código<span class="text-danger">*</span></label>
    <div class="input-group">
        <div class="input-group-prepend">
            <div class="input-group-text">
                <span class="uil-copyright"></span>
            </div>
        </div>
        <input type="text" class="form-control" id="codigo" readonly="readonly" onkeypress="return false;" name="codigo" value="<%=(fiador == null) ? codigo : fiador.getCodigo()%>">
    </div>
</div>
<div class="form-group px-3">
    <label class="font-weight-bold">DUI<span class="text-danger">*</span></label>
    <div class="input-group">
        <div class="input-group-prepend">
            <div class="input-group-text">
                <span class="uil-atm-card"></span>
            </div>
        </div>
        <input type="text" class="form-control" id="dui" name="dui" value="<%=(fiador == null) ? "" : fiador.getPersona().getDui()%>">
    </div>
</div>
<div class="form-group px-3">
    <label class="font-weight-bold">Nombre<span class="text-danger">*</span></label>
    <div class="input-group">
        <div class="input-group-prepend">
            <div class="input-group-text">
                <span class="uil-user"></span>
            </div>
        </div>
        <input type="text" class="form-control" id="nombre" name="nombre" value="<%=(fiador == null) ? "" : fiador.getPersona().getNombre()%>">
    </div>
</div>

<div class="form-group px-3">
    <label class="font-weight-bold">Apellidos<span class="text-danger">*</span></label>
    <div class="input-group">
        <div class="input-group-prepend">
            <div class="input-group-text">
                <span class="uil-user"></span>
            </div>
        </div>
        <input type="text" class="form-control" id="apellido" name="apellido" value="<%=(fiador == null) ? "" : fiador.getPersona().getApellido()%>">
    </div>
</div>

<div class="form-group px-3">
    <label class="font-weight-bold">Sexo<span class="text-danger">*</span></label>
    <div class="mt-2">
        <%if (fiador != null) {%>

        <%if (fiador.getPersona().getSexo().equals("Masculino")) {%>
        <div class="custom-control custom-radio custom-control-inline">
            <input type="radio" id="masculino" name="sexo" class="custom-control-input" checked value="Masculino">
            <label class="custom-control-label" for="masculino">Masculino</label>
        </div>
        <div class="custom-control custom-radio custom-control-inline">
            <input type="radio" id="femenino" name="sexo" class="custom-control-input" value="Femenino">
            <label class="custom-control-label" for="femenino">Femenino</label>
        </div>
        <%} else {%>
        <div class="custom-control custom-radio custom-control-inline">
            <input type="radio" id="masculino" name="sexo" class="custom-control-input" value="Masculino">
            <label class="custom-control-label" for="masculino">Masculino</label>
        </div>
        <div class="custom-control custom-radio custom-control-inline">
            <input type="radio" id="femenino" name="sexo" class="custom-control-input" checked value="Femenino">
            <label class="custom-control-label" for="femenino">Femenino</label>
        </div>
        <%}%>
        <%} else {%>
        <div class="custom-control custom-radio custom-control-inline">
            <input type="radio" id="masculino" name="sexo" class="custom-control-input" value="Masculino">
            <label class="custom-control-label" for="masculino">Masculino</label>
        </div>
        <div class="custom-control custom-radio custom-control-inline">
            <input type="radio" id="femenino" name="sexo" class="custom-control-input" value="Femenino">
            <label class="custom-control-label" for="femenino">Femenino</label>
        </div>
        <%}%>
    </div>
</div>

<div class="form-group px-3">
    <label class="font-weight-bold">Fecha nacimiento<span class="text-danger">*</span></label>
    <div class="input-group">
        <div class="input-group-prepend">
            <div class="input-group-text">
                <span class="uil-schedule"></span>
            </div>
        </div>
        <input type="date" class="form-control" id="fechanacimiento" name="fechanacimiento" value="<%=(fiador == null) ? "" : fiador.getPersona().getFechanacimiento()%>">
    </div>
</div>



<div class="form-group px-3">
    <label class="font-weight-bold">Teléfono<span class="text-danger">*</span></label>
    <div class="input-group">
        <div class="input-group-prepend">
            <div class="input-group-text">
                <span class="uil-phone"></span>
            </div>
        </div>
        <input type="text" class="form-control" id="telefono" name="telefono" value="<%=(fiador == null) ? "" : fiador.getPersona().getTelefono()%>">
    </div>
</div>

<div class="form-group px-3">
    <label class="font-weight-bold">Dirección<span class="text-danger">*</span></label>
    <div class="input-group">
        <div class="input-group-prepend">
            <div class="input-group-text">
                <span class="uil-map-marker"></span>
            </div>
        </div>
        <textarea type="text" class="form-control" id="direccion" name="direccion"><%= (fiador == null) ? "" : fiador.getPersona().getDireccion()%></textarea>
    </div>
</div>


<div class="form-group px-3">
    <label class="font-weight-bold">Estado civil<span class="text-danger">*</span></label>
    <div class="input-group">
        <div class="input-group-prepend">
            <div class="input-group-text">
                <span class="uil-users-alt"></span>
            </div>
        </div>
        <select id="estadocivil" name="estadocivil" class="custom-select form-control" required>
            <option value=" ">--- Seleccionar opción ---</option>
            <%if (fiador != null) {%>
            <option value="Soltero/a" <%=(fiador.getPersona().getEstadocivil().equals("Soltero/a")) ? "selected" : ""%>>Soltero/a</option>
            <option value="Casado/a" <%=(fiador.getPersona().getEstadocivil().equals("Casado/a")) ? "selected" : ""%>>Casado/a</option>
            <option value="Divorciado/a" <%=(fiador.getPersona().getEstadocivil().equals("Divorciado/a")) ? "selected" : ""%>>Divorciado/a</option>
            <option value="Viudo/a" <%=(fiador.getPersona().getEstadocivil().equals("Viudo/a")) ? "selected" : ""%>>Viudo/a</option>
            <option value="Otro" <%=(fiador.getPersona().getEstadocivil().equals("Otro")) ? "selected" : ""%>>Otro</option>
            <%} else {%>
            <option value="Soltero/a">Soltero/a</option>
            <option value="Casado/a">Casado/a</option>
            <option value="Divorciado/a">Divorciado/a</option>
            <option value="Viudo/a">Viudo/a</option>
            <option value="Otro">Otro</option>
            <%}%>
        </select>
    </div>
</div>


<div class="form-group px-3">
    <label class="font-weight-bold">Lugar de trabajo<span class="text-danger">*</span></label>
    <div class="input-group">
        <div class="input-group-prepend">
            <div class="input-group-text">
                <span class="uil-building"></span>
            </div>
        </div>
        <textarea type="text" class="form-control" id="lugartrabajo" name="lugartrabajo"><%= (fiador == null) ? "" : fiador.getLugartrabajo()%></textarea>
    </div>
</div>