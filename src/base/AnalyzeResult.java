package base;

import java.util.Objects;

public class AnalyzeResult {
    private final String sire_;
    private final String dam_;
    private boolean elaboration_; // 凝った配合
    private boolean interesting_; // 面白い配合
    private boolean excellent_;   // 見事な配合
    private boolean danger_;      // 危険な配合

    // クロス本数
    private int sprint_;     // 短距離
    private int speed_;      // 速力
    private int stayer_;     // 長距離
    private int spirit_;     // 底力
    private int stability_;   // 安定
    private int hotTemper_;  // 気性難
    private int precocious_; // 早熟
    private int altricity_;  // 晩成
    private int health_;     // 丈夫
    private int dirt_;       // ダート
    private int power_;      // パワー

    // ニトロ
    private int speedNitro_;
    private int staminaNitro_;
    private int powerNitro_;

    AnalyzeResult(String sire, String dam) throws NullPointerException {
        sire_         = Objects.requireNonNull(sire);
        dam_          = Objects.requireNonNull(dam);
        elaboration_  = false;
        interesting_  = false;
        excellent_    = false;
        danger_       = false;
        sprint_       = 0;
        speed_        = 0;
        stayer_       = 0;
        spirit_       = 0;
        stability_    = 0;
        hotTemper_    = 0;
        precocious_   = 0;
        altricity_    = 0;
        health_       = 0;
        dirt_         = 0;
        power_        = 0;
        speedNitro_   = 0;
        staminaNitro_ = 0;
        powerNitro_   = 0;
    }

    void setElaboration(boolean elaboration) {
        elaboration_ = elaboration;
    }

    void setInteresting(boolean interesting) {
        interesting_ = interesting;
    }

    void setExcellent(boolean excellent) {
        excellent_ = excellent;
    }

    void setDanger(boolean danger) {
        danger_ = danger;
    }

    void incrementSprint() {
        sprint_ += 1;
    }

    void incrementSpeed() {
        speed_ += 1;
    }

    void incrementStayer() {
        stayer_ += 1;
    }

    void incrementSpirit() {
        spirit_ += 1;
    }

    void incrementStability() {
        stability_ += 1;
    }

    void incrementHotTemper() {
        hotTemper_ += 1;
    }

    void incrementPrecociout() {
        precocious_ += 1;
    }

    void incrementAltricity() {
        altricity_ += 1;
    }

    void incrementHealth() {
        health_ += 1;
    }

    void incrementDirt() {
        dirt_ += 1;
    }

    void incrementPower() {
        power_ += 1;
    }

    void addSpeedNitro(int value) {
        speedNitro_ += value;
    }

    void addStaminaNitro(int value) {
        staminaNitro_ += value;
    }

    void addPowerNitro(int value) {
        powerNitro_ += value;
    }

    public static String getCsvColumn() {
        return "父,母,凝った,面白,見事,危険,短距離,速力,長距離,底力,安定,気性難,早熟,晩成,丈夫,ダート,パワー,SP,ST,PW";
    }

    public String toCsvFormat() {
        String output = "";
        output += sire_;
        output += "," + dam_;
        output += "," + (elaboration_ ? "1" : "0");
        output += "," + (interesting_ ? "1" : "0");
        output += "," + (excellent_ ? "1" : "0");
        output += "," + (danger_ ? "1" : "0");
        output += "," + String.valueOf(sprint_);
        output += "," + String.valueOf(speed_);
        output += "," + String.valueOf(stayer_);
        output += "," + String.valueOf(spirit_);
        output += "," + String.valueOf(stability_);
        output += "," + String.valueOf(hotTemper_);
        output += "," + String.valueOf(precocious_);
        output += "," + String.valueOf(altricity_);
        output += "," + String.valueOf(health_);
        output += "," + String.valueOf(dirt_);
        output += "," + String.valueOf(power_);
        output += "," + String.valueOf(speedNitro_);
        output += "," + String.valueOf(staminaNitro_);
        output += "," + String.valueOf(powerNitro_);
        return output;
    }
}
