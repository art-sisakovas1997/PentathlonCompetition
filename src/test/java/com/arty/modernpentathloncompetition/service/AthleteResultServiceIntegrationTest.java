package com.arty.modernpentathloncompetition.service;

import com.arty.modernpentathloncompetition.entity.AthleteResult;
import com.arty.modernpentathloncompetition.entity.AthleteResultOutput;
import com.arty.modernpentathloncompetition.service.implementation.AthleteResultServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;

/**
 * Athlete result service unit test.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AthleteResultServiceIntegrationTest {

    /**
     * Injection of athlete result service implementation.
     */
    @Autowired
    private AthleteResultServiceImpl athleteResultService;

    /**
     * Path to file with correct structure.
     */
    static final String FILE_PATH_CORRECT_STRUCTURE_FILE_EXISTS = "./src/test/resources/AthleteResultsTest1.csv";

    /**
     * Path to file which has wrong structure.
     */
    static final String FILE_PATH_WRONG_STRUCTURE_FILE_EXISTS = "./src/test/resources/AthleteResultsTest2.csv";

    /**
     * Path to file which has data of two athletes with equal times.
     */
    static final String TWO_ATHLETE_SAME_RESULTS = "./src/test/resources/AthleteResultsTest3.csv";

    /**
     * Path to file which has data of 10 athletes with different results.
     */
    static final String NINE_ATHLETES_DIFFERENT_RESULTS = "./src/test/resources/AthleteResultsTest4.csv";

    /**
     * Path to file which has data of 16 athletes with repeating times in-between.
     */
    static final String SIXTEEN_ATHLETES_REPEATING_TIMES = "./src/test/resources/AthleteResultsTest5.csv";

    /**
     * Path to file which has data of 1 athlete.
     */
    static final String ONE_ATHLETE_DATA = "./src/test/resources/AthleteResultsTest6.csv";

    /**
     * Path to file that doesn't exist.
     */
    static final String  FILE_PATH_FILE_DONT_EXIST = "./Athl48646561218jhk68ltsTest12512.csv";

    public List<AthleteResult> prepareAthleteResultWithCorrectStructure() {
        List<AthleteResult> athleteResults = new ArrayList<>();

        AthleteResult athleteResult = new AthleteResult();
        athleteResult.setNameSurname("test1");
        athleteResult.setFencingVictories(2);
        athleteResult.setSwimmingTime("2:44.0");
        athleteResult.setFenceKnockDownCount(0);
        athleteResult.setRefusalToJumpCount(2);
        athleteResult.setObstacleKnockDownCount(1);
        athleteResult.setShootingTargetScore(155);
        athleteResult.setRunTime("10:10.8");

        athleteResults.add(athleteResult);

        AthleteResult athleteResult1 = new AthleteResult();
        athleteResult1.setNameSurname("test2");
        athleteResult1.setFencingVictories(1);
        athleteResult1.setSwimmingTime("2:15.0");
        athleteResult1.setFenceKnockDownCount(2);
        athleteResult1.setRefusalToJumpCount(0);
        athleteResult1.setObstacleKnockDownCount(3);
        athleteResult1.setShootingTargetScore(170);
        athleteResult1.setRunTime("10:30.0");

        athleteResults.add(athleteResult1);

        AthleteResult athleteResult2 = new AthleteResult();
        athleteResult2.setNameSurname("test3");
        athleteResult2.setFencingVictories(0);
        athleteResult2.setSwimmingTime("2:30.6");
        athleteResult2.setFenceKnockDownCount(0);
        athleteResult2.setRefusalToJumpCount(3);
        athleteResult2.setObstacleKnockDownCount(4);
        athleteResult2.setShootingTargetScore(155);
        athleteResult2.setRunTime("10:10.6");

        athleteResults.add(athleteResult2);


        return athleteResults;
    }
    /**
     * Tests method {@link AthleteResultService#generateAthleteResultsFromCsv(String)}.
     * Case when csv file is present according to provided filepath. Also, it's structure is correct.
     * In that case method should return list of parsed AthleteResult entities.
     */
    @Test
    public void whenGenerateAthleteResultsFromCsvAndFilePresentWithGoodStructureReturnListOfAthleteResults() {
        List<AthleteResult> athleteResultList = athleteResultService
                .generateAthleteResultsFromCsv(FILE_PATH_CORRECT_STRUCTURE_FILE_EXISTS);

        assertEquals("Dennis Bowsher", athleteResultList.get(0).getNameSurname());
        assertEquals(9, athleteResultList.get(0).getFencingVictories());
        assertEquals("02:11.0", athleteResultList.get(0).getSwimmingTime());
        assertEquals(0, athleteResultList.get(0).getFenceKnockDownCount());
        assertEquals(2, athleteResultList.get(0).getRefusalToJumpCount());
        assertEquals(1, athleteResultList.get(0).getObstacleKnockDownCount());
        assertEquals(200, athleteResultList.get(0).getShootingTargetScore());
        assertEquals("10:38.0", athleteResultList.get(0).getRunTime());

        assertEquals("Shinichi Tomii", athleteResultList.get(9).getNameSurname());
        assertEquals(6, athleteResultList.get(9).getFencingVictories());
        assertEquals("02:01.0", athleteResultList.get(9).getSwimmingTime());
        assertEquals(2, athleteResultList.get(9).getFenceKnockDownCount());
        assertEquals(0, athleteResultList.get(9).getRefusalToJumpCount());
        assertEquals(1, athleteResultList.get(9).getObstacleKnockDownCount());
        assertEquals(172, athleteResultList.get(9).getShootingTargetScore());
        assertEquals("10:57.2", athleteResultList.get(9).getRunTime());
    }

    /**
     * Tests method {@link AthleteResultService#generateAthleteResultsFromCsv(String)}.
     * Case when file by provided path does not exist.
     * In that case returns empty list.
     */
    @Test
    public void whenGenerateAthleteResultsFromCsvAndFileNotPresentReturnEmptyList() {
        List<AthleteResult> athleteResultList = athleteResultService
                .generateAthleteResultsFromCsv(FILE_PATH_FILE_DONT_EXIST);

        assertEquals(0, athleteResultList.size());
    }

    /**
     * Tests method {@link AthleteResultService#generateAthleteResultsFromCsv(String)}.
     * Case when file exists, but it's structure is wrong. In that case return empty list.
     */
    @Test
    public void whenGenerateAthleteResultsFromCsvAndFilePresentButWrongDataTypeReturnEmptyList() {
        List<AthleteResult> athleteResultList = athleteResultService
                .generateAthleteResultsFromCsv(FILE_PATH_WRONG_STRUCTURE_FILE_EXISTS);

        assertEquals(0, athleteResultList.size());
    }

    /**
     * Tests method {@link AthleteResultService#generateAthleteLeaderboard()}.
     * Checking if after method execution string is the return type. Also checking if some values remain the same, as
     * they were in the input.
     */
    @Test
    public void whenGenerateAthleteLeaderboardCheckIfReturnsSameAmountOfAthleteResults() {
        String leaderboardString = athleteResultService.generateAthleteLeaderboard();
        String[] linesCount = leaderboardString.split("\r\n|\r|\n");

        String[] lastLine = linesCount[9].split(",");
        String[] firstLine = linesCount[0].split(",");

        assertEquals(10, linesCount.length);
        assertEquals("02:39.0", lastLine[5]);
        assertEquals("10:38.0", firstLine[10]);
    }

    /**
     * Tests method {@link AthleteResultService#calculateTotalPointsOfAthletes(List)}.
     * Case when provided list of athlete results is empty. In that case return empty list of athlete results.
     */
    @Test
    public void whenCalculateTotalPointsOfAthletesAndNoProvidedAthleteResultsThenReturnEmptyList() {
        List<AthleteResult> athleteResults = new ArrayList<>();
        assertEquals(0, athleteResultService.calculateTotalPointsOfAthletes(athleteResults).size());
    }

    /**
     * Tests method {@link AthleteResultService#calculateTotalPointsOfAthletes(List)}.
     * Case when provided list of athlete results is not empty. In that case return list with calculated athletes total
     * points.
     */
    @Test
    public void whenCalculateTotalPointsOfAthletesAndProvidedListNotEmptyThenReturnCalculatedTotalScoreOfAthletes() {
        List<AthleteResult> athleteResultsWithoutTotalPoints = prepareAthleteResultWithCorrectStructure();

        assertEquals(0, athleteResultsWithoutTotalPoints.get(0).getTotalPoints());
        assertEquals(0, athleteResultsWithoutTotalPoints.get(1).getTotalPoints());
        assertEquals(0, athleteResultsWithoutTotalPoints.get(2).getTotalPoints());

        List<AthleteResult> athleteResults = athleteResultService
                .calculateTotalPointsOfAthletes(athleteResultsWithoutTotalPoints);

        assertEquals(athleteResultsWithoutTotalPoints.size(), athleteResults.size());
        assertEquals(3672, athleteResults.get(0).getTotalPoints());
        assertEquals(4100, athleteResults.get(1).getTotalPoints());
        assertEquals(3548, athleteResults.get(2).getTotalPoints());
    }

    /**
     * Tests method {@link AthleteResultService#calculateAthletesFencingScore(int, int)}.
     * Case when athlete won 3 games, and amount of games that had to be won for target points was 7. In that case
     * calculate total points from fencing competition.
     */
    @Test
    public void whenCalculateAthletesFencingScoreAndProvidedParametersAreCorrectThenCalculateFencingScore() {
        int individualWins = 3;
        int neededWinsForTargetPoints = 7;

        assertEquals(840, athleteResultService
                .calculateAthletesFencingScore(neededWinsForTargetPoints, individualWins));

    }

    /**
     * Tests method {@link AthleteResultService#calculateAthletesFencingScore(int, int)}.
     * Case when athlete won 0 games, and amount of games that had to be won for target points was 7. In that case
     * calculate total points from fencing competition.
     */
    @Test
    public void whenCalculateAthletesFencingScoreAndAthleteWon0TimesThenCalculateFencingScore() {
        int individualWins = 0;
        int neededWinsForTargetPoints = 7;

        assertEquals(720, athleteResultService
                .calculateAthletesFencingScore(neededWinsForTargetPoints, individualWins));

    }

    /**
     * Tests method {@link AthleteResultService#calculateAthletesFencingScore(int, int)}.
     * Case when athlete won 0 games, and amount of games that had to be won for target points was 60. In that case
     * if the amount of points is less than 0, return 0.
     */
    @Test
    public void whenCalculateAthletesFencingScoreAndTotalPointsAreNegativeThenReturn0Points() {
        int individualWins = 0;
        int neededWinsForTargetPoints = 60;

        assertEquals(0, athleteResultService
                .calculateAthletesFencingScore(neededWinsForTargetPoints, individualWins));

    }

    /**
     * Tests method {@link AthleteResultService#calculateWinsCountToReachFencingTargetPercentageWinRate(int)}.
     * Method checks how many wins athlete has to win to guarantee having target points from fencing competition.
     * Tested with multiple games count. Negative game count always results in -1.
     */
    @Test
    public void whenFencingTargetWinCountCheckHowManyWinsNeededForTargetPercentage() {
        final int gamesCount30 = 30;
        final int gamesCount15 = 15;
        final int gamesCount45 = 45;
        final int gamesCount1 = 1;
        final int gamesCount0 = 0;
        final int gamesCountNegativeOne = -1;
        final int gamesCountNegativeTen = -10;
        final int gamesCountNegativeOneHundred = -100;

        assertEquals(21, athleteResultService
                .calculateWinsCountToReachFencingTargetPercentageWinRate(gamesCount30));
        assertEquals(11, athleteResultService
                .calculateWinsCountToReachFencingTargetPercentageWinRate(gamesCount15));
        assertEquals(32, athleteResultService
                .calculateWinsCountToReachFencingTargetPercentageWinRate(gamesCount45));
        assertEquals(1, athleteResultService
                .calculateWinsCountToReachFencingTargetPercentageWinRate(gamesCount1));
        assertEquals(0, athleteResultService
                .calculateWinsCountToReachFencingTargetPercentageWinRate(gamesCount0));
        assertEquals(-1, athleteResultService
                .calculateWinsCountToReachFencingTargetPercentageWinRate(gamesCountNegativeOne));
        assertEquals(-1, athleteResultService
                .calculateWinsCountToReachFencingTargetPercentageWinRate(gamesCountNegativeTen));
        assertEquals(-1, athleteResultService
                .calculateWinsCountToReachFencingTargetPercentageWinRate(gamesCountNegativeOneHundred));

    }

    /**
     * Tests method {@link AthleteResultService#calculateWinRate(int, int)}.
     * Test cases with 0, positive and negative parameters.
     */
    @Test
    public void whenCalculateWinRateThenCalculateWinRate() {
        int winCount = 3;
        int totalGames = 4;

        assertEquals(75.0, athleteResultService.calculateWinRate(winCount, totalGames));

        winCount = 1;

        assertEquals(25.0, athleteResultService.calculateWinRate(winCount, totalGames));

        winCount = -1;

        assertEquals(-1.0, athleteResultService.calculateWinRate(winCount, totalGames));

        winCount = 0;

        assertEquals(0.0, athleteResultService.calculateWinRate(winCount, totalGames));

        totalGames = 0;

        assertEquals(-1.0, athleteResultService.calculateWinRate(winCount, totalGames));
    }

    /**
     * Tests method {@link AthleteResultService#calculateAthletesShootingScore(int)}.
     * Checking cases with positive, negative and 0 parameter.
     */
    @Test
    public void whenCalculateAthletesShootingScoreThenCalculateShootingScore() {
        int shootingScorePositive = 150;
        int shootingScoreZero = 0;
        int shootingScoreNegative = -150;

        assertEquals(736, athleteResultService.calculateAthletesShootingScore(shootingScorePositive));
        assertEquals(0, athleteResultService.calculateAthletesShootingScore(shootingScoreZero));
        assertEquals(-1, athleteResultService.calculateAthletesShootingScore(shootingScoreNegative));
    }

    /**
     * Tests method {@link AthleteResultService#calculateAthletesRidingScore(int, int, int)}.
     * Checking cases with positive,negative and 0 parameters.
     */
    @Test
    public void whenCalculateAthletesRidingScoreThenCalculateAthleteRidingScore() {
        int fenceKnockDownCount = 1;
        int refusalToJumpCount = 2;
        int obstacleKnockDownCount = 3;

        assertEquals(912, athleteResultService.calculateAthletesRidingScore(fenceKnockDownCount,
                refusalToJumpCount, obstacleKnockDownCount));

        fenceKnockDownCount = -1;
        assertEquals(-1, athleteResultService.calculateAthletesRidingScore(fenceKnockDownCount,
                refusalToJumpCount, obstacleKnockDownCount));

        refusalToJumpCount = -2;
        assertEquals(-1, athleteResultService.calculateAthletesRidingScore(fenceKnockDownCount,
                refusalToJumpCount, obstacleKnockDownCount));

        obstacleKnockDownCount = -3;
        assertEquals(-1, athleteResultService.calculateAthletesRidingScore(fenceKnockDownCount,
                refusalToJumpCount, obstacleKnockDownCount));

        fenceKnockDownCount = 0;
        refusalToJumpCount = 0;
        obstacleKnockDownCount = 0;
        assertEquals(1200, athleteResultService.calculateAthletesRidingScore(fenceKnockDownCount,
                refusalToJumpCount, obstacleKnockDownCount));
    }

    /**
     * Tests method {@link AthleteResultService#calculateAthletesSwimmingScore(String)}.
     * Testing cases:
     *  1) good format + logical time
     *  2) good format + not logical time
     *  3) bad format.
     */
    @Test
    public void whenCalculateAthletesSwimmingScoreThenCalculateAthleteSwimmingScore() {
        String athleteSwimmingScore = "2:15.6";

        assertEquals(1192, athleteResultService.calculateAthletesSwimmingScore(athleteSwimmingScore));

        athleteSwimmingScore = "0";

        assertEquals(-1 , athleteResultService.calculateAthletesSwimmingScore(athleteSwimmingScore));

        athleteSwimmingScore = "-1:00.6";

        assertEquals(-1 , athleteResultService.calculateAthletesSwimmingScore(athleteSwimmingScore));

        athleteSwimmingScore = "30:00.6";

        assertEquals(0 , athleteResultService.calculateAthletesSwimmingScore(athleteSwimmingScore));
    }

    /**
     * Tests method {@link AthleteResultService#convertToMillisFromString(String)}.
     * Case when provided time string is wrong format. In that case return -1.
     */
    @Test
    public void whenConvertToMillisFromStringAndFormatIsWrongReturnNegativeOne() {
        String timeString = "1.30";
        assertEquals(-1, athleteResultService.convertToMillisFromString(timeString));

        timeString = "1:30:30";
        assertEquals(-1, athleteResultService.convertToMillisFromString(timeString));

        timeString = "";
        assertEquals(-1, athleteResultService.convertToMillisFromString(timeString));

        timeString = "-1:30.0";
        assertEquals(-1, athleteResultService.convertToMillisFromString(timeString));

        timeString = "1/:30.0";
        assertEquals(-1, athleteResultService.convertToMillisFromString(timeString));

        timeString = "1;30.0";
        assertEquals(-1, athleteResultService.convertToMillisFromString(timeString));

        timeString = "1h30min";
        assertEquals(-1, athleteResultService.convertToMillisFromString(timeString));

        timeString = "3asd2f11";
        assertEquals(-1, athleteResultService.convertToMillisFromString(timeString));
    }

    /**
     * Tests method {@link AthleteResultService#convertToMillisFromString(String)}.
     * Case when provided time string is correct format. In that case calculate millis.
     */
    @Test
    public void whenConvertToMillisFromStringAndFormatIsCorrectReturnCalculatedMillis() {
        String timeString = "1:30.0";
        assertEquals(900, athleteResultService.convertToMillisFromString(timeString));

        timeString = "2:35.4";
        assertEquals(1554, athleteResultService.convertToMillisFromString(timeString));

        timeString = "0:00.0";
        assertEquals(0, athleteResultService.convertToMillisFromString(timeString));
    }

    /**
     Tests method {@link AthleteResultService#countConcludingEventTimeAndSortAccordingToIt(List)}.
     Tests if method returns sorted list by concluding event time field.
     Tests if calculations are made correctly.
     Checks if all calculated values are >= 0.
     */
    @Test
    public void whenCountConcludingEventTimeAndSortAccordingToItThenCountConcludingEventTime() {
        List<AthleteResult> athleteResultsInputData = prepareAthleteResultWithCorrectStructure();
        List<AthleteResultOutput> athleteResultOutputs = athleteResultService
                .countConcludingEventTimeAndSortAccordingToIt(athleteResultsInputData);

        assertEquals(athleteResultsInputData.size(), athleteResultOutputs.size());

        assertTrue(athleteResultOutputs.get(0).getTotalPoints() >= 0);
        assertTrue(athleteResultOutputs.get(1).getTotalPoints() >= 0);
        assertTrue(athleteResultOutputs.get(2).getTotalPoints() >= 0);

        assertTrue(athleteResultOutputs.get(0).getConcludingEventTime() <=
                athleteResultOutputs.get(1).getConcludingEventTime());
        assertTrue(athleteResultOutputs.get(1).getConcludingEventTime() <=
                athleteResultOutputs.get(2).getConcludingEventTime());

        assertTrue(athleteResultOutputs.get(0).getConcludingEventTime() == 610);
        assertTrue(athleteResultOutputs.get(1).getConcludingEventTime() == 610);
        assertTrue(athleteResultOutputs.get(2).getConcludingEventTime() == 630);

    }

    /**
     * Tests method {@link AthleteResultService#determinePlaceForEachAthlete(List)}.
     * Case when only two athletes participate and  have same scores. In that case set their place
     * as 1/2.
     */
    @Test
    public void whenDeterminePlaceForEachAthleteCaseTwoEqualAthletesReturnSplitPlace() {
        List<AthleteResult> athleteResultsInput = athleteResultService
                .generateAthleteResultsFromCsv(TWO_ATHLETE_SAME_RESULTS);

        List<AthleteResultOutput> athleteResultOutputs = athleteResultService
                .countConcludingEventTimeAndSortAccordingToIt(athleteResultsInput);

        List<AthleteResultOutput> athleteResultsOutputsWithPlaces = athleteResultService
                .determinePlaceForEachAthlete(athleteResultOutputs);

        assertEquals("1/2", athleteResultsOutputsWithPlaces.get(0).getPlace());
        assertEquals("1/2", athleteResultsOutputsWithPlaces.get(1).getPlace());

    }

    /**
     * Tests method {@link AthleteResultService#determinePlaceForEachAthlete(List)}.
     * Case when athletes have completely different times. In that case return incremental places.
     */
    @Test
    public void whenDeterminePlaceForEachAthleteCaseAllAthletesHaveDifferentTimesReturnDifferentPlaces() {
        List<AthleteResult> athleteResultsInput = athleteResultService
                .generateAthleteResultsFromCsv(NINE_ATHLETES_DIFFERENT_RESULTS);

        List<AthleteResultOutput> athleteResultOutputs = athleteResultService
                .countConcludingEventTimeAndSortAccordingToIt(athleteResultsInput);

        List<AthleteResultOutput> athleteResultsOutputsWithPlaces = athleteResultService
                .determinePlaceForEachAthlete(athleteResultOutputs);

        assertEquals("1", athleteResultsOutputsWithPlaces.get(0).getPlace());
        assertEquals("2", athleteResultsOutputsWithPlaces.get(1).getPlace());
        assertEquals("3", athleteResultsOutputsWithPlaces.get(2).getPlace());
        assertEquals("4", athleteResultsOutputsWithPlaces.get(3).getPlace());
        assertEquals("5", athleteResultsOutputsWithPlaces.get(4).getPlace());
        assertEquals("6", athleteResultsOutputsWithPlaces.get(5).getPlace());
        assertEquals("7", athleteResultsOutputsWithPlaces.get(6).getPlace());
        assertEquals("8", athleteResultsOutputsWithPlaces.get(7).getPlace());
        assertEquals("9", athleteResultsOutputsWithPlaces.get(8).getPlace());
    }

    /**
     * Tests method {@link AthleteResultService#determinePlaceForEachAthlete(List)}.
     * Case when athletes have completely different times. In that case return incremental places.
     */
    @Test
    public void whenDeterminePlaceForEachAthleteCaseSomeAthletesHaveSameTimesReturnMixOfSingleAndSplitPlaces() {
        List<AthleteResult> athleteResultsInput = athleteResultService
                .generateAthleteResultsFromCsv(SIXTEEN_ATHLETES_REPEATING_TIMES);

        List<AthleteResultOutput> athleteResultOutputs = athleteResultService
                .countConcludingEventTimeAndSortAccordingToIt(athleteResultsInput);

        List<AthleteResultOutput> athleteResultsOutputsWithPlaces = athleteResultService
                .determinePlaceForEachAthlete(athleteResultOutputs);

        assertEquals("1", athleteResultsOutputsWithPlaces.get(0).getPlace());
        assertEquals("2/3", athleteResultsOutputsWithPlaces.get(1).getPlace());
        assertEquals("2/3", athleteResultsOutputsWithPlaces.get(2).getPlace());
        assertEquals("2/3", athleteResultsOutputsWithPlaces.get(3).getPlace());
        assertEquals("4", athleteResultsOutputsWithPlaces.get(4).getPlace());
        assertEquals("5", athleteResultsOutputsWithPlaces.get(5).getPlace());
        assertEquals("6/7", athleteResultsOutputsWithPlaces.get(6).getPlace());
        assertEquals("6/7", athleteResultsOutputsWithPlaces.get(7).getPlace());
        assertEquals("6/7", athleteResultsOutputsWithPlaces.get(8).getPlace());
        assertEquals("8", athleteResultsOutputsWithPlaces.get(9).getPlace());
        assertEquals("9/10", athleteResultsOutputsWithPlaces.get(10).getPlace());
        assertEquals("9/10", athleteResultsOutputsWithPlaces.get(11).getPlace());
        assertEquals("9/10", athleteResultsOutputsWithPlaces.get(12).getPlace());
        assertEquals("11", athleteResultsOutputsWithPlaces.get(13).getPlace());
        assertEquals("12/13", athleteResultsOutputsWithPlaces.get(14).getPlace());
        assertEquals("12/13", athleteResultsOutputsWithPlaces.get(15).getPlace());
    }

    /**
     * Tests method {@link AthleteResultService#determinePlaceForEachAthlete(List)}.
     * Case when there is only one athlete. In that case return place 1.
     */
    @Test
    public void whenDeterminePlaceForEachAthleteCaseOnlyOneAthleteReturnPlaceOne() {
        List<AthleteResult> athleteResultsInput = athleteResultService
                .generateAthleteResultsFromCsv(ONE_ATHLETE_DATA);

        List<AthleteResultOutput> athleteResultOutputs = athleteResultService
                .countConcludingEventTimeAndSortAccordingToIt(athleteResultsInput);

        List<AthleteResultOutput> athleteResultsOutputsWithPlaces = athleteResultService
                .determinePlaceForEachAthlete(athleteResultOutputs);

        assertEquals("1", athleteResultsOutputsWithPlaces.get(0).getPlace());
    }

    /**
     * Tests method {@link AthleteResultService#determinePlaceForEachAthlete(List)}.
     * Case when provided list is empty. In that case return empty list.
     */
    @Test
    public void whenDeterminePlaceForEachAthleteCaseProvidedListIsEmptyThenReturnEmptyList() {
        List<AthleteResultOutput> athleteResultsOutputsWithPlaces = athleteResultService
                .determinePlaceForEachAthlete(new ArrayList<>());

        assertEquals(0, athleteResultsOutputsWithPlaces.size());
    }

    /**
     * Tests method {@link AthleteResultService#generateFinalLeaderboardList(List)}
     * Case when provided list is not empty. In that case return the same amount of lines in String as there are
     * elements in list.
     */
    @Test
    public void whenGenerateFinalLeaderboardListAndProvidedListNotEmptyReturnSameAmountOfLinesAsListSize() {
        List<AthleteResultOutput> athleteResultOutputList = new ArrayList<>();
        AthleteResultOutput athleteResultOutput1 = new AthleteResultOutput();
        AthleteResultOutput athleteResultOutput2 = new AthleteResultOutput();
        AthleteResultOutput athleteResultOutput3 = new AthleteResultOutput();

        athleteResultOutputList.add(athleteResultOutput1);
        athleteResultOutputList.add(athleteResultOutput2);
        athleteResultOutputList.add(athleteResultOutput3);

        String leaderboardString = athleteResultService.generateFinalLeaderboardList(athleteResultOutputList);
        String[] linesCount = leaderboardString.split("\r\n|\r|\n");

        assertEquals(3,linesCount.length);
    }

}
