package be.pxl.student.util;

import be.pxl.student.entity.Account;
import be.pxl.student.entity.Payment;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * Util class to import csv file
 */
public class BudgetPlannerImporter {
    public static final Logger LOGGER = Logger.getLogger("BudgetPlannerImporter");
    public static final DateFormat FORMAT = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH);
    public static final Path PATH = Paths.get("src/main/resources/");

    public static Set<Account> readFile(String fileName) {
        //LogManager.getLogManager().reset(); //reset root log manager handlers
        Set<Account> accountsSet = new HashSet<>();
        try (BufferedReader reader = Files.newBufferedReader(PATH.resolve(fileName))) {
            FileHandler fh = new FileHandler("src/main/resources/budget_planner_importer.log", true);
            LOGGER.addHandler(fh);
            String line;
            //reader.readLine(); //first line is skipped
            while ((line = reader.readLine()) != null) {
                createAndAddAccount(accountsSet, line.split(","));
            }
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, e.getMessage());
        }
        return accountsSet;
    }

    private static void createAndAddAccount(Set<Account> accountsSet, String[] values) {
        try {
            if (values.length != 7) throw new IndexOutOfBoundsException("input values must be split into seven values");
            String name = values[0];
            String accountIBAN = values[1];
            String counterAccountIBAN = values[2];
            Date date = FORMAT.parse(values[3]);
            float amount = Float.parseFloat(values[4]);
            String currency = values[5];
            String detail = values[6];

            // adding or updating the accounts
            Payment payment = new Payment(date, amount, currency, detail);
            updateOrAddTheAccount(accountsSet, new Account(accountIBAN, name), payment);
            payment.setAmount(payment.getAmount() * -1);
            updateOrAddTheAccount(accountsSet, new Account(counterAccountIBAN), payment);
        } catch (ParseException | IndexOutOfBoundsException e) {
            LOGGER.log(Level.INFO, e.toString());
        }
    }

    private static void updateOrAddTheAccount(Set<Account> accounts, Account account, Payment payment) {
        if (!accounts.contains(account)) {
            // adding account with unique IBAN number
            account.addPayment(payment);
            accounts.add(account);
        } else {
            // update name and payment of the account with the same IBAN number
            Object[] array = accounts.stream()
                    .filter(a -> a.getIBAN().equals(account.getIBAN()))
                    .toArray();
            Account oldAccount = (Account) array[0];
            oldAccount.addPayment(payment);
            oldAccount.setName(account.getName());
        }
    }
}
