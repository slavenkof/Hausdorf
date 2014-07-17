package triangles.draw;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.UIManager;
import triangles.Computer;
import static triangles.Computer.Haus;

public class TPAinterr {

    public static void once(final Polygon p, final Polygon pp) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Throwable e) {
        }

        final JFrame f = new JFrame();
        f.getRootPane().setOpaque(true);
        f.getRootPane().setBackground(Color.white);
        f.getRootPane().setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));

        f.getContentPane().setBackground(Color.white);
        f.getContentPane().setLayout(new BorderLayout(0, 0));

        f.getContentPane().add(new JComponent() {
            private static final long serialVersionUID = 1L;

            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);

                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
//Все что ниже НЕ ТРОГАТЬ - MAGIC!!
                g2d.setPaint(Color.CYAN);
                g2d.drawPolygon(p);
                g2d.setPaint(Color.BLACK);
                int ff = (int) Computer.Haus[0].getVLength()*2;
                for (int i = 0; i < Haus.length; i++) {
                    g2d.drawArc((int) Haus[i].getPoints()[0].getX()-ff/2, (int) Haus[i].getPoints()[0].getY()-ff/2, (int) Haus[0].getVLength()*2, (int) Haus[0].getVLength()*2, 0, 360);
                }

                g2d.setPaint(Color.MAGENTA);
                g2d.drawPolygon(pp);

            }

            public Dimension getPeferredSize() {
                return new Dimension(700, 700);
            }
        });

        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
}
