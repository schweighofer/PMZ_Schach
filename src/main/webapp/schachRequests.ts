var NAME:string = "";
var GAMEID:number;


const firstLoad = () => {
    // @ts-ignore
    loadField();
    document.getElementById("popUp").innerHTML = "<div style=\"background-color: red; position: absolute;  top: 56%; left: 50%;transform: translate(-50%, -50%); height: 50vh; width: 50vh;\"><h1 style=\"text-align: center;\">ALERT!</h1><p style=\"text-align: center; font-size: larger;\">please input your name:</p><div style=\"display: flex; justify-content: center; align-items: center;\"><input type=\"text\" id=\"name\"/><button onclick=\"nameInputed();\">go!</button></div></div>"
    document.getElementById("popUp").style.backgroundColor = "red";
}

const nameInputed = (): void => {
    NAME = (document.getElementById("name") as HTMLInputElement).value;
    console.log("your name is: " + NAME);

    document.getElementById("popUp").style.display = "none";
}


const startGame = () => {
    document.getElementById("startGameButton").style.display = "none";
    document.getElementById("joinGameButton").style.display = "none";

    // @ts-ignore
    var gameID:number;
    const url: string = `http://localhost:8080/pmz-1.0-SNAPSHOT/api/chess/start`;
    fetch(url)
        .then(res => {
            if (!res.ok) {
                throw new Error("GET REQUEST FAILED");
            }
            return res.json();
        })
        .then(gameIDJSON => {
            gameID = parseInt(gameIDJSON);
            document.getElementById("gameID").innerHTML = "<p>your gameID is: "+gameID+"</p><p>the gameID toshare is:"+(gameID+1)+"</p>";
            // @ts-ignore
            const url: string = `http://localhost:8080/pmz-1.0-SNAPSHOT/api/chess/board/` +gameID;
            GAMEID = gameID;
            console.log("hallo1");
            displayGameBoardRequest(url);
        })
        .catch(err => {
            console.log(err);
        });

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

    document.getElementById("popUp").style.display = "block";
    document.getElementById("popUp").innerHTML = "<div style=\"background-color: aqua; position: absolute;  top: 56%; left: 50%;transform: translate(-50%, -50%); height: 50vh; width: 50vh;\"><p>Enter the your given hashcode: </p><input type=\"text\" id=\"hash\"/><button onclick=\"gameIDInputed();\">go!</button></div>"

}
const gameIDInputed = (): void => {
    // @ts-ignore
    var gameID:number = (document.getElementById("hash") as HTMLInputElement).value;
    document.getElementById("popUp").style.display = "none";
    console.log("your hash is: " + gameID);
    GAMEID = gameID;
    const url: string = `http://localhost:8080/pmz-1.0-SNAPSHOT/api/chess/board/` +gameID;
    displayGameBoardRequest(url);
}
const displayGameBoardRequest = async (url): void => {
    console.log("hallo");
    var isBoardNotAktuell : boolean = true;
    do {
        if(isBoardNotAktuell){
            fetch(url)
                .then(res => {
                    if (!res.ok) {
                        throw new Error("GET REQUEST FAILED");
                    }
                    return res.json();
                })
                .then(gameBoardJSON => {
                    console.log(1);
                    displayGameBoard(gameBoardJSON, []);
                    console.log(1 + "finished")
                });
            isBoardNotAktuell = false;
        }
        if(!(await isOnTurn())){//&&!(await hasEnded())
            do{}while (!(await isOnTurn()));
            isBoardNotAktuell = true;
        }

    } while ((await isOnTurn())); //&&!(await hasEnded())
    //getStatistics
}

const planmoveOnline = (position) => {
    const url: string = `http://localhost:8080/pmz-1.0-SNAPSHOT/api/chess/moves/` +GAMEID+"?position="+position;
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
            const url: string = `http://localhost:8080/pmz-1.0-SNAPSHOT/api/chess/board/` +GAMEID;
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
const makemoveOnline = (position) =>{
    const url: string = `http://localhost:8080/pmz-1.0-SNAPSHOT/api/chess/move/`+GAMEID+`?target=`+position;
    const urlGame: string = `http://localhost:8080/pmz-1.0-SNAPSHOT/api/chess/board/` +GAMEID;
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
