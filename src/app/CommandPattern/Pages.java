/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.CommandPattern;

import javax.swing.JPanel;

/**
 *
 * @author Mannulus
 */
public class Pages {
    public Pages(String name, JPanel panel) {
        this.name = name;
        this.panel = panel;
    }
    public String name;
    public javax.swing.JPanel panel;
    
}
