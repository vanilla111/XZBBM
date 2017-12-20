package team.redrock.util;

import java.util.List;

/**
 * @Author zhang
 * @Date 2017/9/22 15:47
 * @Content
 */
public class PageResult {

    private long total;
    private List<?> rows;
    public long getTotal() {
        return total;
    }
    public void setTotal(long total) {
        this.total = total;
    }
    public List<?> getRows() {
        return rows;
    }
    public void setRows(List<?> rows) {
        this.rows = rows;
    }

}