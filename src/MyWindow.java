import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.ExecutionException;

public class MyWindow extends JFrame {
    private static final JButton jButton = new JButton("START!");
    private static final JLabel statusLabel = new JLabel();

    public MyWindow() {
        //Header
        setTitle("My Window");
        //Application behavior when it closes.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //We assign the application window a position on top of windows.
        setAlwaysOnTop(true);
        //Application closing message.
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("Окно закрывается");
            }
        });
        //Set parameters for placement of components.
        setLocationRelativeTo(null);
        setSize(400, 400);
        setVisible(true);
        GridBagLayout gbl = new GridBagLayout();
        setLayout(gbl);
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.SOUTH;
        c.fill = GridBagConstraints.NONE;
        c.gridheight = 1;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.gridx = GridBagConstraints.RELATIVE;
        c.gridy = GridBagConstraints.RELATIVE;
        c.insets = new Insets(40, 0, 0, 0);
        c.ipadx = 0;
        c.ipady = 0;
        c.weightx = 0.0;
        c.weighty = 0.0;

        //We set the type, size and location for the components.
        Font font = new Font("Arial", Font.BOLD, 25);
        statusLabel.setFont(font);
        add(statusLabel, c);

        //We type the button and set the font.
        jButton.setFont(font);
        jButton.setBackground(Color.red);
        gbl.setConstraints(jButton, c);
        add(jButton);
        //Create a button listener
        jButton.addActionListener(e -> {
            statusLabel.setText("Task not completed.");
            jButton.setFont(font);
            SwingWorker<Boolean, Void> worker = new WindowRunnable();
            worker.execute();
            jButton.setEnabled(false);
        });
    }
    public static class WindowRunnable extends SwingWorker<Boolean, Void> {
        private static final long m = 1000;

        @Override
        protected Boolean doInBackground() throws Exception {
            //Running a long-running task.
            for (int i = 0; i < 10; i++) {
                Thread.sleep(m);
                System.out.println(Thread.currentThread().getName());
            }
            return true;
        }
        @Override
        protected void done() {
            boolean status = false;
            try {
                // Retrieve the return value of doInBackground.
                status = get();
                statusLabel.setText("Completed with status: " + status);
                jButton.setEnabled(true);
            } catch (InterruptedException e) {
                // This is thrown if the thread's interrupted.
                } catch (ExecutionException e) {
                // This is thrown if we throw an exception
                // from doInBackground.
                System.out.println("Finished with status " + status);
            }
        }
    }
}

