package org.tenok.coin.data.entity.impl;

import java.util.Date;

import lombok.Setter;


@Setter
public class Candle {
    private Date startAt;
    private double volume;
    private double open;
    private double high;
    private double low;
    private double close;
    private boolean isConfirmed;

    public Candle(Date startAt, double volume, double open, double high, double low, double close) {
        this.startAt = startAt;
        this.volume = volume;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
    }

    public Candle() {
        
    }

    public Date getStartAt() {
        return this.startAt;
    }

    public double getVolume() {
        return this.volume;
    }

    public double getOpen() {
        return this.open;
    }

    public double getHigh() {
        return this.high;
    }

    public double getLow() {
        return this.low;
    }

    public double getClose() {
        return this.close;
    }

    public boolean isConfirmed() {
        return this.isConfirmed;
    }

    @Override
    public String toString() {
        return String.format("open: %f, high:%f, close: %f, low: %f", open, high, close, low);
    }
}
