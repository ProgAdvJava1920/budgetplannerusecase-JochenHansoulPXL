package be.pxl.student;

import be.pxl.student.entity.Account;
import be.pxl.student.util.BudgetPlannerImporter;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class BudgetPlanner {
    private static final Logger logger = Logger.getLogger("BudgetPlanner");

    public static void main(String[] args) {
        //logger.log(Level.WARNING, "first warning log");
        Set<Account> accounts = BudgetPlannerImporter.readFile("account_payments.csv");
        System.out.println(accounts.toString());
    }
}
