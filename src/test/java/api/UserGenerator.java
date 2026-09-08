package api;

import com.github.javafaker.Faker;
import lombok.Data;

@Data
public class UserGenerator {
    private String email;
    private String password;
    private String name;

    public static UserGenerator generateUser() {
        Faker faker = new Faker();
        UserGenerator user = new UserGenerator();
        user.setEmail(faker.internet().emailAddress());
        user.setPassword(faker.internet().password(6, 10));
        user.setName(faker.name().firstName());
        return user;
    }
}