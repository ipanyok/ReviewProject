package review.model.dao;

import review.model.entity.AdminBuffer;

import java.util.List;
import java.util.Map;

public interface IAdminBufferDAO {

    void saveBuffer(AdminBuffer adminBuffer);

    List<AdminBuffer> getAll();

    void deleteBuffer(AdminBuffer adminBuffer);

    AdminBuffer getById(int id);

    List<AdminBuffer> getByUserLogin(String userLogin);

    int getCountFromUsers();

    Map<String, Object> getStatusRequests(Integer idAdminBuffer);
}
