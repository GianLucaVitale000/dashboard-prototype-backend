package com.heartwoodlabs.dashboard.rs;

import com.heartwoodlabs.dashboard.dao.BaseDao;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api")
public class RestApplication extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = new HashSet<>();
		classes.add(ChartResource.class);
		classes.add(ProdottoResource.class);
		classes.add(JacksonConfiguration.class);
		classes.add(HeadersFilter.class);
		classes.add(CORSRequestFilter.class);
		classes.add(InvalidDataExceptionMapper.class);

		BaseDao.initFactory("DefaultPersistenceUnit");

		return classes;
	}

	@Override
	public Set<Object> getSingletons() {
		Set<Object> singletons = new HashSet<>();
		singletons.add(new HeadersFilter());
		return singletons;
	}
}
