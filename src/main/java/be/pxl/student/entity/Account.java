package be.pxl.student.entity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Account {

    private String IBAN;
    private String name;
    private List<Payment> payments;

    public Account(String IBAN) {
        this(IBAN, "Unknown");
    }

    public Account(String IBAN, String name) {
        this.IBAN = IBAN;
        this.name = name;
        this.payments = new ArrayList<>();
    }

    public String getIBAN() {
        return IBAN;
    }

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public void addPayment(Payment payment) {
        payments.add(payment);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        Account account = (Account) o;
        return IBAN.equals(account.IBAN);
    }

    @Override
    public int hashCode() {
        return Objects.hash(IBAN);
    }

    @Override
    public String toString() {
        return "Account{" +
                "IBAN='" + IBAN + '\'' +
                ", name='" + name + '\'' +
                ", payments=[" + payments.stream().map(Payment::toString).collect(Collectors.joining(",")) + "]}";
    }
}
