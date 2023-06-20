package at.kaindorf.pmz.web;

import at.kaindorf.pmz.bl.PMZController;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;


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
    public Response startGame(@QueryParam("time") int time) {
        return Response.accepted(PMZController.getInstance().startGame(time)).build();
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
    public Response makeMove(@PathParam("id") Integer id, @QueryParam("target") Integer target, @QueryParam("lastPiece") Integer lastPiece) {
        if (PMZController.getInstance().makeMove(id, target, lastPiece)) {
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

    @GET
    @Path("/isChess/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response isChess(@PathParam("id") Integer id) {
        return Response.ok(PMZController.getInstance().isChess(id, id % 2 == 0)).build();
    }

    @GET
    @Path("/isCheckmate/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response isCheckmate(@PathParam("id") Integer id) {
        return Response.ok(PMZController.getInstance().isCheckmate(id, id % 2 == 1)).build();
    }

    @GET
    @Path("/ended/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response hasEnded(@PathParam("id") Integer id) {
        return Response.ok(PMZController.getInstance().hasEnded(id)).build();
    }

    @GET
    @Path("/getEnemyName/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEnemyName(@PathParam("id") Integer id) {
        return Response.ok("\"" +PMZController.getInstance().getName((id % 2 == 0 ? id + 1 : id - 1))+"\"", MediaType.APPLICATION_JSON).build();
    }


    @GET
    @Path("/setName/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response setName(@PathParam("id") Integer id, @QueryParam("name") String name) {
        PMZController.getInstance().setName(id, name);
        return Response.ok("\""+ name+"\"", MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/getOwnTime/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOwnTime(@PathParam("id") Integer id) {
        return Response.ok(PMZController.getInstance().getTime(id)).build();
    }
    @GET
    @Path("/getEnemyTime/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEnemyTime(@PathParam("id") Integer id) {
        return Response.ok(PMZController.getInstance().getTime((id % 2 == 0 ? id + 1 : id - 1))).build();
    }


    @GET
    @Path("/getStats/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStats(@PathParam("id") Integer id) {
        return Response.ok(PMZController.getInstance().getStats(id)).build();
    }

    @GET
    @Path("/hasEnemyJoined/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response hasEnemyJoined(@PathParam("id") Integer id) {
        return Response.ok(PMZController.getInstance().getName((id % 2 == 0 ? id + 1 : id - 1)) != null).build();
    }
}