package codeworld.projectjava.registration.model;

public class UserResidence {
    private Long userId;
    private Long residenceId;


    public UserResidence(){}

    public UserResidence(Long userId, Long residenceId){
        this.userId = userId;
        this.residenceId = residenceId;
    }


    public Long getUserId(){return userId;}
    public Long getResidenceId(){return residenceId;}

    public void setUserId(Long userId){this.userId = userId;}
    public void setResidenceId(Long residenceId){this.residenceId = residenceId;}
}
