package review.model.dao;

import review.model.entity.AdminBuffer;

import java.util.List;

public interface IAdminBufferDAO {

    void saveBuffer(AdminBuffer adminBuffer);

    List<AdminBuffer> getAll();

    void deleteBuffer(AdminBuffer adminBuffer);

    AdminBuffer getById(int id);

    List<AdminBuffer> getByUserId(int idUser);
}
