package com.company.rtsystem.service.banner;

import com.company.rtsystem.service.UserRoleService;
import io.jmix.core.DataManager;
import io.jmix.core.security.CurrentAuthentication;
import org.springframework.stereotype.Service;

@Service
public class BannerService {

    private final DataManager dataManager;
    private final CurrentAuthentication currentAuthentication;
    private final UserRoleService userRoleService;

    public BannerService(DataManager dataManager, CurrentAuthentication currentAuthentication, UserRoleService userRoleService) {
        this.dataManager = dataManager;
        this.currentAuthentication = currentAuthentication;
        this.userRoleService = userRoleService;
    }

//    public List<SitewideBanner> getBanners() {
//        List<SitewideBanner> list = dataManager.load(SitewideBanner.class).query("select e from ppm_SitewideBanner e " +
//                        "where ((e.endDate is null or e.endDate >= :now) and (e.startDate is null or e.startDate <= :now)) order by e.startDate, e.endDate, e.content")
//                .parameter("now", new Date()).list();
//
//        if (userRoleService.currentUserHasRole(UserRoleService.SYSTEM_FULL_ACCESS)) return list;
//
//        List<String> currentUserRolesList = userRoleService.getPPMRolesForUsername(currentAuthentication.getAuthentication().getName());
//        return list.stream()
//                .filter(banner -> banner.getRoles().stream()
//                        .map(BannerRole::getRole)
//                        .anyMatch(currentUserRolesList::contains))
//                .collect(Collectors.toList());
//
//
//    }

//    public void addRoleToBanner(SitewideBanner banner, Set<BannerRole> roles) {
//        dataManager.load(BannerRole.class)
//                .query("select e from ppm_BannerRole e where e.sitewideBanner = :banner")
//                .parameter("banner", banner)
//                .list()
//                .forEach(dataManager::remove);
//
//        banner.setRoles(roles);
//        dataManager.save(banner);
//    }
}
