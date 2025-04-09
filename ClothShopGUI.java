import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ClothShopGUI extends JFrame {

    CardLayout cardLayout;
    JPanel mainPanel;
    DefaultTableModel productModel, materialModel, orderModel;

    final String DB_URL = "jdbc:mysql://localhost:3306/clothshop";
    final String DB_USER = "rokesh";
    final String DB_PASS = "jacksparrow";

    public ClothShopGUI() {
        setTitle("🧵 Cloth Shop Management");
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

        JButton productBtn = createNavButton("🛍 Products", buttonColor, fieldFont);
        JButton materialBtn = createNavButton("📦 Materials", buttonColor, fieldFont);
        JButton orderBtn = createNavButton("🧾 Orders", buttonColor, fieldFont);

        sidebar.add(productBtn);
        sidebar.add(Box.createVerticalStrut(20));
        sidebar.add(materialBtn);
        sidebar.add(Box.createVerticalStrut(20));
        sidebar.add(orderBtn);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.add(createProductPanel(headerFont, fieldFont, backgroundColor), "Products");
        mainPanel.add(createMaterialPanel(headerFont, fieldFont, backgroundColor), "Materials");
        mainPanel.add(createOrderPanel(headerFont, fieldFont, backgroundColor), "Orders");

        productBtn.addActionListener(e -> cardLayout.show(mainPanel, "Products"));
        materialBtn.addActionListener(e -> cardLayout.show(mainPanel, "Materials"));
        orderBtn.addActionListener(e -> cardLayout.show(mainPanel, "Orders"));

        add(sidebar, BorderLayout.WEST);
        add(mainPanel, BorderLayout.CENTER);

        loadProductData();
        loadMaterialData();
        loadOrderData();
    }

    private JButton createNavButton(String text, Color bg, Font font) {
        JButton btn = new JButton(text);
        btn.setFont(font);
        btn.setFocusPainted(false);
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        return btn;
    }

    private JPanel createProductPanel(Font titleFont, Font fieldFont, Color bgColor) {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(bgColor);
        JPanel form = new JPanel(new GridLayout(5, 2, 10, 10));
        form.setBorder(new EmptyBorder(20, 30, 20, 30));
        form.setBackground(bgColor);

        JTextField name = new JTextField(), category = new JTextField(), price = new JTextField(), stock = new JTextField();
        form.add(createLabel("Product Name:", fieldFont)); form.add(name);
        form.add(createLabel("Category:", fieldFont)); form.add(category);
        form.add(createLabel("Price:", fieldFont)); form.add(price);
        form.add(createLabel("Stock:", fieldFont)); form.add(stock);
        form.add(new JLabel());

        productModel = new DefaultTableModel(new String[]{"Product Name", "Category", "Price", "Stock"}, 0);
        JTable table = new JTable(productModel);

        JButton submit = new JButton("Add Product");
        submit.addActionListener(e -> {
            try {
                insertProduct(name.getText(), category.getText(), Double.parseDouble(price.getText()), Integer.parseInt(stock.getText()));
                name.setText(""); category.setText(""); price.setText(""); stock.setText("");
                loadProductData();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
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

        JTextField name = new JTextField(), qty = new JTextField(), unit = new JTextField(), supplier = new JTextField(), delivery = new JTextField();
        form.add(createLabel("Material Name:", fieldFont)); form.add(name);
        form.add(createLabel("Quantity:", fieldFont)); form.add(qty);
        form.add(createLabel("Unit:", fieldFont)); form.add(unit);
        form.add(createLabel("Supplier:", fieldFont)); form.add(supplier);
        form.add(createLabel("Delivery Date:", fieldFont)); form.add(delivery);
        form.add(new JLabel());

        materialModel = new DefaultTableModel(new String[]{"Name", "Quantity", "Unit", "Supplier", "Delivery"}, 0);
        JTable table = new JTable(materialModel);

        JButton submit = new JButton("Add Material");
        submit.addActionListener(e -> {
            try {
                insertMaterial(name.getText(), Double.parseDouble(qty.getText()), unit.getText(), supplier.getText(), delivery.getText());
                name.setText(""); qty.setText(""); unit.setText(""); supplier.setText(""); delivery.setText("");
                loadMaterialData();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
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

        JTextField oid = new JTextField(), cust = new JTextField(), prod = new JTextField(), qty = new JTextField(), date = new JTextField();
        form.add(createLabel("Order ID:", fieldFont)); form.add(oid);
        form.add(createLabel("Customer:", fieldFont)); form.add(cust);
        form.add(createLabel("Product:", fieldFont)); form.add(prod);
        form.add(createLabel("Quantity:", fieldFont)); form.add(qty);
        form.add(createLabel("Order Date:", fieldFont)); form.add(date);
        form.add(new JLabel());

        orderModel = new DefaultTableModel(new String[]{"Order ID", "Customer", "Product", "Quantity", "Date"}, 0);
        JTable table = new JTable(orderModel);

        JButton submit = new JButton("Add Order");
        submit.addActionListener(e -> {
            try {
                insertOrder(oid.getText(), cust.getText(), prod.getText(), Integer.parseInt(qty.getText()), date.getText());
                oid.setText(""); cust.setText(""); prod.setText(""); qty.setText(""); date.setText("");
                loadOrderData();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
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
        JLabel label = new JLabel(title);
        label.setFont(font);
        label.setBorder(new EmptyBorder(10, 20, 10, 10));
        wrapper.add(label, BorderLayout.NORTH);
        wrapper.add(inner, BorderLayout.CENTER);
        return wrapper;
    }

    // === DB Methods ===

    private void insertProduct(String name, String cat, double price, int stock) throws SQLException {
        try (Connection conn = getConn()) {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO products (name, category, price, stock) VALUES (?, ?, ?, ?)");
            ps.setString(1, name); ps.setString(2, cat); ps.setDouble(3, price); ps.setInt(4, stock); ps.executeUpdate();
        }
    }

    private void insertMaterial(String name, double qty, String unit, String sup, String del) throws SQLException {
        try (Connection conn = getConn()) {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO materials (name, quantity, unit, supplier, delivery) VALUES (?, ?, ?, ?, ?)");
            ps.setString(1, name); ps.setDouble(2, qty); ps.setString(3, unit); ps.setString(4, sup); ps.setString(5, del); ps.executeUpdate();
        }
    }

    private void insertOrder(String oid, String cust, String prod, int qty, String date) throws SQLException {
        try (Connection conn = getConn()) {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO orders (order_id, customer, product, quantity, order_date) VALUES (?, ?, ?, ?, ?)");
            ps.setString(1, oid); ps.setString(2, cust); ps.setString(3, prod); ps.setInt(4, qty); ps.setString(5, date); ps.executeUpdate();
        }
    }

    private void loadProductData() {
        productModel.setRowCount(0);
        try (Connection conn = getConn(); ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM products")) {
            while (rs.next()) {
                productModel.addRow(new Object[]{rs.getString("name"), rs.getString("category"), rs.getDouble("price"), rs.getInt("stock")});
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void loadMaterialData() {
        materialModel.setRowCount(0);
        try (Connection conn = getConn(); ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM materials")) {
            while (rs.next()) {
                materialModel.addRow(new Object[]{
                    rs.getString("name"),
                    rs.getDouble("quantity"),
                    rs.getString("unit"),
                    rs.getString("supplier"),
                    rs.getString("delivery")
                });
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void loadOrderData() {
        orderModel.setRowCount(0);
        try (Connection conn = getConn(); ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM orders")) {
            while (rs.next()) {
                orderModel.addRow(new Object[]{
                    rs.getString("order_id"),
                    rs.getString("customer"),
                    rs.getString("product"),
                    rs.getInt("quantity"),
                    rs.getString("order_date")
                });
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    // ✅ Updated getConn with JDBC driver loading
    private Connection getConn() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ClothShopGUI().setVisible(true));
    }
}
