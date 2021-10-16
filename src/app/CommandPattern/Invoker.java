/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.CommandPattern;

import Frames.Body;
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

    public void AddCmd(){
        
    }
    public static void ChangePanel(String name){
        System.out.println(Layouts.size());
        currentView = name;
        Layouts.forEach((e)->{
            System.out.println(e.panel);
            e.panel.setVisible(e.name.equals(name));
        });
        //Body.home1.setVisible(false);
        //Body.estadisticas1.setVisible(true);
    }
    public static void Back(){
        Body.home1.setVisible(true);
        Body.estadisticas1.setVisible(false);
    }
}
