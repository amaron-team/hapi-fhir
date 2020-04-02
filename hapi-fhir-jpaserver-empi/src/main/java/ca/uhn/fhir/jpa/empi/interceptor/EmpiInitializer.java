package ca.uhn.fhir.jpa.empi.interceptor;

import ca.uhn.fhir.empi.rules.config.IEmpiConfig;
import ca.uhn.fhir.interceptor.api.IInterceptorService;
import ca.uhn.fhir.jpa.api.IEmpiInterceptor;
import ca.uhn.fhir.jpa.empi.provider.EmpiProviderLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class EmpiInitializer {
	private static final Logger ourLog = LoggerFactory.getLogger(EmpiInitializer.class);
	public static final String EMPI_CONSUMER_COUNT_DEFAULT = "5";

	@Autowired
	IEmpiInterceptor myEmpiInterceptor;
	@Autowired
	IInterceptorService myInterceptorService;
	@Autowired
	EmpiProviderLoader myEmpiProviderLoader;
	@Autowired
	IEmpiConfig myEmpiConfig;

	@PostConstruct
	public void init() {
		if (!myEmpiConfig.isEnabled()) {
			return;
		}
		myInterceptorService.registerInterceptor(myEmpiInterceptor);
		myEmpiInterceptor.start();
		ourLog.info("EMPI interceptor registered");

		myEmpiProviderLoader.loadProvider();
	}
}
