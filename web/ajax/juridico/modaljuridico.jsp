<%-- 
    Document   : modaljuridico
    Created on : Dec 29, 2020, 10:34:27 AM
    Author     : jsaul
--%>

<%@page import="process.GeneraCodigos"%>
<%@page import="entities.Cjuridico"%>
<%@page import="models.ModeloJuridico"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    //calcular razones de liquides
    int id = Integer.parseInt(request.getParameter("id"));
    ModeloJuridico mj = new ModeloJuridico();
    GeneraCodigos cg = new GeneraCodigos();
    int valor = cg.autoincrementableJuridico(mj.listadoJuridico());
    String codigo = cg.codigo("JUR", valor);
    Cjuridico juridico = mj.recuperaJuridico(id);

%>

<%    if (juridico != null) {
%>
<input type="hidden" class="form-control" id="id" name="id" value="<%=juridico.getId()%>">
<input type="hidden" class="form-control" id="idpersona" name="idpersona" value="<%=juridico.getPersona().getId()%>">
<%
    }
%>

<div class="row">
    <div class="col-lg-6">
        <div class="px-3">
            <div class="alert-info py-2 font-weight-bold px-2 text-center">
                Datos del representante
            </div>
        </div>
        <div class="form-group px-3 pt-2">
            <label class="font-weight-bold">DUI<span class="text-danger">*</span></label>
            <div class="input-group">
                <div class="input-group-prepend">
                    <div class="input-group-text">
                        <span class="uil-atm-card"></span>
                    </div>
                </div>
                <input type="text" class="form-control" id="dui" name="dui" value="<%=(juridico == null) ? "" : juridico.getPersona().getDui()%>">
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
                <input type="text" class="form-control" id="nombre" name="nombre" value="<%=(juridico == null) ? "" : juridico.getPersona().getNombre()%>">
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
                <input type="text" class="form-control" id="apellido" name="apellido" value="<%=(juridico == null) ? "" : juridico.getPersona().getApellido()%>">
            </div>
        </div>

        <div class="form-group px-3">
            <label class="font-weight-bold">Sexo<span class="text-danger">*</span></label>
            <div class="mt-2">
                <%if (juridico != null) {%>

                <%if (juridico.getPersona().getSexo().equals("Masculino")) {%>
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
                <input type="date" class="form-control" id="fechanacimiento" name="fechanacimiento" value="<%=(juridico == null) ? "" : juridico.getPersona().getFechanacimiento()%>">
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
                <input type="text" class="form-control" id="telefono" name="telefono" value="<%=(juridico == null) ? "" : juridico.getPersona().getTelefono()%>">
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
                <textarea type="text" class="form-control" id="direccion" name="direccion"><%= (juridico == null) ? "" : juridico.getPersona().getDireccion()%></textarea>
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
                    <%if (juridico != null) {%>
                    <option value="Soltero/a" <%=(juridico.getPersona().getEstadocivil().equals("Soltero/a")) ? "selected" : ""%>>Soltero/a</option>
                    <option value="Casado/a" <%=(juridico.getPersona().getEstadocivil().equals("Casado/a")) ? "selected" : ""%>>Casado/a</option>
                    <option value="Divorciado/a" <%=(juridico.getPersona().getEstadocivil().equals("Divorciado/a")) ? "selected" : ""%>>Divorciado/a</option>
                    <option value="Viudo/a" <%=(juridico.getPersona().getEstadocivil().equals("Viudo/a")) ? "selected" : ""%>>Viudo/a</option>
                    <option value="Otro" <%=(juridico.getPersona().getEstadocivil().equals("Otro")) ? "selected" : ""%>>Otro</option>
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
    </div>
    <div class="col-lg-6">
        <div class="px-3">
            <div class="alert-info py-2 font-weight-bold px-2 text-center">
                Datos de la institución
            </div>
        </div>
        <div class="form-group px-3 pt-2" style="<%=(juridico == null) ? "display:block" : "display:none"%>">
            <label class="font-weight-bold">Código<span class="text-danger">*</span></label>
            <div class="input-group">
                <div class="input-group-prepend">
                    <div class="input-group-text">
                        <span class="uil-copyright"></span>
                    </div>
                </div>
                <input type="text" class="form-control" readonly="readonly" onkeypress="return false;" id="codigo" name="codigo" value="<%=(juridico == null) ? codigo : juridico.getCodigo()%>">
            </div>
        </div>
        <div class="form-group px-3 <%= (juridico == null) ? "" : "pt-2"%>">
            <label class="font-weight-bold">Empresa<span class="text-danger">*</span></label>
            <div class="input-group">
                <div class="input-group-prepend">
                    <div class="input-group-text">
                        <span class="uil-building"></span>
                    </div>
                </div>
                <input type="text" class="form-control" id="empresa" name="empresa" value="<%=(juridico == null) ? "" : juridico.getEmpresa()%>">
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
                <input type="text" class="form-control" id="telefonoempresa" name="telefonoempresa" value="<%=(juridico == null) ? "" : juridico.getTelefono()%>">
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
                <textarea type="text" class="form-control" id="direccionempresa" name="direccionempresa"><%= (juridico == null) ? "" : juridico.getDireccion()%></textarea>
            </div>
        </div>
    </div>
</div>