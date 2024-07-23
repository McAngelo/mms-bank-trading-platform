package com.mms.market_data_service.services.interfaces;

import com.mms.market_data_service.models.StreamOrder;

public interface DataService {
    void ingestOrder(StreamOrder order);
}
