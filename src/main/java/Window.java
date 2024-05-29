import java.awt.Font;
import java.io.Serial;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public final class Window {

    public static synchronized Message create(String text) {
        JFrame jFrame = new JFrame(text);
        Message jLabel = new Message();
        jLabel.setText("<html><body>" + text + "</body></html>");
        jLabel.setFont(new Font("Serif", Font.PLAIN, 72));
        JPanel jPanel = new JPanel();
        jPanel.add(jLabel);
        jFrame.add(jPanel);
        jFrame.setSize(800, 600);
        jFrame.setLocation(1000, 200);
        jFrame.setVisible(true);
        return jLabel;
    }

    public static void monitor(Supplier<String> supplier) {
        Message msg = Window.create("Counter");
        Runnable monitor = () -> msg.setText(supplier.get());
        ScheduledExecutorService executor =
                Executors.newScheduledThreadPool(1);
        executor.scheduleWithFixedDelay(monitor, 0, 50, TimeUnit.MILLISECONDS);
    }

    public static class Message extends JLabel {
        @Serial
        private static final long serialVersionUID = 1L;

        @Override
        public void setText(String text) {
            super.setText("<html><body><p style=\"width:400px\">" + text + "</p></body></html>");
        }
    }
}