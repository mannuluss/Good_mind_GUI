/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Frames;

import app.CommandPattern.Invoker;
import app.Conversacion;
import app.Goodmind;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Mannulus
 */
public class JHomeuser extends javax.swing.JPanel {

    /**
     * Creates new form JHomeuser
     */
    public JHomeuser() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jContacto1 = new Frames.JContacto();

        setBackground(new java.awt.Color(204, 255, 255));
        setMinimumSize(new java.awt.Dimension(343, 640));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1.setBackground(new java.awt.Color(0, 204, 255));
        jButton1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(51, 51, 51));
        jButton1.setText("Nueva Conversar");
        jButton1.setBorder(null);
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 501, 193, 61));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Username");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(138, 135, -1, 30));

        jButton2.setBackground(new java.awt.Color(204, 255, 255));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Estadistica_icon.png"))); // NOI18N
        jButton2.setBorder(null);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(281, 10, -1, 54));

        jButton3.setBackground(new java.awt.Color(204, 255, 255));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Logout_icon.png"))); // NOI18N
        jButton3.setBorder(null);
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
        });
        add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 20, 45, -1));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/good_text.png"))); // NOI18N
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(96, 10, -1, 118));
        jLabel2.getAccessibleContext().setAccessibleDescription("");

        jButton4.setBackground(new java.awt.Color(204, 255, 255));
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/user_icon.png"))); // NOI18N
        jButton4.setBorder(null);
        add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(82, 183, 194, -1));
        add(jContacto1, new org.netbeans.lib.awtextra.AbsoluteConstraints(17, 397, 310, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        if (Goodmind.CurrentChat != null) {
            int opt = JOptionPane.showConfirmDialog(null, "Se cerra la conversacion actual para crear una nueva",
                    "Advertencia", JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.WARNING_MESSAGE);
            if (opt == 0) {
                Goodmind.CloseChat();
                if (Goodmind.CreateConversacion()) {
                    Invoker.ChangePanel("chat");
                } else {
                    System.out.println("NO profesional disponibles");
                }
            }
        } else {
            if (Goodmind.CreateConversacion()) {
                Invoker.ChangePanel("chat");
            } else {
                System.out.println("NO profesional disponibles");
            }
        }
    }//GEN-LAST:event_jButton1MouseClicked

    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked
        Invoker.ChangePanel("inicio");
    }//GEN-LAST:event_jButton3MouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        Invoker.ChangePanel("est");
    }//GEN-LAST:event_jButton2ActionPerformed
    @Override
    public void setVisible(boolean v) {
        super.setVisible(v);
        if (Goodmind.user == null) {
            return;
        }
        System.out.println(Goodmind.user.nombre);
        jLabel1.setText(Goodmind.user.nombre);

        jContacto1.setVisible(Goodmind.CurrentChat != null ? true : false);
        jContacto1.name_user = Goodmind.CurrentChat != null ? Goodmind.CurrentChat.ChatUser : "";
        jContacto1.name.setText(Goodmind.CurrentChat != null ? Goodmind.CurrentChat.ChatUser : "");
        if (Goodmind.CurrentChat != null) {
            System.out.println("seleccionando: " + Goodmind.CurrentChat.ChatUser);
        }
        /*for (Conversacion ch : Goodmind.user.conversaciones) {
            if (ch.activo) {
                jContacto1.name.setText(ch.ChatUser);
                jContacto1.setVisible(true);
                break;
            }else{
                jContacto1.setVisible(false);
            }
        }*/
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private Frames.JContacto jContacto1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables
}
