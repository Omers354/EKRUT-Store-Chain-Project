package server;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;

/**

EndOfMonthThread is a class that implements the Runnable interface, it is used to perform a task on the last day of the month.
The class contains a single method named run() which is executed when a thread starts.
The run() method continuously checks the current day and month, if it matches the last day of the month,
it performs the task of getting monthly reports created from the mysqlConnection class.
It sleeps for a day after each check.
@author Shahar
@version 4.0

*/
public class EndOfMonthThread implements Runnable {
	/*
	The run method is the entry point for the thread, it checks the current day and month, if it matches the last day of the month,
	it performs the task of getting monthly reports created from the mysqlConnection class.
	It sleeps for a day after each check.
	*/
    @Override
    public void run() {
        LocalDate date = LocalDate.now();
        int nextMonth = date.getMonthValue()+1;
    	 while (true) {
    		 date = LocalDate.now();
             int currentDay = date.getDayOfMonth();
             int currentMonth = date.getMonthValue();
             int currentYear  = date.getYear();
             LocalDate lastDayOfCurrentYearMonth = date.with(TemporalAdjusters.lastDayOfMonth());
             int lastday =lastDayOfCurrentYearMonth.getDayOfMonth();
             if (currentDay == lastday || nextMonth ==currentMonth ) {
                 date = LocalDate.now();
                  currentDay = date.getDayOfMonth();
                  currentMonth = date.getMonthValue();
                  nextMonth = date.getMonthValue()+1;
                  currentYear  = date.getYear();
                  lastDayOfCurrentYearMonth = date.with(TemporalAdjusters.lastDayOfMonth());
                  lastday =lastDayOfCurrentYearMonth.getDayOfMonth();
                 // Perform task on the last day of the month
                 mysqlConnection.getMonthlyReportsCreated();
             }
             try {
                 Thread.sleep(86400000); // sleep for a day
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }
         }
     }
 }

