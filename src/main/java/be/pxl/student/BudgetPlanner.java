package be.pxl.student;

import be.pxl.student.entity.Account;
import be.pxl.student.util.BudgetPlannerImporter;

import java.io.IOException;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class BudgetPlanner {
    private static final Logger logger = Logger.getLogger(BudgetPlanner.class.getName());

    public static void main(String[] args) {
        try {
            //logger.log(Level.WARNING, "first warning log");
            FileHandler fh = new FileHandler("src/main/resources/budget_planner.log", true);
            Set<Account> accounts = BudgetPlannerImporter.readFile("account_payments.csv");
            logger.addHandler(fh);
            //logger.log(Level.WARNING, accounts.toString());
        } catch (IOException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }
}
