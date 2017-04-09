package org.yuval.resource;

import org.bson.Document;
import org.yuval.dao.ShowInstanceDao;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.yuval.utils.Parameters.*;

/**
 * Created by Yuval on 28-Mar-17.
 * This class handles the show instance related HTTP requests
 */
@Path("/show_instance")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ShowInstanceResource {

    /**
     * @param showInstanceId is a json format object to insert
     * @return  insertion status message
     */
    @POST
    public String insertShowInstance(String showInstanceId){
        //turn string into document
        Document document = Document.parse(showInstanceId);
        return new ShowInstanceDao().insertValidation(document);
    }

    /**
     * @param showInstanceId to delete
     * @return deletion status message
     */
    @DELETE
    @Path("/{showInstanceId}")
    public Response deleteShowInstance(@PathParam("showInstanceId")String showInstanceId){
        //check if show instance exists
        ShowInstanceDao showInstanceDao = new ShowInstanceDao();
        if (showInstanceDao.read(showInstanceId)==null){
            return  Response.status(Response.Status.NOT_FOUND).entity(DOES_NOT_EXIST).build();
        }
        if (showInstanceDao.isInUse(showInstanceId)){
            return Response.status(Response.Status.FORBIDDEN).entity(RESOURCE_IS_IN_USE).build();
        }
        if (showInstanceDao.drop(showInstanceId)==false){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ERROR_IN_DELETION).build();
        }
        return Response.status(Response.Status.OK).entity(RESOURCE_HAS_BEEN_DELETED).build();
    }
}
