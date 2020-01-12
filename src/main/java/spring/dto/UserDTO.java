package spring.dto;


import lombok.Data;
import spring.entity.Sex;

@Data
public class UserDTO {
    private String username;
    private String firstName;
    private String surname;
    private Sex sex;
    private String password;
    private String phoneNumber;
    private int age;
    private Long teamId;
}
