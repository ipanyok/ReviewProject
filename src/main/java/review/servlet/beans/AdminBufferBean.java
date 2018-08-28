package review.servlet.beans;

import review.model.entity.AdminBuffer;

public class AdminBufferBean {

    private AdminBuffer adminBuffer;
    private String status;

    public AdminBufferBean() {
    }

    public AdminBufferBean(AdminBuffer adminBuffer, String status) {
        this.adminBuffer = adminBuffer;
        this.status = status;
    }

    public AdminBuffer getAdminBuffer() {
        return adminBuffer;
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
