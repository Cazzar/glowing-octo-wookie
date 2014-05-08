package net.cazzar.util.glowingoctowookie.gui;

import net.cazzar.util.glowingoctowookie.Main;
import net.cazzar.util.glowingoctowookie.internal.UserProgressListener;
import net.cazzar.util.glowingoctowookie.util.JarParser;
import org.apache.commons.io.FilenameUtils;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class GuiMain extends JFrame {
    public final JLabel lblInput =  new JLabel("Input Jar: ");
    public final JTextField txtInput = new JTextField();
    public final JButton btnInput = new JButton("Browse");

    public final JLabel lblOutput = new JLabel("Output Jar: ");
    public final JTextField txtOutput = new JTextField();
    public final JButton btnOutput = new JButton("Browse");

    public final JLabel lblForge = new JLabel("Forge Environment: ");
    public final JTextField txtForge = new JTextField();
    public final JButton btnForge = new JButton("Browse");

    public final JProgressBar progressBar = new JProgressBar(0, 100);
    public final JButton btnDeobf = new JButton("Deobfusticate!");


    public static void main(String[] args) {
        new GuiMain().setVisible(true);
    }

    GuiMain() {
        super();
        setTitle("Glowing Octo Wookie");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        txtInput.setPreferredSize(new Dimension(1, 20));
        initUI();
        initComponents();
        pack();
    }

    private void initComponents() {
        final FileFilter filter = new FileFilter() {
            @Override
            public boolean accept(File f) {
                return FilenameUtils.isExtension(f.getAbsolutePath(), new String[]{"jar", "zip"});
            }

            @Override
            public String getDescription() {
                return "Mod Files";
            }
        };

        btnInput.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.setDialogType(JFileChooser.OPEN_DIALOG);
                chooser.setFileFilter(filter);
                chooser.setMultiSelectionEnabled(false);
                chooser.showOpenDialog(GuiMain.this);
                txtInput.setText(chooser.getSelectedFile().getAbsolutePath());
            }
        });

        btnOutput.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.setDialogType(JFileChooser.SAVE_DIALOG);
                chooser.setFileFilter(filter);
                chooser.setMultiSelectionEnabled(false);
                chooser.showSaveDialog(GuiMain.this);
                txtOutput.setText(chooser.getSelectedFile().getAbsolutePath());
            }
        });

        btnForge.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.setDialogType(JFileChooser.OPEN_DIALOG);
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                chooser.setMultiSelectionEnabled(false);
                chooser.showSaveDialog(GuiMain.this);
                txtForge.setText(chooser.getSelectedFile().getAbsolutePath());
            }
        });

        btnDeobf.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (txtInput.getText() == null || txtInput.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(GuiMain.this, "You need to set an input");
                    return;
                }

                if (txtOutput.getText() == null || txtOutput.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(GuiMain.this, "You need to set an output");
                    return;
                }

                if (txtForge.getText() == null || txtForge.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(GuiMain.this, "You need to set a setup for ForgeGradle/build/unpacked");
                    return;
                }

                final File in = new File(txtInput.getText());
                final File out = new File(txtOutput.getText());

                if (!out.exists()) try {
                    //noinspection ResultOfMethodCallIgnored
                    out.createNewFile();

                    Main.setConfig(new File(txtForge.getText()));

                    JarParser.parseJar(in, out, new UserProgressListener() {
                        @Override
                        public void itemCompleted() {
                            super.itemCompleted();

                            synchronized (GuiMain.this.progressBar) {
                                progressBar.setValue((int) Math.round(getProgress()));
                            }
                        }
                    });
                } catch (IOException e1) {
                    JOptionPane.showMessageDialog(GuiMain.this, "There was a error creating the output file.");
                }
            }
        });

    }

    private void initUI() {
        JPanel panel = new JPanel();
        getContentPane().add(panel);
        GroupLayout layout = new GroupLayout(panel);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        panel.setLayout(layout);

        txtOutput.setPreferredSize(new Dimension(50, 20));

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
                                        .addGroup(layout.createSequentialGroup()
                                                        .addComponent(lblForge)
                                                        .addComponent(txtForge)
                                                        .addComponent(btnForge)
                                        )
                                        .addGroup(layout.createSequentialGroup()
                                                        .addComponent(progressBar)
                                                        .addComponent(btnDeobf)
                                        )
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
                                        .addComponent(lblForge)
                                        .addComponent(txtForge)
                                        .addComponent(btnForge)
                        )
                        .addGroup(
                                layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(progressBar)
                                        .addComponent(btnDeobf)
                        )
        );
    }
}
