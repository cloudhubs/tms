package baylor.csi.questionManagement.model;

import baylor.csi.questionManagement.model.supermodel.UUIDHashedEntityObject;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;


@Entity
@Table(name = "language")
@SequenceGenerator(initialValue = 1, allocationSize = 1, name = "idgen", sequenceName = "language_id_seq")
public class Language extends UUIDHashedEntityObject {

    private String name;


    @NotNull
    @Column(nullable = false)
    @Size(max = 256, min = 3)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
