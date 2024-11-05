package ra.entity;

import java.util.Date;
import java.util.Scanner;

public class Product implements IEntityManagement{
    private int productId;
    private String productName;
    private int stock;
    private double cost_price;
    private double selling_price;
    private Date created_at;
    private int category_id;
    public Product() {

    }

    public Product(int productId, String productName, int stock, double cost_price, double selling_price, Date created_at, int category_id) {
        this.productId = productId;
        this.productName = productName;
        this.stock = stock;
        this.cost_price = cost_price;
        this.selling_price = selling_price;
        this.created_at = created_at;
        this.category_id = category_id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getCost_price() {
        return cost_price;
    }

    public void setCost_price(double cost_price) {
        this.cost_price = cost_price;
    }

    public double getSelling_price() {
        return selling_price;
    }

    public void setSelling_price(double selling_price) {
        this.selling_price = selling_price;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    @Override
    public void inputData(Scanner scanner) {
        do {
            System.out.print("Nhập tên sản phẩm: ");
            this.productName = scanner.nextLine().trim();
            if (this.productName.isEmpty() || this.productName.length() > 20) {
                System.err.println("Tên sản phẩm không được để trống và có tối đa 20 kí tự, vui lòng nhập lại.");
            }
        } while (this.productName.isEmpty() || this.productName.length() > 20);

        do {
            System.out.print("Nhập số lượng sản phẩm: ");
            while (!scanner.hasNextInt()) {
                System.err.println("Vui lòng nhập 1 số nguyên hợp lệ");
                scanner.next();
            }
            this.stock = Integer.parseInt(scanner.nextLine());
            if (this.stock < 0) {
                System.err.println("Số lượng sản phẩm phải lớn hơn hoặc bằng 0. vui lòng nhập lại.");
            }
        }while (this.stock < 0);

        do {
            System.out.print("Nhập giá nhập sản phẩm: ");
            while (!scanner.hasNextInt()) {
                System.err.println("Vui lòng nhập 1 số thực hợp lệ");
                scanner.next();
            }
            this.cost_price = Double.parseDouble(scanner.nextLine());
            if (this.cost_price <= 0) {
                System.err.println("Giá nhập phải lớn hơn 0. vui lòng nhập lại.");
            }
        }while (this.cost_price <= 0);

        do {
            System.out.print("Nhập giá bán sản phẩm: ");
            while (!scanner.hasNextInt()) {
                System.err.println("Vui lòng nhập 1 số thực hợp lệ");
                scanner.next();
            }
            this.selling_price = Double.parseDouble(scanner.nextLine());
            if (this.selling_price <= 0) {
                System.err.println("Giá bán phải lớn hơn 0. vui lòng nhập lại.");
            }
        }while (this.selling_price <= 0);
        this.created_at = new Date();
        do {
            System.out.println("Nhập mã danh mục của sản phẩm: ");
            while (!scanner.hasNextInt()) {
                System.err.println("Vui lòng nhập 1 số nguyên hợp lệ.");
                scanner.next();
            }
            this.category_id = Integer.parseInt(scanner.nextLine());
            if (this.category_id <= 0) {
                System.err.println("Mã danh mục phải lớn hơn 0. vui lòng nhập lại");
            }
        }while (this.category_id <= 0);
    }

    @Override
    public void displayData() {
        System.out.printf("Mã sản phẩm: %d - Tên sản phẩm: %s - Số lượng: %d -Giá nhâp: %2.f - Ngày tạo: %s - Mã danh mục: %d\n", this.productId, this.productName, this.stock, this.cost_price, this.selling_price, this.created_at, this.category_id);
    }

    public double calculateProfit(){
        return this.selling_price - this.cost_price;
    }
}
