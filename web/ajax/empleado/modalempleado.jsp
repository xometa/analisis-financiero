<%-- 
    Document   : modalempleado
    Created on : 02-ene-2021, 12:13:45
    Author     : BONIFACIO
--%>

<%@page import="entities.Persona"%>
<%@page import="models.ModeloEmpleado"%>
<%@page import="entities.Cnatural"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%

    int id = Integer.parseInt(request.getParameter("id"));
    ModeloEmpleado me = new ModeloEmpleado();
    Persona empleado = me.recuperaPersona(id, "", "p.id=?");

%>

<%    if (empleado != null) {
%>
<input type="hidden" class="form-control" id="id" name="id" value="<%=empleado.getId()%>">
<%
    }
%>


<div class="form-group px-3">
    <label class="font-weight-bold">DUI<span class="text-danger">*</span></label>
    <div class="input-group">
        <div class="input-group-prepend">
            <div class="input-group-text">
                <span class="uil-archive"></span>
            </div>
        </div>
        <input type="text" class="form-control" id="dui" name="dui" value="<%=(empleado == null) ? "" : empleado.getDui()%>">
    </div>
</div>
<div class="form-group px-3">
    <label class="font-weight-bold">Nombre<span class="text-danger">*</span></label>
    <div class="input-group">
        <div class="input-group-prepend">
            <div class="input-group-text">
                <span class="uil-archive"></span>
            </div>
        </div>
        <input type="text" class="form-control" id="nombre" name="nombre" value="<%=(empleado == null) ? "" : empleado.getNombre()%>">
    </div>
</div>

<div class="form-group px-3">
    <label class="font-weight-bold">Apellidos<span class="text-danger">*</span></label>
    <div class="input-group">
        <div class="input-group-prepend">
            <div class="input-group-text">
                <span class="uil-archive"></span>
            </div>
        </div>
        <input type="text" class="form-control" id="apellido" name="apellido" value="<%=(empleado == null) ? "" : empleado.getApellido()%>">
    </div>
</div>

<div class="form-group px-3">
    <label class="font-weight-bold">Sexo<span class="text-danger">*</span></label>
    <div class="mt-2">
        <%if (empleado != null) {%>

        <%if (empleado.getSexo().equals("Masculino")) {%>
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
                <span class="uil-archive"></span>
            </div>
        </div>
        <input type="date" class="form-control" id="fechanacimiento" name="fechanacimiento" value="<%=(empleado == null) ? "" : empleado.getFechanacimiento()%>">
    </div>
</div>



<div class="form-group px-3">
    <label class="font-weight-bold">Teléfono<span class="text-danger">*</span></label>
    <div class="input-group">
        <div class="input-group-prepend">
            <div class="input-group-text">
                <span class="uil-archive"></span>
            </div>
        </div>
        <input type="text" class="form-control" id="telefono" name="telefono" value="<%=(empleado == null) ? "" : empleado.getTelefono()%>">
    </div>
</div>


<div class="form-group px-3">
    <label class="font-weight-bold">Dirección<span class="text-danger">*</span></label>
    <div class="input-group">
        <div class="input-group-prepend">
            <div class="input-group-text">
                <span class="uil-archive"></span>
            </div>
        </div>
        <textarea type="text" class="form-control" id="direccion" name="direccion"><%= (empleado == null) ? "" : empleado.getDireccion()%></textarea>
    </div>
</div>


<div class="form-group px-3">
    <label class="font-weight-bold">Estado civil<span class="text-danger">*</span></label>
    <div class="input-group">
        <div class="input-group-prepend">
            <div class="input-group-text">
                <span class="uil-archive"></span>
            </div>
        </div>
        <select id="estadocivil" name="estadocivil" class="custom-select form-control" required>
            <option value=" ">--- Seleccionar opción ---</option>
            <%if (empleado != null) {%>
            <option value="Soltero/a" <%=(empleado.getEstadocivil().equals("Soltero/a")) ? "selected" : ""%>>Soltero/a</option>
            <option value="Casado/a" <%=(empleado.getEstadocivil().equals("Casado/a")) ? "selected" : ""%>>Casado/a</option>
            <option value="Divorciado/a" <%=(empleado.getEstadocivil().equals("Divorciado/a")) ? "selected" : ""%>>Divorciado/a</option>
            <option value="Viudo/a" <%=(empleado.getEstadocivil().equals("Viudo/a")) ? "selected" : ""%>>Viudo/a</option>
            <option value="Otro" <%=(empleado.getEstadocivil().equals("Otro")) ? "selected" : ""%>>Otro</option>
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
