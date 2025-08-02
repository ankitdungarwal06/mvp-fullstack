package com.neelkanth.headerbackend.scheduler;

import com.neelkanth.headerbackend.entity.Pnr;
import com.neelkanth.headerbackend.repository.PnrRepository;
import com.neelkanth.headerbackend.service.PnrService;
import com.neelkanth.headerbackend.service.UserService;
import com.neelkanth.headerbackend.util.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Stream;

@Component
public class PnrScheduler {

    @Autowired
    private UserService userService;
    @Autowired
    private PnrRepository pnrRepository;
    @Autowired
    private PnrService pnrService;
    @Autowired
    private NotificationService notificationService;


    //@Scheduled(cron = "0 0 8 * * ?") // Run daily at 8 AM
    @Scheduled(cron = "0 */2 * * * ?") // Run every 2 minutes for testing purposes
    public void checkPnrStatuses() {
        System.out.println("Scheduler started at: " + LocalDateTime.now());
        List<Pnr> pnrs = pnrRepository.findAll();
        System.out.println("Found " + pnrs.size() + " PNRs");
        for (Pnr pnr : pnrs) {
            long daysUntilTravel = ChronoUnit.DAYS.between(LocalDate.now(), pnr.getTravelDate());
            boolean shouldCheck = daysUntilTravel > 7 ? LocalDate.now().getDayOfWeek().getValue() == 1 : // Weekly on Monday
                    daysUntilTravel <= 2 || daysUntilTravel <= 7; // Twice daily or daily

            if (true) {
                try {
                    Pnr updatedPnr = pnrService.getPnrStatus(pnr.getPnrNumber());
                    pnr.setStatus(updatedPnr.getStatus());
                    pnr.setCoach(updatedPnr.getCoach());
                    pnr.setSeat(updatedPnr.getSeat());
                    pnr.setTrainName(updatedPnr.getTrainName());
                    pnr.setTrainNumber(updatedPnr.getTrainNumber());
                    pnr.setLastChecked(LocalDate.now());
                    pnrRepository.save(pnr);

                    String message = String.format("PNR %s status: %s\nTrain: %s (%s)\nCoach: %s, Seat: %s",
                            pnr.getPnrNumber(), pnr.getStatus(), pnr.getTrainName(), pnr.getTrainNumber(), pnr.getCoach(), pnr.getSeat());
                    if (daysUntilTravel <= 2 && !pnr.getStatus().equals("CONFIRMED")) {
                        message += "\nTravel date is soon! Cancel to save refund? Visit: https://www.irctc.co.in";
                    }
                    notificationService.sendEmail(fetchUserEmails(pnr), "PNR Status Update", message);
                } catch (Exception e) {
                    System.err.println("Error checking PNR " + pnr.getPnrNumber() + ": " + e.getMessage());
                    notificationService.sendEmail(fetchUserEmails(pnr), "PNR Check Error", "Failed to check PNR " + pnr.getPnrNumber());
                }
            }
        }
    }

    @Scheduled(cron = "0 */2 * * * ?")
    public void aggressiveReminders() {
        System.out.println("Aggressive reminders started at: " + LocalDateTime.now());
        List<Pnr> pnrs = pnrRepository.findAll();
        for (Pnr pnr : pnrs) {
            if (ChronoUnit.DAYS.between(LocalDate.now(), pnr.getTravelDate()) <= 2 && !pnr.getStatus().equals("CONFIRMED")) {
                String message = String.format("Urgent: PNR %s is %s. Train: %s (%s), Coach: %s, Seat: %s\nCancel now to maximize refund? Visit: https://www.irctc.co.in",
                        pnr.getPnrNumber(), pnr.getStatus(), pnr.getTrainName(), pnr.getTrainNumber(), pnr.getCoach(), pnr.getSeat());
                notificationService.sendEmail(fetchUserEmails(pnr), "PNR Urgent Reminder", message);
            }
        }
    }

    public String[] fetchUserEmails(Pnr pnr) {
      return Stream.of(pnr.getUsers())
                    .map(user -> userService.fetchUserById(Long.valueOf(user)).getEmail())
                    .toArray(String[]::new);
    }
}
