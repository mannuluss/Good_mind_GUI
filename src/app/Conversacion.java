package app;
import java.util.ArrayList;
import java.io.Serializable;

/**
 * Clase que representa la conversación
 * 
 * @author Valentina Escobar, Daniel Gonzalez y Felipe Rojas 
 * 
 */
public class Conversacion implements Serializable {
    public ArrayList<Mensaje> chat = new ArrayList<>();
    public boolean activo = true;
    public String ChatUser;
    public String id_remitente;

    /**
     * Constructor for objects of class Conversacion
     */
    public Conversacion() {

    }
    public Conversacion(String touser,String id_remitente) {
        ChatUser = touser;
        this.id_remitente = id_remitente;
    }
    public void AddMsj(String msj,TypeMenssage typemsj,String id_remitente){
        chat.add(new Mensaje(msj, typemsj, id_remitente));
    }
}
