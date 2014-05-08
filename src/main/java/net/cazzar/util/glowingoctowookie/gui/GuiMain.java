package net.cazzar.util.glowingoctowookie.gui;

import javax.swing.*;
import java.awt.*;

public class GuiMain extends JFrame {
    public final JLabel lblInput =  new JLabel("Input Jar: ");
    public final JTextField txtInput = new JTextField();
    public final JButton btnInput = new JButton("Browse");

    public final JLabel lblOutput = new JLabel("Output Jar: ");
    public final JTextField txtOutput = new JTextField();
    public final JButton btnOutput = new JButton("Browse");

    public final JProgressBar progressBar = new JProgressBar();
    public final JButton btnDeobf = new JButton("Deobfusticate!");;


    public static void main(String[] args) {
        new GuiMain().setVisible(true);
    }

    GuiMain() {
        super("Glowing Octo Wookie");

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        txtInput.setPreferredSize(new Dimension(1, 20));
        initUI();
        pack();
    }

    private void initUI() {
        JPanel panel = new JPanel();
        getContentPane().add(panel);
        GroupLayout layout = new GroupLayout(panel);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        panel.setLayout(layout);

        txtOutput.setPreferredSize(new Dimension(1, 20));
        layout.setHorizontalGroup(layout
                        .createSequentialGroup()
                        .addGroup(
                                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                        .addComponent(lblInput)
                                                        .addComponent(txtInput)
                                                        .addComponent(btnInput)
                                        )
                                        .addGroup(layout.createSequentialGroup()
                                                        .addComponent(lblOutput)
                                                        .addComponent(txtOutput)
                                                        .addComponent(btnOutput)
                                        )
                                        .addComponent(progressBar)
                                        .addComponent(btnDeobf)
                        )
        );

        layout.setVerticalGroup(layout
                        .createSequentialGroup()
                        .addGroup(
                                layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblInput)
                                        .addComponent(txtInput)
                                        .addComponent(btnInput)
                        )
                        .addGroup(
                                layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblOutput)
                                        .addComponent(txtOutput)
                                        .addComponent(btnOutput)
                        )
                        .addGroup(
                                layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(progressBar)
                        )
                        .addGroup(
                                layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnDeobf)
                        )
        );
    }
}
