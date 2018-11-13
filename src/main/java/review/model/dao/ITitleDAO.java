package review.model.dao;

import review.model.entity.Title;
import review.servlet.beans.TitlesBean;

import java.util.List;

public interface ITitleDAO {

    void saveTitle(Title title);

    List<Title> getAll();

    void deleteTitle(Title title);

    Title getById(int id);

    List<Title> getByName(String name);

    List<Title> getBySubCategoryId(int id);

    List<TitlesBean> getBySubCategoryIdWithCity(int id);

    List<Title> getAllByLimit(int limit);
}
