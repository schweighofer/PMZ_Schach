package at.kaindorf.pmz.web;

import at.kaindorf.pmz.bl.Game;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Path("/chess")
public class ChessResource {

    private Game game;

    @GET
    @Path("/init")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response initSession() {
        String session = "12345";
        return Response.accepted(session).build();
    }

    @GET
    @Path("/start")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStartingField() {
        game = new Game();
        return Response.accepted(game.getBoard()).build();
    }
}