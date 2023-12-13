package com.romei;

import java.util.List;

public interface IDBRepository {

    public List<Object> dataIsInDB(List<Object> fields,String query, String place);
    public boolean queryWithData(List<Object> data, String query, String place);


}
