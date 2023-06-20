var OWN_NAME:string = "";
var GAMEID:number;
var ENEMY_GAMEID:number;
var MAX_TIME:number;


const firstLoad = () => {
    // @ts-ignore
    loadField();
}
const multiplayer = () => {
    document.getElementById("startGameButton").style.display = "none";
    document.getElementById("joinGameButton").style.display = "none";
    document.getElementById("overlay").style.display = "block";
    document.getElementById("popup").innerHTML =
        "            <button id=\"close-btn\" onclick=\"start();\">Spiel starten</button>" +
        "            <button id=\"close-btn\" onclick=\"joinGame();\">Spiel beitreten</button>";
}

const nameInputed = (): void => {
    OWN_NAME = (document.getElementById("name") as HTMLInputElement).value;
    console.log("your name is: " + OWN_NAME);

    while(!(OWN_NAME.length > 0)){
        document.getElementById("popup").innerHTML = "<h2>Enter Your Name(>0)</h2>\n" +
            "        <input type=\"text\" id=\"name\" placeholder=\"Your Name\">\n" +
            "        <button id=\"close-btn\" onclick=\"nameInputed();\">Enter</button>";
        OWN_NAME = (document.getElementById("name") as HTMLInputElement).value;
    }
    document.getElementById("overlay").style.display = "none";
}



const displayGameBoard = async (gameBoardJSON, moveAbles) => {

    var reihe: number = 1;
    var html: string = "";

    for (const [key, value] of Object.entries(gameBoardJSON)) {
        if ((key % 8) == 0) {
            reihe = reihe + 1;
        }

        if (Object.values(moveAbles).includes(value.position) && await isOnTurn()) {
            html += "<input type=\"button\" class=\"greenField\" value=\"" + value.char + "\" class=\"greenField\" name=\"" + key + "\" onclick=\"makemoveOnline(" + value.position + ");\">";
        } else if (((key % 2) != 0 && (reihe % 2) == 0) || ((key % 2) == 0 && (reihe % 2) != 0)) {
            html += "<input type=\"button\" class=\"blackField\" value=\"" + value.char + "\" class=\"blackField\" name=\"" + key + "\" onclick=\"planmoveOnline(" + value.position + ");\">";
        } else {
            html += "<input type=\"button\" class=\"whiteField\" value=\"" + value.char + "\" class=\"whiteField\" name=\"" + key + "\" onclick=\"planmoveOnline(" + value.position + ");\">";
        }
        if ((parseInt(key) + 1) % 8 == 0) {
            html += "<br>";
        }
        //console.log(html);
        document.getElementById("playingField").innerHTML = html;

    }
    

}
const joinGame = () => {
    document.getElementById("startGameButton").style.display = "none";
    document.getElementById("joinGameButton").style.display = "none";

    document.getElementById("overlay").style.display = "block";
    document.getElementById("popup").innerHTML = "<a>Enter Game ID:</a>\n" +
            "            <input type=\"text\" id=\"hash\" placeholder=\"_\">\n" +
            "            <button id=\"close-btn\" onclick=\"gameIDInputed();\">Enter</button>";



}
const start = async (): void => {
    document.getElementById("overlay").style.display = "block";
    document.getElementById("popup").innerHTML = "<a>Enter Time:</a>\n" +
        "            <input type=\"text\" id=\"time\">\n" + "</br>" +
        "            <button id=\"startAsWhite\" onclick=\"startAsWhite();\">als Weiß starten</button>"+
        "            <button id=\"startAsBlack\" onclick=\"startAsBlack();\">als Schwarz auf Weißen warten</button>";


}
const gameIDInputed = async (): void => {
    var gameID : number = (document.getElementById("hash") as HTMLInputElement).value;
    document.getElementById("overlay").style.display = "none";
    console.log("your hash is: " + gameID);
    GAMEID = gameID;
    if(GAMEID % 2 == 0){
        ENEMY_GAMEID = parseInt(GAMEID) + 1;
    }
    else {
        ENEMY_GAMEID = parseInt(GAMEID) - 1;
    }
    const url: string = `http://localhost:8080/pmz-1.0-SNAPSHOT/api/chess/board/` +gameID;
    await setOwnName();
    displayGameBoardRequest(url);

}
const startAsWhite = async () : void => {
    const timeInput = (document.getElementById("time") as HTMLInputElement).value;
    MAX_TIME = parseInt(timeInput);
    console.log("1"+MAX_TIME);
    console.log("2"+timeInput);
    console.log("3"+document.getElementById("time"));
    while(MAX_TIME < 60){
        document.getElementById("popup").innerHTML = "<a>Enter Time(>=60):</a>\n" +
            "            <input type=\"text\" id=\"time\">\n" + "</br>"
        "            <button id=\"startAsWhite\" onclick=\"startAsWhite();\">als Weiß starten</button>"+
        "            <button id=\"startAsBlack\" onclick=\"startAsBlack();\">als Schwarz auf Weißen warten</button>";
        const timeInput = document.getElementById("time") as HTMLInputElement;
        MAX_TIME = parseInt(timeInput.value);
    }
    // @ts-ignore
    GAMEID = await startGame();
    ENEMY_GAMEID = parseInt(GAMEID) + 1;
    await setOwnName();
    const url : string = `http://localhost:8080/pmz-1.0-SNAPSHOT/api/chess/board/` + GAMEID;
    displayGameBoardRequest(url);
    document.getElementById("overlay").style.display = "none";
}
const startAsBlack = async () : void => {
    const timeInput = (document.getElementById("time") as HTMLInputElement).value;
    MAX_TIME = parseInt(timeInput);
    console.log("1"+MAX_TIME);
    console.log("2"+timeInput);
    console.log("3"+document.getElementById("time"));
    while(MAX_TIME < 60){
        document.getElementById("popup").innerHTML = "<a>Enter Time(>=60):</a>\n" +
            "            <input type=\"text\" id=\"time\">\n" + "</br>"
        "            <button id=\"startAsWhite\" onclick=\"startAsWhite();\">als Weiß starten</button>"+
        "            <button id=\"startAsBlack\" onclick=\"startAsBlack();\">als Schwarz auf Weißen warten</button>";
        const timeInput = document.getElementById("time") as HTMLInputElement;
        MAX_TIME = parseInt(timeInput.value);
    }
    ENEMY_GAMEID = await startGame();
    GAMEID = parseInt(ENEMY_GAMEID) + 1;
    console.log("ugabugaranpararf::"+GAMEID);
    await setOwnName();
    const url : string = `http://localhost:8080/pmz-1.0-SNAPSHOT/api/chess/board/` + GAMEID;
    displayGameBoardRequest(url);
    document.getElementById("overlay").style.display = "none";
}
const startGame = async (): number => {
    // @ts-ignore
    var gameID:number;
    gameID = (await getGameID());
    console.log("gameID::" + gameID.toString());


    return gameID;
}
const displayGameBoardRequest = async (url): void => {
    var isBoardNotAktuell : boolean = true;
    while (!(await hasEnemyJoined())){
        document.getElementById("overlay").style.display = "block";
        document.getElementById("popup").innerHTML = "<h1>GEGNER IST NOCH NICHT GEJOINED</h1><a>seine GameID wäre:" + ENEMY_GAMEID + "</a>";
        console.log(await getEnemyName(GAMEID) + ":::" + await getEnemyName(ENEMY_GAMEID));
        console.log(GAMEID + ":::" + ENEMY_GAMEID);
    }
    document.getElementById("overlay").style.display = "none";
    if(!(await hasEnded())) {
        do {
            document.getElementById("ownField").innerHTML = OWN_NAME + " : " + (await getOwnTime());
            if (isBoardNotAktuell) {
                fetch(url)
                    .then(res => {
                        if (!res.ok) {
                            throw new Error("GET REQUEST FAILED");
                        }
                        return res.json();
                    })
                    .then(gameBoardJSON => {
                        displayGameBoard(gameBoardJSON, []);
                    });
                isBoardNotAktuell = false;
            }

            if (!(await isOnTurn()) && !(await hasEnded())) {
                do {
                    document.getElementById("enemyField").innerHTML = (await getEnemyName(GAMEID)) + " : " + (await getEnemyTime());
                } while (!(await isOnTurn()) && !(await hasEnded()));
                isBoardNotAktuell = true;
            }
            if ((await isCheck())) {
                console.log("du bist im schach! :(");
                //todo: popup machen
            }
            console.log("whats up ");
            console.log(await isOnTurn());
        } while ((await isOnTurn()) && !(await hasEnded()));
    }

    console.log("uga buga bin draußen und spiel is vorbei");
    fetch(url)
        .then(res => {
            if (!res.ok) {
                throw new Error("GET REQUEST FAILED");
            }
            return res.json();
        })
        .then(gameBoardJSON => {
            displayGameBoard(gameBoardJSON, []);
        });
    //todo: getStats()

}


var POSITION;
const planmoveOnline = async (position): void => {
    if(!(await hasEnded())) {
        POSITION = position;
        console.log(POSITION);
        const url: string = `http://localhost:8080/pmz-1.0-SNAPSHOT/api/chess/moves/` + GAMEID + "?position=" + position;
        console.log(url);
        fetch(url)
            .then(res => {
                if (!res.ok) {
                    throw new Error("GET REQUEST FAILED");
                }
                return res.json();
            })
            .then(moveAbleJSON => {
                console.log(Object.values(moveAbleJSON));
                const url: string = `http://localhost:8080/pmz-1.0-SNAPSHOT/api/chess/board/` + GAMEID;
                fetch(url)
                    .then(res => {
                        if (!res.ok) {
                            throw new Error("GET REQUEST FAILED");
                        }
                        return res.json();
                    })
                    .then(gameBoardJSON => {
                        displayGameBoard(gameBoardJSON, Object.values(moveAbleJSON));
                        console.log("aaa")
                    })
                    .catch(err => {
                        console.log(err);
                    })
            })
            .catch(err => {
                console.log(err);

            })
        }
    }
const makemoveOnline = (position) =>{
    console.log(POSITION);
    const url: string = `http://localhost:8080/pmz-1.0-SNAPSHOT/api/chess/move/`+GAMEID+`?target=`+position;
    const urlGame: string = `http://localhost:8080/pmz-1.0-SNAPSHOT/api/chess/board/` +GAMEID;
    url = url +'&lastPiece='+POSITION;
    console.log(url);
    const init = {
        method: 'post'
    }

    fetch(url, init)
        .then(res => {
            if (!res.ok) {
                throw new Error("POST REQUEST FAILED");
            }
            return res.json();
        })
        .then(gameBoardJSON => {
            console.log(gameBoardJSON);
            displayGameBoardRequest(urlGame);
        })
        .catch(err => {
            console.log(err);
        })
}
const isOnTurn = async () => {
    const url: string = `http://localhost:8080/pmz-1.0-SNAPSHOT/api/chess/turn/` + GAMEID;
    const response = await fetch(url);
    return await response.json();
}

const hasEnded = async () => {
    const url: string = `http://localhost:8080/pmz-1.0-SNAPSHOT/api/chess/ended/` + GAMEID;
    const response = await fetch(url);
    console.log("203");
    return await response.json();
}

const isCheck = async () => {
    const url: string = `http://localhost:8080/pmz-1.0-SNAPSHOT/api/chess/isChess/` + GAMEID;
    const response = await fetch(url);
    return await response.json();
}

const setOwnName = async () => {
    const url: string = `http://localhost:8080/pmz-1.0-SNAPSHOT/api/chess/setName/` + GAMEID + '?name=' + OWN_NAME;
    console.log("215::::"+OWN_NAME);
    console.log(`http://localhost:8080/pmz-1.0-SNAPSHOT/api/chess/setName/` + GAMEID + '?name=' + OWN_NAME);
    const response = await fetch(url);
    return response.json();
}
const getEnemyName = async (gameID : number) => {
    const url: string = `http://localhost:8080/pmz-1.0-SNAPSHOT/api/chess/getEnemyName/` + gameID ;
    console.log("220");
    return fetch(url)
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to fetch enemy name');
            }
            return response.json();
        })
        .then(data => {
            return data as string;
        });
}
const getOwnTime = async () => {
    const url: string = `http://localhost:8080/pmz-1.0-SNAPSHOT/api/chess/getOwnTime/` + GAMEID ;
    const response = await fetch(url);
    console.log("227");
    return await response.json();
}
const getEnemyTime = async () => {
    const url: string = `http://localhost:8080/pmz-1.0-SNAPSHOT/api/chess/getEnemyTime/` + GAMEID ;
    const response = await fetch(url);
    console.log("233");
    return await response.json();
}
const getStats = async () => {
    const url: string = `http://localhost:8080/pmz-1.0-SNAPSHOT/api/chess/getStats/` + GAMEID ;
    const response = await fetch(url);

    return await response.json();
}
const getGameID = async () => {
    const url: string = `http://localhost:8080/pmz-1.0-SNAPSHOT/api/chess/start?time=`+MAX_TIME+"";
    const response = await fetch(url);
    console.log("245");
    return await response.json();
}
const hasEnemyJoined = async () => {
    const url: string = `http://localhost:8080/pmz-1.0-SNAPSHOT/api/chess/hasEnemyJoined/` + GAMEID;
    const response = await fetch(url);
    return await response.json();
}


