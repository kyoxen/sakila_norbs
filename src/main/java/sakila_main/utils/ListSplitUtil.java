package sakila_main.utils;


import java.util.ArrayList;
import java.util.List;

public class ListSplitUtil<T extends Object> {
    /**
     *
     * @param list
     * @param size
     * @return
     * @param <T>
     */

    public static<T> List<List<T>> split(List<T> list, int size) {
        if(list==null || list.size()==0) {
            return new ArrayList<>();
        }
        // Get the total amount of data
        int count = list.size();
        // Calculate how many batches to split
        int pageCount =(count /size) + (count % size== 0 ? 0 : 1);
        List<List<T>> temp = new ArrayList<>(pageCount);
        for (int i = 0, from = 0, to = 0; i < pageCount; i++) {
            from = i * size;
            to = from+size;
            // If the total is exceeded, take the position of the last number
            to = to>count?count:to;
            // Splitting the list
            List<T> list1 = list.subList(from,to);
            //Return the split list into a large list
            temp.add(list1);
            //You can also modify this method to do the operation directly here
        }
        return temp;
    }
}
