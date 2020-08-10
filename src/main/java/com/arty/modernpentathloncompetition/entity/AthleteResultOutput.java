package com.arty.modernpentathloncompetition.entity;

/**
 * Athlete result output.
 */
public class AthleteResultOutput extends AthleteResult {

    /**
     * Concluding event time in seconds.
     */
    private int concludingEventTime;

    /**
     * Place of the athlete.
     */
    private String place;

    public AthleteResultOutput(){};

    public AthleteResultOutput(AthleteResult athleteResult) {
        this.setNameSurname(athleteResult.getNameSurname());
        this.setFencingVictories(athleteResult.getFencingVictories());
        this.setSwimmingTime(athleteResult.getSwimmingTime());
        this.setFenceKnockDownCount(athleteResult.getFenceKnockDownCount());
        this.setRefusalToJumpCount(athleteResult.getRefusalToJumpCount());
        this.setObstacleKnockDownCount(athleteResult.getObstacleKnockDownCount());
        this.setShootingTargetScore(athleteResult.getShootingTargetScore());
        this.setRunTime(athleteResult.getRunTime());
        this.setTotalPoints(athleteResult.getTotalPoints());
    }


    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public int getConcludingEventTime() {
        return concludingEventTime;
    }

    public void setConcludingEventTime(int concludingEventTime) {
        this.concludingEventTime = concludingEventTime;
    }
}
