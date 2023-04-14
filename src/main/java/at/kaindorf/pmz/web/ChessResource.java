package at.kaindorf.pmz.web;

import at.kaindorf.pmz.bl.PMZController;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Path("/chess")
public class ChessResource {
    // in zukunft list, map ?
    private PMZController controller = new PMZController();

    @GET
    @Path("/start")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response startGame() {
        Integer id = 1234;
        controller.startGame(id);
        return Response.accepted(id).build();
    }

    @GET
    @Path("/board/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBoard(@PathParam("id") Integer id) {
        return Response.ok(controller.getBoard(id)).build();
    }

    @GET
    @Path("/moves/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPossibleMoves(@PathParam("id") Integer id, @QueryParam("position") Integer position) {
        return Response.ok(controller.getPossibleMoves(id, position)).build();
    }
}