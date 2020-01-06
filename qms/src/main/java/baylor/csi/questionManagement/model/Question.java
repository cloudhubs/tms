package baylor.csi.questionManagement.model;

import baylor.csi.questionManagement.model.supermodel.UUIDHashedEntityObject;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "question")
@SequenceGenerator(initialValue = 1, allocationSize = 1, name = "idgen", sequenceName = "question_id_seq")
@NamedQueries({
        @NamedQuery(name = Question.FIND_BY_CATEGORYID_AND_NAME,
                query = "select new baylor.csi.questionManagement.model.dto.QuestionDto(q.id,q.title,q.level) from Question q join q.categories c where c.id = ?1 and lower(q.title) like ?2"),
        @NamedQuery(name = Question.FIND_DTO_BY_NAME,
                query = "select new baylor.csi.questionManagement.model.dto.QuestionDto(q.id,q.title,q.level) from Question q  where lower(q.title) like ?1"),
        @NamedQuery(name = Question.FIND_BY_CATEGORYID,
                query = "select q from Question q join q.categories c where c.id = ?1"),
        @NamedQuery(name = Question.FIND_BY_CATEGORYID_LEVEL_LANGUAGE,
                query = "select q from Question q join q.categories c join q.codes code join code.language l where c.id = ?1 and q.level= ?2 and l.id= ?3"),



})
public class Question extends UUIDHashedEntityObject {
    public static final String FIND_BY_CATEGORYID_AND_NAME = "FIND_BY_CATEGORYID_AND_NAME";
    public static final String FIND_DTO_BY_NAME = "FIND_DTO_BY_NAME";
    public static final String FIND_BY_CATEGORYID = "FIND_BY_CATEGORYID";
    public static final String FIND_BY_CATEGORYID_LEVEL_LANGUAGE = "FIND_BY_CATEGORYID_LEVEL_LANGUAGE";
    private String title;
    private Integer level;
    private String body;
    private Set<Choice> choices = new HashSet<>();
    private Set<Code> codes = new HashSet<>();
    private Set<Category> categories = new HashSet<>();


    @NotNull
    @Column(nullable = false)
    @Size(max = 256, min = 3)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @NotNull
    @Column(nullable = false)
    @Min(1)@Max(5)
    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @NotNull
    @Column(nullable = false)
    @Size(min = 3)
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @OneToMany(mappedBy = "question", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    public Set<Choice> getChoices() {
        return choices;
    }

    public void setChoices(Set<Choice> choices) {
        this.choices = choices;
    }

    @OneToMany(mappedBy = "question", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    public Set<Code> getCodes() {
        return codes;
    }

    public void setCodes(Set<Code> codes) {
        this.codes = codes;
    }


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable
    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }
}
