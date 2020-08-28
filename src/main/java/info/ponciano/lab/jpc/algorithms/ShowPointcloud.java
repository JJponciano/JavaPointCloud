/*
 * Copyright (C) 2020 Dr Jean-Jacques Ponciano <jean-jacques@ponciano.info>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package info.ponciano.lab.jpc.algorithms;

import com.jogamp.opengl.awt.GLCanvas;
import info.ponciano.lab.jpc.math.Color;
import info.ponciano.lab.jpc.pointcloud.Pointcloud;
import info.ponciano.lab.jpc.pointcloud.components.APointCloud;
import info.ponciano.lab.jpc.pointcloud.components.PointCloudView;
import info.ponciano.lab.jpc.math.Coord3D;
import info.ponciano.lab.jpc.math.Point;
import info.ponciano.lab.jpc.math.RandomColor;
import info.ponciano.lab.jpc.math.vector.Normal;
import info.ponciano.lab.jpc.opengl.DrawingScene;
import info.ponciano.lab.jpc.opengl.IObjectGL;
import info.ponciano.lab.jpc.pointcloud.components.PointCloudMap;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * {@code
 * ShowPointCloud spc = new ShowPointCloud(null, true, this, title, false);
 * spc.setVisible(true);
 * }
 *
 * @author Dr. Jean-Jacques Ponciano
 */
public class ShowPointcloud extends javax.swing.JDialog {

    /**
     *
     */
    private static final long serialVersionUID = -2486796547445751021L;
    /**
     * A return status code - returned if Cancel button has been pressed
     */
    public static final int RET_CANCEL = 0;
    /**
     * A return status code - returned if OK button has been pressed
     */
    public static final int RET_OK = 1;
    private DrawingScene scene;
    private int heightScreen;
    private int widthScreen;
    private PointCloudView view;
    private Coord3D centroid;
    private Pointcloud pointcloud;

    public ShowPointcloud(final java.awt.Frame parent, final boolean modal, final Pointcloud cloud, final String title,
            final boolean normalRGB) {
        this(parent, modal, cloud, title, normalRGB, true);
    }

    public ShowPointcloud(final java.awt.Frame parent, final boolean modal, List<APointCloud> pcm, final String title,
            final boolean normalRGB, boolean randomcolorize) {
        super(parent, modal);
        initComponents();
        RandomColor rc = new RandomColor();
        Pointcloud pc = new Pointcloud();
        APointCloud patch = new PointCloudMap();
        pcm.forEach(cl -> {
            Color color = null;
            if (randomcolorize) {
                color = rc.getColor();
            }
            Iterator<Point> iterator = cl.iterator();
            while (iterator.hasNext()) {
                Point next = iterator.next();
                if (!randomcolorize) {
                    color = next.getColor();
                }
                patch.add(new Point(new Coord3D(next.getCoords().getX(), next.getCoords().getY(), next.getCoords().getZ()),
                        color, new Normal(next.getNormal().getX(), next.getNormal().getY(), next.getNormal().getZ())));
            }

        });
        pc.add(patch);
        init(title, pc);
    }

    /**
     * Creates new form ShowPointcloud
     *
     * @param parent
     * @param modal
     * @param cloud
     * @param title
     * @param normalRGB
     * @param toCenter
     */
    public ShowPointcloud(final java.awt.Frame parent, final boolean modal, final Pointcloud cloud, final String title,
            final boolean normalRGB, final boolean toCenter) {
        super(parent, modal);
        initComponents();
        init(title, cloud);
    }

    protected final void init(final String title1, final Pointcloud cloud) throws HeadlessException {
        // Close the dialog when Esc is pressed
        final String cancelName = "cancel";
        final InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), cancelName);
        final ActionMap actionMap = getRootPane().getActionMap();
        actionMap.put(cancelName, new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                doClose(RET_CANCEL);
            }
        });
        this.scene = new DrawingScene();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Rectangle maximumWindowBounds = ge.getMaximumWindowBounds();
        this.heightScreen = (int) maximumWindowBounds.getHeight();
        this.widthScreen = (int) maximumWindowBounds.getWidth();
        this.setSize(new Dimension(widthScreen, heightScreen));
        this.title.setText(title1);
        this.pointcloud = cloud;
        updateCloud();
        //        this.center.removeAll();
        GLCanvas canvas = scene.getCanvas(heightScreen, widthScreen);
        this.center.add(canvas);
    }

    public final void updateCloud() {
        scene.clear();
        final APointCloud pointCloud = this.pointcloud.getPoints();
        this.centroid = pointCloud.center();
        this.view = new PointCloudView(pointCloud, this.jCheckBox1.isSelected());
        scene.addObject((IObjectGL) view);
    }

    public Coord3D getCentroid() {
        return centroid;
    }

    /**
     * @return the return status of this dialog - one of RET_OK or RET_CANCEL
     */
    public int getReturnStatus() {
        return returnStatus;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        title = new javax.swing.JLabel();
        normal = new javax.swing.JPanel();
        jCheckBox1 = new javax.swing.JCheckBox();
        okButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        center = new javax.swing.JPanel();

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        jPanel1.add(title);

        getContentPane().add(jPanel1, java.awt.BorderLayout.NORTH);

        normal.setOpaque(false);

        jCheckBox1.setText("normal");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });
        normal.add(jCheckBox1);

        okButton.setText("OK");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });
        normal.add(okButton);
        getRootPane().setDefaultButton(okButton);

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });
        normal.add(cancelButton);

        jButton1.setText("Refactor");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        normal.add(jButton1);

        getContentPane().add(normal, java.awt.BorderLayout.SOUTH);

        center.setBackground(new java.awt.Color(204, 0, 0));
        center.setPreferredSize(new java.awt.Dimension(800, 800));
        center.setLayout(new java.awt.BorderLayout());
        getContentPane().add(center, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        this.pointcloud.refactor();
        this.pointcloud.randomColorizesPatches();
        IoPointcloud.saveASCII("test.txt", pointcloud);
        this.updateCloud();

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        this.updateCloud();
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void okButtonActionPerformed(final java.awt.event.ActionEvent evt) {// GEN-FIRST:event_okButtonActionPerformed
        doClose(RET_OK);
    }// GEN-LAST:event_okButtonActionPerformed

    private void cancelButtonActionPerformed(final java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cancelButtonActionPerformed
        doClose(RET_CANCEL);
    }// GEN-LAST:event_cancelButtonActionPerformed

    /**
     * Closes the dialog
     */
    private void closeDialog(final java.awt.event.WindowEvent evt) {// GEN-FIRST:event_closeDialog
        doClose(RET_CANCEL);
    }// GEN-LAST:event_closeDialog

    private void doClose(final int retStatus) {
        returnStatus = retStatus;
        setVisible(false);
        dispose();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(final String args[]) {
        /* Set the Nimbus look and feel */
        // <editor-fold defaultstate="collapsed" desc=" Look and feel setting code
        // (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the default
         * look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (final javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (final ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ShowPointcloud.class.getName()).log(java.util.logging.Level.SEVERE, null,
                    ex);
        }
        // </editor-fold>
        // </editor-fold>

        // </editor-fold>
        // </editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(() -> {
            try {
                JFrame jFrame = new javax.swing.JFrame();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Pointcloud", "xyz", "txt");
                String path = "";
                final File fileio = new File("");
                final JFileChooser fc = new JFileChooser(fileio);
                fc.addChoosableFileFilter(filter);
                fc.setAcceptAllFileFilterUsed(false);
                fc.setFileFilter(filter);

                final int returnVal = fc.showOpenDialog(jFrame);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    path = fc.getSelectedFile().getAbsolutePath();
                }

                Pointcloud loaded = IoPointcloud.loadASCII(path);
                int lastIndexOf = path.lastIndexOf("\\") + 1;
                if (lastIndexOf < 0) {
                    lastIndexOf = path.lastIndexOf("/");
                }
                if (lastIndexOf < 0) {
                    lastIndexOf = 0;
                }
                final ShowPointcloud dialog = new ShowPointcloud(jFrame, true,
                        loaded, path.substring(lastIndexOf, path.length()), false);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(final java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ShowPointcloud.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JPanel center;
    private javax.swing.JButton jButton1;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel normal;
    private javax.swing.JButton okButton;
    private javax.swing.JLabel title;
    // End of variables declaration//GEN-END:variables

    private int returnStatus = RET_CANCEL;

    public DrawingScene getScene() {
        return scene;
    }

    public void setPointSize(final int d) {
        this.view.setPointSize(d);
    }

}
