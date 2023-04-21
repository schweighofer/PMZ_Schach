var NAME = "";
var GAMEID = 0;
var firstLoad = function () {
    loadField();
    document.getElementById("popUp").innerHTML = "<div style=\"background-color: red; position: absolute;  top: 56%; left: 50%;transform: translate(-50%, -50%); height: 50vh; width: 50vh;\"><h1 style=\"text-align: center;\">ALERT!</h1><p style=\"text-align: center; font-size: larger;\">please input your name:</p><div style=\"display: flex; justify-content: center; align-items: center;\"><input type=\"text\" id=\"name\"/><button onclick=\"nameInputed();\">go!</button></div></div>";
    document.getElementById("popUp").style.backgroundColor = "red";
};
var nameInputed = function () {
    NAME = document.getElementById("name").value;
    console.log("your name is: " + NAME);
    document.getElementById("popUp").style.display = "none";
};
var startGame = function () {
    document.getElementById("startGameButton").style.display = "none";
    document.getElementById("joinGameButton").style.display = "none";
    var url = "http://localhost:8080/pmz-1.0-SNAPSHOT/api/chess/start";
    fetch(url)
        .then(function (res) {
        if (!res.ok) {
            throw new Error("GET REQUEST FAILED");
        }
        return res.json();
    })
        .then(function (gameIDJSON) {
        console.log(gameIDJSON);
        GAMEID = gameIDJSON;
        console.log(GAMEID);
    })
        .catch(function (err) {
        console.log(err);
    });
    document.getElementById("gameID").innerHTML = "your gameID is: " + GAMEID;
    var url = "http://localhost:8080/pmz-1.0-SNAPSHOT/api/chess/board/" + GAMEID;
    fetch(url)
        .then(function (res) {
        if (!res.ok) {
            throw new Error("GET REQUEST FAILED");
        }
        return res.json();
    })
        .then(function (gameBoardJSON) {
        displayGameBoard(gameBoardJSON);
    })
        .catch(function (err) {
        console.log(err);
    });
};
var displayGameBoard = function (gameBoardJSON) {
    //todo: array als paramater hinzufügen. in diesem array sind ints von fields possiblemoves
    for (var _i = 0, _a = Object.entries(gameBoardJSON); _i < _a.length; _i++) {
        var _b = _a[_i], key = _b[0], value = _b[1];
        if (value == null) {
            console.log(key + " ");
        }
        else {
            console.log(key + " " + value.char);
        }
    }
    //todo: schleife ausm html nehmen
};
var joinGame = function () {
    document.getElementById("startGameButton").style.display = "none";
    document.getElementById("joinGameButton").style.display = "none";
    document.getElementById("popUp").style.display = "block";
    document.getElementById("popUp").innerHTML = "<div style=\"background-color: aqua; position: absolute;  top: 56%; left: 50%;transform: translate(-50%, -50%); height: 50vh; width: 50vh;\"><p>Enter the your given hashcode: </p><input type=\"text\" id=\"hash\"/><button onclick=\"hashInputed();\">go!</button></div>";
};
var hashInputed = function () {
    GAMEID = document.getElementById("hash").value;
    document.getElementById("popUp").style.display = "none";
    console.log("your hash is: " + GAMEID);
    //todo: getBoard request
};
