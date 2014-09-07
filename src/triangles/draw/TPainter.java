package triangles.draw;

import java.awt.*;
import javax.swing.*;

public class TPainter {

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
                g2d.setPaint(Color.CYAN);
                g2d.drawPolygon(p);
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
