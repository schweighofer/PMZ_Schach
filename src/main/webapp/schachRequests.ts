var NAME:string = "";

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
    var GAMEID:number;
    const url: string = `http://localhost:8080/pmz-1.0-SNAPSHOT/api/chess/start`;
    fetch(url)
        .then(res => {
            if (!res.ok) {
                throw new Error("GET REQUEST FAILED");
            }
            return res.json();
        })
        .then(gameIDJSON => {
            console.log(gameIDJSON);
            GAMEID = parseInt(gameIDJSON);
            console.log(GAMEID);
            document.getElementById("gameID").innerHTML = "<p>your gameID is: "+GAMEID+"</p><p>the gameID toshare is:"+(GAMEID+1)+"</p>";
        })
        .catch(err => {
            console.log(err);
        })



    // @ts-ignore
    const url: string = `http://localhost:8080/pmz-1.0-SNAPSHOT/api/chess/board/` +GAMEID;
    fetch(url)
        .then(res => {
            if (!res.ok) {
                throw new Error("GET REQUEST FAILED");
            }
            return res.json();
        })
        .then(gameBoardJSON => {
            displayGameBoard(gameBoardJSON, []);
        })
        .catch(err => {
            console.log(err);
        })
}
const displayGameBoard = (gameBoardJSON, moveAbles) =>{
    //todo: array als paramater hinzufügen. in diesem array sind ints von fields possiblemoves
    var reihe : number = 1;
    var html : string = "";
    for(const [key, value] of Object.entries(gameBoardJSON)) {
        if ((key % 8) ==  0) {
            reihe = reihe + 1;
        }
        if(key in moveAbles){
            if(value == null){
                html += "<input type=\"button\" class=\"greenField\" value=\"" + " " + "\" class=\"blackField\" name=\"" + key + "\" onclick=\"/*planmove(value.position)*/\">";

            } else{
                html += "<input type=\"button\" class=\"greenField\" value=\"" + value.char + "\" class=\"blackField\" name=\"" + key + "\" onclick=\"/*planmove(value.position)*/\">";

            }

        }
        else if (((key % 2) != 0 && (reihe % 2) ==  0)||((key % 2) ==  0 && (reihe % 2) != 0)) {
            if(value == null){
                html += "<input type=\"button\" class=\"blackField\" value=\"" + " " + "\" class=\"blackField\" name=\"" + key + "\" onclick=\"/*planmove(value.position)*/\">";

            } else{
                html += "<input type=\"button\" class=\"blackField\" value=\"" + value.char + "\" class=\"blackField\" name=\"" + key + "\" onclick=\"/*planmove(value.position)*/\">";
            }
        }
        else {
            if(value == null){
                html += "<input type=\"button\" class=\"whiteField\" value=\"" + " " + "\" class=\"blackField\" name=\"" + key + "\" onclick=\"/*planmove(value.position)*/\">";

            } else{
                html += "<input type=\"button\" class=\"whiteField\" value=\"" + value.char + "\" class=\"blackField\" name=\"" + key + "\" onclick=\"/*planmove(value.position)*/\">";
            }
        }

        if ((parseInt(key) + 1) % 8 ==  0) {
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
    var GAMEID:number = (document.getElementById("hash") as HTMLInputElement).value;
    document.getElementById("popUp").style.display = "none";
    console.log("your hash is: " + GAMEID);
    const url: string = `http://localhost:8080/pmz-1.0-SNAPSHOT/api/chess/board/` +GAMEID;
    fetch(url)
        .then(res => {
            if (!res.ok) {
                throw new Error("GET REQUEST FAILED");
            }
            return res.json();
        })
        .then(gameBoardJSON => {
            displayGameBoard(gameBoardJSON, []);
        })
        .catch(err => {
            console.log(err);
        })
}
