package review.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import review.model.dao.IAdminBufferDAO;
import review.model.entity.AdminBuffer;

import java.util.List;

@Service
@Transactional
public class AdminBufferService {

    @Autowired
    private IAdminBufferDAO adminBufferDAO;

    public void save(AdminBuffer adminBuffer) {
        adminBufferDAO.saveBuffer(adminBuffer);
    }

    public List<AdminBuffer> getAll() {
        return adminBufferDAO.getAll();
    }

    public void delete(AdminBuffer adminBuffer) {
        adminBufferDAO.deleteBuffer(adminBuffer);
    }

    public AdminBuffer getById(int id) {
        return adminBufferDAO.getById(id);
    }

    public List<AdminBuffer> getByUserLogin(String userLogin) {
        return adminBufferDAO.getByUserLogin(userLogin);
    }

    public int getCountFromUsers() {
        return adminBufferDAO.getCountFromUsers();
    }
}
