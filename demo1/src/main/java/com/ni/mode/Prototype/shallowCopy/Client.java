package com.ni.mode.Prototype.shallowCopy;

import com.ni.mode.Prototype.common.WorkExperience;

import java.util.ArrayList;

public class Client {
    public static void main(String[] args) {
        ArrayList<WorkExperience> workExperience = new ArrayList<>();
        workExperience.add(new WorkExperience("2019-2020", "company A"));
        workExperience.add(new WorkExperience("2020-2021", "company B"));
        Resume resumeA = new Resume("张三", 20, workExperience);
        Resume resumeB = resumeA.clone();
        Resume resumeC = resumeA.clone();
        resumeC.setAge(22);
        resumeC.setName("zhangsan");
        workExperience.add(new WorkExperience("2021-2022", "company C"));
        resumeC.setWorkExperience(workExperience);

        System.out.println("resumeA:" + resumeA);
        System.out.println("resumeB:" + resumeB);
        System.out.println("resumeC:" + resumeC);

    }
}
