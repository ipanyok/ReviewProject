package review.servlet.beans;

import review.model.entity.AdminBuffer;
import review.model.entity.Category;
import review.model.entity.SubCategory;

import java.util.List;

public class AdminBufferBean {

    private AdminBuffer adminBuffer;
    private String categoryName;
    private List<SubCategory> subCategoriesList;
    private String status;

    public AdminBufferBean() {
    }

    public AdminBufferBean(AdminBuffer adminBuffer, String categoryName, List<SubCategory> subCategoriesList, String status) {
        this.adminBuffer = adminBuffer;
        this.categoryName = categoryName;
        this.subCategoriesList = subCategoriesList;
        this.status = status;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public AdminBuffer getAdminBuffer() {
        return adminBuffer;
    }

    public List<SubCategory> getSubCategoriesList() {
        return subCategoriesList;
    }

    public void setSubCategoriesList(List<SubCategory> subCategoriesList) {
        this.subCategoriesList = subCategoriesList;
    }

    public void setAdminBuffer(AdminBuffer adminBuffer) {
        this.adminBuffer = adminBuffer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
