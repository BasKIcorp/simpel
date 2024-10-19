package org.simpel.pumpingUnits.service.installationService;

import org.simpel.pumpingUnits.model.enums.TypeInstallations;
import org.simpel.pumpingUnits.model.enums.subtypes.SubtypeForGm;
import org.simpel.pumpingUnits.model.installation.GMInstallation;
import org.simpel.pumpingUnits.repository.GMRepository;
import org.simpel.pumpingUnits.repository.HozPitRepository;
import org.simpel.pumpingUnits.repository.PnsAFEIJPRepository;
import org.simpel.pumpingUnits.repository.PnsERWRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
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
    private final GMRepository gmRepository;
    private final HozPitRepository hozPitRepository;
    private final PnsERWRepository pnsERWRepository;
    private final PnsAFEIJPRepository pnsAFEIJPRepository;

    public InstallationServiceFactory(GMService gmService, HozPitService hozPitService, PNSServiceAFEIJP pnsServiceAFEIJP, PNSServiceERW pnsServiceERW, GMRepository gmRepository, HozPitRepository hozPitRepository, PnsERWRepository pnsERWRepository, PnsAFEIJPRepository pnsAFEIJPRepository) {
        this.gmService = gmService;
        this.hozPitService = hozPitService;
        this.pnsServiceAFEIJP = pnsServiceAFEIJP;
        this.pnsServiceERW = pnsServiceERW;
        this.gmRepository = gmRepository;
        this.hozPitRepository = hozPitRepository;
        this.pnsERWRepository = pnsERWRepository;
        this.pnsAFEIJPRepository = pnsAFEIJPRepository;
    }

    public InstallationServiceInterface<?> getInstallationService(TypeInstallations typeInstallations, String subtype) {
        switch (typeInstallations) {
            case GM:
                return gmService;
            case HOZPIT:
                return hozPitService;
            case PNS:
                if (subtype.equals("ERW_SYSTEM") || subtype.equals("AFEIJP2")) {
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
    public JpaRepository<?, Long> getRepo(TypeInstallations typeInstallations, String subtype) {
        switch (typeInstallations) {
            case GM:
                return gmRepository;
            case HOZPIT:
                return hozPitRepository;
            case PNS:
                if (subtype.equals("ERW_SYSTEM") || subtype.equals("AFEIJP2")) {
                    return pnsERWRepository;
                }
                else if (subtype.equals("AFEIJP")) {
                    return pnsAFEIJPRepository;
                }
                else {
                    throw new IllegalArgumentException("Not have supported subtype: " + subtype);
                }
            default:
                throw new IllegalArgumentException("Not have supported type");
        }
    }
    }
