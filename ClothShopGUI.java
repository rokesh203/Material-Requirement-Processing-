import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ClothShopGUI extends JFrame {

    CardLayout cardLayout;
    JPanel mainPanel;
    DefaultTableModel productTableModel;

    // DB Config
    final String DB_URL = "jdbc:mysql://localhost:3306/clothshop";
    final String DB_USER = "rokesh";
    final String DB_PASS = "jacksparrow"; 

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
        sidebar.add(productButton);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(createProductPanel(headerFont, fieldFont, backgroundColor), "Products");
        productButton.addActionListener(e -> cardLayout.show(mainPanel, "Products"));

        add(sidebar, BorderLayout.WEST);
        add(mainPanel, BorderLayout.CENTER);

        // Initialize table with database content
        loadProductData();
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
        productTableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(productTableModel);

        JButton submit = new JButton("Add Product");
        submit.addActionListener(e -> {
            try {
                String pname = name.getText();
                String cat = category.getText();
                double prc = Double.parseDouble(price.getText());
                int stk = Integer.parseInt(stock.getText());

                insertProduct(pname, cat, prc, stk);
                loadProductData();

                name.setText(""); category.setText(""); price.setText(""); stock.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid Input!");
            }
        });

        form.add(submit);
        wrapper.add(wrapWithTitle(form, "🛍 Product Catalog", titleFont), BorderLayout.NORTH);
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

    // === JDBC Helpers ===
    private void insertProduct(String name, String category, double price, int stock) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            String sql = "INSERT INTO products (name, category, price, stock) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, category);
            ps.setDouble(3, price);
            ps.setInt(4, stock);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadProductData() {
        productTableModel.setRowCount(0);
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            String sql = "SELECT name, category, price, stock FROM products";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Object[] row = {
                    rs.getString("name"),
                    rs.getString("category"),
                    rs.getDouble("price"),
                    rs.getInt("stock")
                };
                productTableModel.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ClothShopGUI().setVisible(true);
        });
    }
}
