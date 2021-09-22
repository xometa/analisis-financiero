<%-- 
    Document   : modaltipoprestamo
    Created on : Jan 6, 2021, 1:14:56 PM
    Author     : jsaul
--%>

<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.DecimalFormatSymbols"%>
<%@page import="entities.Tipoprestamo"%>
<%@page import="models.ModeloTipoPrestamo"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    int id = Integer.parseInt(request.getParameter("id"));
    DecimalFormatSymbols simbolo = new DecimalFormatSymbols();
    simbolo.setDecimalSeparator('.');
    simbolo.setGroupingSeparator(',');
    DecimalFormat f = new DecimalFormat("###,##0.00", simbolo);
    ModeloTipoPrestamo mtp = new ModeloTipoPrestamo();
    Tipoprestamo tp = mtp.recuperaTipoPrestamo(id, "", "tp.id=?");
%>

<%    if (tp != null) {
%>
<input type="hidden" class="form-control" id="id" name="id" value="<%=tp.getId()%>">
<%
    }
%>

<div class="form-group px-3">
    <label class="font-weight-bold">Tipo prestamo<span class="text-danger">*</span></label>
    <div class="input-group">
        <div class="input-group-prepend">
            <div class="input-group-text">
                <span class="uil-newspaper"></span>
            </div>
        </div>
        <input type="text" class="form-control" id="tipoprestamo" name="tipoprestamo" value="<%=(tp == null) ? "" : tp.getTipo()%>">
    </div>
</div>

<div class="form-group px-3">
    <label class="font-weight-bold">Porcentaje<span class="text-danger">*</span></label>
    <div class="input-group">
        <div class="input-group-prepend">
            <div class="input-group-text">
                <span class="uil-bill"></span>
            </div>
        </div>
        <input type="text" class="form-control" id="porcentaje" name="porcentaje" value="<%=(tp == null) ? "" : f.format(tp.getPorcentaje())%>">
        <div class="input-group-prepend">
            <div class="input-group-text">
                %
            </div>
        </div>
    </div>
</div>
