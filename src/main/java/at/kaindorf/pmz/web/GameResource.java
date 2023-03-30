package at.kaindorf.pmz.web;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Path("/hello-world")
public class GameResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStartingField() {
        return null;
    }
}