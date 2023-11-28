package com.ni.common.structure;

/**
 * @ClassName BinarySearch
 * @Description
 * @Author zihao.ni
 * @Date 2023/11/10 08:06
 * @Version 1.0
 **/
public class BinarySearch {
    public static void main(String[] args) {
        String aa = "SELECT TA.OWNER, TA.TABLE_NAME, TA.COLUMN_ID, TA.COLUMN_NAME, TB.COMMENTS, CASE WHEN INSTR (TA.DATA_TYPE , 'CHAR') > 0 OR TA.DATA_TYPE = 'BLOB' OR TA.DATA_TYPE = 'CLOB' THEN TA.DATA_TYPE || '(' || TRIM(TA.DATA_LENGTH) || ')' WHEN TA.DATA_TYPE = 'NUMBER' AND TA.DATA_PRECISION IS NOT NULL THEN TA.DATA_TYPE || '(' || TRIM(TA.DATA_PRECISION) || ',' || TRIM(TA.DATA_SCALE)||')' WHEN TA.DATA_TYPE = 'NUMBER' AND TA.DATA_PRECISION IS NULL AND TA.DATA_SCALE = '0' THEN 'INTEGER' ELSE TA.DATA_TYPE END AS DATA_TYPE, TA.NULLABLE AS IS_NULLABLE, TC.IS_PK FROM all_tab_cols TA, all_col_comments TB, (SELECT T1.TABLE_NAME,T2.COLUMN_NAME, 'PS' AS IS_PK FROM all_constraints T1, all_cons_columns T2 WHERE T1.CONSTRAINT_TYPE = 'P' AND T1.OWNER = T2.OWNER AND T1.CONSTRAINT_NAME = T2.CONSTRAINT_NAME AND T1.TABLE_NAME = T2.TABLE_NAME) TC WHERE TA.OWNER = TB.OWNER AND TA.TABLE_NAME = TB.TABLE_NAME AND TA.COLUMN_NAME = TB.COLUMN_NAME AND TA.TABLE_NAME = TC.TABLE_NAME(+) AND TA.COLUMN_NAME = TC.COLUMN_NAME(+)&&%%select * from sys.tenant_virtual_table_column where TABLE_ID=(select TABLE_ID from sys.all_virtual_table_agent t1 join sys.all_virtual_database_agent t2 on t1.DATABASE_ID=t2.DATABASE_ID where t2.DATABASE_NAME='%s' and t1.table_name='%s')";
        String[] split = aa.split("&&%%");
        System.out.println(split[0]);
        System.out.println(split[1]);
    }

    public int binarySearch(int[] arr, int target) {
        int left = 0;
        int right = arr.length-1;
        while (left <= right) {
            int midIndex = left + (right - left) / 2;
            int mid = arr[midIndex];
            if (mid == target) {
                return midIndex;
            } else if (mid < target) {
                left = midIndex + 1;
            } else {
                right = midIndex - 1;
            }
        }
        return -1;
    }
}