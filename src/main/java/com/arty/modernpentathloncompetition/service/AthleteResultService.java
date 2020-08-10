package com.arty.modernpentathloncompetition.service;

import com.arty.modernpentathloncompetition.entity.AthleteResult;
import com.arty.modernpentathloncompetition.entity.AthleteResultOutput;

import java.util.List;

public interface AthleteResultService {

    /**
     * Generating list of athlete results from csv file.
     * @return list of athlete results.
     */
    List<AthleteResult> generateAthleteResultsFromCsv(String filePath);

    /**
     * Method generates athlete leaderboard and returns it to the console.
     * @return leaderboard presented as string.
     */
    String generateAthleteLeaderboard();

    /**
     * Method calculates total points of Athletes.
     * @param athleteResults participating athletes.
     * @return list of athletes with calculated total points.
     */
    List<AthleteResult> calculateTotalPointsOfAthletes(List<AthleteResult> athleteResults);

    /**
     * Method calculates athletes fencing score that is dependant on fencing victories count and total games played.
     * @param winRateFor1000Points victories count to reach 1000 points.
     * @param fencingVictoriesCount amount of victories out of total games.
     * @return amount of points athlete got at fencing competition.
     */
    int calculateAthletesFencingScore(int winRateFor1000Points, int fencingVictoriesCount);

    /**
     * Method calculates athletes swimming score.
     * @param swimmingTime swimming time in String format.
     * @return amount of points athlete got at swimming competition.
     */
    int calculateAthletesSwimmingScore(String swimmingTime);

    /**
     * Method calculates athletes riding score according to fence knock down count, horse's refusal to jump count and
     * obstacle knock down count.
     * @param fenceKnockDownCount fence knock-down quantity.
     * @param refusalToJumpCount horse's refusal to jump quantity.
     * @param obstacleKnockDownCount obstacle knock-down quantity.
     * @return amount of points athlete got at riding competition.
     */
    int calculateAthletesRidingScore(int fenceKnockDownCount, int refusalToJumpCount, int obstacleKnockDownCount);

    /**
     * Method calculates athletes shooting score according to his target score.
     * @param athletesShootingTotalScore athletes shooting total score.
     *
     * @return amount of points athlete got at riding competition.
     */
    int calculateAthletesShootingScore(int athletesShootingTotalScore);

    /**
     * Method calculates win rate by win count and total games.
     * @param winCount amount of wins.
     * @param totalGames total games.
     * @return win rate value,
     */
    double calculateWinRate(int winCount, int totalGames);

    /**
     * Method calculates how many fencing wins athlete must get to have 70% win rate.
     * @param gamesCount total games played by athlete
     * @return wins count
     */
    int calculateWinsCountToReachFencingTargetPercentageWinRate(int gamesCount);

    /**
     * Method calculates time in milliseconds from provided string.
     * @param timeAsString time string. Format: 'mm:ss.S'.
     * @return time in millis.
     */
    int convertToMillisFromString(String timeAsString);

    /**
     * Method calculates concluding event time for the list of athletes and returns them in a sorter order according
     * to concluding event time.
     * @param athleteResults list of athlete results
     * @return sorted list of athlete results prepared for output.
     */
    List<AthleteResultOutput> countConcludingEventTimeAndSortAccordingToIt(List<AthleteResult> athleteResults);

    /**
     * Method determines which place got every single athlete.
     */
    List<AthleteResultOutput> determinePlaceForEachAthlete(List<AthleteResultOutput> athleteResultsForOutput);

    /**
     * Method prepares list of athletes grouped to places and outputs them as string (CSV).
     * @param athleteResultsForOutput list of athlete results sorted by concluding event time.
     * @return leaderboard as string (CSV)
     */
    String generateFinalLeaderboardList(List<AthleteResultOutput> athleteResultsForOutput);
}
