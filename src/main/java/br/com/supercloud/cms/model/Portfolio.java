package br.com.supercloud.cms.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "T_PORTFOLIO")
public class Portfolio extends AbstractEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;
	@NotBlank
	private String title;
	private String customer;
	@Column(length = 2048)
	private String description;
	@ManyToMany
	@JoinTable(name = "T_PORTFOLIO_TAGS")
	private List<Tag> tags;
	@OneToMany(mappedBy = "portfolio")
	private List<SCFile> images;
	@OneToOne
	private SCFile coverImage;


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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public List<SCFile> getImages() {
		return images;
	}

	public void setImages(List<SCFile> images) {
		this.images = images;
	}

	public SCFile getCoverImage() {
		return coverImage;
	}

	public void setCoverImage(SCFile coverImage) {
		this.coverImage = coverImage;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

}
