
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import javax.swing.JFileChooser;
import java.io.*;

/**
 *
 * @author Vaijyant
 */
public class CopyToFlash extends JFrame implements ActionListener {

    private JLabel img;
    private JTextField addr;
    private JButton br, st, re;
    private JCheckBox drives[] = new JCheckBox[10];
    private JPanel p1, p2, p2_1, p2_3, p2_2;
    private JLabel desc;
    private JProgressBar pb;
    private ButtonGroup g;
    private JFileChooser fc;
    private File f;
    private int i = 0, j;
    private String pat;
    private ImageIcon ico;
    boolean flag = false;

    /**
     * Creates new form CopyToFlash
     */
    public CopyToFlash() {
        initComponents();
        setTitle("Copy to Flash - by Vaijyant Tomar");
        setLayout(new GridLayout(2, 1, 10, 10));
        setIconImage(new ImageIcon(this.getClass().getResource("/images/VT.jpg")).getImage());

        fc = new JFileChooser(".");

        p1 = new JPanel(new FlowLayout());
        p2 = new JPanel(new GridLayout(1, 3, 5, 5));
        add(p1);
        add(p2);

        p2_1 = new JPanel(new GridLayout(3, 1, 10, 10));
        p2_2 = new JPanel(new GridLayout(2, 1, 10, 55));
        p2_3 = new JPanel(new GridLayout(0, 2));

        img = new JLabel("");
        img.setIcon(new ImageIcon(this.getClass().getResource("/images/copyImg.jpg")));

        addr = new JTextField();
        addr.setEditable(false);
        br = new JButton("Browse...");
        st = new JButton("Start");
        st.setEnabled(false);

        pb = new JProgressBar(0, 100);

        re = new JButton("Refresh List >>");

        p1.add(img);
        p2.add(p2_1);
        p2_1.add(addr);
        p2_1.add(br);
        p2_1.add(st);

        p2_2.add(re);
        p2_2.add(pb);

        p2.add(p2_2);

        p2.add(new JScrollPane(p2_3));

        getDisks();
        pack();

        br.addActionListener(CopyToFlash.this);
        st.addActionListener(CopyToFlash.this);
        re.addActionListener(CopyToFlash.this);
    }

    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource() == br) {
            int x = fc.showOpenDialog(this);
            if (x == JFileChooser.APPROVE_OPTION) {
                f = fc.getSelectedFile();
                addr.setText(f.getAbsolutePath());
                st.setEnabled(true);
                flag = true;
            }
        }
        if (evt.getSource() == st) {

            if (flag) {
                st.setEnabled(false);
                re.setEnabled(false);
                Thread t = new Thread() {
                    public void run() {
                        pb.setIndeterminate(true);
                        pb.setString("Copying, Please Wait...");
                        pb.setStringPainted(true);
                        for (i = j; i >= 0; i--) {
                            if (drives[i].isSelected()) {
                                pat = drives[i].getText();
                                copyFile(pat);
                            }
                        }
                        pb.setIndeterminate(false);
                        pb.setString("File Copied!");
                        pb.setValue(100);
                        re.setEnabled(true);
                        JOptionPane.showMessageDialog(CopyToFlash.this, "File Copied\nClick OK to return", "Transfer Complete", JOptionPane.PLAIN_MESSAGE);
                        refresh();
                    }
                };
                t.start();
            } else {
                JOptionPane.showMessageDialog(CopyToFlash.this, "No flash Drive deteceted.", "Warning!", JOptionPane.PLAIN_MESSAGE);
                refresh();
            }
        }
        if (evt.getSource() == re) {
            refresh();
        }
    }

    public void refresh() {

        i = 0;
        p2_3.removeAll();
        p2_3.repaint();
        p2_3.revalidate();

        getDisks();

        addr.setText("");
        st.setEnabled(false);
        pb.setString("");
        pb.setValue(0);
        flag = false;



    }

    private void getDisks() {


        File[] paths;
        File dir;
        String p, name;
        FileSystemView fsv = FileSystemView.getFileSystemView();

        // returns pathnames for files and directory
        paths = File.listRoots();

        // for each pathname in pathname array
        for (File path : paths) {

            // prints file and directory paths
            if (fsv.getSystemTypeDescription(path).equals("Removable Disk")) {
                p = path.toString();
                dir = new File(p);
                name = fsv.getSystemDisplayName(dir);

                drives[i] = new JCheckBox(p, true);
                desc = new JLabel(name);
                p2_3.add(drives[i]);
                p2_3.add(desc);
                i++;
                flag = true;
            }
        }
        j = i - 1;

    }

    void copyFile(String p) {
        try {
            File sourceFile = new File(f.getAbsolutePath());
            File destinationFile = new File(p + "\\" + sourceFile.getName());
            FileOutputStream fileOutputStream;
            try (FileInputStream fileInputStream = new FileInputStream(sourceFile)) {
                fileOutputStream = new FileOutputStream(destinationFile);
                int bufferSize;
                byte[] bufffer = new byte[512];
                while ((bufferSize = fileInputStream.read(bufffer)) > 0) {
                    fileOutputStream.write(bufffer, 0, bufferSize);
                }
            }
            fileOutputStream.close();
        } catch (Exception e) {
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CopyToFlash.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CopyToFlash.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CopyToFlash.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CopyToFlash.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        CopyToFlash ctf = new CopyToFlash();
        ctf.setLocationRelativeTo(null);
        ctf.setSize(725, 300);
        ctf.setVisible(true);
        ctf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ctf.setResizable(false);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
