package review.model.dao;

import review.model.entity.SubCategory;

import java.util.List;

public interface ISubCategoryDAO {

    void saveSubCategory(SubCategory subCategory);

    List<SubCategory> getAll();

    void deleteSubCategory(SubCategory subCategory);

    SubCategory getById(int id);

    List<SubCategory> getByCategoryId(int idCategory);

}
