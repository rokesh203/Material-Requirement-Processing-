import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ClothShopGUI extends JFrame {

    CardLayout cardLayout;
    JPanel mainPanel;

    public ClothShopGUI() {
        setTitle("🧵 Cloth Shop - Material Requirement App");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        Font headerFont = new Font("Segoe UI", Font.BOLD, 18);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 14);
        Color sidebarColor = new Color(45, 62, 80);
        Color buttonColor = new Color(52, 152, 219);
        Color backgroundColor = new Color(236, 240, 241);

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

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(createProductPanel(headerFont, fieldFont, backgroundColor), "Products");
        mainPanel.add(createMaterialPanel(headerFont, fieldFont, backgroundColor), "Material");
        mainPanel.add(createOrderPanel(headerFont, fieldFont, backgroundColor), "Order");

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
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(bgColor);

        JPanel form = new JPanel(new GridLayout(5, 2, 10, 10));
        form.setBorder(new EmptyBorder(20, 30, 20, 30));
        form.setBackground(bgColor);

        JTextField name = new JTextField();
        JTextField category = new JTextField();
        JTextField price = new JTextField();
        JTextField stock = new JTextField();

        form.add(createLabel("Product Name:", fieldFont));
        form.add(name);
        form.add(createLabel("Category:", fieldFont));
        form.add(category);
        form.add(createLabel("Price:", fieldFont));
        form.add(price);
        form.add(createLabel("Stock Quantity:", fieldFont));
        form.add(stock);
        form.add(new JLabel());

        String[] columns = {"Product Name", "Category", "Price", "Stock"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);

        JButton submit = new JButton("Submit");
        submit.addActionListener(e -> {
            String[] row = {name.getText(), category.getText(), price.getText(), stock.getText()};
            model.addRow(row);
            name.setText(""); category.setText(""); price.setText(""); stock.setText("");
        });

        form.add(submit);
        wrapper.add(wrapWithTitle(form, "🛍 Product Catalog", titleFont), BorderLayout.NORTH);
        wrapper.add(new JScrollPane(table), BorderLayout.CENTER);
        return wrapper;
    }

    private JPanel createMaterialPanel(Font titleFont, Font fieldFont, Color bgColor) {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(bgColor);

        JPanel form = new JPanel(new GridLayout(6, 2, 10, 10));
        form.setBorder(new EmptyBorder(20, 30, 20, 30));
        form.setBackground(bgColor);

        JTextField name = new JTextField();
        JTextField quantity = new JTextField();
        JTextField unit = new JTextField();
        JTextField supplier = new JTextField();
        JTextField delivery = new JTextField();

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

        String[] columns = {"Name", "Quantity", "Unit", "Supplier", "Delivery"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);

        JButton submit = new JButton("Submit");
        submit.addActionListener(e -> {
            String[] row = {name.getText(), quantity.getText(), unit.getText(), supplier.getText(), delivery.getText()};
            model.addRow(row);
            name.setText(""); quantity.setText(""); unit.setText(""); supplier.setText(""); delivery.setText("");
        });

        form.add(submit);
        wrapper.add(wrapWithTitle(form, "📦 Material Entry", titleFont), BorderLayout.NORTH);
        wrapper.add(new JScrollPane(table), BorderLayout.CENTER);
        return wrapper;
    }

    private JPanel createOrderPanel(Font titleFont, Font fieldFont, Color bgColor) {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(bgColor);

        JPanel form = new JPanel(new GridLayout(6, 2, 10, 10));
        form.setBorder(new EmptyBorder(20, 30, 20, 30));
        form.setBackground(bgColor);

        JTextField orderId = new JTextField();
        JTextField customer = new JTextField();
        JTextField product = new JTextField();
        JTextField quantity = new JTextField();
        JTextField date = new JTextField();

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

        String[] columns = {"Order ID", "Customer", "Product", "Quantity", "Date"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);

        JButton submit = new JButton("Submit");
        submit.addActionListener(e -> {
            String[] row = {orderId.getText(), customer.getText(), product.getText(), quantity.getText(), date.getText()};
            model.addRow(row);
            orderId.setText(""); customer.setText(""); product.setText(""); quantity.setText(""); date.setText("");
        });

        form.add(submit);
        wrapper.add(wrapWithTitle(form, "🧾 Order Entry", titleFont), BorderLayout.NORTH);
        wrapper.add(new JScrollPane(table), BorderLayout.CENTER);
        return wrapper;
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
