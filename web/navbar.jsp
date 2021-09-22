<%-- 
    Document   : navbar
    Created on : Dec 29, 2020, 9:23:53 AM
    Author     : jsaul
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="navbar-custom">
    <ul class="list-unstyled topbar-right-menu float-right mb-0">
        <li class="dropdown notification-list">
            <a class="nav-link dropdown-toggle nav-user arrow-none mr-0" data-toggle="dropdown" href="#" role="button" aria-haspopup="false"
               aria-expanded="false">
                <span class="account-user-avatar"> 
                    <%
                        Object genero = session.getAttribute("sexo");
                        String image = "";
                        if (String.valueOf(genero).equals("Masculino")) {
                            image = "assets/images/avatar1.jpg";
                        } else {
                            image = "assets/images/avatar2.jpg";
                        }
                    %>
                    <img src="<%= image%>" alt="user-image" class="rounded-circle">
                </span>
                <span>
                    <span class="account-user-name"><%out.println(session.getAttribute("nombre"));%></span>
                    <span class="account-position">Administrador</span>
                </span>
            </a>
            <div
                class="dropdown-menu dropdown-menu-right dropdown-menu-animated topbar-dropdown-menu profile-dropdown">
                <!-- item-->
                <div class=" dropdown-header noti-title">
                    <h6 class="text-overflow m-0">Bienvenido !</h6>
                </div>

                <!-- item
                <a href="javascript:void(0);" class="dropdown-item notify-item">
                    <i class="mdi mdi-account-circle mr-1"></i>
                    <span>Mi cuenta</span>
                </a>-->
                <form method="POST" autocomplete="off" action="Logout">
                    <button type="submit" class="dropdown-item notify-item">
                        <i class="mdi mdi-logout mr-1"></i>
                        <span>Cerrar Sesión</span>
                    </button>
                </form>
            </div>
        </li>

    </ul>
    <button class="button-menu-mobile open-left disable-btn">
        <i class="mdi mdi-menu"></i>
    </button>
</div>
