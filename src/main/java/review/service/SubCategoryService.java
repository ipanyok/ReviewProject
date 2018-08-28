package review.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import review.model.dao.ISubCategoryDAO;
import review.model.entity.SubCategory;

import java.util.List;

@Service
@Transactional
public class SubCategoryService {

    @Autowired
    private ISubCategoryDAO subCategoryDAO;

    public void save(SubCategory subCategory) {
        subCategoryDAO.saveSubCategory(subCategory);
    }

    public List<SubCategory> getAll() {
        return subCategoryDAO.getAll();
    }

    public void delete(SubCategory subCategory) {
        subCategoryDAO.deleteSubCategory(subCategory);
    }

    public SubCategory getById(int id) {
        return subCategoryDAO.getById(id);
    }

    public List<SubCategory> getByCategoryId(int idCategory) {
        return subCategoryDAO.getByCategoryId(idCategory);
    }

    public SubCategory getByName(String name) {
        return subCategoryDAO.getByName(name);
    }
}
