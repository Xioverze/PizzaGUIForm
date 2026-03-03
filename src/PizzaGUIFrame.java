import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;

public class PizzaGUIFrame extends JFrame {

    private JRadioButton thinCrust, regularCrust, deepDishCrust;
    private ButtonGroup crustGroup;

    private JComboBox<String> sizeCombo;
    // Prices
    private JCheckBox[] toppingBoxes;
    private final String[] TOPPINGS = {
        "Eyeball Slices ($1.00)",
        "Werewolf Fur ($1.00)",
        "Vampire Tears ($1.00)",
        "Dragon Scales ($1.00)",
        "Zombie Brain Bits ($1.00)",
        "Witch Wart Mushrooms ($1.00)",
        "Ghost Pepper Lava ($1.00)",
        "Mummy Wrap Mozzarella ($1.00)"
    };

    private final String[] SIZES = {"Small", "Medium", "Large", "Super"};
    private final double[] SIZE_PRICES = {8.00, 12.00, 16.00, 20.00};

    private JTextArea orderTextArea;

    // Buttons
    private JButton orderBtn, clearBtn, quitBtn;

    public PizzaGUIFrame() {
        setTitle("Monster Pizza Palace - Order System");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                confirmQuit();
            }
        });

        JPanel topPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));

        topPanel.add(buildCrustPanel());
        topPanel.add(buildSizePanel());
        topPanel.add(buildToppingsPanel());

        JPanel receiptPanel = buildReceiptPanel();

        JPanel buttonPanel = buildButtonPanel();

        JPanel centerPanel = new JPanel(new BorderLayout(5, 5));
        centerPanel.add(topPanel, BorderLayout.NORTH);
        centerPanel.add(receiptPanel, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setMinimumSize(new Dimension(750, 600));
        setLocationRelativeTo(null);
        setVisible(true);
    }
    // Crust Stuff
    private JPanel buildCrustPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 1, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "Crust Type",
            TitledBorder.CENTER, TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 13), new Color(139, 0, 0)));

        crustGroup = new ButtonGroup();
        thinCrust = new JRadioButton("Thin");
        regularCrust = new JRadioButton("Regular");
        deepDishCrust = new JRadioButton("Deep-Dish");

        thinCrust.setFont(new Font("Arial", Font.PLAIN, 13));
        regularCrust.setFont(new Font("Arial", Font.PLAIN, 13));
        deepDishCrust.setFont(new Font("Arial", Font.PLAIN, 13));

        crustGroup.add(thinCrust);
        crustGroup.add(regularCrust);
        crustGroup.add(deepDishCrust);

        panel.add(thinCrust);
        panel.add(regularCrust);
        panel.add(deepDishCrust);

        return panel;
    }
    // Size Stuff
    private JPanel buildSizePanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "Pizza Size",
            TitledBorder.CENTER, TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 13), new Color(139, 0, 0)));

        sizeCombo = new JComboBox<>(new String[]{
            "Small  - $8.00",
            "Medium - $12.00",
            "Large  - $16.00",
            "Super  - $20.00"
        });
        sizeCombo.setFont(new Font("Arial", Font.PLAIN, 13));
        sizeCombo.setPreferredSize(new Dimension(180, 30));

        panel.add(sizeCombo);
        return panel;
    }
    // Toppings Stuff
    private JPanel buildToppingsPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 1, 3, 3));
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "Monster Toppings",
            TitledBorder.CENTER, TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 13), new Color(139, 0, 0)));

        toppingBoxes = new JCheckBox[TOPPINGS.length];
        for (int i = 0; i < TOPPINGS.length; i++) {
            toppingBoxes[i] = new JCheckBox(TOPPINGS[i]);
            toppingBoxes[i].setFont(new Font("Arial", Font.PLAIN, 12));
            panel.add(toppingBoxes[i]);
        }
        return panel;
    }
    // Receipt
    private JPanel buildReceiptPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "Order Receipt",
            TitledBorder.CENTER, TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 13), new Color(139, 0, 0)));

        orderTextArea = new JTextArea(10, 50);
        orderTextArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        orderTextArea.setEditable(false);
        orderTextArea.setBackground(new Color(255, 255, 240));

        JScrollPane scrollPane = new JScrollPane(orderTextArea);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(5, 10, 5, 10),
            BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Order Receipt",
                TitledBorder.CENTER, TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 13), new Color(139, 0, 0))
        ));

        return panel;
    }
    // More Button stuff
    private JPanel buildButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        panel.setBackground(new Color(50, 50, 50));

        orderBtn = createStyledButton("Order", new Color(0, 128, 0));
        clearBtn = createStyledButton("Clear", new Color(0, 70, 150));
        quitBtn  = createStyledButton("Quit",  new Color(180, 0, 0));

        orderBtn.addActionListener(e -> buildOrder());
        clearBtn.addActionListener(e -> clearForm());
        quitBtn.addActionListener(e -> confirmQuit());

        panel.add(orderBtn);
        panel.add(clearBtn);
        panel.add(quitBtn);

        return panel;
    }

    private JButton createStyledButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(100, 36));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void buildOrder() {
        if (!thinCrust.isSelected() && !regularCrust.isSelected() && !deepDishCrust.isSelected()) {
            JOptionPane.showMessageDialog(this,
                "Please select a crust type!", "Missing Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean hasToppings = false;
        for (JCheckBox box : toppingBoxes) {
            if (box.isSelected()) { hasToppings = true; break; }
        }
        if (!hasToppings) {
            JOptionPane.showMessageDialog(this,
                "Please select at least one topping!", "Missing Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String crust = thinCrust.isSelected() ? "Thin" :
                       regularCrust.isSelected() ? "Regular" : "Deep-Dish";

        int sizeIdx = sizeCombo.getSelectedIndex();
        String size = SIZES[sizeIdx];
        double subTotal = SIZE_PRICES[sizeIdx];

        // Build receipt
        StringBuilder sb = new StringBuilder();
        String divider  = "=========================================\n";
        String divider2 = "-----------------------------------------\n";

        sb.append(divider);
        sb.append(String.format("%-28s %s\n", "MONSTER PIZZA PALACE", ""));
        sb.append(divider);
        sb.append(String.format("%-28s %s\n", "Crust Type:", crust));
        sb.append(String.format("%-28s %s\n", "Size:", size));
        sb.append(String.format("%-28s %s\n", "Base Price:", String.format("$%.2f", subTotal)));
        sb.append(divider2);
        sb.append(String.format("%-28s %s\n", "Toppings:", "Price"));
        sb.append(divider2);

        for (JCheckBox box : toppingBoxes) {
            if (box.isSelected()) {
                String name = box.getText().replace(" ($1.00)", "");
                sb.append(String.format("  %-26s $1.00\n", name));
                subTotal += 1.00;
            }
        }

        double tax   = subTotal * 0.07;
        double total = subTotal + tax;

        sb.append(divider2);
        sb.append(String.format("%-28s $%.2f\n", "Sub-total:", subTotal));
        sb.append(String.format("%-28s $%.2f\n", "Tax (7%):", tax));
        sb.append(divider2);
        sb.append(String.format("%-28s $%.2f\n", "TOTAL:", total));
        sb.append(divider);
        sb.append("    Thank you for ordering at Monster Pizza!\n");
        sb.append(divider);

        orderTextArea.setText(sb.toString());
    }

    private void clearForm() {
        crustGroup.clearSelection();
        sizeCombo.setSelectedIndex(0);
        for (JCheckBox box : toppingBoxes) box.setSelected(false);
        orderTextArea.setText("");
    }

    private void confirmQuit() {
        int response = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to quit?",
            "Confirm Exit",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
        if (response == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}
