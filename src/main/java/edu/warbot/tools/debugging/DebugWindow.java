package edu.warbot.tools.debugging;

import javax.swing.*;
import java.awt.*;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.management.ManagementFactory;

/**
 * Created by BEUGNON on 04/07/2015.
 * <p/>
 * DebugWindow. utilisé quand on a besoin d'avoir de débugguer une partie par processus.
 */
@SuppressWarnings("serial")
public class DebugWindow extends JFrame {


    public DebugWindow() {
        super("Warbot-" + ManagementFactory.getRuntimeMXBean().getName());
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setPreferredSize(new Dimension(800, 600));
        JTextArea jta = new JTextArea();
        jta.setEditable(true);
        JScrollPane jsp = new JScrollPane();
        jsp.createVerticalScrollBar();
        jsp.setViewportView(jta);

        super.getContentPane().add(jsp);
        super.pack();
        super.setVisible(true);
        OutputStream dos = new DocumentOutputStream(jta.getDocument());
        System.setOut(new PrintStream(dos));
        System.setErr(new PrintStream(dos));

    }
}
