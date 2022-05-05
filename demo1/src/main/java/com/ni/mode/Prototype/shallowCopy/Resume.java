package com.ni.mode.Prototype.shallowCopy;

import com.ni.mode.Prototype.common.WorkExperience;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@ToString
public class Resume implements Cloneable {
    private String name;
    private int age;
    private List<WorkExperience> workExperience;

    @Override
    public Resume clone() {
        try {
            Resume v = (Resume) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            //List<WorkExperience> workExperiences = new ArrayList<WorkExperience>();
            //List<WorkExperience> workExperience1 = v.workExperience;
            //workExperience1.forEach(WorkExperience::clone);
            //workExperiences.addAll(workExperience1);
            //v.workExperience=workExperiences;
            return v;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

}


