let NAME = "";
let GAMEID = 0;

const firstLoad = () => {
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

    const url: string = `http://localhost:8080/pmz-1.0-SNAPSHOT/api/chess/start`;
    fetch(url)
        .then(res => {
            if (!res.ok) {
                throw new Error("GET REQUEST FAILED - Couldn't get all trains");
            }
            return res.json();
        })
        .then(gameIDJSON => {
            GAMEID = gameIDJSON;
        })
        .catch(err => {
            document.getElementById("error").innerText = err;
        })

    document.getElementById("gameID").innerHTML ="your gameID is: "+GAMEID;

    const url: string = `http://localhost:8080/pmz-1.0-SNAPSHOT/api/chess/board/` +GAMEID;
    fetch(url)
        .then(res => {
            if (!res.ok) {
                throw new Error("GET REQUEST FAILED - Couldn't get all trains");
            }
            return res.json();
        })
        .then(gameBoardJSON => {
            displayGameBoard(gameBoardJSON);
        })
        .catch(err => {
            document.getElementById("error").innerText = err;
        })
}
displayGameBoard(gameBoardJSON);
const joinGame = () => {
    document.getElementById("startGameButton").style.display = "none";
    document.getElementById("joinGameButton").style.display = "none";

    document.getElementById("popUp").style.display = "block";
    document.getElementById("popUp").innerHTML = "<div style=\"background-color: aqua; position: absolute;  top: 56%; left: 50%;transform: translate(-50%, -50%); height: 50vh; width: 50vh;\"><p>Enter the your given hashcode: </p><input type=\"text\" id=\"hash\"/><button onclick=\"hashInputed();\">go!</button></div>"

}
const hashInputed = (): void => {
    GAMEID = (document.getElementById("hash") as HTMLInputElement).value;
    document.getElementById("popUp").style.display = "none";
    console.log("your hash is: " + GAMEID);
    //todo: getBoard request
}
