package com.company.rtsystem.ui.banner;

import com.company.rtsystem.entity.BannerStyle;
import com.company.rtsystem.service.banner.BannerService;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import io.jmix.core.session.SessionData;
import io.jmix.flowui.kit.component.button.JmixButton;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Component
public class BannerManager {

    private static final Map<BannerStyle, String> STYLE_TO_CSS_MAPPING;

    static {
        STYLE_TO_CSS_MAPPING = new HashMap<>();
        STYLE_TO_CSS_MAPPING.put(BannerStyle.INFO, "banner-info");
        STYLE_TO_CSS_MAPPING.put(BannerStyle.WARN, "banner-warning");
    }

    private final BannerService bannerService;
    private final ObjectProvider<SessionData> sessionDataProvider;

    public BannerManager(BannerService bannerService, ObjectProvider<SessionData> sessionDataProvider) {
        this.bannerService = bannerService;
        this.sessionDataProvider = sessionDataProvider;
    }

//    private Div createBannerDiv(SitewideBanner sitewideBanner) {
//        Div div = new Div();
//        BannerStyle style = sitewideBanner.getStyle() != null ? sitewideBanner.getStyle() : BannerStyle.INFO;
//        String bannerClass = STYLE_TO_CSS_MAPPING.get(style);
//        Html html = new Html("<span class='span-with-break'>" + sitewideBanner.getContent() + "</span>");
//        div.addClassNames(bannerClass);
//        div.add(html);
//
//        return div;
//    }

//    private HorizontalLayout bannerWithDismiss(SitewideBanner sitewideBanner, int zIndex, SessionData sessionData) {
//        // create individual banner
//        Div bannerDiv = createBannerDiv(sitewideBanner);
//
//        // create individual dismiss button for each banner
//        JmixButton dismissButton = new JmixButton();
//        dismissButton.setText("Dismiss");
//        dismissButton.setClassName("dismiss-banner-btn ");
//        dismissButton.addClickListener(buttonClickEvent -> {
//            bannerDiv.setVisible(false);
//            dismissButton.setVisible(false);
//            sessionData.setAttribute("dismiss" + zIndex, true);
//        });
//
//        bannerDiv.getElement().getStyle().set("z-index", String.valueOf(zIndex));
//        dismissButton.getElement().getStyle().set("z-index", String.valueOf(zIndex));
//        HorizontalLayout bannerAndDismiss = new HorizontalLayout();
//        bannerAndDismiss.add(bannerDiv, dismissButton);
//        return bannerAndDismiss;
//    }

    public void show(Div bannerDiv, HorizontalLayout bannerHBox, SessionData sessionData) {
        bannerDiv.removeAll();

        if (sessionData.getAttribute("dismissed") != null) {
            bannerHBox.setVisible(false);
            return;
        }
//        int zIndex = bannerService.getBanners().size();
        boolean allDismissed = true;
//        for (SitewideBanner banner : bannerService.getBanners()) {
//            if (sessionData.getAttribute("dismiss" + zIndex) == null) {
//                bannerDiv.add(bannerWithDismiss(banner, zIndex, sessionData));
//                allDismissed = false;
//            }
//            zIndex--;
//        }
        if (allDismissed) {
            bannerHBox.setVisible(false);
        }
    }
}
