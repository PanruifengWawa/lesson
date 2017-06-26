package com.lesson.utils;

/**
 * Created by nixinan on 2017/1/18.
 */
public class DaoUtils {
    public static int getTotalPageNum(Integer totalltemNum,Integer numberperpage  )
    {
     return (totalltemNum%numberperpage)==0?(totalltemNum/numberperpage):(totalltemNum/numberperpage+1);
    }
}
