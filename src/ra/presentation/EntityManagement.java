package ra.presentation;

import ra.business.CategoriesBusiness;
import ra.business.ProductBusiness;
import ra.entity.Categories;

import java.util.Scanner;

public class EntityManagement {
    private static final Scanner scanner = new Scanner(System.in);
    private static final CategoriesBusiness categoriesBusiness = new CategoriesBusiness();

    public static void main(String[] args) {
        while (true) {
            System.out.println("******************SHOP****************");
            System.out.println("1.Quản lý danh mục");
            System.out.println("2.Quản lý sản phẩm");
            System.out.println("3.Thoát");
            System.out.print("lựa chọn của bạn: ");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    categoryMenu();
                    break;
                case 2:
                    productMenu();
                    break;
                case 3:
                    System.out.println("Thoát chương trình");
                    return;
                default:
                    System.err.println("Lựa chọn không hợp lệ. VUi lòng chọn lại");
            }
        }
    }

    private static void categoryMenu() {
        while (true) {
            System.out.println("******************CATEGORY-MENU****************");
            System.out.println("1. Danh sách danh mục");
            System.out.println("2. Tạo mới danh mục");
            System.out.println("3. Cập nhật danh mục");
            System.out.println("4. Xóa danh mục");
            System.out.println("5. Thống kê số lượng sản phẩm theo danh mục");
            System.out.println("6. Thoát");
            System.out.print("Lựa chọn của bạn: ");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    categoriesBusiness.displayCategories();
                    break;
                case 2:
                    categoriesBusiness.addCategory(scanner);
                    break;
                case 3:
                    categoriesBusiness.updateCategory(scanner);
                    break;
                case 4:
                    categoriesBusiness.deleteCategory(scanner);
                    break;
                case 5:
                    categoriesBusiness.countProductsByCategory();
                    break;
                case 6:
                    System.out.println("Thoát khỏi quản lý danh mục");
                    return;
                default:
                    System.err.println("Lựa chọn không hợp lệ. Vui lòng chọn lại");
            }
        }
    }

    private static void productMenu() {
        while (true) {
            System.out.println("******************PRODUCT-MENU****************");
            System.out.println("1.Danh sách sản phẩm");
            System.out.println("lựa chọn của bạn");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    ProductBusiness.displayAllProducts();
                    break;
            }
        }
    }
}
