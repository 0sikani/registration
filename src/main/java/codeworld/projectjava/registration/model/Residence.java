package codeworld.projectjava.registration.model;

import java.util.List;

public class Residence {
    private Long id; 
    private String physicalAddress;
    private String digitalAddress;
    private String city;
    private List<User> user;

    public Residence(){}

    public Residence(Long id, String physicalAddress, String digitalAddress, String city){
        this.id = id;
        this.physicalAddress = physicalAddress;
        this.digitalAddress = digitalAddress;
        this.city = city;
    }


    public Long getId(){return id;}
    public String getPhysicalAddress(){return physicalAddress;}
    public String getDigitalAddress(){return digitalAddress;}
    public String getCity(){return city;}
    public List<User> getUser(){return user;}
    

    public void setId(Long id){this.id = id;}
    public void setPhysicalAddress(String physicalAddress){this.physicalAddress = physicalAddress;}
    public void setDigitalAddress(String digitalAddress){this.digitalAddress = digitalAddress;}
    public void setCity(String city){this.city = city;}
    public void setResidenceUser(List<User> user){this.user = user;}
}
