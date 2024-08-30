package org.simpel.pumpingUnits.service.installationService;

import org.simpel.pumpingUnits.model.enums.TypeInstallations;
import org.simpel.pumpingUnits.model.enums.subtypes.SubtypeForGm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class InstallationServiceFactory {
    private final GMService gmService;
    private final HozPitService hozPitService;
    private final PNSServiceAFEIJP pnsServiceAFEIJP;
    private final PNSServiceERW pnsServiceERW;

    public InstallationServiceFactory(GMService gmService, HozPitService hozPitService, PNSServiceAFEIJP pnsServiceAFEIJP, PNSServiceERW pnsServiceERW) {
        this.gmService = gmService;
        this.hozPitService = hozPitService;
        this.pnsServiceAFEIJP = pnsServiceAFEIJP;
        this.pnsServiceERW = pnsServiceERW;
    }

    public InstallationServiceInterface<?> getInstallationService(TypeInstallations typeInstallations, String subtype) {
        switch (typeInstallations) {
            case GM:
                return gmService;
            case HOZPIT:
                return hozPitService;
            case PNS:
                if (subtype.equals("ERW_SYSTEM")) {
                    return pnsServiceERW;
                }
                else if (subtype.equals("AFEIJP")) {
                    return pnsServiceAFEIJP;
                }
                else {
                    throw new IllegalArgumentException("Not have supported subtype: " + subtype);
                }
                default:
                    throw new IllegalArgumentException("Not have supported type");
        }
    }
}