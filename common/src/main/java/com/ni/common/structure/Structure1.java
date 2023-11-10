package com.ni.common.structure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName Structure1
 * @Description
 * @Author zihao.ni
 * @Date 2023/11/7 14:25
 * @Version 1.0
 **/
public class Structure1<MapType, ListType> {
    public static void main(String[] args) {
        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student("aa", "class1", 12, 12, 1));
        studentList.add(new Student("bb", "class1", 12, 12, 1));
        studentList.add(new Student("cc", "class2", 12, 12, 2));
        studentList.add(new Student("dd", "class2", 12, 12, 2));
        Structure1<Integer, Student> structure = new Structure1<>();
    }

    private MapType key;
    private List<ListType> value;
}
