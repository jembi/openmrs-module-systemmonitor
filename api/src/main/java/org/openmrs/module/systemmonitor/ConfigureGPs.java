package org.openmrs.module.systemmonitor;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.openmrs.GlobalProperty;
import org.openmrs.api.context.Context;
import org.openmrs.module.ModuleUtil;

/**
 * Reads and persists configurations which are supported as global properties,
 * should only be used at: configurations controller layer
 */
public class ConfigureGPs {
	private GlobalProperty viralLoadConceptIdGp;

	private GlobalProperty reasonForExistingCareConceptIdGp;

	private GlobalProperty hivProgramIdGp;

	private GlobalProperty siteIdGp;

	private GlobalProperty dhisAPIRootUrlGp;

	private GlobalProperty dhisUsernameGp;

	private GlobalProperty dhisPasswordGp;

	private GlobalProperty openingHourGp;

	private GlobalProperty openingDaysGp;

	private GlobalProperty closingHourGp;

	private GlobalProperty schedulerUsernameGp;

	private GlobalProperty schedulerPasswordGp;

	private boolean mustSetScheduler;

	public ConfigureGPs() {
		initialiseGPsWithTheirCurrentValues();
	}

	private void initialiseGPsWithTheirCurrentValues() {
		viralLoadConceptIdGp = Context.getAdministrationService()
				.getGlobalPropertyObject(ConfigurableGlobalProperties.VIRALLOAD_CONCEPTID);
		reasonForExistingCareConceptIdGp = Context.getAdministrationService()
				.getGlobalPropertyObject(ConfigurableGlobalProperties.CAREEXITREASON_CONCEPTID);
		hivProgramIdGp = Context.getAdministrationService()
				.getGlobalPropertyObject(ConfigurableGlobalProperties.HIV_PROGRAMID);
		siteIdGp = Context.getAdministrationService().getGlobalPropertyObject(ConfigurableGlobalProperties.SITE_ID);
		dhisAPIRootUrlGp = Context.getAdministrationService()
				.getGlobalPropertyObject(ConfigurableGlobalProperties.DHISAPI_URL);
		dhisUsernameGp = Context.getAdministrationService()
				.getGlobalPropertyObject(ConfigurableGlobalProperties.DHIS_USERNAME);
		dhisPasswordGp = Context.getAdministrationService()
				.getGlobalPropertyObject(ConfigurableGlobalProperties.DHIS_PASSWORD);
		openingHourGp = Context.getAdministrationService()
				.getGlobalPropertyObject(ConfigurableGlobalProperties.CONFIGS_OPENNINGHOUR);
		openingDaysGp = Context.getAdministrationService()
				.getGlobalPropertyObject(ConfigurableGlobalProperties.CONFIGS_OPENNINGDAYS);
		closingHourGp = Context.getAdministrationService()
				.getGlobalPropertyObject(ConfigurableGlobalProperties.CONFIGS_CLOSINGHOUR);
		if (mustConfigureSchedulerAccount()) {
			schedulerUsernameGp = Context.getAdministrationService().getGlobalPropertyObject("scheduler.username");
			schedulerPasswordGp = Context.getAdministrationService().getGlobalPropertyObject("scheduler.password");
		}
	}

	private boolean mustConfigureSchedulerAccount() {
		return !ModuleUtil.matchRequiredVersions(SystemMonitorConstants.OPENMRS_VERSION, "1.9");
	}

	public void updateAndPersistConfigurableGPs(HttpServletRequest request) {
		List<GlobalProperty> gps = new ArrayList<GlobalProperty>();

		if (mustConfigureSchedulerAccount()) {
			schedulerUsernameGp.setPropertyValue(request.getParameter("schedulerUsername"));
			schedulerPasswordGp.setPropertyValue(request.getParameter("schedulerPassword"));
		}
		viralLoadConceptIdGp.setPropertyValue(request.getParameter("viralLoadConceptId"));
		reasonForExistingCareConceptIdGp.setPropertyValue(request.getParameter("reasonForExistingCareConceptId"));
		hivProgramIdGp.setPropertyValue(request.getParameter("hivProgramId"));
		siteIdGp.setPropertyValue(request.getParameter("siteId"));
		dhisAPIRootUrlGp.setPropertyValue(request.getParameter("dhisAPIRootUrl"));
		dhisUsernameGp.setPropertyValue(request.getParameter("dhisUsername"));
		dhisPasswordGp.setPropertyValue(request.getParameter("dhisPassword"));
		openingDaysGp.setPropertyValue(request.getParameter("openingDays"));
		openingHourGp.setPropertyValue(request.getParameter("openingHour"));
		closingHourGp.setPropertyValue(request.getParameter("closingHour"));

		if (mustConfigureSchedulerAccount()) {
			gps.add(schedulerUsernameGp);
			gps.add(schedulerPasswordGp);
		}
		gps.add(viralLoadConceptIdGp);
		gps.add(reasonForExistingCareConceptIdGp);
		gps.add(hivProgramIdGp);
		gps.add(siteIdGp);
		gps.add(dhisAPIRootUrlGp);
		gps.add(dhisUsernameGp);
		gps.add(dhisPasswordGp);
		gps.add(openingDaysGp);
		gps.add(openingHourGp);
		gps.add(closingHourGp);

		Context.getAdministrationService().saveGlobalProperties(gps);
	}

	public GlobalProperty getViralLoadConceptIdGp() {
		return viralLoadConceptIdGp;
	}

	public GlobalProperty getReasonForExistingCareConceptIdGp() {
		return reasonForExistingCareConceptIdGp;
	}

	public GlobalProperty getHivProgramIdGp() {
		return hivProgramIdGp;
	}

	public GlobalProperty getSiteIdGp() {
		return siteIdGp;
	}

	public GlobalProperty getDhisAPIRootUrlGp() {
		return dhisAPIRootUrlGp;
	}

	public GlobalProperty getDhisUsernameGp() {
		return dhisUsernameGp;
	}

	public GlobalProperty getDhisPasswordGp() {
		return dhisPasswordGp;
	}

	public GlobalProperty getOpeningHourGp() {
		return openingHourGp;
	}

	public GlobalProperty getOpeningDaysGp() {
		return openingDaysGp;
	}

	public GlobalProperty getClosingHourGp() {
		return closingHourGp;
	}

	public GlobalProperty getSchedulerUsernameGp() {
		return schedulerUsernameGp;
	}

	public GlobalProperty getSchedulerPasswordGp() {
		return schedulerPasswordGp;
	}

	public boolean getMustSetScheduler() {
		return mustConfigureSchedulerAccount();
	}
}
