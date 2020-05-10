package com.mduczmal.therapy;

import javax.persistence.Embeddable;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.validator.routines.EmailValidator;

@Embeddable
public class AdDetails {
    private String name;
    private String surname;
    private String address;
    private String description;
    private Map<String, Integer> pricing = new HashMap<>();
    private String therapyCenter;
    private String imagePath;
    private String telephoneNumber;
    private String email;
    private String therapyApproach;
    private String training;
    private boolean supervision;
    private boolean onlineSessions;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (!name.isBlank()) this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        if (!surname.isBlank()) this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        if (!address.isBlank()) this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (!description.isBlank()) this.description = description;
    }

    public Map<String, Integer> getPricing() {
        return pricing;
    }

    public Integer getPrice(String service) {
        return pricing.get(service);
    }

    public void setPrice(String service, Integer price) {
        if (price >= 0) pricing.put(service, price);
    }

    public void deletePrice(String service) {
        pricing.remove(service);
    }

    public String getTherapyCenter() {
        return therapyCenter;
    }

    public void setTherapyCenter(String center) {
        this.therapyCenter = center;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImage(String path) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String number) {
        if (number.length() != 9 && number.length() != 11) return;
        for (char c : number.toCharArray()) {
            if (!Character.isDigit(c)) {
                return;
            }
        }
        this.telephoneNumber = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        EmailValidator validator = EmailValidator.getInstance();
        if (validator.isValid(email)) this.email = email;
    }

    public String getTherapyApproach() {
        return therapyApproach;
    }

    public void setTherapyApproach(String approach) {
        if (!approach.isBlank()) this.therapyApproach = approach;
    }

    public String getTraining() {
        return training;
    }

    public void setTraining(String training) {
        if (training.isBlank()) this.training = training;
    }

    public boolean getSupervision() {
        return supervision;
    }

    public void setSupervision(boolean isSupervised) {
        this.supervision = isSupervised;
    }

    public boolean getOnlineSessions() {
        return onlineSessions;
    }

    public void setOnlineSessions(boolean onlineSessions) {
        this.onlineSessions = onlineSessions;
    }

    public boolean obligatoryFieldsPresent() {
        return (name != null && surname != null && address != null);
    }
}
