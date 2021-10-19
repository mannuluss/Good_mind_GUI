package app;

import Frames.Body;
import app.CommandPattern.Invoker;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Esta clase principal representa el funcionamiento y modelado de Goodmind
 * donde se encuentran todos los métodos para la interfaz de consola
 *
 * @author Valentina Escobar, Daniel Gonzalez y Felipe Rojas
 *
 */
public class Goodmind {

    public static Scanner sc = new Scanner(System.in);
    public static Usuario user;
    public static Conversacion CurrentChat;
    private static Conversacion receptorChat;
    /**
     * arreglo que contiene a todos los usuarios registrados en la plataforma
     */
    public static ArrayList<Usuario> Allusers = new ArrayList<Usuario>();

    public static void Init() {
        Allusers.addAll(Server.GetAllUsers("./users.json"));
    }
    
    public static void setCurrentChat(String name_user) {
        Usuario pro = user.role == Role.profesional ? Usuario.GetUsuario(Allusers, name_user) : Usuario.GetProfesional(Allusers, name_user);
        CurrentChat = user.InitChat(name_user, pro.id);
        receptorChat = pro.InitChat(user.nombre, user.id);
    }

    public static boolean CreateConversacion() {
        Usuario pro = user.RamdomProfesional(Allusers);
        if (pro == null) {
            return false;
        }
        setCurrentChat(pro.nombre);
        return true;
    }

    public static void sendMsj(String msj, TypeMenssage typemsj) {
        CurrentChat.AddMsj(msj, typemsj, user.id);//daniel
        receptorChat.AddMsj(msj, typemsj, user.id);
    }

    /**
     * Metodo que permite el login de un usuario en la aplicacion
     */
    public static void Login(Role RoleUser, String nameuser, String password) {
        //System.out.println("============ Inicio de sesion ============");
        user = VerifyCredencials(nameuser, password, RoleUser);
        if (user != null) {
            System.out.println("Login Exitoso");
            Conversacion ch = user.GetActiveChat();
            if (ch != null) {
                setCurrentChat(ch.ChatUser);
            }

            Invoker.ChangePanel(RoleUser == Role.usuario ? "Huser" : "Hpro");
        } else {
            System.out.println("Usuario no registrado");
        }
    }

    /**
     * metodo que verfica si el usuario esta registrado y las credenciales son
     * correctas
     *
     * @param nameuser nombre del usuario
     * @param password contraseña del usuario
     * @param rol rol (usuario/profesional)
     * @return null o el usuario en caso de encontrarlo
     */
    public static Usuario VerifyCredencials(String nameuser, String password, Role rol) {
        for (Usuario u : Allusers) {
            // System.out.println(u.nombre);
            if (u.nombre.compareTo(nameuser) == 0 && u.role == rol) {
                // System.out.println("OK");
                return u;
            }
        }
        return null;
    }

    /**
     * método que crea cuenta
     *
     * @param username nombre de usuario
     * @param password contraseña del usuario
     * @param rol rol (usuario/profesional)
     * @return null o el usuario en caso de encontrarlo
     */
    public static Usuario CreateAcount(String username, String password, Role rol) {
        for (Usuario usuario : Allusers) {
            if (usuario.nombre.equals(username) && usuario.role == rol) {
                return null;
            }
        }
        // envia informacion al servidor para registrar el usuario
        Usuario newUser = new Usuario(username, rol);
        Allusers.add(newUser);
        user = newUser;
        return newUser;
    }

    /**
     * metodo que busca un usuario
     *
     * @param nameuser nombre del usuario
     * @param rol rol (usuario/profesional)
     * @return el usuario o nulo si no lo encuentra
     */
    public static Usuario GetUser(String nameuser, Role rol) {
        for (Usuario usuario : Allusers) {
            if (usuario.nombre.equals(nameuser) && rol == usuario.role) {
                return usuario;
            }
        }
        return null;
    }

    public static void CloseChat() {
        CurrentChat.activo = false;
        receptorChat.activo = false;
        receptorChat = null;
        CurrentChat = null;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        Init();
        Invoker.ChangePanel("inicio");
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Body frame = new Body();
                frame.setVisible(true);
            }
        });
    }

}
