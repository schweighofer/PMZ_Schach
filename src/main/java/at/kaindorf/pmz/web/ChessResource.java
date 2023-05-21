package at.kaindorf.pmz.web;

import at.kaindorf.pmz.bl.PMZController;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


/**
 * @Author Marcus Schweighofer
 * Created on 24.03.2023.
 * Class: ChessResource.java
 */

@Path("/chess")
public class ChessResource {
    @GET
    @Path("/start")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response startGame() {
        return Response.accepted(PMZController.getInstance().startGame()).build();
    }

    @GET
    @Path("/board/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBoard(@PathParam("id") Integer id) {
        return Response.ok(PMZController.getInstance().getBoard(id)).build();
    }

    @GET
    @Path("/moves/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPossibleMoves(@PathParam("id") Integer id, @QueryParam("position") Integer position) {
        return Response.ok(PMZController.getInstance().getPossibleMoves(id, position)).build();
    }

    @POST
    @Path("/move/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response makeMove(@PathParam("id") Integer id, @QueryParam("target") Integer target) {
        if (PMZController.getInstance().makeMove(id, target)) {
            return Response.accepted(PMZController.getInstance().getBoard(id)).build();
        }
        return Response.notModified().build();
    }

    @GET
    @Path("/turn/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getWhoIsTurn(@PathParam("id") Integer id) {
        return Response.ok((id % 2 == 1) == PMZController.getInstance().hasBlackTurn(id)).build();
    }
}