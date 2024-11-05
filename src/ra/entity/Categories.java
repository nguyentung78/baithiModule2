package ra.entity;

import java.io.Serializable;
import java.util.Scanner;

public class Categories implements IEntityManagement {
    private int category_id;
    private String category_name;
    private boolean category_status;

    public Categories() {
    }

    public Categories(int category_id, String category_name, boolean category_status) {
        this.category_id = category_id;
        this.category_name = category_name;
        this.category_status = category_status;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public boolean isCategory_status() {
        return category_status;
    }

    public void setCategory_status(boolean category_status) {
        this.category_status = category_status;
    }


    @Override
    public void inputData(Scanner scanner) {
        do {
            System.out.print("Nhập tên danh mục: ");
            this.category_name = scanner.nextLine().trim();
            if (this.category_name.isEmpty()) {
                System.err.println("Tên danh mục không được để trống. Vui lòng nhập lại.");
            }
        }while (this.category_name.isEmpty());
        this.category_status = true;
    }

    @Override
    public void displayData() {
        System.out.printf("Mã danh mục: %d - Tên danh mục: %s - Trạng thái: %s\n",
                this.category_id, this.category_name, (this.category_status ? "Hoạt động":"Không hoạt động "));
    }
}
