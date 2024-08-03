'use strict';

document.addEventListener("DOMContentLoaded", function() {
    const socket = new SockJS('ws://localhost:8082/ws');
    const stompClient = Stomp.over(socket);

    stompClient.connect({}, function(frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/queue/market-data', function(message) {
            const productData = JSON.parse(message.body);
            document.getElementById('ticker').textContent = productData.TICKER || productData.ticker;
            document.getElementById('sellLimit').textContent = productData.SELL_LIMIT || productData.sellLimit;
            document.getElementById('lastTradedPrice').textContent = productData.LAST_TRADED_PRICE || productData.lastTradedPrice;
            document.getElementById('askPrice').textContent = productData.ASK_PRICE || productData.askPrice;
            document.getElementById('bidPrice').textContent = productData.BID_PRICE || productData.bidPrice;
            document.getElementById('buyLimit').textContent = productData.BUY_LIMIT || productData.buyLimit;
            document.getElementById('maxPriceShift').textContent = productData.MAX_PRICE_SHIFT || productData.maxPriceShift;
            document.getElementById('timeStamp').textContent = productData.TIMESTAMP || productData.timeStamp;
        });
    });

    console.log('App.js loaded');
});