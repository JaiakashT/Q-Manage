import java.awt.*;
import java.io.*;
import java.net.*;
import javax.swing.*;

public class QueueClient extends JFrame {
    private JLabel currentTicketLabel, counterLabel;
    private JTextArea historyArea;
    private JButton nextButton, resetButton, exitButton;
    private PrintWriter out;
    public QueueClient(String serverAddress) {
        setTitle("Queue Management Client");
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(
                        0, 0, new Color(10, 25, 47),
                        0, getHeight(), new Color(36, 123, 160));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel displayPanel = new JPanel();
        displayPanel.setBackground(new Color(240, 240, 240));
        displayPanel.setLayout(new GridLayout(2, 1, 10, 10));
        displayPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 3, true),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        currentTicketLabel = new JLabel("Ticket No: ---", SwingConstants.CENTER);
        currentTicketLabel.setFont(new Font("Arial", Font.BOLD, 36));
        currentTicketLabel.setForeground(Color.RED);

        counterLabel = new JLabel("Counter: 1", SwingConstants.CENTER);
        counterLabel.setFont(new Font("Arial", Font.BOLD, 24));
        counterLabel.setForeground(Color.DARK_GRAY);

        displayPanel.add(currentTicketLabel);
        displayPanel.add(counterLabel);

        historyArea = new JTextArea();
        historyArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        historyArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(historyArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.WHITE, 2),
                "Serving History",
                0, 0,
                new Font("Arial", Font.BOLD, 16),
                Color.WHITE
        ));

        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 15, 15));
        buttonPanel.setOpaque(false);

        nextButton = new JButton("Next Ticket");
        resetButton = new JButton("Reset Queue");
        exitButton = new JButton("Exit");

        styleButton(nextButton, new Color(34, 139, 34));   // Green
        styleButton(resetButton, new Color(255, 165, 0)); // Orange
        styleButton(exitButton, new Color(178, 34, 34));  // Red

        buttonPanel.add(nextButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(exitButton);

        try {
            Socket socket = new Socket(serverAddress, 5000);
            out = new PrintWriter(socket.getOutputStream(), true);

            new Thread(() -> {
                try (BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()))) {
                    String response;
                    while ((response = in.readLine()) != null) {
                        if (response.startsWith("TICKET:")) {
                            String ticket = response.substring(7);
                            currentTicketLabel.setText("Ticket No: " + ticket);
                            historyArea.append("Now Serving: " + ticket + "\n");
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, " Cannot connect to server!");
            System.exit(1);
        }

        nextButton.addActionListener(e -> out.println("NEXT"));
        resetButton.addActionListener(e -> out.println("RESET"));
        exitButton.addActionListener(e -> System.exit(0));

        mainPanel.add(displayPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    private void styleButton(JButton button, Color bgColor) {
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2, true),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
    }

    public static void main(String[] args) {
        String serverAddress = JOptionPane.showInputDialog("Enter Server IP (e.g., localhost):");
        SwingUtilities.invokeLater(() -> new QueueClient(serverAddress));
    }
}
