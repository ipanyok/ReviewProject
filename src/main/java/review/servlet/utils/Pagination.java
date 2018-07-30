package review.servlet.utils;

import review.model.entity.Title;
import review.servlet.beans.PagesBean;

import java.util.ArrayList;
import java.util.List;

public class Pagination {

    public static List<PagesBean> pagesCount(List<Title> titles, int paginationTotal) {
        int count = titles.size();
        int result;
        if (count % paginationTotal > 0) {
            result = count / paginationTotal + 1;
        } else {
            result = count / paginationTotal;
        }
        List<PagesBean> pagesList = new ArrayList<>();
        for (int i = 1; i <= result; i++) {
            pagesList.add(new PagesBean(i));
        }
        return pagesList;
    }

    public static List<Title> printResult(List<Title> list, int start, int total) {
        int lenght = start + total;
        List<Title> result = new ArrayList<>();
        for (int i = start; i < lenght; i++) {
            if (i < list.size()) {
                result.add(list.get(i));
            } else {
                break;
            }
        }
        return result;
    }
}
