
package com.heartwoodlabs.dashboard.rs;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
public class CORSRequestFilter implements ContainerRequestFilter {
 @Override
 public void filter(ContainerRequestContext requestContext) throws IOException {
 if ("OPTIONS".equalsIgnoreCase(requestContext.getMethod())) {
 requestContext.abortWith(Response.ok().build());
 }
 }
}

