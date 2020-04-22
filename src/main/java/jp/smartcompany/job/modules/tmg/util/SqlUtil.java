package jp.smartcompany.job.modules.tmg.util;


import java.util.Collections;
import java.util.Date;
import java.util.List;

public class SqlUtil {


    /**
     *Oracle nvl()変換
     *
     *
     * */
    public static  Object nvlSql(Object value1,Object value2){
        if(value1!=null){
            return value2;
        }else{
            return value1;
        }
    }

    /**
     *Oracle greatest()変換
     *
     *
     * */
    public  static Object greatestSql(List<Object> objectList){

        Object obj=null;

        if(objectList.get(0) instanceof Integer){
            List<Integer> ints = (List<Integer>)(List)objectList;
            obj=Collections.max(ints);
        }else if(objectList.get(0) instanceof String){
            List<String> strs = (List<String>)(List)objectList;
            obj=Collections.max(strs);
        }else if(objectList.get(0) instanceof Date){
            List<Date> dates = (List<Date>)(List)objectList;
            obj=Collections.max(dates);
        }else if(objectList.get(0) instanceof Double){
            List<Double> dobs = (List<Double>)(List)objectList;
            obj=Collections.max(dobs);
        }
        return  obj;
    }

}
