package com.romei;

import java.util.List;
import java.util.Map;

public interface IDBRepository {

    public List<Object> dataIsInDB(List<Object> fields,String query, String place);
    public boolean queryWithData(List<Object> data, String query, String place);
    public List<Map<String,Object>> getItemsWithQuery(String query, List<String> properties);
}
