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
var OWN_NAME = "";
var GAMEID;
var ENEMY_GAMEID;
var MAX_TIME;
var firstLoad = function () {
    // @ts-ignore
    loadField();
};
var multiplayer = function () {
    document.getElementById("startGameButton").style.display = "none";
    document.getElementById("joinGameButton").style.display = "none";
    document.getElementById("overlay").style.display = "block";
    document.getElementById("popup").innerHTML =
        "            <button id=\"close-btn\" onclick=\"start();\">Spiel starten</button>" +
            "            <button id=\"close-btn\" onclick=\"joinGame();\">Spiel beitreten</button>";
};
var nameInputed = function () {
    OWN_NAME = document.getElementById("name").value;
    console.log("your name is: " + OWN_NAME);
    if (OWN_NAME == null) {
        OWN_NAME = "Piet :(";
    }
    document.getElementById("overlay").style.display = "none";
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
    document.getElementById("overlay").style.display = "block";
    document.getElementById("popup").innerHTML = "<a>Enter Game ID:</a>\n" +
        "            <input type=\"text\" id=\"hash\" placeholder=\"_\">\n" +
        "            <button id=\"close-btn\" onclick=\"gameIDInputed();\">Enter</button>";
};
var start = function () { return __awaiter(_this, void 0, void 0, function () {
    return __generator(this, function (_a) {
        document.getElementById("overlay").style.display = "block";
        document.getElementById("popup").innerHTML = "<a>Enter Time:</a>\n" +
            "            <input type=\"text\" id=\"time\">\n" + "</br>" +
            "            <button id=\"startAsWhite\" onclick=\"startAsWhite();\">als Weiß starten</button>" +
            "            <button id=\"startAsBlack\" onclick=\"startAsBlack();\">als Schwarz auf Weißen warten</button>";
        return [2 /*return*/];
    });
}); };
var gameIDInputed = function () { return __awaiter(_this, void 0, void 0, function () {
    var gameID, url;
    return __generator(this, function (_a) {
        switch (_a.label) {
            case 0:
                gameID = document.getElementById("hash").value;
                document.getElementById("overlay").style.display = "none";
                console.log("your hash is: " + gameID);
                GAMEID = gameID;
                if (GAMEID % 2 == 0) {
                    ENEMY_GAMEID = parseInt(GAMEID) + 1;
                }
                else {
                    ENEMY_GAMEID = parseInt(GAMEID) - 1;
                }
                url = "http://localhost:8080/pmz-1.0-SNAPSHOT/api/chess/board/" + gameID;
                return [4 /*yield*/, setOwnName()];
            case 1:
                _a.sent();
                displayGameBoardRequest(url);
                return [2 /*return*/];
        }
    });
}); };
var startAsWhite = function () { return __awaiter(_this, void 0, void 0, function () {
    var timeInput, url;
    return __generator(this, function (_a) {
        switch (_a.label) {
            case 0:
                timeInput = document.getElementById("time").value;
                MAX_TIME = parseInt(timeInput);
                return [4 /*yield*/, startGame()];
            case 1:
                // @ts-ignore
                GAMEID = _a.sent();
                ENEMY_GAMEID = parseInt(GAMEID) + 1;
                return [4 /*yield*/, setOwnName()];
            case 2:
                _a.sent();
                url = "http://localhost:8080/pmz-1.0-SNAPSHOT/api/chess/board/" + GAMEID;
                displayGameBoardRequest(url);
                document.getElementById("overlay").style.display = "none";
                return [2 /*return*/];
        }
    });
}); };
var startAsBlack = function () { return __awaiter(_this, void 0, void 0, function () {
    var timeInput, url;
    return __generator(this, function (_a) {
        switch (_a.label) {
            case 0:
                timeInput = document.getElementById("time").value;
                MAX_TIME = parseInt(timeInput);
                return [4 /*yield*/, startGame()];
            case 1:
                ENEMY_GAMEID = _a.sent();
                GAMEID = parseInt(ENEMY_GAMEID) + 1;
                console.log("ugabugaranpararf::" + GAMEID);
                return [4 /*yield*/, setOwnName()];
            case 2:
                _a.sent();
                url = "http://localhost:8080/pmz-1.0-SNAPSHOT/api/chess/board/" + GAMEID;
                displayGameBoardRequest(url);
                document.getElementById("overlay").style.display = "none";
                return [2 /*return*/];
        }
    });
}); };
var startGame = function () { return __awaiter(_this, void 0, void 0, function () {
    var gameID;
    return __generator(this, function (_a) {
        switch (_a.label) {
            case 0: return [4 /*yield*/, getGameID()];
            case 1:
                gameID = (_a.sent());
                console.log("gameID::" + gameID.toString());
                return [2 /*return*/, gameID];
        }
    });
}); };
var displayGameBoardRequest = function (url) { return __awaiter(_this, void 0, void 0, function () {
    var isBoardNotAktuell, _a, _b, _c, _d, _e, _f, _g, _h, _j, _k, _l, _m, _o, _p, _q, _r;
    return __generator(this, function (_s) {
        switch (_s.label) {
            case 0:
                isBoardNotAktuell = true;
                _s.label = 1;
            case 1: return [4 /*yield*/, hasEnemyJoined()];
            case 2:
                if (!!(_s.sent())) return [3 /*break*/, 3];
                document.getElementById("overlay").style.display = "block";
                document.getElementById("popup").innerHTML = "<h1>GEGNER IST NOCH NICHT GEJOINED</h1><a>seine GameID wäre:" + ENEMY_GAMEID + "</a>";
                return [3 /*break*/, 1];
            case 3:
                _a = document.getElementById("ownField");
                _b = "<h1>" + OWN_NAME + " : ";
                return [4 /*yield*/, getOwnTime()];
            case 4:
                _a.innerHTML = _b + (_s.sent()) + "</h1>";
                document.getElementById("ownField").style.color = "white";
                _c = document.getElementById("enemyField");
                _d = "<h1>";
                return [4 /*yield*/, getEnemyName(GAMEID)];
            case 5:
                _e = _d + (_s.sent()) + " : ";
                return [4 /*yield*/, getEnemyTime()];
            case 6:
                _c.innerHTML = _e + (_s.sent()) + "</h1>";
                document.getElementById("enemyField").style.color = "white";
                document.getElementById("overlay").style.display = "none";
                return [4 /*yield*/, hasEnded()];
            case 7:
                if (!!(_s.sent())) return [3 /*break*/, 28];
                _s.label = 8;
            case 8:
                _f = document.getElementById("ownField");
                _g = OWN_NAME + " : ";
                return [4 /*yield*/, getOwnTime()];
            case 9:
                _f.innerHTML = _g + (_s.sent());
                if (isBoardNotAktuell) {
                    fetch(url)
                        .then(function (res) {
                        if (!res.ok) {
                            throw new Error("GET REQUEST FAILED");
                        }
                        return res.json();
                    })
                        .then(function (gameBoardJSON) {
                        displayGameBoard(gameBoardJSON, []);
                    });
                    isBoardNotAktuell = false;
                }
                return [4 /*yield*/, isOnTurn()];
            case 10:
                _h = !(_s.sent());
                if (!_h) return [3 /*break*/, 12];
                return [4 /*yield*/, hasEnded()];
            case 11:
                _h = !(_s.sent());
                _s.label = 12;
            case 12:
                if (!_h) return [3 /*break*/, 21];
                _s.label = 13;
            case 13:
                _j = document.getElementById("enemyField");
                return [4 /*yield*/, getEnemyName(GAMEID)];
            case 14:
                _k = (_s.sent()) + " : ";
                return [4 /*yield*/, getEnemyTime()];
            case 15:
                _j.innerHTML = _k + (_s.sent());
                _s.label = 16;
            case 16: return [4 /*yield*/, isOnTurn()];
            case 17:
                _l = !(_s.sent());
                if (!_l) return [3 /*break*/, 19];
                return [4 /*yield*/, hasEnded()];
            case 18:
                _l = !(_s.sent());
                _s.label = 19;
            case 19:
                if (_l) return [3 /*break*/, 13];
                _s.label = 20;
            case 20:
                isBoardNotAktuell = true;
                _s.label = 21;
            case 21: return [4 /*yield*/, isCheck()];
            case 22:
                if ((_s.sent())) {
                    console.log("du bist im schach! :(");
                    //todo: popup machen
                }
                console.log("whats up ");
                _o = (_m = console).log;
                return [4 /*yield*/, isOnTurn()];
            case 23:
                _o.apply(_m, [_s.sent()]);
                _s.label = 24;
            case 24: return [4 /*yield*/, isOnTurn()];
            case 25:
                _p = (_s.sent());
                if (!_p) return [3 /*break*/, 27];
                return [4 /*yield*/, hasEnded()];
            case 26:
                _p = !(_s.sent());
                _s.label = 27;
            case 27:
                if (_p) return [3 /*break*/, 8];
                _s.label = 28;
            case 28:
                document.getElementById("overlay").style.display = "block";
                _q = document.getElementById("popup");
                _r = "<h1>SPIEL IST VORBEI</h1><a>";
                return [4 /*yield*/, getStats()];
            case 29:
                _q.innerHTML = _r + (_s.sent()) + "</a>";
                fetch(url)
                    .then(function (res) {
                    if (!res.ok) {
                        throw new Error("GET REQUEST FAILED");
                    }
                    return res.json();
                })
                    .then(function (gameBoardJSON) {
                    displayGameBoard(gameBoardJSON, []);
                });
                return [2 /*return*/];
        }
    });
}); };
var POSITION;
var planmoveOnline = function (position) { return __awaiter(_this, void 0, void 0, function () {
    var url;
    return __generator(this, function (_a) {
        switch (_a.label) {
            case 0: return [4 /*yield*/, hasEnded()];
            case 1:
                if (!(_a.sent())) {
                    POSITION = position;
                    console.log(POSITION);
                    url = "http://localhost:8080/pmz-1.0-SNAPSHOT/api/chess/moves/" + GAMEID + "?position=" + position;
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
                }
                return [2 /*return*/];
        }
    });
}); };
var makemoveOnline = function (position) {
    console.log(POSITION);
    var url = "http://localhost:8080/pmz-1.0-SNAPSHOT/api/chess/move/" + GAMEID + "?target=" + position;
    var urlGame = "http://localhost:8080/pmz-1.0-SNAPSHOT/api/chess/board/" + GAMEID;
    url = url + '&lastPiece=' + POSITION;
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
var hasEnded = function () { return __awaiter(_this, void 0, void 0, function () {
    var url, response;
    return __generator(this, function (_a) {
        switch (_a.label) {
            case 0:
                url = "http://localhost:8080/pmz-1.0-SNAPSHOT/api/chess/ended/" + GAMEID;
                return [4 /*yield*/, fetch(url)];
            case 1:
                response = _a.sent();
                return [4 /*yield*/, response.json()];
            case 2: return [2 /*return*/, _a.sent()];
        }
    });
}); };
var isCheck = function () { return __awaiter(_this, void 0, void 0, function () {
    var url, response;
    return __generator(this, function (_a) {
        switch (_a.label) {
            case 0:
                url = "http://localhost:8080/pmz-1.0-SNAPSHOT/api/chess/isChess/" + GAMEID;
                return [4 /*yield*/, fetch(url)];
            case 1:
                response = _a.sent();
                return [4 /*yield*/, response.json()];
            case 2: return [2 /*return*/, _a.sent()];
        }
    });
}); };
var setOwnName = function () { return __awaiter(_this, void 0, void 0, function () {
    var url, response;
    return __generator(this, function (_a) {
        switch (_a.label) {
            case 0:
                url = "http://localhost:8080/pmz-1.0-SNAPSHOT/api/chess/setName/" + GAMEID + '?name=' + OWN_NAME;
                console.log("http://localhost:8080/pmz-1.0-SNAPSHOT/api/chess/setName/" + GAMEID + '?name=' + OWN_NAME);
                return [4 /*yield*/, fetch(url)];
            case 1:
                response = _a.sent();
                return [2 /*return*/, response.json()];
        }
    });
}); };
var getEnemyName = function (gameID) { return __awaiter(_this, void 0, void 0, function () {
    var url;
    return __generator(this, function (_a) {
        url = "http://localhost:8080/pmz-1.0-SNAPSHOT/api/chess/getEnemyName/" + gameID;
        return [2 /*return*/, fetch(url)
                .then(function (response) {
                if (!response.ok) {
                    throw new Error('Failed to fetch enemy name');
                }
                return response.json();
            })
                .then(function (data) {
                return data;
            })];
    });
}); };
var getOwnTime = function () { return __awaiter(_this, void 0, void 0, function () {
    var url, response;
    return __generator(this, function (_a) {
        switch (_a.label) {
            case 0:
                url = "http://localhost:8080/pmz-1.0-SNAPSHOT/api/chess/getOwnTime/" + GAMEID;
                return [4 /*yield*/, fetch(url)];
            case 1:
                response = _a.sent();
                return [4 /*yield*/, response.json()];
            case 2: return [2 /*return*/, _a.sent()];
        }
    });
}); };
var getEnemyTime = function () { return __awaiter(_this, void 0, void 0, function () {
    var url, response;
    return __generator(this, function (_a) {
        switch (_a.label) {
            case 0:
                url = "http://localhost:8080/pmz-1.0-SNAPSHOT/api/chess/getEnemyTime/" + GAMEID;
                return [4 /*yield*/, fetch(url)];
            case 1:
                response = _a.sent();
                return [4 /*yield*/, response.json()];
            case 2: return [2 /*return*/, _a.sent()];
        }
    });
}); };
var getStats = function () { return __awaiter(_this, void 0, void 0, function () {
    var url;
    return __generator(this, function (_a) {
        url = "http://localhost:8080/pmz-1.0-SNAPSHOT/api/chess/getStats/" + GAMEID;
        console.log("220");
        return [2 /*return*/, fetch(url)
                .then(function (response) {
                if (!response.ok) {
                    throw new Error('Failed to fetch stats');
                }
                return response.json();
            })
                .then(function (data) {
                return data;
            })];
    });
}); };
var getGameID = function () { return __awaiter(_this, void 0, void 0, function () {
    var url, response;
    return __generator(this, function (_a) {
        switch (_a.label) {
            case 0:
                url = "http://localhost:8080/pmz-1.0-SNAPSHOT/api/chess/start?time=" + MAX_TIME + "";
                return [4 /*yield*/, fetch(url)];
            case 1:
                response = _a.sent();
                return [4 /*yield*/, response.json()];
            case 2: return [2 /*return*/, _a.sent()];
        }
    });
}); };
var hasEnemyJoined = function () { return __awaiter(_this, void 0, void 0, function () {
    var url, response;
    return __generator(this, function (_a) {
        switch (_a.label) {
            case 0:
                url = "http://localhost:8080/pmz-1.0-SNAPSHOT/api/chess/hasEnemyJoined/" + GAMEID;
                return [4 /*yield*/, fetch(url)];
            case 1:
                response = _a.sent();
                return [4 /*yield*/, response.json()];
            case 2: return [2 /*return*/, _a.sent()];
        }
    });
}); };
var giveUp = function () { return __awaiter(_this, void 0, void 0, function () {
    var url, response;
    return __generator(this, function (_a) {
        switch (_a.label) {
            case 0:
                url = "http://localhost:8080/pmz-1.0-SNAPSHOT/api/chess/giveUp/" + GAMEID;
                return [4 /*yield*/, fetch(url)];
            case 1:
                response = _a.sent();
                console.log(GAMEID + "is giving up");
                return [4 /*yield*/, response.json()];
            case 2: return [2 /*return*/, _a.sent()];
        }
    });
}); };
var giveUpHelp = function () { return __awaiter(_this, void 0, void 0, function () {
    return __generator(this, function (_a) {
        switch (_a.label) {
            case 0: return [4 /*yield*/, giveUp()];
            case 1:
                _a.sent();
                return [2 /*return*/];
        }
    });
}); };
//todo: namen und zeit verschönern und debug verschwinden lassen
