package review.model.dao;

import review.model.entity.Category;

import java.util.List;

public interface ICategoryDAO {

    void saveCategory(Category category);

    List<Category> getAll();

    void deleteCategory(Category category);

    Category getById(int id);

    Category getByName(String name);

}
