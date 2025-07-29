package codeworld.projectjava.registration.model;

import java.util.List;

public class User {

    private Long id;
	private String userName; 
    private String phone;
    private String email;
    private String role;
    private String passWord;
    private List<Residence> residence;

    public User(){}

    public User(Long id, String userName, 
                String phone, String email, 
                String passWord, String role){
        this.id = id;
        this.userName = userName;
        this.phone = phone;
        this.email = email;
        this.role = role;
        this.passWord = passWord;
    }
    
    public Long getId(){return id;}
    public String getUserName(){return userName;}
    public String getPhone(){return phone;}
    public String getEmail(){return email;}
    public String getRole(){return role;}
    public String getPassWord(){return passWord;}
    public List<Residence> getResidence(){return residence;}

    public void setId(Long id){this.id = id;}
    public void setUserName(String userName){this.userName = userName;}
    public void setPhone(String phone){this.phone = phone;}
    public void setEmail(String email){this.email = email;}
    public void setRole(String role){this.role = role;}
    public void setPassWord(String passWord){this.passWord = passWord;}
    public void setUserResidence(List<Residence> residence){this.residence = residence;}
}
