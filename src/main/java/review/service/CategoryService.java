package review.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import review.model.dao.ICategoryDAO;
import review.model.entity.Category;

import java.util.List;

@Service
@Transactional
public class CategoryService {

    @Autowired
    private ICategoryDAO categoryDAO;

    public void save(Category category) {
        categoryDAO.saveCategory(category);
    }

    public List<Category> getAll() {
        return categoryDAO.getAll();
    }

    public void delete(Category category) {
        categoryDAO.deleteCategory(category);
    }

    public Category getById(int id) {
        return categoryDAO.getById(id);
    }

    public Category getByName(String name) {
        return categoryDAO.getByName(name);
    }
}
