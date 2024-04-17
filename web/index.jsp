<%-- 
    Document   : index
    Created on : 1/05/2023, 1:05:47 p. m.
    Author     : SANTIAGO
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <link rel="stylesheet" href="estilo.css">
  <title>BASES DE DATOS</title>
</head>
<body>
    <section>
        <div class="form-box" id="iniciar-sesion" >
            <div class="form-value">
                <form action="Controlador">
                    <h2>Login</h2>
                    <div class="inputbox">
                        <ion-icon name="mail-outline"></ion-icon>
                        <input name = "correo" type="email" required>
                        <label>Email</label>
                    </div>
                    <div class="inputbox">
                        <ion-icon name="lock-closed-outline"></ion-icon>
                        <input name = "contraseña"required>
                        <label>Password</label>
                    </div>
                    <div class="forget">
                        <label for=""><input type="checkbox">Remember Me  <a href="#">Forget Password</a></label>
                    </div>
                    <button type="submit" name="accion" value="login" >Log in</button>
                    <div class="register">
                        <p>Don't have a account <a href="registro.jsp">Register</a></p>
                    </div>
                </form>
            </div>
        </div>
    </section>
    <script type="module" src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
    <script nomodule src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>
</body>
</html>
 