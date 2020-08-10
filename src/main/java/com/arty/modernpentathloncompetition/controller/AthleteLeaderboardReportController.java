package com.arty.modernpentathloncompetition.controller;

import com.arty.modernpentathloncompetition.service.implementation.AthleteResultServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * Athlete result report controller.
 */
@Controller
public class AthleteLeaderboardReportController {

    /**
     * Injection of athlete result service.
     */
    private final AthleteResultServiceImpl athleteResultService;

    /**
     * Injection of service bean through constructor.
     * @param athleteResultService the Athlete result service.
     */
    public AthleteLeaderboardReportController(AthleteResultServiceImpl athleteResultService) {
        this.athleteResultService = athleteResultService;
    }

    /**
     * Method on GET request returns leaderboard list as CSV to the console.
     * @return leaderboard list in the console of application.
     */
    @GetMapping("/generate-athlete-leaderboard")
    public String generateAthleteLeaderboardReport() {
        System.out.println(athleteResultService.generateAthleteLeaderboard());
        return athleteResultService.generateAthleteLeaderboard();
    }

}