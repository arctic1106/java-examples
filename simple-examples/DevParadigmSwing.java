import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Map;

public class DevParadigmSwing {
    private static final Map<Character, String[]> DEV_STYLES = Map.ofEntries(
            Map.entry('A', new String[] { "Anxiety Driven Development", "When the deadline is near, all roads lead to it!" }),
            Map.entry('B', new String[] { "Bug Driven Development", "If it compiles, ship it! Fix it later." }),
            Map.entry('C', new String[] { "Conference Driven Development", "You only code after attending a conference!" }),
            Map.entry('D', new String[] { "Deadline Driven Development", "Productivity spikes only near deadlines." }),
            Map.entry('E', new String[] { "Experiment Driven Development", "Keep trying things until something works." }),
            Map.entry('F', new String[] { "Fear Driven Development", "You change code only when your manager isn't watching." }),
            Map.entry('G', new String[] { "Google Driven Development", "If you don't know it, Google it!" }),
            Map.entry('H', new String[] { "Hackathon Driven Development", "You work best when there's free pizza and a timer." }),
            Map.entry('I', new String[] { "Interview Driven Development", "You only study before job interviews." }),
            Map.entry('J', new String[] { "JavaDoc Driven Development", "Code first, documentation later (maybe)." }),
            Map.entry('K', new String[] { "Keyboard Driven Development", "More keystrokes mean better coding!" }),
            Map.entry('L', new String[] { "Log Driven Development", "More logs, more debugging!" }),
            Map.entry('M', new String[] { "Micromanagement Driven Development", "Every line of code must be reviewed twice!" }),
            Map.entry('N', new String[] { "Night Owl Driven Development", "Best code is written at 2 AM." }),
            Map.entry('O', new String[] { "Open Source Driven Development", "If it's open source, it must be good!" }),
            Map.entry('P', new String[] { "Patch Driven Development", "Code first, patch later." }),
            Map.entry('Q', new String[] { "Quick Fix Driven Development", "Make it work first, think later." }),
            Map.entry('R', new String[] { "Refactor Driven Development", "The first version is never good enough." }),
            Map.entry('S', new String[] { "Stack Overflow Driven Development", "90% copy-paste, 10% debugging." }),
            Map.entry('T', new String[] { "Test Driven Development", "Or at least pretending to do so." }),
            Map.entry('U', new String[] { "User Complaint Driven Development", "Fix it only when users notice!" }),
            Map.entry('V', new String[] { "Version Driven Development", "Let's just increment the version number." }),
            Map.entry('W', new String[] { "Wiki Driven Development", "If it’s not on the wiki, does it exist?" }),
            Map.entry('X', new String[] { "XML Driven Development", "Because JSON is too mainstream." }),
            Map.entry('Y', new String[] { "YOLO Driven Development", "Ship now, fix later." }),
            Map.entry('Z', new String[] { "Zero Bug Driven Development", "Close all bugs by marking them 'Won't Fix'." }));

    public static void main(String[] args) {
        SwingUtilities.invokeLater(DevParadigmSwing::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }

        var frame = new JFrame("Development Style Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(650, 450);
        frame.setLayout(new BorderLayout(10, 10));

        var label = new JLabel("What kind of development do you practice?", SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI", Font.BOLD, 20));
        label.setBorder(new EmptyBorder(15, 10, 10, 10));
        frame.add(label, BorderLayout.NORTH);

        var buttonPanel = new JPanel(new GridLayout(4, 7, 8, 8));
        buttonPanel.setBorder(new EmptyBorder(10, 15, 15, 15));
        buttonPanel.setBackground(new Color(245, 245, 245));

        for (char ch = 'A'; ch <= 'Z'; ch++) {
            buttonPanel.add(createLetterButton(ch, frame));
        }

        buttonPanel.add(new JLabel(""));
        buttonPanel.add(new JLabel(""));

        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null); // Center window on screen
        frame.setVisible(true);
    }

    private static JButton createLetterButton(char ch, JFrame parentFrame) {
        var button = new JButton(String.valueOf(ch));
        button.setFont(new Font("Segoe UI", Font.BOLD, 18));
        button.setFocusPainted(false);
        button.setBackground(new Color(51, 122, 183));
        button.setForeground(Color.WHITE);
        button.setOpaque(true);
        button.setBorderPainted(false);

        button.addActionListener(e -> {
            String[] messages = DEV_STYLES.getOrDefault(ch, new String[] { "No definition found!", "Try another letter." });
            Window activeWindow = SwingUtilities.getWindowAncestor(button);
            showPopup(activeWindow, messages[0], messages[1]);
        });

        return button;
    }

    private static void showPopup(Window parent, String title, String subtitle) {
        var dialog = new JDialog(parent, "Your Development Style", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setLayout(new BorderLayout());
        dialog.getContentPane().setBackground(Color.WHITE);

        var contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(new EmptyBorder(20, 25, 15, 25));

        var titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(new Color(33, 33, 33));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(titleLabel);

        contentPanel.add(Box.createRigidArea(new Dimension(0, 12)));

        String htmlMessage = "<html><body style='width: 360px; text-align: center; margin: 0; padding: 0;'>"
                + subtitle + "</body></html>";

        var subtitleLabel = new JLabel(htmlMessage);
        subtitleLabel.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        subtitleLabel.setForeground(Color.DARK_GRAY);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(subtitleLabel);

        dialog.add(contentPanel, BorderLayout.CENTER);

        var okButton = new JButton("Got it!");
        okButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        okButton.addActionListener(e -> dialog.dispose());

        var buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(new EmptyBorder(5, 0, 15, 0));
        buttonPanel.add(okButton);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.pack();
        dialog.setSize(Math.max(dialog.getWidth(), 450), Math.max(dialog.getHeight(), 200));

        dialog.setLocationRelativeTo(parent);

        dialog.setVisible(true);
    }
}
