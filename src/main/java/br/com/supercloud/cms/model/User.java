package br.com.supercloud.cms.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "T_USER")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class User extends AbstractEntity implements UserDetails, Serializable {

	private static final long serialVersionUID = -604570634696304253L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Transient
	private transient Collection<GrantedAuthority> authorities;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name = "T_USER_ROLES")
	private List<Role> roles;
	
	private String name;
	
	private String email;
	
	@Temporal(TemporalType.DATE)
	private Date dob;
	
	private String password;
	
	private String username;
	
	public User() {
		super();
	}

	public static UserDetails create(Long id,String username, String password,
			String... authorities) {
		return new User(id, username, password, authorities);
	}

	private User(Long id, String username, String password, String... authorities) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.name = "";
		this.email = "";
		this.dob = new Date();
		this.authorities = AuthorityUtils.createAuthorityList(authorities);
	}

	public Collection<GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public String getPassword() {
		return password;
	}

	public String getUsername() {
		return username;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Collection<GrantedAuthority> getAuthorities_() {
		return authorities;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public void setAuthorities(Collection<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
}
