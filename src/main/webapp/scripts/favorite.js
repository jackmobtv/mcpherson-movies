let star;
let starred
let wsProtocol = (window.location.protocol === 'https:' ? 'wss://' : 'ws://');
const wsUriFavorite = wsProtocol + window.location.host + document.location.pathname + "/favorite";
const wsUriUnfavorite = wsProtocol + window.location.host + document.location.pathname + "/unfavorite";
const movieId = $("#movieId").val();
const userId = $("#userId").val();
let FavoriteSocket;
let UnfavoriteSocket;
let busy = false;
let json = JSON.stringify({"user": userId, "movie": movieId});

$(document).ready(function (){
    star = $("#star");
    starred = $("#favorite").html() === "true";
    FavoriteSocket = new WebSocket(wsUriFavorite);
    UnfavoriteSocket = new WebSocket(wsUriUnfavorite);
    if(starred){
        star.removeClass("bi-star");
        star.addClass("bi-star-fill");
    }

    star.click(function (){
        if(!busy){
            if(starred){
                busy = true;
                if(UnfavoriteSocket.readyState === UnfavoriteSocket.OPEN){
                    UnfavoriteSocket.send(json);
                }
            } else {
                busy = true;
                if(FavoriteSocket.readyState === FavoriteSocket.OPEN){
                    FavoriteSocket.send(json);
                }
            }
        }
    });

    FavoriteSocket.onopen = function() {
        console.log("WebSocket is connected."); // Confirm connection
    };

    FavoriteSocket.onmessage = function(event) {
        if(busy){
            star.removeClass("bi-star");
            star.addClass("bi-star-fill");
            starred = true;
        }
        busy = false;
    };

    FavoriteSocket.onerror = function(error) {
        console.error("WebSocket Error: ", error);
    };

    FavoriteSocket.onclose = function() {
        console.log("WebSocket connection closed.");
    };

    UnfavoriteSocket.onopen = function() {
        console.log("WebSocket is connected."); // Confirm connection
    };

    UnfavoriteSocket.onmessage = function(event) {
        if(busy){
            star.removeClass("bi-star-fill");
            star.addClass("bi-star");
            starred = false;
        }
        busy = false;
    };

    UnfavoriteSocket.onerror = function(error) {
        console.error("WebSocket Error: ", error);
    };

    UnfavoriteSocket.onclose = function() {
        console.log("WebSocket connection closed.");
    };
});