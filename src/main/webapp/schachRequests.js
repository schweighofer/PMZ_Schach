var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
var __generator = (this && this.__generator) || function (thisArg, body) {
    var _ = { label: 0, sent: function() { if (t[0] & 1) throw t[1]; return t[1]; }, trys: [], ops: [] }, f, y, t, g;
    return g = { next: verb(0), "throw": verb(1), "return": verb(2) }, typeof Symbol === "function" && (g[Symbol.iterator] = function() { return this; }), g;
    function verb(n) { return function (v) { return step([n, v]); }; }
    function step(op) {
        if (f) throw new TypeError("Generator is already executing.");
        while (_) try {
            if (f = 1, y && (t = op[0] & 2 ? y["return"] : op[0] ? y["throw"] || ((t = y["return"]) && t.call(y), 0) : y.next) && !(t = t.call(y, op[1])).done) return t;
            if (y = 0, t) op = [op[0] & 2, t.value];
            switch (op[0]) {
                case 0: case 1: t = op; break;
                case 4: _.label++; return { value: op[1], done: false };
                case 5: _.label++; y = op[1]; op = [0]; continue;
                case 7: op = _.ops.pop(); _.trys.pop(); continue;
                default:
                    if (!(t = _.trys, t = t.length > 0 && t[t.length - 1]) && (op[0] === 6 || op[0] === 2)) { _ = 0; continue; }
                    if (op[0] === 3 && (!t || (op[1] > t[0] && op[1] < t[3]))) { _.label = op[1]; break; }
                    if (op[0] === 6 && _.label < t[1]) { _.label = t[1]; t = op; break; }
                    if (t && _.label < t[2]) { _.label = t[2]; _.ops.push(op); break; }
                    if (t[2]) _.ops.pop();
                    _.trys.pop(); continue;
            }
            op = body.call(thisArg, _);
        } catch (e) { op = [6, e]; y = 0; } finally { f = t = 0; }
        if (op[0] & 5) throw op[1]; return { value: op[0] ? op[1] : void 0, done: true };
    }
};
var _this = this;
var NAME = "";
var GAMEID;
var firstLoad = function () {
    // @ts-ignore
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
    // @ts-ignore
    var gameID;
    var url = "http://localhost:8080/pmz-1.0-SNAPSHOT/api/chess/start";
    fetch(url)
        .then(function (res) {
        if (!res.ok) {
            throw new Error("GET REQUEST FAILED");
        }
        return res.json();
    })
        .then(function (gameIDJSON) {
        gameID = parseInt(gameIDJSON);
        document.getElementById("gameID").innerHTML = "<p>your gameID is: " + gameID + "</p><p>the gameID toshare is:" + (gameID + 1) + "</p>";
        // @ts-ignore
        var url = "http://localhost:8080/pmz-1.0-SNAPSHOT/api/chess/board/" + gameID;
        GAMEID = gameID;
        console.log("hallo1");
        displayGameBoardRequest(url);
    })
        .catch(function (err) {
        console.log(err);
    });
};
var displayGameBoard = function (gameBoardJSON, moveAbles) { return __awaiter(_this, void 0, void 0, function () {
    var reihe, html, _i, _a, _b, key, value, _c;
    return __generator(this, function (_d) {
        switch (_d.label) {
            case 0:
                reihe = 1;
                html = "";
                _i = 0, _a = Object.entries(gameBoardJSON);
                _d.label = 1;
            case 1:
                if (!(_i < _a.length)) return [3 /*break*/, 5];
                _b = _a[_i], key = _b[0], value = _b[1];
                if ((key % 8) == 0) {
                    reihe = reihe + 1;
                }
                _c = Object.values(moveAbles).includes(value.position);
                if (!_c) return [3 /*break*/, 3];
                return [4 /*yield*/, isOnTurn()];
            case 2:
                _c = (_d.sent());
                _d.label = 3;
            case 3:
                if (_c) {
                    html += "<input type=\"button\" class=\"greenField\" value=\"" + value.char + "\" class=\"greenField\" name=\"" + key + "\" onclick=\"makemoveOnline(" + value.position + ");\">";
                }
                else if (((key % 2) != 0 && (reihe % 2) == 0) || ((key % 2) == 0 && (reihe % 2) != 0)) {
                    html += "<input type=\"button\" class=\"blackField\" value=\"" + value.char + "\" class=\"blackField\" name=\"" + key + "\" onclick=\"planmoveOnline(" + value.position + ");\">";
                }
                else {
                    html += "<input type=\"button\" class=\"whiteField\" value=\"" + value.char + "\" class=\"whiteField\" name=\"" + key + "\" onclick=\"planmoveOnline(" + value.position + ");\">";
                }
                if ((parseInt(key) + 1) % 8 == 0) {
                    html += "<br>";
                }
                //console.log(html);
                document.getElementById("playingField").innerHTML = html;
                _d.label = 4;
            case 4:
                _i++;
                return [3 /*break*/, 1];
            case 5: return [2 /*return*/];
        }
    });
}); };
var joinGame = function () {
    document.getElementById("startGameButton").style.display = "none";
    document.getElementById("joinGameButton").style.display = "none";
    document.getElementById("popUp").style.display = "block";
    document.getElementById("popUp").innerHTML = "<div style=\"background-color: aqua; position: absolute;  top: 56%; left: 50%;transform: translate(-50%, -50%); height: 50vh; width: 50vh;\"><p>Enter the your given hashcode: </p><input type=\"text\" id=\"hash\"/><button onclick=\"gameIDInputed();\">go!</button></div>";
};
var gameIDInputed = function () {
    // @ts-ignore
    var gameID = document.getElementById("hash").value;
    document.getElementById("popUp").style.display = "none";
    console.log("your hash is: " + gameID);
    GAMEID = gameID;
    var url = "http://localhost:8080/pmz-1.0-SNAPSHOT/api/chess/board/" + gameID;
    displayGameBoardRequest(url);
};
var displayGameBoardRequest = function (url) { return __awaiter(_this, void 0, void 0, function () {
    var isBoardNotAktuell;
    return __generator(this, function (_a) {
        switch (_a.label) {
            case 0:
                console.log("hallo");
                isBoardNotAktuell = true;
                _a.label = 1;
            case 1:
                if (isBoardNotAktuell) {
                    fetch(url)
                        .then(function (res) {
                        if (!res.ok) {
                            throw new Error("GET REQUEST FAILED");
                        }
                        return res.json();
                    })
                        .then(function (gameBoardJSON) {
                        console.log(1);
                        displayGameBoard(gameBoardJSON, []);
                        console.log(1 + "finished");
                    });
                    isBoardNotAktuell = false;
                }
                return [4 /*yield*/, isOnTurn()];
            case 2:
                if (!!(_a.sent())) return [3 /*break*/, 6];
                _a.label = 3;
            case 3: return [4 /*yield*/, isOnTurn()];
            case 4:
                if (!(_a.sent())) return [3 /*break*/, 3];
                _a.label = 5;
            case 5:
                isBoardNotAktuell = true;
                _a.label = 6;
            case 6: return [4 /*yield*/, isOnTurn()];
            case 7:
                if ((_a.sent())) return [3 /*break*/, 1];
                _a.label = 8;
            case 8: return [2 /*return*/];
        }
    });
}); };
var planmoveOnline = function (position) {
    var url = "http://localhost:8080/pmz-1.0-SNAPSHOT/api/chess/moves/" + GAMEID + "?position=" + position;
    console.log(url);
    fetch(url)
        .then(function (res) {
        if (!res.ok) {
            throw new Error("GET REQUEST FAILED");
        }
        return res.json();
    })
        .then(function (moveAbleJSON) {
        console.log(Object.values(moveAbleJSON));
        var url = "http://localhost:8080/pmz-1.0-SNAPSHOT/api/chess/board/" + GAMEID;
        fetch(url)
            .then(function (res) {
            if (!res.ok) {
                throw new Error("GET REQUEST FAILED");
            }
            return res.json();
        })
            .then(function (gameBoardJSON) {
            displayGameBoard(gameBoardJSON, Object.values(moveAbleJSON));
            console.log("aaa");
        })
            .catch(function (err) {
            console.log(err);
        });
    })
        .catch(function (err) {
        console.log(err);
    });
};
var makemoveOnline = function (position) {
    var url = "http://localhost:8080/pmz-1.0-SNAPSHOT/api/chess/move/" + GAMEID + "?target=" + position;
    var urlGame = "http://localhost:8080/pmz-1.0-SNAPSHOT/api/chess/board/" + GAMEID;
    console.log(url);
    var init = {
        method: 'post'
    };
    fetch(url, init)
        .then(function (res) {
        if (!res.ok) {
            throw new Error("POST REQUEST FAILED");
        }
        return res.json();
    })
        .then(function (gameBoardJSON) {
        console.log(gameBoardJSON);
        displayGameBoardRequest(urlGame);
    })
        .catch(function (err) {
        console.log(err);
    });
};
var isOnTurn = function () { return __awaiter(_this, void 0, void 0, function () {
    var url, response;
    return __generator(this, function (_a) {
        switch (_a.label) {
            case 0:
                url = "http://localhost:8080/pmz-1.0-SNAPSHOT/api/chess/turn/" + GAMEID;
                return [4 /*yield*/, fetch(url)];
            case 1:
                response = _a.sent();
                return [4 /*yield*/, response.json()];
            case 2: return [2 /*return*/, _a.sent()];
        }
    });
}); };
