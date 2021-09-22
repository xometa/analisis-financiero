<%-- 
    Document   : clientenatural
    Created on : Dec 2, 2021, 8:00:40 AM
    Author     : BONIFACIO
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    response.setHeader("Pragma", "no-cache");
    response.addHeader("Cache-control", "must-revalidate");
    response.addHeader("Cache-control", "no-cache");
    response.addHeader("Cache-control", "no-store");
    response.setDateHeader("Expires", 0);
    try {
        if (session.getAttribute("idusuario") == null) {
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    } catch (Exception e) {
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
%>
<!DOCTYPE html>
<html lang="es">

    <head>
        <title>Clientes Naturales</title>
        <%@include file="head.jsp" %>
    </head>
    <body class="loading"
          data-layout-config='{"leftSideBarTheme":"dark","layoutBoxed":false, "leftSidebarCondensed":false, "leftSidebarScrollable":false,"darkMode":false, "showRightSidebarOnStart": true}'>
        <!-- Begin page -->
        <div class="wrapper">
            <!-- ========== Left Sidebar Start ========== -->
            <%@include file="sidebar.jsp" %>
            <!-- Left Sidebar End -->

            <!-- ============================================================== -->
            <!-- Start Page Content here -->
            <!-- ============================================================== -->

            <div class="content-page">
                <div class="content">
                    <!-- Topbar Start -->
                    <%@include file="navbar.jsp" %>
                    <!-- end Topbar -->

                    <!-- Start Content-->
                    <div class="container-fluid">
                        <!--Aquí va el contenido-->
                        <!-- start page title -->
                        <div class="row">
                            <div class="col-12">
                                <div class="page-title-box">
                                    <div class="page-title-right">
                                        <ol class="breadcrumb m-0">
                                            <li class="breadcrumb-item"><a href="javascript: void(0);">Análisis Financiero</a></li>
                                            <li class="breadcrumb-item"><a href="javascript: void(0);">Página</a></li>
                                            <li class="breadcrumb-item active">Clientes Naturales</li>
                                        </ol>
                                    </div>
                                    <h4 class="page-title">Cliente Natural</h4>
                                </div>
                            </div>
                        </div>
                        <!-- end page title -->

                        <div class="row">
                            <div class="col-md-12">
                                <div class="card">
                                    <div class="card-body">
                                        <div class="row">
                                            <div class="col-lg-12 pb-2">
                                                <button type="button" class="btn btn-success font-weight-bold btn-rounded" id="btn-addfiador"><i class="mdi mdi-plus"></i> Nuevo Cliente Natural</button>
                                            </div>
                                            <h4 class="header-title col-md-12 mt-2 mb-2">Listado de Clientes Naturales</h4>
                                            <div class="col-lg-3">
                                                <div class="row">
                                                    <div class="col-md-2 pt-1"><strong>Ver</strong></div>
                                                    <div class="col-md-4">
                                                        <select id="showQuantity" class="form-control">
                                                            <option value="5" selected>5</option>
                                                            <option value="10">10</option>
                                                            <option value="15">15</option>
                                                            <option value="20">20</option>
                                                            <option value="25">25</option>
                                                            <option value="50">50</option>
                                                            <option value="100">100</option>
                                                        </select>
                                                    </div>
                                                    <div class="col-md-6 pt-1"><strong>registros</strong></div>
                                                </div>
                                            </div>
                                            <div class="col-lg-5"></div>
                                            <div class="col-lg-4">
                                                <div class="row">
                                                    <div class="col-md-2 pt-1"><strong>Búscar:</strong></div>
                                                    <div class="col-lg-10">
                                                        <div class="form-group">
                                                            <div class="input-group">
                                                                <div class="input-group-prepend">
                                                                    <div class="input-group-text">
                                                                        <span class="uil-search"></span>
                                                                    </div>
                                                                </div>
                                                                <input type="text" class="form-control" id="searchData">
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="py-2 col-lg-12">
                                                <div class="row px-2">
                                                    <div class="table-responsive" id="employed-list">
                                                        <!-- home employed list -->
                                                        <table id="scroll-horizontal-datatable-1" class="table table-centered w-100 nowrap">
                                                            <thead class="thead-dark">
                                                                <tr>
                                                                    <th>Código</th>
                                                                    <th>DUI</th>
                                                                    <th>Nombre completo</th>
                                                                    <th>Sexo</th>
                                                                    <th>Fecha Nacimiento</th>
                                                                    <th>Teléfono</th>
                                                                    <th>Dirección</th>
                                                                    <th>Estado civil</th>
                                                                    <th>Lugar de trabajo</th>
                                                                    <th>Ingresos/Egresos</th>
                                                                    <th>Acciones</th>
                                                                </tr>
                                                            </thead>
                                                            <tbody id="listado-fiadores">

                                                            </tbody>
                                                        </table>
                                                        <!-- end employed list-->
                                                    </div>
                                                </div><!-- end row-->
                                            </div><!-- end col-->
                                        </div><!-- end row-->
                                    </div><!-- end car-body-->
                                    <div class="card-footer">
                                        <div class="row" id="create-pagination">
                                        </div><!-- end row-->
                                    </div><!-- end car-footer-->
                                </div><!-- end card-->
                            </div><!-- end col-->
                        </div><!-- end row-->

                    </div>
                    <!-- container -->
                </div> <!-- content -->
                <!-- Footer Start -->
                <%@include file="footer.jsp" %>
                <!-- end Footer -->
            </div>
            <!-- ============================================================== -->
            <!-- End Page content -->
            <!-- ============================================================== -->
        </div>
        <!-- END wrapper -->

        <div class="rightbar-overlay"></div>

        <!--modals-->
        <!--  Modal content for clients-->
        <div class="modal fade" id="modal-fiador" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel"
             aria-hidden="true">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header modal-colored-header bg-dark">
                        <h4 class="modal-title" id="tittle-modal">Nuevo Cliente Natural</h4>
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                    </div>
                    <form id="formFiador" name="formFiador" method="POST" autocomplete="off">
                        <div class="modal-body" id="cuerpo-modal">
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-light font-weight-bold btn-rounded" id="btn-cancel"><i
                                    class="mdi mdi-close"></i> Cancelar</button>
                            <button type="submit" class="btn btn-dark font-weight-bold btn-rounded" id="btn-action"><i
                                    class="mdi mdi-content-save"></i> Guardar</button>
                        </div>
                    </form>
                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div><!-- /.modal -->

        <!-- Alert Modal -->
        <div id="modalEliminarFiador" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog modal-sm modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-body p-4">
                        <div class="text-center">
                            <i class="dripicons-trash h1 text-danger"></i>
                            <h4 class="mt-2" id="titulo-eliminar">Eliminar fiador</h4>
                            <p class="mt-3" id="mtitle-delete">El registro se eliminará de manera permanente. ¿Estás seguro?</p>
                            <button type="button" class="btn btn-light my-2" data-dismiss="modal">Cancelar</button>
                            <button type="button" class="btn btn-danger my-2" id="mbtn-eliminar" data-dismiss="modal">Sí,
                                eliminar</button>
                        </div>
                    </div>
                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div><!-- /.modal -->

        <!--  Modal content for revenue-->
        <div class="modal fade" id="modal-in" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel"
             aria-hidden="true">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header modal-colored-header bg-dark">
                        <h4 class="modal-title" id="titulo-dispo">Registro de ingresos / egresos</h4>
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                    </div>
                    <div class="px-3 py-3">
                        <form id="formIng" name="formIng" method="POST" autocomplete="off" class="border border-secondary-100 rounded px-2 py-2">
                            <div class="modal-body" id="modal-datos"> 
                                <div class="form-group px-3">
                                    <label class="font-weight-bold">Descripción<span class="text-danger">*</span></label>
                                    <div class="input-group">
                                        <div class="input-group-prepend">
                                            <div class="input-group-text">
                                                <span class="uil-comment"></span>
                                            </div>
                                        </div>
                                        <input type="text" class="form-control" id="descripcion" name="descripcion">
                                    </div>
                                </div>
                                <div class="form-group px-3">
                                    <label class="font-weight-bold">Monto<span class="text-danger">*</span></label>
                                    <div class="input-group">
                                        <div class="input-group-prepend">
                                            <div class="input-group-text">
                                                <span class="uil-dollar-sign"></span>
                                            </div>
                                        </div>
                                        <input type="text" class="form-control" id="monto" name="monto">
                                    </div>
                                </div>
                            </div><!-- end modal-body -->
                            <div class="modal-footer new-footer">
                                <button type="button" class="btn btn-light font-weight-bold btn-rounded" id="btn-cleanineg"><i
                                        class="mdi mdi-close"></i> Cancelar</button>
                                <button type="submit" class="btn btn-dark font-weight-bold btn-rounded" id="btn-acciondispo"><i
                                        class="mdi mdi-content-save"></i> Guardar</button>
                            </div><!-- end modal-footer -->
                        </form>
                        <!-- table-->
                        <div class="border border-secondary-100 rounded px-2 mt-3">
                            <div class="modal-body">
                                <label for="" class="font-weight-bold" id="titulo-tabla">Listado de ingresos / egresos</label>
                                <div class="row">
                                    <div class="col-lg-3">
                                        <div class="row">
                                            <div class="col-md-3 pt-1"><strong>Ver</strong></div>
                                            <div class="col-md-6">
                                                <select class="form-control">
                                                    <option value="3" selected>3</option>
                                                    <option value="6">6</option>
                                                    <option value="12">12</option>
                                                    <option value="18">18</option>
                                                    <option value="24">24</option>
                                                    <option value="30">30</option>
                                                    <option value="50">50</option>
                                                    <option value="100">100</option>
                                                </select>
                                            </div>
                                            <div class="col-md-3 pt-1"><strong>registros</strong></div>
                                        </div>
                                    </div>
                                    <div class="col-lg-4"></div>
                                    <div class="col-lg-5">
                                        <div class="row">
                                            <div class="col-md-3 pt-1"><strong>Búscar:</strong></div>
                                            <div class="col-lg-9">
                                                <div class="form-group">
                                                    <div class="input-group">
                                                        <div class="input-group-prepend">
                                                            <div class="input-group-text">
                                                                <span class="uil-search"></span>
                                                            </div>
                                                        </div>
                                                        <input type="text" class="form-control">
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <table class="table table-centered mb-0">
                                        <thead class="font-weight-bold alert-success">
                                            <tr>
                                                <td>Ingreso</td>
                                                <td>Monto ($)</td>
                                                <td>Acciones</td>
                                            </tr>
                                        </thead>
                                        <tbody id="listado-ingresos">

                                        </tbody>
                                    </table>
                                </div>
                                <div class="row">

                                </div>
                            </div>
                        </div>
                    </div>
                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div><!-- /.modal -->

        <!--  Modal content for revenue and egresos-->
        <div class="modal fade" id="modal-ingresosegresos" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel"
             aria-hidden="true">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header modal-colored-header bg-dark">
                        <h4 class="modal-title" id="titulo-dispo">Diferencia entre Ingresos y Egresos</h4>
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                    </div>
                    <div class="row px-3 py-3">
                        <!-- table-->
                        <div class="col-lg-12 border border-secondary-100 rounded px-2">
                            <div class="modal-body">
                                <div class="row" id="diferenciaInEg">
                                    
                                </div>
                                <div class="row">

                                </div>
                            </div>
                        </div>
                    </div>
                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div><!-- /.modal -->

        <!-- bundle -->
        <script src="assets/js/vendor.min.js"></script>
        <script src="assets/js/app.min.js"></script>
        <script src="assets/js/demo.toastr.js"></script>
        <script src="assets/js/query/query.js"></script>
        <script src="assets/js/clientenatural/clientenatural.js"></script>

    </body>
</html>
