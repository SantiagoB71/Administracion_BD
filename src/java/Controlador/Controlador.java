package Controlador;

import Modelo.Usuario;
//  Importa la clase IOException que permite manejar excepciones de entrada/salida.
import java.io.IOException;
// Importa la clase PrintWriter que permite escribir texto en un flujo de salida.
import java.io.PrintWriter;
// Importa la clase RequestDispatcher que permite reenviar una solicitud a otro componente web para que éste genere una respuesta.
import javax.servlet.RequestDispatcher;
// Importa la clase ServletException que representa una excepción que indica que se ha producido un error en el procesamiento de una solicitud del cliente.
import javax.servlet.ServletException;
// Importa la clase HttpServlet que es la clase base abstracta para todas las clases de servlets HTTP que deben responder a solicitudes HTTP.
import javax.servlet.http.HttpServlet;
// Importa la clase HttpServletRequest que proporciona información sobre la solicitud HTTP.
import javax.servlet.http.HttpServletRequest;
// Importa la clase HttpServletResponse que proporciona funcionalidad para enviar una respuesta HTTP a un cliente.
import javax.servlet.http.HttpServletResponse;
// Importa la clase HttpSession que proporciona una forma de identificar a un usuario a través de varias solicitudes o visitas a un sitio web.
import javax.servlet.http.HttpSession;
// Importa la clase MessageDigest del paquete java.security
import java.security.MessageDigest;
// Importa la clase NoSuchAlgorithmException del paquete java.security
import java.security.NoSuchAlgorithmException;
// Importa la clase Level del paquete java.util.logging
import java.util.logging.Level;
// Importa la clase Logger del paquete java.util.logging
import java.util.logging.Logger;

// Definimos la clase Controlador que extiende de HttpServlet
public class Controlador extends HttpServlet {

    // Definición de variables para las rutas de las vistas del dashboard y del formulario de inicio de sesión
    String dashboard = "registro.jsp";
    String login = "index.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        // Inicio del try-with-resources para cerrar automáticamente el objeto PrintWriter
        try ( PrintWriter out = response.getWriter()) {
            // Imprimir el encabezado HTML
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Controlador</title>");
            out.println("</head>");
            out.println("<body>");
            // Imprimir el título del servlet y la ruta de contexto de la solicitud
            out.println("<h1>Servlet Controlador at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // Procesamiento de la solicitud GET
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String acceso = "";
        String action = request.getParameter("accion");
        // Si la acción es "login", se intenta iniciar sesión con los datos proporcionados
        if (action.equalsIgnoreCase("login")) {
            try {
                acceso = dashboard;
                String correo = request.getParameter("correo");
                String contraseña = request.getParameter("contraseña");

                // Se encripta la contraseña escrita
                String hashedPassword = encriptar(contraseña);
                // Creación de un objeto Usuario para verificar si existe el usuario en la base de datos
                Usuario usuario = new Usuario(correo, hashedPassword);
                if (usuario.iniciarSesion().size() > 0) {
                    acceso = dashboard;
                } else {
                    acceso = login;
                }
            } // Si la acción es "register", se intenta crear un nuevo usuario con los datos proporcionados // Si la acción es "register", se intenta crear un nuevo usuario con los datos proporcionados
            catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (action.equalsIgnoreCase("register")) {
            try {
                String nombre = request.getParameter("nombre");
                String apellido = request.getParameter("apellido");
                String correo = request.getParameter("correo");
                String contraseña = request.getParameter("contraseña");
                String confirmar_contraseña = request.getParameter("confirmar_contraseña");
                // Se encripta la contraseña
                String hashedPassword = encriptar(contraseña);
                // Creación de un objeto Usuario para crear un nuevo usuario en la base de datos
                Usuario usuario = new Usuario(nombre, apellido, correo, hashedPassword);

                if (contraseña.equals(confirmar_contraseña) && usuario.crear()) {
                    // Si se crea el usuario con éxito, se devuelve un mensaje de éxito y se redirige al usuario al formulario de inicio de sesión
                    response.getWriter().write("La operación se realizó con éxito");
                    acceso = login;
                }
            } // Si la acción es "logout", se invalida la sesión del usuario y se redirige al formulario de inicio de sesión // Si la acción es "logout", se invalida la sesión del usuario y se redirige al formulario de inicio de sesión
            catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (action.equalsIgnoreCase("logout")) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }

            // Redirigir al usuario al formulario de inicio de sesión
            acceso = login;
        }
        // Se crea un objeto RequestDispatcher para redirigir al usuario a la vista correspondiente
        RequestDispatcher vista = request.getRequestDispatcher(acceso);
        vista.forward(request, response);
    }

    public static String encriptar(String password) throws NoSuchAlgorithmException {
        // Define el método encriptar que recibe una contraseña como parámetro y lanza una excepción NoSuchAlgorithmException si no se encuentra el algoritmo de hash solicitado

        MessageDigest md = MessageDigest.getInstance("SHA-256"); // Crea un objeto MessageDigest utilizando el algoritmo SHA-256

        md.update(password.getBytes()); // Actualiza el objeto MessageDigest con los bytes de la contraseña

        byte[] bytes = md.digest(); // Obtiene los bytes del hash encriptado

        StringBuilder sb = new StringBuilder(); // Crea un objeto StringBuilder para convertir los bytes a una cadena de caracteres

        for (int i = 0; i < bytes.length; i++) {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1)); // Convierte cada byte en una cadena de caracteres alfanuméricos y los agrega al objeto StringBuilder
        }

        return sb.toString(); // Devuelve la cadena de caracteres que representa la contraseña encriptada
    }

    // Procesamiento de la solicitud POST
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    // Obtiene una descripción corta del servlet
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
