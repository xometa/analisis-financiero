<%-- 
    Document   : prestamos
    Created on : 16-ene-2021, 15:44:39
    Author     : BONIFACIO
--%>

<!DOCTYPE html>
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
        <title>Prestamos</title>
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
                                            <li class="breadcrumb-item active">Prestamos</li>
                                        </ol>
                                    </div>
                                    <h4 class="page-title">Prestamos</h4>
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
                                                <button type="button" class="btn btn-success font-weight-bold btn-rounded" id="btn-addprestamo"><i class="mdi mdi-plus"></i> Nuevo Prestamo</button>
                                            </div>
                                            <h4 class="header-title col-md-12 mt-2 mb-2">Listado de Prestamos</h4>
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
                                                    <div class="col-md-6 pt-1"><strong>Tipo Clientes</strong></div>
                                                    <div class="col-lg-10">
                                                        <div class="form-group">
                                                            <div class="input-group">
                                                                <div class="input-group-prepend">
                                                                    <div class="input-group-text">
                                                                        <span class="uil-search"></span>
                                                                    </div>
                                                                </div>
                                                                <select id="combo_natural" name="combo_natural" onChange="verlistass(this.value);"  class="custom-select form-control" required>
                                                                    <option value="0">--- Seleccionar opción ---</option>            
                                                                    <option value="Natural">Clientes Naturales</option>
                                                                    <option value="Juridico">Clientes Juridicos</option>
                                                                </select>
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
                                                                    <th>Cliente</th>
                                                                    <th>Tipo prestamo</th>
                                                                    <th>Cantidad</th>
                                                                    <th>Cuota mensual</th>
                                                                    <th>Tiempo prestamo</th>
                                                                    <th>Fecha prestamo</th>
                                                                </tr>
                                                            </thead>
                                                            <tbody id="listado-prestamos">

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
        <div class="modal fade" id="modal-prestamo" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel"
             aria-hidden="true">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header modal-colored-header bg-dark">
                        <h4 class="modal-title" id="tittle-modal">Nuevo Usuario</h4>
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                    </div>
                    <form id="formPrestamo" name="formPrestamo" method="POST" autocomplete="off">
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
        <div id="modalEliminarEmpleado" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog modal-sm modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-body p-4">
                        <div class="text-center">
                            <i class="dripicons-trash h1 text-danger"></i>
                            <h4 class="mt-2" id="titulo-eliminar">Eliminar Usuario</h4>
                            <p class="mt-3" id="mtitle-delete">El registro se eliminará de manera permanente. ¿Estás seguro?</p>
                            <button type="button" class="btn btn-light my-2" data-dismiss="modal">Cancelar</button>
                            <button type="button" class="btn btn-danger my-2" id="mbtn-eliminar" data-dismiss="modal">Sí,
                                eliminar</button>
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
        <script src="assets/js/prestamo/prestamo.js"></script>

    </body>
</html>

