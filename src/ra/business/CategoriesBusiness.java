package ra.business;

import ra.entity.Categories;
import ra.util.ConnectionDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class CategoriesBusiness {
    private List<Categories> categoryList = new ArrayList<>();

    public CategoriesBusiness() {
        loadCategoriesFromDatabase();
    }

    private void loadCategoriesFromDatabase() {
        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement callSt = conn.prepareCall("{CALL Proc_GetAllCategories()}")) {
            ResultSet rs = callSt.executeQuery();
            while (rs.next()){
                int categoryId = rs.getInt("category_id");
                String categoryName = rs.getString("category_name");
                boolean categoryStatus = rs.getBoolean("category_status");
                categoryList.add(new Categories(categoryId, categoryName, categoryStatus));
            }
        }catch (SQLException e){
            System.err.println("Lỗi khi tải danh sách danh mục: "+e.getMessage());
        }
    }

    public boolean isDuplicateCategoryName(String categoryName) {
        return categoryList.stream().anyMatch(category -> category.getCategory_name().equalsIgnoreCase(categoryName));
    }

    public void displayCategories() {
        if (categoryList.isEmpty()){
            System.out.println("Danh sách danh mục trống");
        }else {
            categoryList.forEach(Categories::displayData);
        }
    }

    public void addCategory(Scanner scanner) {
        Categories newCategory = new Categories();
        newCategory.inputData(scanner);
        if (isDuplicateCategoryName(newCategory.getCategory_name())) {
            System.err.println("Tên danh mục đã tồn tại. Vui lòng nhập lại.");
            return;
        }
        try (Connection conn = ConnectionDB.openConnection();
            CallableStatement callSt = conn.prepareCall("{CALL Proc_AddCategory(?,?)}")) {
            callSt.setString(1, newCategory.getCategory_name());
            callSt.setBoolean(2, newCategory.isCategory_status());
            callSt.execute();
            newCategory.setCategory_id(categoryList.size()+1);
            categoryList.add(newCategory);
            System.out.println("Đã thêm danh mục thành công");
        } catch (SQLException e){
            System.err.println("Lỗi khi thêm danh mục: " + e.getMessage());
        }
    }

    public void updateCategory(Scanner scanner) {
        System.out.println("Nhập mã danh mục cần cập nhât: ");
        int categoryId = Integer.parseInt(scanner.nextLine());
        Categories category = categoryList.stream().filter(c -> c.getCategory_id() == categoryId).findFirst().orElse(null);
        if (category == null) {
            System.err.println("Không tìm thấy mã danh mục " + categoryId);
            return;
        }
        System.out.println("Thông tin danh mục hiện tại: ");
        category.displayData();
        System.out.println("Chọn thông tin cần sửa: ");
        System.out.println("1. Tên danh mục");
        System.out.println("2.Trạng thái danh mục (1 cho hoạt động, 0 cho không hoạt động)");
        System.out.print("Lựa chọn của bạn: ");
        int updateChoice = Integer.parseInt(scanner.nextLine());
        String newCategoryName = category.getCategory_name();
        boolean newCategoryStatus = category.isCategory_status();
        switch (updateChoice) {
            case 1:
                System.out.print("Nhập tên danh mục mới: ");
                newCategoryName = scanner.nextLine().trim();
                if (isDuplicateCategoryName(newCategoryName)) {
                    System.err.println("Tên danh mục đã tồn tại. Không thể cập nhật");
                    return;
                }
                break;
                case 2:
                    System.out.print("Nhập vào trạng thái danh mục (1 cho hoạt động, 0 cho không hoạt động): ");
                    newCategoryStatus = Integer.parseInt(scanner.nextLine())==1;
                    break;
                    default:
                        System.err.println("Lựa chọn không hợp lệ. Không có thay đổi nào được thực hiện");
                        return;
        }

        try (Connection conn = ConnectionDB.openConnection();
            CallableStatement callSt = conn.prepareCall("{CALL Proc_UpdateCategory(?,?,?)}")) {
            callSt.setInt(1, categoryId);
            callSt.setString(2, newCategoryName);
            callSt.setBoolean(3, newCategoryStatus);
            callSt.execute();
            category.setCategory_name(newCategoryName);
            category.setCategory_status(newCategoryStatus);
            System.out.println("Đã cập nhật danh mục thành công");
        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật danh mục: " + e.getMessage());
        }
    }

    public void deleteCategory(Scanner scanner) {
        System.out.print("Nhập mã danh mục cần xóa: ");
        int categoryId = Integer.parseInt(scanner.nextLine());
        try (Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = conn.prepareCall("CALL Proc_DeleteCategory(?)")) {
            callSt.setInt(1, categoryId);
            callSt.execute();
            categoryList.removeIf(category -> category.getCategory_id() == categoryId);
            System.out.println("Đã đánh dấu xóa danh mục thành công");
        }catch (SQLException e){
            System.err.println("Lỗi khi xóa danh mục: " + e.getMessage());
        }
    }

    public void countProductsByCategory(){
        try(Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = conn.prepareCall("{CALL Proc_CountProductsByCategory(?)}")) {
            ResultSet rs = callSt.executeQuery();
            System.out.println("Thống kê số lượng sản phẩm theo danh mục: ");
            while (rs.next()) {
                int categoryId = rs.getInt("category_id");
                String categoryName = rs.getString("category_name");
                int productCount = rs.getInt("product_count");
                System.out.printf("Mã danh mục: %d - Tên danh mục: %s - Số lượng sản phẩm: %d\n", categoryId, categoryName, productCount);
                System.out.println("-------------------------------------------");
            }
        }catch (SQLException ex){
            System.err.println("Lỗi khi thống kê số lượng sản phẩm: "+ex.getMessage());
        }
    }
}
