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
    /**
     * arreglo que contiene a todos los usuarios registrados en la plataforma
     */
    public static ArrayList<Usuario> Allusers = new ArrayList<Usuario>();

    public static void Init() {
        Allusers.addAll(Server.GetAllUsers("./users.json"));
    }

    /**
     * Esta método representa la creación del usuario
     *
     */
    private static void SectionCreateUser() {
        //Scanner sc = new Scanner(System.in);
        boolean wait = true;
        do {
            System.out.println("Creando Usuario");
            System.out.print("profesional o usuario (p/u): ");
            Role RoleUser = sc.next().compareTo("p") == 0 ? Role.profesional : Role.usuario;
            System.out.print("nombre: ");
            String name = sc.next();
            System.out.print("contraseña: ");
            String password = sc.next();
            Usuario newuser = VerifyCredencials(name, password, RoleUser);
            if (newuser == null) {
                wait = false;
                user = CreateAcount(name, password, RoleUser);
                System.out.println("Usuario Creado Exitosamente");
                //sc.close();
                return;
            } else {
                System.out.println("Usuario ya registrado...");
            }
            System.out.println("1. regresar a la pantalla principal");
            System.out.println("2. intentarlo de nuevo");
            System.out.print("Respuesta: ");
            if (sc.next().equals("1")) {
                wait = false;
                //sc.close();
                return;
            }
        } while (wait);
        //sc.close();
    }

    /**
     * Esta método representa la sección del profesional
     *
     */
    private static void SeccionProfesional() {
        boolean exit = false;
        do {
            System.out.println("========= " + user.nombre + " =============");
            //Scanner sc = new Scanner(System.in);
            System.out.println("> 1. Conversar");
            System.out.println("> 3. Cerrar sesion");
            System.out.print("Ingrese un numero de opcion : ");
            String opt = sc.next();
            if (opt.compareTo("1") == 0) {
                Conversar();
            }
            if (opt.compareTo("3") == 0) {
                exit = true;
                user = null;
                //sc.close();
                return;
            }
            //sc.close();
        } while (!exit);
    }

    /**
     * seccion principal del usuario (Home)
     */
    private static void SeccionUser() {
        boolean exit = false;
        do {
            System.out.println("========= " + user.nombre + " =============");
            //Scanner sc = new Scanner(System.in);
            System.out.println("> 1. Conversar");
            System.out.println("> 2. Estadisticas");
            System.out.println("> 3. Cerrar sesion");
            System.out.print("Ingrese un numero de opcion : ");
            String opt = sc.next();
            if (opt.compareTo("1") == 0) {
                Conversar();
            }
            if (opt.compareTo("2") == 0) {
                Estadisticas();
            }
            if (opt.compareTo("3") == 0) {
                exit = true;
                user = null;
                //sc.close();
                return;
            }
            //sc.close();
        } while (!exit);
    }

    /**
     * Esta método representa las estadísticas de las emociones del usuario
     *
     */
    private static void Estadisticas() {
        //Scanner sc = new Scanner(System.in);
        float total = user.registro.GetTotal();
        System.out.println("============= Estadisticas ====================");
        System.out.println("Total emociones registradas: " + user.registro.GetTotal());
        System.out.print("positivas: ");
        PrintBar(user.registro.positivos, total);
        System.out.print("negativos: ");
        PrintBar(user.registro.negativos, total);
        System.out.print("neutros: ");
        PrintBar(user.registro.neutros, total);
        boolean exit = false;
        System.out.println("");
        System.out.println("> 1. regresar");
        if (user.notificacion) {
            System.out.println("> 2. Ha sido notificado como usuario en condición de riesgo, para mayor infomación");
        }
        System.out.print("Respuesta: ");
        do {
            String res = sc.next();
            if (res.equals("1")) {
                return;
            }
            if (res.equals("2") && user.notificacion) {
                exit = true;
                Notify();
            }
        } while (!exit);
        //sc.close();
    }

    /**
     * Esta método representa la información sobre consultorios de profesionales
     * de la salud cerca a la ubicación del usuario.
     *
     */
    private static void Notify() {
        System.out.println("============== Consultorios =================");
        // revisa por ip y solicitar activar GPS del dispositivo para buscar
        // consultorios cerca
        System.out.println("Tu ubicacion actual es: " + "080001");

        ArrayList<consultorio> array = Server.GetConsultorios("080001");
        for (consultorio item : array) {
            System.out.println("____________________________________");
            System.out.println(item.nombre);
            System.out.println(item.descripcion);
        }
        if (array.size() == 0) {
            System.out.println("no se encontro consultorio cerca de tu zona");
        }
        // System.out.println("presione cualquier boton para volver ...");
    }

    /**
     * Este método representa la parte porcental de las estadísticas de las
     * emociones del usuario.
     *
     * @param value cantidad
     * @param total total de valores
     */
    private static void PrintBar(float value, float total) {
        System.out.print("[");
        float percent = 0;
        if (total != 0) {
            percent = value / total;
        }
        int temp = 1;
        for (int index = 0; index < 10; index++) {
            if (temp < percent * 10) {
                System.out.print("◘");
                temp++;
            } else {
                System.out.print(" ");
            }
        }

        System.out.println("]  " + value + "/" + total + " => " + percent * 100 + "%");

    }

    /**
     * Este método representa la conversación entre usuario y profesional o
     * viceversa
     *
     * Cuando un usuario o profesional desea conversar con alguien
     *
     * en caso de ser el profesional obtiene una lista de usuarios con los que
     * ha hablado. en caso de ser un usuario obtiene de manera aleatorio un
     * profesional con el cual hablar.
     */
    private static void Conversar() {
        //Scanner sc = new Scanner(System.in);
        if (user.role == Role.usuario) {
            Usuario pro = Usuario.RamdomProfesional(Allusers);
            if (pro == null) {
                System.out.println("NO hay usuarios disponibles intente mas tarde");
            } else {
                System.out.println("Iniciando conversacion con: " + pro.nombre);
                Conversacion conversation = user.InitChat(pro.nombre);
                Conversacion prochat = pro.InitChat(user.nombre);
                SeccionChat(conversation, prochat);
            }
        } else {
            // var allchats = user.GetListNameChat(false);
            boolean exit = false;
            do {
                System.out.println("=========== Conversaciones ============");
                if (user.conversaciones.size() == 0) {
                    System.out.println("No Existen conversaciones");
                }
                for (int i = 0; i < user.conversaciones.size(); i++) {
                    Conversacion chat = user.conversaciones.get(i);
                    System.out.println(i + ". " + chat.ChatUser + " Activo: " + chat.activo);
                }
                System.out.println("-1. Regresar");
                System.out.print("Respuesta: ");
                int opt = sc.nextInt();
                if (opt <= -1) {
                    return;
                }
                if (opt < Allusers.size()) {

                    Usuario touser = GetUser(user.conversaciones.get(opt).ChatUser, Role.usuario);
                    Conversacion chatuser = touser.InitChat(user.nombre);
                    SeccionChat(user.conversaciones.get(opt), chatuser);
                } else {
                    System.out.println("respuesta NO valida");
                }
            } while (!exit);
        }
    }

    /**
     * Esta método representa las partes o secciones de la conversación
     *
     * @param Chatfrom chat remitente
     * @param ChatDestino chat destino
     */
    public static void SeccionChat(Conversacion Chatfrom, Conversacion ChatDestino) {
        System.out.println("============== Chat (" + Chatfrom.ChatUser + ") ==================");
        boolean exit = false;
        //Scanner sc = new Scanner(System.in);
        //PrintConversacion(Chatfrom);
        do {
            // Aqui se imprimiria la respuesta de la otra persona
            System.out.println("> 1.texto");
            System.out.println("> 2.voz");
            if (user.role == Role.usuario) {
                System.out.println("> 3.emocion");
            }
            System.out.println("> 4.back");
            System.out.print("Respuesta: ");
            String option = sc.next();
            switch (option) {
                case "1":
                    System.out.print("Ingrese Texto: ");
                    String msj = sc.next();
                    Chatfrom.AddMsj(msj, TypeMenssage.text, user.id);
                    ChatDestino.AddMsj(msj, TypeMenssage.text, user.id);
                    break;
                case "2":
                    System.out.print("Ingrese voz: ");
                    String msjv = sc.next();
                    Chatfrom.AddMsj(msjv, TypeMenssage.voice, user.id);
                    ChatDestino.AddMsj(msjv, TypeMenssage.voice, user.id);
                    break;
                case "3":
                    if (user.role != Role.usuario) {
                        System.out.println("Opcion no valida");
                        break;
                    }
                    System.out.println("Registrar Emociones: ");
                    for (int i = 0; i < emociones.values().length; i++) {
                        emociones m = emociones.values()[i];
                        System.out.println(i + ". " + m.name());
                    }
                    System.out.print("Ingrese: ");
                    int opt = sc.nextInt();
                    emociones emocion = emociones.values()[opt];
                    user.registro.registrar(emocion);
                    Chatfrom.AddMsj(emocion.name(), TypeMenssage.emocion, user.id);
                    ChatDestino.AddMsj(emocion.name(), TypeMenssage.emocion, user.id);
                    break;
                case "4":
                    exit = true;
                    return;
            }
        } while (!exit);
    }

    /**
     * Metodo que permite el login de un usuario en la aplicacion
     */
    public static void Login(Role RoleUser, String nameuser, String password) {
        System.out.println("============ Inicio de sesion ============");
        user = VerifyCredencials(nameuser, password, RoleUser);
        if (user != null) {
            System.out.println("Login Exitoso");
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
