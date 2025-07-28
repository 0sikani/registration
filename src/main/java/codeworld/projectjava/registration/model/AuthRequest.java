package codeworld.projectjava.registration.model;

public class AuthRequest {
    private String email;
    private String password;

    public AuthRequest(){}

    public AuthRequest(String email, String password){
        this.email = email;
        this.password = password;
    }

    public String getEmail(){return this.email;}
    public String getPassword(){return this.password;}

    public void setEmail(String email){this.email = email;}
    public void setPassWord(String password){this.password = password;}
}
