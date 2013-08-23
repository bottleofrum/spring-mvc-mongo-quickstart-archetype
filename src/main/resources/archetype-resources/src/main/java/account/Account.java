package ${package}.account;

import org.codehaus.jackson.annotate.JsonIgnore;

@SuppressWarnings("serial")
public class Account implements java.io.Serializable {

    public static final String FIND_BY_EMAIL = "Account.findByEmail";

    private Long id;

    private String email;

    @JsonIgnore
    private String password;

    private String role = "ROLE_USER";

    protected Account() {

    }

    public Account(String email, String password, String role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
