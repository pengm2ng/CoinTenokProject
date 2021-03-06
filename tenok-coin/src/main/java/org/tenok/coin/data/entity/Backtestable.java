package org.tenok.coin.data.entity;

import org.tenok.coin.type.CoinEnum;

public interface BackTestable {
    public double getRealtimeProfit(CoinEnum coinType, Orderable order);

    public double getWholeProfit();

    public boolean nextSeq(CoinEnum coinType);
}
