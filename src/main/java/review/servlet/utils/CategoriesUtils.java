package review.servlet.utils;

import review.model.entity.SubCategory;

import javax.servlet.http.HttpSession;
import java.util.*;

public class CategoriesUtils {

    public static Map<String, List<SubCategory>> addCategoryInMap(HttpSession session, String categoryName, SubCategory subCategory, String categoryMapAttribute) {
        Map<String, List<SubCategory>> categoryMap = (Map<String, List<SubCategory>>) session.getAttribute(categoryMapAttribute);
        List<SubCategory> subCategories = new ArrayList<>();
        if (categoryMap != null) {
            for (Map.Entry<String, List<SubCategory>> elem : categoryMap.entrySet()) {
                if (elem.getKey().equals(categoryName)) {
                    subCategories = elem.getValue();
                    if (subCategories == null) {
                        subCategories = new ArrayList<>();
                    }
                    break;
                }
            }
            if (!subCategories.contains(subCategory)) {
                subCategories.add(subCategory);
                categoryMap.put(categoryName, subCategories);
            }
        } else {
            categoryMap = new TreeMap<>();
            categoryMap.put(categoryName, new ArrayList<>(Arrays.asList(subCategory)));
        }
        return categoryMap;
    }
}
