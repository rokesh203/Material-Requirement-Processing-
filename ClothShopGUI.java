import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ClothShopGUI extends JFrame {

    CardLayout cardLayout;
    JPanel mainPanel;

    public ClothShopGUI() {
        setTitle("🧵 Cloth Shop - Material Requirement App");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        Font headerFont = new Font("Segoe UI", Font.BOLD, 18);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 14);
        Color sidebarColor = new Color(45, 62, 80);
        Color buttonColor = new Color(52, 152, 219);
        Color backgroundColor = new Color(236, 240, 241);

        // Sidebar
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(sidebarColor);
        sidebar.setPreferredSize(new Dimension(200, getHeight()));
        sidebar.setBorder(new EmptyBorder(20, 10, 10, 10));

        JButton productButton = createNavButton("🛍 Products", buttonColor, fieldFont);
        JButton materialButton = createNavButton("📦 Material", buttonColor, fieldFont);
        JButton orderButton = createNavButton("🧾 Order", buttonColor, fieldFont);

        sidebar.add(productButton);
        sidebar.add(Box.createVerticalStrut(20));
        sidebar.add(materialButton);
        sidebar.add(Box.createVerticalStrut(20));
        sidebar.add(orderButton);

        // Main Area
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        JPanel productPanel = createProductPanel(headerFont, fieldFont, backgroundColor);
        JPanel materialPanel = createMaterialPanel(headerFont, fieldFont, backgroundColor);
        JPanel orderPanel = createOrderPanel(headerFont, fieldFont, backgroundColor);

        mainPanel.add(productPanel, "Products");
        mainPanel.add(materialPanel, "Material");
        mainPanel.add(orderPanel, "Order");

        productButton.addActionListener(e -> cardLayout.show(mainPanel, "Products"));
        materialButton.addActionListener(e -> cardLayout.show(mainPanel, "Material"));
        orderButton.addActionListener(e -> cardLayout.show(mainPanel, "Order"));

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

    private JPanel createProductPanel(Font titleFont, Font fieldFont, Color bgColor) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(bgColor);

        JPanel form = new JPanel(new GridLayout(5, 2, 10, 10));
        form.setBorder(new EmptyBorder(20, 30, 20, 30));
        form.setBackground(bgColor);

        JTextField name = new JTextField();
        JTextField category = new JTextField();
        JTextField price = new JTextField();
        JTextField stock = new JTextField();
        JTextArea display = new JTextArea();
        display.setEditable(false);

        form.add(createLabel("Product Name:", fieldFont));
        form.add(name);
        form.add(createLabel("Category:", fieldFont));
        form.add(category);
        form.add(createLabel("Price:", fieldFont));
        form.add(price);
        form.add(createLabel("Stock Quantity:", fieldFont));
        form.add(stock);
        form.add(new JLabel());

        JButton submit = new JButton("Submit");
        submit.addActionListener((ActionEvent e) -> {
            String msg = "Product added:\nName: " + name.getText() +
                    "\nCategory: " + category.getText() +
                    "\nPrice: " + price.getText() +
                    "\nStock: " + stock.getText();
            JOptionPane.showMessageDialog(this, msg);
            display.append(msg + "\n\n");
            name.setText(""); category.setText(""); price.setText(""); stock.setText("");
        });

        form.add(submit);

        panel.add(wrapWithTitle(form, "🛍 Product Catalog", titleFont), BorderLayout.NORTH);
        panel.add(new JScrollPane(display), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createMaterialPanel(Font titleFont, Font fieldFont, Color bgColor) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(bgColor);

        JPanel form = new JPanel(new GridLayout(6, 2, 10, 10));
        form.setBorder(new EmptyBorder(20, 30, 20, 30));
        form.setBackground(bgColor);

        JTextField name = new JTextField();
        JTextField quantity = new JTextField();
        JTextField unit = new JTextField();
        JTextField supplier = new JTextField();
        JTextField delivery = new JTextField();
        JTextArea display = new JTextArea();
        display.setEditable(false);

        form.add(createLabel("Material Name:", fieldFont));
        form.add(name);
        form.add(createLabel("Quantity:", fieldFont));
        form.add(quantity);
        form.add(createLabel("Unit:", fieldFont));
        form.add(unit);
        form.add(createLabel("Supplier:", fieldFont));
        form.add(supplier);
        form.add(createLabel("Expected Delivery:", fieldFont));
        form.add(delivery);
        form.add(new JLabel());

        JButton submit = new JButton("Submit");
        submit.addActionListener((ActionEvent e) -> {
            String msg = "Material added:\nName: " + name.getText() +
                    "\nQty: " + quantity.getText() +
                    " " + unit.getText() +
                    "\nSupplier: " + supplier.getText() +
                    "\nDelivery: " + delivery.getText();
            JOptionPane.showMessageDialog(this, msg);
            display.append(msg + "\n\n");
            name.setText(""); quantity.setText(""); unit.setText(""); supplier.setText(""); delivery.setText("");
        });

        form.add(submit);

        panel.add(wrapWithTitle(form, "📦 Material Entry", titleFont), BorderLayout.NORTH);
        panel.add(new JScrollPane(display), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createOrderPanel(Font titleFont, Font fieldFont, Color bgColor) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(bgColor);

        JPanel form = new JPanel(new GridLayout(6, 2, 10, 10));
        form.setBorder(new EmptyBorder(20, 30, 20, 30));
        form.setBackground(bgColor);

        JTextField orderId = new JTextField();
        JTextField customer = new JTextField();
        JTextField product = new JTextField();
        JTextField quantity = new JTextField();
        JTextField date = new JTextField();
        JTextArea display = new JTextArea();
        display.setEditable(false);

        form.add(createLabel("Order ID:", fieldFont));
        form.add(orderId);
        form.add(createLabel("Customer Name:", fieldFont));
        form.add(customer);
        form.add(createLabel("Product:", fieldFont));
        form.add(product);
        form.add(createLabel("Quantity:", fieldFont));
        form.add(quantity);
        form.add(createLabel("Order Date:", fieldFont));
        form.add(date);
        form.add(new JLabel());

        JButton submit = new JButton("Submit");
        submit.addActionListener((ActionEvent e) -> {
            String msg = "Order placed:\nOrder ID: " + orderId.getText() +
                    "\nCustomer: " + customer.getText() +
                    "\nProduct: " + product.getText() +
                    "\nQuantity: " + quantity.getText() +
                    "\nDate: " + date.getText();
            JOptionPane.showMessageDialog(this, msg);
            display.append(msg + "\n\n");
            orderId.setText(""); customer.setText(""); product.setText(""); quantity.setText(""); date.setText("");
        });

        form.add(submit);

        panel.add(wrapWithTitle(form, "🧾 Order Entry", titleFont), BorderLayout.NORTH);
        panel.add(new JScrollPane(display), BorderLayout.CENTER);
        return panel;
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
        titleLabel.setBorder(new EmptyBorder(10, 20, 10, 10));

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
