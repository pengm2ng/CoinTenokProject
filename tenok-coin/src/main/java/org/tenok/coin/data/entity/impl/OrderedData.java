package org.tenok.coin.data.entity.impl;

import java.util.Date;

import org.tenok.coin.data.entity.OrderDataAccessable;
import org.tenok.coin.type.CoinEnum;
import org.tenok.coin.type.OrderTypeEnum;
import org.tenok.coin.type.SideEnum;
import org.tenok.coin.type.TIFEnum;

import lombok.Builder;
import lombok.NonNull;

@Builder
public class OrderedData implements OrderDataAccessable {
    @NonNull
    private SideEnum side;
    @NonNull
    private CoinEnum coinType;
    @NonNull
    private OrderTypeEnum orderType;
    @NonNull
    private TIFEnum tif;
    private double qty;
    private int leverage;
    private double cumExecFee;
    private Date timeStamp;

    @Override
    public SideEnum getSide() {
        return side;
    }

    @Override
    public CoinEnum getCoinType() {
        return coinType;
    }

    @Override
    public OrderTypeEnum getOrderType() {
        return orderType;
    }

    @Override
    public TIFEnum getTIF() {
        return tif;
    }

    @Override
    public double getQty() {
        return qty;
    }

    @Override
    public Date getTimeStamp() {
        return timeStamp;
    }

    @Override
    public int getLeverage() {
        return leverage;
    }

    public double getCumExecFee() {
        return cumExecFee;
    }

}
