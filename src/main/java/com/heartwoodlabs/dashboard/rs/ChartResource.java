package com.heartwoodlabs.dashboard.rs;

import com.heartwoodlabs.dashboard.business.ChartManager;
import com.heartwoodlabs.dashboard.dto.ChartDto;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("/charts")
public class ChartResource {

	@GET
	@Path("/prodotti/{limit}")
	public Response elencoProdotti(@PathParam("limit") int limit) {
		ChartDto chart = ChartManager.elencoProdotti(limit);
		return Utility.buildOkResponse(chart);
	}

	@GET
	@Path("/produzione/{numMonths}")
	public Response produzione(@PathParam("numMonths") int numMonths) {
		ChartDto chart = ChartManager.produzione(numMonths);
		return Utility.buildOkResponse(chart);
	}

	@GET
	@Path("/energiaElettrica/{numMonths}")
	public Response energiaElettrica(@PathParam("numMonths") int numMonths) {
		ChartDto chart = ChartManager.energiaElettrica(numMonths);
		return Utility.buildOkResponse(chart);
	}

	@GET
	@Path("/fatturato/{numMonths}")
	public Response fatturato(@PathParam("numMonths") int numMonths) {
		ChartDto chart = ChartManager.fatturato(numMonths);
		return Utility.buildOkResponse(chart);
	}

	@GET
	@Path("/consumoIdrico/{numMonths}")
	public Response consumoIdrico(@PathParam("numMonths") int numMonths) {
		ChartDto chart = ChartManager.consumoIdrico(numMonths);
		return Utility.buildOkResponse(chart);
	}

	@GET
	@Path("/rifiuti/{numMonths}")
	public Response rifiuti(@PathParam("numMonths") int numMonths) {
		ChartDto chart = ChartManager.rifiuti(numMonths);
		return Utility.buildOkResponse(chart);
	}

	@GET
	@Path("/test")
	public Response test() {
		return Utility.buildOkResponse(true);
	}
}
