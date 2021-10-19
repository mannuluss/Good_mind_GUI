/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.CommandPattern;

import Frames.Body;
import app.Goodmind;
import app.Role;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mannulus
 */
public class Invoker {

    private static List<ICommand> cmds = new ArrayList<ICommand>();
    public static ArrayList<Pages> Layouts = new ArrayList<Pages>();
    private static String currentView = "inicio";
    private static String prevView = "";

    public void AddCmd() {

    }

    public static void ChangePanel(String name) {
        prevView = currentView;
        currentView = name;
        Layouts.forEach((e) -> {
            //System.out.println(e.panel);
            e.panel.setVisible(e.name.equals(name));
        });
        //Body.home1.setVisible(false);
        //Body.estadisticas1.setVisible(true);
    }

    public static void Back() {
        System.out.println(prevView);
        if (currentView.equals("chat")){
            if (Goodmind.user.role == Role.profesional){
                ChangePanel("Hpro");
            }
            if (Goodmind.user.role == Role.usuario){
                ChangePanel("Hpro");
            }
            return;
        }
        
        Layouts.forEach((e) -> {
            if (e.name.equals(prevView)) {
                e.panel.setVisible(true);
                currentView = e.name;
            } else if (e.name.equals(currentView)) {
                e.panel.setVisible(false);
            }
        });
        
        //Body.home1.setVisible(true);
        //Body.estadisticas1.setVisible(false);
    }
}
