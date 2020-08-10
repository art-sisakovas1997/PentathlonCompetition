package com.arty.modernpentathloncompetition.service.implementation;

import com.arty.modernpentathloncompetition.entity.AthleteResult;
import com.arty.modernpentathloncompetition.entity.AthleteResultOutput;
import com.arty.modernpentathloncompetition.service.AthleteResultService;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class AthleteResultServiceImpl implements AthleteResultService {

    /**
     * Constant that determines at what win rate percent athlete gets pre-determined points.
     * Used for fencing competition.
     */
    static final int FENCING_WIN_RATE_PERCENT = 70;

    /**
     * Target score points.
     */
    static final int TARGET_SCORE_POINTS = 1000;

    /**
     * Amount of points athlete gets added or subtracted when he exceeds or does not reach pre-determined win-rate.
     * Used for fencing competition.
     */
    static final int FENCING_VARIABLE_POINTS = 40;

    /**
     * Amount of points athlete gets added or subtracted when he exceeds or does not reach pre-determined target score.
     * Used for shooting competition.
     */
    static final int SHOOTING_VARIABLE_POINTS = 12;

    /**
     * Amount of points athlete gets added or subtracted when he exceeds or does not reach pre-determined target score.
     * Used for swimming competition.
     */
    static final int SWIMMING_VARIABLE_POINTS = 4;

    /**
     * Knocking down a fence loss in points.
     */
    static final int RIDING_FENCE_KNOCKDOWN_LOSS = 28;

    /**
     * Refusal by the horse to jump loss in points.
     */
    static final int RIDING_REFUSAL_BY_HORSE_LOSS = 40;

    /**
     * Disobedience leading to the knocking down of an obstacle loss in points.
     */
    static final int RIDING_OBSTACLE_KNOCKDOWN = 60;

    /**
     * Shooting target score.
     */
    static final int SHOOTING_TARGET_SCORE_VALUE = 172;

    /**
     * Swimming target score value.
     */
    static final String SWIMMING_TARGET_SCORE_VALUE = "2:30.0";

    /**
     * Riding target score value.
     */
    static final int RIDING_TARGET_SCORE_VALUE = 1200;

    /**
     * File path.
     */
    @Value("${csv_file_path}")
    private String FILE_PATH;

    /**
     * Fencing target win count.
     */
    static int fencingTargetWinCount;

    @Override
    public List<AthleteResult> generateAthleteResultsFromCsv(String filePath) {
        Path myPath = Paths.get(filePath);

        try (BufferedReader br = Files.newBufferedReader(myPath,
                StandardCharsets.UTF_8)) {
            ColumnPositionMappingStrategy<AthleteResult> strategy = new ColumnPositionMappingStrategy<>();
            strategy.setType(AthleteResult.class);

            String[] fields = new String[]{"nameSurname","fencingVictories", "swimmingTime",
                    "knockDownCount", "refusalCount", "disobedienceLeadingCount", "shootingScore", "runTime"};
            strategy.setColumnMapping(fields);

            CsvToBean<AthleteResult> csvToBean = new CsvToBeanBuilder<AthleteResult>(br)
                    .withMappingStrategy(strategy)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            return csvToBean.parse();

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public String generateAthleteLeaderboard() {

        List<AthleteResult> athleteResults =  generateAthleteResultsFromCsv(FILE_PATH);

        athleteResults = calculateTotalPointsOfAthletes(athleteResults);

        List<AthleteResultOutput> athleteResultsForOutput = countConcludingEventTimeAndSortAccordingToIt(athleteResults);

        athleteResultsForOutput = determinePlaceForEachAthlete(athleteResultsForOutput);

        return generateFinalLeaderboardList(athleteResultsForOutput);
    }

    @Override
    public List<AthleteResult> calculateTotalPointsOfAthletes(List<AthleteResult> athleteResults) {
        List<AthleteResult> athleteResultsWithTotalPoints = new ArrayList<>();
        fencingTargetWinCount = calculateWinsCountToReachFencingTargetPercentageWinRate(athleteResults.size()-1);
        for (AthleteResult athleteResult : athleteResults) {
            int fencingScore = calculateAthletesFencingScore(fencingTargetWinCount,
                    athleteResult.getFencingVictories());
            int swimmingScore = calculateAthletesSwimmingScore(athleteResult.getSwimmingTime());
            int ridingScore = calculateAthletesRidingScore(athleteResult.getFenceKnockDownCount(),
                    athleteResult.getRefusalToJumpCount(), athleteResult.getObstacleKnockDownCount());
            int shootingScore = calculateAthletesShootingScore(athleteResult.getShootingTargetScore());

            int totalPointsOfAthlete = fencingScore + swimmingScore + ridingScore + shootingScore;

            athleteResult.setTotalPoints(totalPointsOfAthlete);
            athleteResultsWithTotalPoints.add(athleteResult);
        }
        return athleteResultsWithTotalPoints;
    }

    @Override
    public int calculateAthletesFencingScore(int fencingTargetWinCount, int fencingVictoriesCount) {
        int extraPoints = fencingVictoriesCount - fencingTargetWinCount;
        int totalFencingScore = TARGET_SCORE_POINTS + (extraPoints * FENCING_VARIABLE_POINTS);

        return Math.max(totalFencingScore, 0);
    }

    @Override
    public int calculateWinsCountToReachFencingTargetPercentageWinRate(int gamesCount) {
        for (int i = 1; i <= gamesCount; i++) {
            if (calculateWinRate(i,gamesCount) >= FENCING_WIN_RATE_PERCENT) {
                return i;
            }
        }
        return Integer.compare(gamesCount, 0);
    }

    @Override
    public double calculateWinRate(int winCount, int totalGames) {
        double winRate = winCount * 100.0 / totalGames;
        return (winRate >= 0.0) ? winRate : -1.0;
    }

    @Override
    public int calculateAthletesShootingScore(int athletesShootingTotalScore) {
        final int extraPoints = athletesShootingTotalScore - SHOOTING_TARGET_SCORE_VALUE;
        final int totalShootingScore = TARGET_SCORE_POINTS + (SHOOTING_VARIABLE_POINTS * extraPoints);
        return (athletesShootingTotalScore >= 0) ? Math.max(totalShootingScore, 0): -1;
    }

    @Override
    public int calculateAthletesRidingScore(int fenceKnockDownCount, int refusalToJumpCount,
                                            int obstacleKnockDownCount) {
        if (fenceKnockDownCount < 0 || refusalToJumpCount < 0 || obstacleKnockDownCount < 0) {
            return -1;
        }

        final int totalFenceKnockDownsLoss = fenceKnockDownCount * RIDING_FENCE_KNOCKDOWN_LOSS;
        final int totalRefusalByHorseLoss = refusalToJumpCount * RIDING_REFUSAL_BY_HORSE_LOSS;
        final int totalObstacleKnockDownLoss = obstacleKnockDownCount * RIDING_OBSTACLE_KNOCKDOWN;
        final int totalRidingScore = RIDING_TARGET_SCORE_VALUE -
                (totalFenceKnockDownsLoss + totalRefusalByHorseLoss + totalObstacleKnockDownLoss);

        return Math.max(totalRidingScore, 0);
    }

    @Override
    public int calculateAthletesSwimmingScore(String swimmingTime) {
        final int targetSwimmingTime = convertToMillisFromString(SWIMMING_TARGET_SCORE_VALUE);
        final int athletesSwimmingTime = convertToMillisFromString(swimmingTime);

        if (targetSwimmingTime < 0 || athletesSwimmingTime < 0) {
            return -1;
        }

        final int extraPoints = (targetSwimmingTime - athletesSwimmingTime) / 3;

        final int totalSwimmingScore = TARGET_SCORE_POINTS + (SWIMMING_VARIABLE_POINTS * extraPoints);

        return Math.max(totalSwimmingScore, 0);
    }

    @Override
    public int convertToMillisFromString(String timeAsString) {
        try {
            String[] time = timeAsString.split(":");
            String[] secondsAndMillis = time[1].split("\\.");

            int minutes = Integer.parseInt(time[0]);
            int seconds = Integer.parseInt(secondsAndMillis[0]);
            int millis = Integer.parseInt(secondsAndMillis[1]);

            final int totalMillis = millis + (seconds * 10) + (minutes * 60 * 10);

            return totalMillis >= 0 ? totalMillis : -1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public List<AthleteResultOutput> countConcludingEventTimeAndSortAccordingToIt(List<AthleteResult> athleteResults) {
        athleteResults.sort(Comparator.comparing(AthleteResult::getTotalPoints).reversed());

        List<AthleteResultOutput> athleteResultsForOutput = new ArrayList<>();
        int maxPoints = athleteResults.get(0).getTotalPoints();

        for (AthleteResult athleteResult : athleteResults) {
            AthleteResultOutput athleteResultOutput = new AthleteResultOutput(athleteResult);

            int runTimeInSeconds = convertToMillisFromString(athleteResultOutput.getRunTime()) / 10;
            int differenceBetweenLeader = maxPoints - athleteResultOutput.getTotalPoints();
            int concludingEventTime = runTimeInSeconds + differenceBetweenLeader;

            athleteResultOutput.setConcludingEventTime(concludingEventTime);
            athleteResultsForOutput.add(athleteResultOutput);
        }

        athleteResultsForOutput.sort(Comparator.comparing(AthleteResultOutput::getConcludingEventTime));
        return athleteResultsForOutput;
    }

    public List<AthleteResultOutput> determinePlaceForEachAthlete(List<AthleteResultOutput> athleteResultsForOutput) {
        int place = 0;
        int placeAfter = 0;
        boolean singlePlace = false;
        if (athleteResultsForOutput.size() == 1) {
            athleteResultsForOutput.get(0).setPlace(String.valueOf(1));
            return athleteResultsForOutput;
        }
        for (int i = 0; i <= athleteResultsForOutput.size() - 2; i++) {
            for (int j = i + 1; j <= athleteResultsForOutput.size() - 1; j++) {
                if (athleteResultsForOutput.get(i).getConcludingEventTime()
                        == athleteResultsForOutput.get(j).getConcludingEventTime()) {
                        if (athleteResultsForOutput.get(i).getPlace() == null || !athleteResultsForOutput.get(i).getPlace().contains("/")) {
                            if (i == 0) {
                                place++;
                            }
                            placeAfter = place +1;
                            String placeString = place + "/" + placeAfter;
                            athleteResultsForOutput.get(i).setPlace(placeString);
                            singlePlace = false;
                        }
                        if (athleteResultsForOutput.get(j).getPlace() == null ) {
                            if (singlePlace) {
                                place++;
                            }
                            placeAfter = place +1;
                            String placeString = place + "/" + placeAfter;
                            athleteResultsForOutput.get(j).setPlace(placeString);
                            singlePlace = false;
                        }
                } else {
                    if (placeAfter > place) {
                        place++;
                    }
                    if (athleteResultsForOutput.get(i).getPlace() == null) {
                        place++;
                        String placeString = String.valueOf(place);
                        athleteResultsForOutput.get(i).setPlace(placeString);


                        if (athleteResultsForOutput.get(j).getPlace() == null) {
                            place++;
                            placeString = String.valueOf(place);
                            athleteResultsForOutput.get(j).setPlace(placeString);
                        }
                    }
                    if (athleteResultsForOutput.get(j).getPlace() == null) {
                        place++;
                        String placeString = String.valueOf(place);
                        athleteResultsForOutput.get(j).setPlace(placeString);
                    }
                    singlePlace = true;
                    break;
                }
            }
        }
        return athleteResultsForOutput;
    }

    public String generateFinalLeaderboardList(List<AthleteResultOutput> athleteResultsForOutput) {
        StringBuilder builder = new StringBuilder();
        for (AthleteResultOutput athleteResultOutput : athleteResultsForOutput) {
        String athleteResultString = (athleteResultOutput.getPlace() + "," + athleteResultOutput.getTotalPoints() + ","
                + athleteResultOutput.getConcludingEventTime() + "," + athleteResultOutput.getNameSurname()
                + "," + athleteResultOutput.getFencingVictories() + "," + athleteResultOutput.getSwimmingTime()
                + "," + athleteResultOutput.getFenceKnockDownCount() + "," + athleteResultOutput.getRefusalToJumpCount()
                + "," + athleteResultOutput.getObstacleKnockDownCount() + ","
                + athleteResultOutput.getShootingTargetScore() + "," + athleteResultOutput.getRunTime() + "\r\n");
        builder.append(athleteResultString);
        }
        return builder.toString();
    }

}
