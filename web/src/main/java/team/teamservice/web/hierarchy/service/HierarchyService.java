package team.teamservice.web.hierarchy.service;

import java.util.Collection;

public interface HierarchyService
    {
        /**
         * A method to find the list of application id's under an application.
         * Entity's above Application in the hierarchy do not return a list of children.
         * @param applicationId the id of the application
         * @return a collection of applicationId's
         */
        Collection<String> getApplicationHierarchyIds(String applicationId);
    }
