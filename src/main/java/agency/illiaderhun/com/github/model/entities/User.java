package agency.illiaderhun.com.github.model.entities;

import agency.illiaderhun.com.github.annotations.*;

import java.util.Objects;

/**
 * User class represents some user with {@link Role}
 *
 * @author Illia Derhun
 * @version 1.0
 */

@Table(name = "queries/user")
public class User {

    @Column(name = "user_id")
    @AutoGeneration(TypeOfGeneration.AUTOINCREMENT)
    private int userId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "e_mail")
    private String eMail;

    @Column(name = "phone")
    private String phone;

    @Column(name = "catchword")
    private String catchword;

    @Column(name = "role_id")
    @JoinTable(name = "role", joinsColums = @JoinColumn(name = "user_id"),
            inverseJoinColumn = @JoinColumn(name = "role_id"))
    private int roleId;

    public static class Builder{

        // Required fields
        private int userId;
        private int roleId;
        private String eMail;
        // Optional fields

        private String firstName = "defaultName";
        private String lastName = "defaultSurName";
        private String phone = "+38(85)12-77-954";
        private String catchword = "123";

        public Builder(int userId, int roleId, String eMail){
            this.userId = userId;
            this.roleId = roleId;
            this.eMail = eMail;
        }

        public Builder firstName(String firstName){
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName){
            this.lastName = lastName;
            return this;
        }

        public Builder phone(String phone){
            this.phone = phone;
            return this;
        }

        public Builder catchword(String catchword){
            this.catchword = catchword;
            return this;
        }

        public User build(){
            return new User(this);
        }
    }

    private User(Builder builder) {
        this.userId = builder.userId;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.eMail = builder.eMail;
        this.phone = builder.phone;
        this.catchword = builder.catchword;
        this.roleId = builder.roleId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCatchword() {
        return catchword;
    }

    public void setCatchword(String catchword) {
        this.catchword = catchword;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId == user.userId &&
                roleId == user.roleId &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(eMail, user.eMail) &&
                Objects.equals(phone, user.phone) &&
                Objects.equals(catchword, user.catchword);
    }

    @Override
    public int hashCode() {

        return Objects.hash(userId, firstName, lastName, eMail, phone, catchword, roleId);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", eMail='" + eMail + '\'' +
                ", phone='" + phone + '\'' +
                ", catchword='" + catchword + '\'' +
                ", roleId=" + roleId +
                '}';
    }
}
