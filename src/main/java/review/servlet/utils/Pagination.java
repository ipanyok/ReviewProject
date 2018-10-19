package review.servlet.utils;

import review.servlet.beans.PagesBean;

import java.util.ArrayList;
import java.util.List;

public class Pagination {

    public static List<PagesBean> pagesCount(List titles, int paginationTotal) {
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

    public static List printResult(List list, int start, int total) {
        int length = start + total;
        List result = new ArrayList<>();
        for (int i = start; i < length; i++) {
            if (i < list.size()) {
                result.add(list.get(i));
            } else {
                break;
            }
        }
        return result;
    }
}
