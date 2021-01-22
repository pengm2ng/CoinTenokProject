package org.tenok.coin.data.entity;

import java.util.Date;

import org.tenok.coin.type.CoinEnum;
import org.tenok.coin.type.OrderTypeEnum;
import org.tenok.coin.type.SideEnum;
import org.tenok.coin.type.TIFEnum;

public interface Orderable {
    /**
     * Sell, Buy인지 아닌지
     * @return
     */
    public SideEnum getSide();
    
    /**
     * @return 주문한 코인
     */
    public CoinEnum getCoin();

    /**
     * Market, limit 인지 아닌지
     * @return 
     */
    public OrderTypeEnum getOrderType();

    /**
     * @return Time In Force
     */
    public TIFEnum getTIF();

    /**
     * 
     * @return qty
     * 
     */
    public double getQty();
    /**
     * 
     * @return timestamp
     */
    public Date getTimeStamp();
}