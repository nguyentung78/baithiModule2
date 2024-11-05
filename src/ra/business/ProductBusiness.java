package ra.business;

import ra.entity.Product;
import ra.util.ConnectionDB;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class ProductBusiness {
    private List<Product> productList = new ArrayList<>();
    public ProductBusiness() {
        loadProductsFromDatabase();
    }
    private void loadProductsFromDatabase() {
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement callSt = conn.prepareCall("{CALL Proc_GetAllProduct()}")) {
             ResultSet rs = callSt.executeQuery();
             while (rs.next()) {
                 int productId = rs.getInt("product_id");
                 String productName = rs.getString("product_name");
                 int stock = rs.getInt("stock");
                 double costPrice = rs.getDouble("cost_price");
                 double sellingPrice = rs.getDouble("selling_price");
                 Date createdAt = rs.getDate("created_at");
                 int categoryId = rs.getInt("category_id");
                 productList.add(new Product(productId, productName, stock, costPrice, sellingPrice, createdAt, categoryId));

             }
        }catch (SQLException e) {
            System.err.println("Lỗi khi tải danh sac sản phẩm"+e.getMessage());
        }
    }

    public void displayAllProducts() {
        if (productList.isEmpty()) {
            System.out.println("Danh sách trống");
        }else {
            productList.forEach(Product::displayData);
        }
    }

}
