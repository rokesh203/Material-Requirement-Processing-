import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ClothShopGUI extends JFrame {

    CardLayout cardLayout;
    JPanel mainPanel;

    public ClothShopGUI() {
        setTitle("🧵 Cloth Shop - Material Requirement App");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Fonts and Colors
        Font headerFont = new Font("Segoe UI", Font.BOLD, 18);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 14);
        Color sidebarColor = new Color(45, 62, 80);
        Color buttonColor = new Color(52, 152, 219);
        Color backgroundColor = new Color(236, 240, 241);

        // Sidebar (Navigation)
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(sidebarColor);
        sidebar.setPreferredSize(new Dimension(200, getHeight()));
        sidebar.setBorder(new EmptyBorder(20, 10, 10, 10));

        JButton materialButton = createNavButton("📦 Material", buttonColor, fieldFont);
        JButton orderButton = createNavButton("🧾 Order", buttonColor, fieldFont);
        JButton productButton = createNavButton("🛍 Products", buttonColor, fieldFont);

        sidebar.add(materialButton);
        sidebar.add(Box.createVerticalStrut(20));
        sidebar.add(orderButton);
        sidebar.add(Box.createVerticalStrut(20));
        sidebar.add(productButton);

        // Main Panel (Card Layout)
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        JPanel materialPanel = createMaterialPanel(headerFont, fieldFont, backgroundColor);
        JPanel orderPanel = createOrderPanel(headerFont, fieldFont, backgroundColor);
        JPanel productPanel = createProductPanel(headerFont, fieldFont, backgroundColor);

        mainPanel.add(materialPanel, "Material");
        mainPanel.add(orderPanel, "Order");
        mainPanel.add(productPanel, "Products");

        // Button Actions
        materialButton.addActionListener(e -> cardLayout.show(mainPanel, "Material"));
        orderButton.addActionListener(e -> cardLayout.show(mainPanel, "Order"));
        productButton.addActionListener(e -> cardLayout.show(mainPanel, "Products"));

        // Frame Assembly
        add(sidebar, BorderLayout.WEST);
        add(mainPanel, BorderLayout.CENTER);
    }

    private JButton createNavButton(String title, Color bgColor, Font font) {
        JButton button = new JButton(title);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setFont(font);
        button.setFocusPainted(false);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return button;
    }

    private JPanel createMaterialPanel(Font titleFont, Font fieldFont, Color bgColor) {
        JPanel panel = new JPanel(new GridLayout(6, 2, 15, 15));
        panel.setBorder(new EmptyBorder(30, 30, 30, 30));
        panel.setBackground(bgColor);

        panel.add(createLabel("Material Name:", fieldFont));
        panel.add(new JTextField());

        panel.add(createLabel("Quantity:", fieldFont));
        panel.add(new JTextField());

        panel.add(createLabel("Unit:", fieldFont));
        panel.add(new JTextField());

        panel.add(createLabel("Supplier:", fieldFont));
        panel.add(new JTextField());

        panel.add(createLabel("Expected Delivery:", fieldFont));
        panel.add(new JTextField());

        panel.add(new JLabel());
        panel.add(new JButton("Submit"));

        return wrapWithTitle(panel, "📦 Material Entry", titleFont);
    }

    private JPanel createOrderPanel(Font titleFont, Font fieldFont, Color bgColor) {
        JPanel panel = new JPanel(new GridLayout(6, 2, 15, 15));
        panel.setBorder(new EmptyBorder(30, 30, 30, 30));
        panel.setBackground(bgColor);

        panel.add(createLabel("Order ID:", fieldFont));
        panel.add(new JTextField());

        panel.add(createLabel("Customer Name:", fieldFont));
        panel.add(new JTextField());

        panel.add(createLabel("Product:", fieldFont));
        panel.add(new JTextField());

        panel.add(createLabel("Quantity:", fieldFont));
        panel.add(new JTextField());

        panel.add(createLabel("Order Date:", fieldFont));
        panel.add(new JTextField());

        panel.add(new JLabel());
        panel.add(new JButton("Submit"));

        return wrapWithTitle(panel, "🧾 Order Entry", titleFont);
    }

    private JPanel createProductPanel(Font titleFont, Font fieldFont, Color bgColor) {
        JPanel panel = new JPanel(new GridLayout(5, 2, 15, 15));
        panel.setBorder(new EmptyBorder(30, 30, 30, 30));
        panel.setBackground(bgColor);

        panel.add(createLabel("Product Name:", fieldFont));
        panel.add(new JTextField());

        panel.add(createLabel("Category:", fieldFont));
        panel.add(new JTextField());

        panel.add(createLabel("Price:", fieldFont));
        panel.add(new JTextField());

        panel.add(createLabel("Stock Quantity:", fieldFont));
        panel.add(new JTextField());

        panel.add(new JLabel());
        panel.add(new JButton("Submit"));

        return wrapWithTitle(panel, "🛍 Product Catalog", titleFont);
    }

    private JLabel createLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        return label;
    }

    private JPanel wrapWithTitle(JPanel inner, String title, Font font) {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(inner.getBackground());

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(font);
        titleLabel.setBorder(new EmptyBorder(10, 20, 20, 10));

        wrapper.add(titleLabel, BorderLayout.NORTH);
        wrapper.add(inner, BorderLayout.CENTER);
        return wrapper;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ClothShopGUI gui = new ClothShopGUI();
            gui.setVisible(true);
        });
    }
}
