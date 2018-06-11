package Baseclass;

public class BabyHealth {
    private int cordRateX;
    private int cordRateY;
    private int eyescoreX;
    private int eyescoreY;

    public BabyHealth(int cordRateX, int cordRateY, int eyescoreX, int eyescoreY) {
        this.cordRateX = cordRateX;
        this.cordRateY = cordRateY;
        this.eyescoreX = eyescoreX;
        this.eyescoreY = eyescoreY;
    }

    public BabyHealth(int cordRateX, int cordRateY) {
        this.cordRateX = cordRateX;
        this.cordRateY = cordRateY;
    }

    public int getCordRateX() {
        return cordRateX;
    }

    public void setCordRateX(int cordRateX) {
        this.cordRateX = cordRateX;
    }

    public int getCordRateY() {
        return cordRateY;
    }

    public void setCordRateY(int cordRateY) {
        this.cordRateY = cordRateY;
    }

    public int getEyescoreX() {
        return eyescoreX;
    }

    public void setEyescoreX(int eyescoreX) {
        this.eyescoreX = eyescoreX;
    }

    public int getEyescoreY() {
        return eyescoreY;
    }

    public void setEyescoreY(int eyescoreY) {
        this.eyescoreY = eyescoreY;
    }
}
