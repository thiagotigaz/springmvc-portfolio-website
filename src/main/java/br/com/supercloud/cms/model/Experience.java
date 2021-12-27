package br.com.supercloud.cms.model;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.annotations.Cache;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Calendar;

@Entity
@Table(name = "T_EXPERIENCE")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Experience extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1428656326486892703L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @NotBlank
    private String title;

    @NotBlank
    private String subtitle;

    @NotBlank
    @Column(length = 2048)
    private String description;

    @NotNull
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(name = "startdate", nullable = false)
    private Calendar startDate;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(name = "enddate", nullable = true)
    private Calendar endDate;

    @Enumerated
    @Column(name = "experiencetypeenum")
    private ExperienceTypeEnum experienceTypeEnum;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Calendar getStartDate() {
        return startDate;
    }

    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public void setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }

    public ExperienceTypeEnum getExperienceTypeEnum() {
        return experienceTypeEnum;
    }

    public void setExperienceTypeEnum(ExperienceTypeEnum experienceTypeEnum) {
        this.experienceTypeEnum = experienceTypeEnum;
    }

}
