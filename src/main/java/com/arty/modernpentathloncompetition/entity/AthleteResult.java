package com.arty.modernpentathloncompetition.entity;

import com.opencsv.bean.CsvBindByPosition;

/**
 * Athlete result class
 */
public class AthleteResult {

    /**
     * Athlete's name and surname.
     */
    @CsvBindByPosition(position = 0)
    private String nameSurname;

    /**
     * Amount of fencing victories the athlete have.
     */
    @CsvBindByPosition(position = 1)
    private int fencingVictories;

    /**
     * Swimming time.
     */
    @CsvBindByPosition(position = 2)
    private String swimmingTime;

    /**
     * Amount of knock downs during riding.
     */
    @CsvBindByPosition(position = 3)
    private int fenceKnockDownCount;

    /**
     * Amount of refusals during riding.
     */
    @CsvBindByPosition(position = 4)
    private int refusalToJumpCount;

    /**
     * Amount of disobedience leading.
     */
    @CsvBindByPosition(position = 5)
    private int obstacleKnockDownCount;

    /**
     * Shooting score.
     */
    @CsvBindByPosition(position = 6)
    private int shootingTargetScore;

    /**
     * Time of run counted individually.
     */
    @CsvBindByPosition(position = 7)
    private String runTime;

    /**
     * Total amount of points.
     */
    private int totalPoints = 0;

    public String getNameSurname() {
        return nameSurname;
    }

    public void setNameSurname(String nameSurname) {
        this.nameSurname = nameSurname;
    }

    public int getFencingVictories() {
        return fencingVictories;
    }

    public void setFencingVictories(int fencingVictories) {
        this.fencingVictories = fencingVictories;
    }

    public String getSwimmingTime() {
        return swimmingTime;
    }

    public void setSwimmingTime(String swimmingTime) {
        this.swimmingTime = swimmingTime;
    }

    public int getFenceKnockDownCount() {
        return fenceKnockDownCount;
    }

    public void setFenceKnockDownCount(int fenceKnockDownCount) {
        this.fenceKnockDownCount = fenceKnockDownCount;
    }

    public int getRefusalToJumpCount() {
        return refusalToJumpCount;
    }

    public void setRefusalToJumpCount(int refusalToJumpCount) {
        this.refusalToJumpCount = refusalToJumpCount;
    }

    public int getObstacleKnockDownCount() {
        return obstacleKnockDownCount;
    }

    public void setObstacleKnockDownCount(int obstacleKnockDownCount) {
        this.obstacleKnockDownCount = obstacleKnockDownCount;
    }

    public int getShootingTargetScore() {
        return shootingTargetScore;
    }

    public void setShootingTargetScore(int shootingTargetScore) {
        this.shootingTargetScore = shootingTargetScore;
    }

    public String getRunTime() {
        return runTime;
    }

    public void setRunTime(String runTime) {
        this.runTime = runTime;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }
}
