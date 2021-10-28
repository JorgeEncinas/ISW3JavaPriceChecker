/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package verificadorat2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.imageio.ImageIO;
import java.util.concurrent.TimeUnit;
import javax.swing.JOptionPane;

/**
 *
 * @author jorge
 */
public class windowP extends javax.swing.JFrame {

    //private HashMap<String, Runnable> statesMap;
    private String productKey;
    private ScheduledExecutorService executor;
    private ScheduledFuture<?> timerControl;
    private boolean timerActive;
    /**
     * Creates new form windowP
     */
    public windowP() {
        initComponents();
        this.setVisible(true);
        this.setLayout(null);
        this.setExtendedState( getExtendedState() | JFrame.MAXIMIZED_BOTH);
        this.setResizable(false);
        this.getContentPane().setBackground(new Color(153,255,153));
        //stateTimer = new Timer();
        productKey = "";
        timerActive = false;
        //extimer = Executors.newSingleThreadScheduledExecutor();
        executor = Executors.newScheduledThreadPool(1);
        setLayout();
        clean();
        start();
    }
    
    private void startTimer() {
        timerControl = executor.schedule(new Runnable() {
            @Override
            public void run() {
                try {
                    clean();
                    start();
                    timerActive = false;
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.toString());
                }
            }
        },8, TimeUnit.SECONDS);
        timerActive = true;
    }
    
    private void start() {
        //Changes to interface here
        //Only text-align: center is necessary
        //Ins1.setText("<html><body style=\" text-align: center; max-width:"+ this.getWidth()*0.90 +"px; max-height:"+ this.getHeight()*0.40 +"px; \">¿Desea obtener más información del producto?</body></html>");
        Ins1.setText("<html><body style=\" text-align: center; \">¿Desea obtener más información del producto?</body></html>");
        Ins2.setText("<html><body style=\" text-align: center; \">Coloque el código de barras del producto frente al lector</body></html>");
        Ins1.setLocation(((int)((this.getWidth() * 0.50) - (Ins1.getWidth() * 0.50))),
                                       ((int) ((this.getHeight() * 0.25) - (Ins1.getHeight()*0.50))));
        Ins2.setLocation(((int)((this.getWidth() * 0.50) - (Ins2.getWidth() * 0.50))),
                                       ((int) ((this.getHeight() * 0.70) - (Ins2.getHeight()*0.50))));
        Ins1.setVisible(true);
        Ins2.setVisible(true);
        ScanImg.setVisible(true);
    }
    private void loading() {
        //Changes to interface here
        Ins1.setText("<html><body style=\" text-align:center; \">Cargando...</body></html>");
        Ins1.setLocation(((int)((this.getWidth() * 0.50) - (Ins1.getWidth() * 0.50))),
                          ((int) ((this.getHeight() * 0.50) - (Ins1.getHeight()*0.50))));

        //jLabelScanImg.setText();
        Ins1.setHorizontalAlignment(JLabel.CENTER);
        ScanImg.setVisible(true);
    }
    private void retrieved() {
        //Changes to interface here
        ProdName.setVisible(true);
        ProdCont.setVisible(true);
        ScanBottom.setVisible(true);
        InsBottom.setVisible(true);
        ProdImg.setVisible(true);
    }    
    private void readError() {
        //Changes to interface here
        Ins1.setText("<html><body style=\" text-align: center; \">¡Lo sentimos! <br> Ha ocurrido un error al leer el código de barras.</body></html>");
        Ins2.setText("<html><body style=\" text-align: center; \">Intente pasar el código de nuevo, o <br> Pase a Servicio al Cliente, <br> ¡Con gusto le atenderemos!</body></html>");
        Ins1.setLocation(((int)((this.getWidth() * 0.50) - (Ins1.getWidth() * 0.50))),
                        ((int) ((this.getHeight() * 0.25) - (Ins1.getHeight()*0.50))));
        Ins2.setLocation(((int)((this.getWidth() * 0.50) - (Ins2.getWidth() * 0.50))),
                        ((int) ((this.getHeight() * 0.70) - (Ins2.getHeight()*0.50))));
        //jLabelScanImg.setVisible(true);
        Ins1.setVisible(true);
        Ins2.setVisible(true);
        Service.setVisible(true);
        ScanBottom.setVisible(true);
        InsBottom.setVisible(true);
    }
    private void notFound() {
        //Changes to interface here
        Ins1.setText("<html><body style=\" text-align:center; \">¡Lo sentimos! \n No se ha encontrado el producto leído.</body></html>");
        Ins2.setText("<html><body style=\" text-align:center; \">Pase a Servicio al Cliente, \n ¡Con gusto le atenderemos!</body></html>");
        Ins1.setLocation(((int)((this.getWidth() * 0.50) - (Ins1.getWidth() * 0.50))),
                        ((int) ((this.getHeight() * 0.25) - (Ins1.getHeight()*0.50))));
        Ins2.setLocation(((int)((this.getWidth() * 0.50) - (Ins2.getWidth() * 0.50))),
                        ((int) ((this.getHeight() * 0.70) - (Ins2.getHeight()*0.50))));
        Ins1.setVisible(true);
        Ins2.setVisible(true);
        Service.setVisible(true);
        ScanBottom.setVisible(true);
        InsBottom.setVisible(true);
    }
    
    public void setLayout() {
        ScanImg.setText("");
        Logo.setText("");
        ProdImg.setText("");
        ScanBottom.setText("");
        Service.setText("");

        Ins1.setSize(new Dimension(((int)(this.getWidth()*0.90)),((int)(this.getHeight()*0.40))));
        Ins2.setSize(new Dimension(((int)(this.getWidth()*0.90)),((int)(this.getHeight()*0.40))));
        InsBottom.setSize(new Dimension(((int)(this.getWidth()*0.60)),((int)(this.getHeight()*0.40))));
        ProdName.setSize(new Dimension(((int)(this.getWidth()*0.70)),((int)(this.getHeight()*0.20))));
        ProdImg.setSize((int)(this.getWidth()*0.40), (int) (this.getHeight()*0.40));
        ProdCont.setSize(new Dimension(((int)(this.getWidth()*0.50)),((int)(this.getHeight()*0.50))));
        
        ScanImg.setSize((int)(this.getWidth()*0.20), (int) (this.getHeight()*0.25));
        ScanImg.setHorizontalAlignment(JLabel.CENTER);
        ScanImg.setIcon(new ImageIcon(
            new ImageIcon(getClass().getClassLoader().getResource("img/cropped-scangif-2.gif")).getImage().getScaledInstance((int)(this.getWidth()*0.20), (int)(this.getHeight()*0.25), Image.SCALE_DEFAULT)));
        
        ScanBottom.setSize((int)(this.getWidth()*0.16), (int) (this.getHeight()*0.20));
        ScanBottom.setHorizontalAlignment(JLabel.CENTER);
        ScanBottom.setIcon(new ImageIcon(
            new ImageIcon(getClass().getClassLoader().getResource("img/cropped-scangif-2.gif")).getImage().getScaledInstance((int)(this.getWidth()*0.16), (int)(this.getHeight()*0.20), Image.SCALE_DEFAULT)));
        
        Logo.setSize((int)(this.getWidth()*0.25), (int) (this.getHeight()*0.10));
        Logo.setIcon(new ImageIcon(
            new ImageIcon(getClass().getClassLoader().getResource("img/placeholder-logo2.png")).getImage().getScaledInstance((int)(this.getWidth()*0.25), (int)(this.getHeight()*0.10), Image.SCALE_DEFAULT)));
        Logo.setHorizontalAlignment(JLabel.CENTER);
        
        Service.setSize((int)(this.getWidth()*0.17), (int)(this.getHeight()*0.27));
        Service.setIcon(new ImageIcon(
            new ImageIcon(getClass().getClassLoader().getResource("img/clerk-2.png")).getImage().getScaledInstance((int)(this.getWidth()*0.17), (int)(this.getHeight()*0.27), Image.SCALE_DEFAULT)));
        Service.setHorizontalAlignment(JLabel.CENTER);
        
        ScanImg.setLocation(((int)((this.getWidth() * 0.50) - (ScanImg.getWidth() * 0.50))),
               ((int) ((this.getHeight() * 0.45) - (ScanImg.getHeight()*0.50))));
        ScanBottom.setLocation(((int)(this.getWidth() * 0.05)),
               ((int) (this.getHeight() * 0.75)));
        Logo.setLocation(((int)((this.getWidth() * 0.73))),
               ((int) ((this.getHeight() * 0.018))));
        Service.setLocation(((int)((this.getWidth() * 0.50) - (Service.getWidth() * 0.50))),
               ((int) ((this.getHeight() * 0.45) - (Service.getHeight()*0.50))));
        ProdImg.setLocation(((int)(this.getWidth() * 0.10)),
               ((int) ((this.getHeight() * 0.30))));
        
        InsBottom.setText("<html><body>Puede pasar otro producto <br> en cualquier momento.</body></html>");
        InsBottom.setLocation(((int)(this.getWidth() * 0.22)), ((int)(this.getHeight() * 0.66)));
        ProdName.setLocation(((int)(this.getWidth() * 0.10)),
               ((int) ((this.getHeight() * 0.10))));
        ProdCont.setLocation(((int)(this.getWidth() * 0.60)),
               ((int) ((this.getHeight() * 0.15))));
        Logo.setVisible(true);
    }
    
    public void clean() {
        //Remove everything
        Ins1.setVisible(false);
        Ins2.setVisible(false);
        InsBottom.setVisible(false);
        ProdName.setVisible(false);
        ProdCont.setVisible(false);
        ScanImg.setVisible(false);
        ScanBottom.setVisible(false);
        ProdImg.setVisible(false);
        Service.setVisible(false);

    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Ins1 = new javax.swing.JLabel();
        Ins2 = new javax.swing.JLabel();
        InsBottom = new javax.swing.JLabel();
        ProdName = new javax.swing.JLabel();
        ProdCont = new javax.swing.JLabel();
        ScanImg = new javax.swing.JLabel();
        ScanBottom = new javax.swing.JLabel();
        ProdImg = new javax.swing.JLabel();
        Service = new javax.swing.JLabel();
        Logo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        Ins1.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        Ins1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Ins1.setText("¿Desea saber más de un producto?");
        Ins1.setMaximumSize(new Dimension(((int)(this.getWidth()*0.90)), ((int) (this.getHeight()*0.40))));
        Ins1.setMinimumSize(new Dimension(((int)(this.getWidth()*0.90)), ((int) (this.getHeight()*0.40))) );
        Ins1.setPreferredSize(new java.awt.Dimension(921, 307));

        Ins2.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        Ins2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Ins2.setText("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        Ins2.setMaximumSize(new Dimension(((int)(this.getWidth()*0.90)), ((int) (this.getHeight()*0.40))));
        Ins2.setMinimumSize(new Dimension(((int)(this.getWidth()*0.90)), ((int) (this.getHeight()*0.40))));
        Ins2.setName(""); // NOI18N
        Ins2.setPreferredSize(new Dimension(((int)(this.getWidth()*0.90)), ((int) (this.getHeight()*0.40))) );

        InsBottom.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        InsBottom.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        InsBottom.setText("jLabel1");
        InsBottom.setMaximumSize(new Dimension(((int)(this.getWidth()*0.60)), ((int) (this.getHeight()*0.40))));
        InsBottom.setMinimumSize(new Dimension(((int)(this.getWidth()*0.60)), ((int) (this.getHeight()*0.40))));
        InsBottom.setPreferredSize(new Dimension(((int)(this.getWidth()*0.60)), ((int) (this.getHeight()*0.40))));

        ProdName.setFont(new java.awt.Font("Tahoma", 1, 28)); // NOI18N
        ProdName.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ProdName.setText("ProdName");
        ProdName.setMaximumSize(new Dimension(((int)(this.getWidth()*0.70)), ((int) (this.getHeight()*0.20))));
        ProdName.setMinimumSize(new Dimension(((int)(this.getWidth()*0.70)), ((int) (this.getHeight()*0.20))));
        ProdName.setPreferredSize(new Dimension(((int)(this.getWidth()*0.70)), ((int) (this.getHeight()*0.20))));

        ProdCont.setFont(new java.awt.Font("Tahoma", 0, 28)); // NOI18N
        ProdCont.setText("jLabel1");
        ProdCont.setMaximumSize(new Dimension(((int)(this.getWidth()*0.50)), ((int) (this.getHeight()*0.50))));
        ProdCont.setMinimumSize(new Dimension(((int)(this.getWidth()*0.50)), ((int) (this.getHeight()*0.50))));
        ProdCont.setPreferredSize(new Dimension(((int)(this.getWidth()*0.50)), ((int) (this.getHeight()*0.50))));

        ScanImg.setMaximumSize(new Dimension(((int)(this.getWidth()*0.20)), ((int) (this.getHeight()*0.25))));
        ScanImg.setMinimumSize(new Dimension(((int)(this.getWidth()*0.20)), ((int) (this.getHeight()*0.25))));
        ScanImg.setPreferredSize(new Dimension(((int)(this.getWidth()*0.20)), ((int) (this.getHeight()*0.25))));

        ScanBottom.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ScanBottom.setText("jLabel1");
        ScanBottom.setMaximumSize(new Dimension(((int)(this.getWidth()*0.15)), ((int) (this.getHeight()*0.20))));
        ScanBottom.setMinimumSize(new Dimension(((int)(this.getWidth()*0.15)), ((int) (this.getHeight()*0.20))));
        ScanBottom.setPreferredSize(new Dimension(((int)(this.getWidth()*0.15)), ((int) (this.getHeight()*0.20))));

        ProdImg.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ProdImg.setText("ProdImg");
        ProdImg.setMaximumSize(new Dimension(((int)(this.getWidth()*0.30)), ((int) (this.getHeight()*0.35))) );
        ProdImg.setMinimumSize(new Dimension(((int)(this.getWidth()*0.30)), ((int) (this.getHeight()*0.35))) );
        ProdImg.setPreferredSize(new Dimension(((int)(this.getWidth()*0.30)), ((int) (this.getHeight()*0.35))));

        Service.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Service.setText("Service");
        Service.setMaximumSize(new Dimension(((int)(this.getWidth()*0.20)), ((int) (this.getHeight()*0.35))));
        Service.setMinimumSize(new Dimension(((int)(this.getWidth()*0.20)), ((int) (this.getHeight()*0.35))));
        Service.setPreferredSize(new Dimension(((int)(this.getWidth()*0.20)), ((int) (this.getHeight()*0.35))));

        Logo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Logo.setText("Logo");
        Logo.setMaximumSize(new Dimension(((int)(this.getWidth()*0.25)), ((int) (this.getHeight()*0.12))));
        Logo.setMinimumSize(new Dimension(((int)(this.getWidth()*0.25)), ((int) (this.getHeight()*0.12))));
        Logo.setPreferredSize(new Dimension(((int)(this.getWidth()*0.25)), ((int) (this.getHeight()*0.12))));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Ins2, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(87, 87, 87)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(Ins1, javax.swing.GroupLayout.PREFERRED_SIZE, 514, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(ProdCont, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(ProdImg, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(72, 72, 72)
                                .addComponent(ScanImg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(ScanBottom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(92, 92, 92)
                        .addComponent(InsBottom, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(84, 84, 84)
                        .addComponent(Service, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(ProdName, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(150, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(Logo, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(ProdName, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(105, 105, 105)
                .addComponent(ProdImg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(93, 93, 93)
                .addComponent(Ins1, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(ScanBottom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(InsBottom, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25))))
            .addGroup(layout.createSequentialGroup()
                .addGap(166, 166, 166)
                .addComponent(ScanImg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Logo, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 316, Short.MAX_VALUE)
                .addComponent(ProdCont, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(112, 112, 112)
                .addComponent(Ins2, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48)
                .addComponent(Service, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58))
        );

        Ins1.getAccessibleContext().setAccessibleName("jLabelInstruction1");
        ScanImg.getAccessibleContext().setAccessibleName("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        if(evt.getKeyChar() == '\n') {
            if(timerActive == true) {
                timerControl.cancel(false);
                timerActive = false;
            }
            clean();
            loading();
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/verpres3", "root", "");
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT nombre, existencias, precio, rutaimg FROM Productos WHERE clave = " + productKey);            
                if(rs.next()) {
                    clean();
                    retrieved();
                    URL imgPath = new URL(rs.getString(4));
                    BufferedImage buffImg = ImageIO.read(imgPath);
                    ProdImg.setIcon(new ImageIcon(
                        new ImageIcon(buffImg).getImage().getScaledInstance((int)(this.getWidth()*0.30), (int)(this.getHeight()*0.40), Image.SCALE_DEFAULT)));
                    //ProdName.setText("<html><body style=\"text-align: justify; max-width:"+ this.getWidth()*0.70 +"px; max-height:"+ this.getHeight()*0.30 +"px; \">" +rs.getString(1) + "</body></html>");
                    ProdName.setText(rs.getString(1));
                    ProdCont.setText("<html><body style=\"text-align: justify; \">$" + rs.getString(3) + " MX <br><br> Existencias: " + rs.getString(2) + "</body></html>");
                    productKey = "";
                } else {
                    clean();
                    notFound();
                }
            } catch(Exception ex) {
                productKey = "";
                clean();
                readError();
            }
            startTimer();
            productKey = "";
        } else {
            productKey += evt.getKeyChar();
        }
    }//GEN-LAST:event_formKeyPressed

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
            java.util.logging.Logger.getLogger(windowP.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(windowP.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(windowP.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(windowP.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new windowP().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Ins1;
    private javax.swing.JLabel Ins2;
    private javax.swing.JLabel InsBottom;
    private javax.swing.JLabel Logo;
    private javax.swing.JLabel ProdCont;
    private javax.swing.JLabel ProdImg;
    private javax.swing.JLabel ProdName;
    private javax.swing.JLabel ScanBottom;
    private javax.swing.JLabel ScanImg;
    private javax.swing.JLabel Service;
    // End of variables declaration//GEN-END:variables
}
