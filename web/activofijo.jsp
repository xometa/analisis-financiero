<%-- 
    Document   : activofijo
    Created on : Jan 1, 2021, 2:54:22 PM
    Author     : jsaul
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
        <title>Activo fijo</title>
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
                                            <li class="breadcrumb-item active">Activo Fijo</li>
                                        </ol>
                                    </div>
                                    <h4 class="page-title">Activo Fijo</h4>
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
                                                <button type="button" class="btn btn-success font-weight-bold btn-rounded" id="btn-addactivo"><i class="mdi mdi-plus"></i> Nuevo activo fijo</button>
                                            </div>
                                            <h4 class="header-title col-md-12 mt-2 mb-2">Listado de activos fijos</h4>
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
                                                        <table id="scroll-horizontal-datatable" class="table table-centered w-100 nowrap">
                                                            <thead class="thead-dark">
                                                                <tr>
                                                                    <th>Código</th>
                                                                    <th>Activo fijo</th>
                                                                    <th>Descripción</th>
                                                                    <th>Procedencia</th>
                                                                    <th>Precio</th>
                                                                    <th>Fecha adquisición</th>
                                                                    <th>Vida útil</th>
                                                                    <th>Sucursal</th>
                                                                    <th>Departamento</th>
                                                                    <th>Tipo</th>
                                                                    <th>Uso</th>
                                                                    <th>Baja</th>
                                                                    <th>Acciones</th>
                                                                </tr>
                                                            </thead>
                                                            <tbody id="listado-activos">

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
        <div class="modal fade" id="modal-activofijo" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel"
             aria-hidden="true">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header modal-colored-header bg-dark">
                        <h4 class="modal-title" id="titulo-modal">Nuevo Activo Fijo</h4>
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                    </div>
                    <form id="formActivo" name="formActivo" method="POST" autocomplete="off">
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
        <div id="modalEliminarActivo" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog modal-sm modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-body p-4">
                        <div class="text-center">
                            <i class="dripicons-trash h1 text-danger"></i>
                            <h4 class="mt-2" id="titulo-eliminar">Eliminar activo fijo</h4>
                            <p class="mt-3" id="mtitle-delete">El registro se eliminará de manera permanente. ¿Estás seguro?</p>
                            <button type="button" class="btn btn-light my-2" data-dismiss="modal">Cancelar</button>
                            <button type="button" class="btn btn-danger my-2" id="mbtn-eliminar" data-dismiss="modal">Sí,
                                eliminar</button>
                        </div>
                    </div>
                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div><!-- /.modal -->

        <div id="modal-darbaja" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="standard-modalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header modal-colored-header bg-dark">
                        <h4 class="modal-title" id="titulo-darbaja">Dar de baja</h4>
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                    </div>

                    <form id="formDarBaja" name="formDarBaja" method="POST" autocomplete="off">
                        <input type="hidden" id="idactivofijo" name="idactivofijo" class="form-control" value="0">
                        <div class="modal-body">
                            <div id="verinformacionbaja" hidden="hidden">
                                <div class="row px-3">
                                    <div class="col-lg-2 font-weight-bold">Motivo:</div>
                                    <div class="col-lg-10" id="motivobajaactivo">sdsdfsfsd sdfsdgsdgds sdgsdgdsgsdgds sdgsdgsdgsdg sgsdgdsg vsdfgsdgds no se</div>
                                    <div class="col-lg-2 font-weight-bold pt-2">Fecha: </div>
                                    <div class="col-lg-10 pt-2" id="fechabajaactivo">12/24/25</div>
                                </div>
                            </div>
                            <div id="registrarBaja" hidden="hidden">
                                <div class="form-group px-3">
                                    <div class="alert-warning rounded py-2 px-2 font-weight-bold">Al dar de baja el activo, ya no se podra modificar su información.</div>
                                </div>
                                <div class="form-group px-3">
                                    <label class="font-weight-bold">Motivo<span class="text-danger">*</span></label>
                                    <div class="input-group">
                                        <div class="input-group-prepend">
                                            <div class="input-group-text">
                                                <span class="uil-archive"></span>
                                            </div>
                                        </div>
                                        <textarea type="text" class="form-control" id="motivo" name="motivo"></textarea>
                                    </div>
                                </div>
                                <div class="form-group px-3">
                                    <label class="font-weight-bold">Fecha<span class="text-danger">*</span></label>
                                    <div class="input-group">
                                        <div class="input-group-prepend">
                                            <div class="input-group-text">
                                                <span class="uil-archive"></span>
                                            </div>
                                        </div>
                                        <input type="date" class="form-control" id="fecha" name="fecha">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-light font-weight-bold btn-rounded" id="btn-cancelarbaja"> <i
                                    class="mdi mdi-close"></i> Cerrar</button>
                            <button type="submit" class="btn btn-dark font-weight-bold btn-rounded" id="btn-darbaja"><i
                                    class="mdi mdi-content-save"></i> Guardar</button>
                        </div>
                    </form>
                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div><!-- /.modal -->

        <!-- bundle -->
        <script src="assets/js/vendor.min.js"></script>
        <script src="assets/js/app.min.js"></script>
        <script src="assets/js/demo.toastr.js"></script>
        <script src="assets/js/query/query.js"></script>
        <script src="assets/js/activofijo/activofijo.js"></script>

    </body>
</html>


