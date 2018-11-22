package hk.hku.cs.comp7506_project.Wiki.kline.model;


public class LineModel {

    /**
     * time : 0930
     * price : 11198.77
     * volume : 0
     * average : 11199.368
     * num : 0
     */

    private String time;
    private double price;
    private long volume;
    private double average;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getVolume() {
        return volume;
    }

    public void setVolume(long volume) {
        this.volume = volume;
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(int average) {
        this.average = average;
    }
}
