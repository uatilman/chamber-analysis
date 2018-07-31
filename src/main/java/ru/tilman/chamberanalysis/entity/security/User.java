package ru.tilman.chamberanalysis.entity.security;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Pattern(regexp = "[A-Z][a-zA-Z]*", message = "{validation.user.firstname.pattern}")
    @Size(min = 2, max = 50, message = "{validation.user.firstname.size}")
    @Column(name = "first_name")
    private String firstname;

    @Pattern(regexp = "[a-zA-z]+([ '-][a-zA-Z]+)*", message = "{validation.user.lastname.pattern}")
    @Size(min = 2, max = 50, message = "{validation.user.lastname.size}")
    @Column(name = "last_name")
    private String lastname;

    @Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", message = "{validation.user.email.pattern}")
    @Column(name = "email")
    private String email;

    @ManyToMany
    @JoinTable(name = "user_role",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")}
    )
    private List<Role> roleList;

    @Pattern(regexp = "^[a-zA-Z0-9._-]{3,}$", message = "{validation.user.login.pattern}")
    @Column(name = "login", unique = true)
    private String login;

    @Pattern(regexp = ".{8,}", message = "{validation.user.password.pattern}")
    @Column(name = "password")
    private String password;

    @Column(name = "enabled")
    private boolean enabled;

/*	@JsonIgnore
	@OneToMany(mappedBy="author", fetch=FetchType.LAZY)
	private List<Article> articles;
	*/

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
/*
	public List<Article> getArticles() {
		return articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}*/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
