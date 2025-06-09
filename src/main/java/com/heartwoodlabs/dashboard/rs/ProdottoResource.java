package com.heartwoodlabs.dashboard.rs;

import com.heartwoodlabs.dashboard.business.ProdottoManager;
import com.heartwoodlabs.dashboard.dto.ProdottoDto;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/prodotti")
public class ProdottoResource {

    @POST
    @Path("/")
    public Response insert(ProdottoDto dto) {
        Long id = ProdottoManager.insert(dto);
        return Utility.buildOkResponse(id);
    }
}
