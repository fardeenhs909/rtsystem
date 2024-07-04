package com.company.rtsystem.view.mainviewtopmenu;


import com.company.rtsystem.service.UserRoleService;
import com.company.rtsystem.service.banner.BannerService;
import com.company.rtsystem.ui.banner.BannerManager;
import com.company.rtsystem.view.main.MainView;

import com.google.common.base.Strings;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.dom.Style;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.LumoUtility;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.core.session.SessionData;
import io.jmix.flowui.app.main.StandardMainView;
import io.jmix.flowui.component.UiComponentUtils;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nullable;

@Route("")
@ViewController("rtsystem_MainViewTopMenu")
@ViewDescriptor("main-view-top-menu.xml")
@AnonymousAllowed
public class MainViewTopMenu extends StandardMainView {

    @Autowired
    private CurrentAuthentication currentAuthentication;
    @Autowired
    private UserRoleService userRoleService;
    @ViewComponent
    private VerticalLayout navigationBarBox;
    private VerticalLayout contentWrapper;
    private HorizontalLayout footerLayout;
    private Div footerSpacer;
    private Component initialLayout;
    private BannerManager bannerManager;
    @Autowired
    private SessionData sessionData;
    @ViewComponent
    private HorizontalLayout bannerHBox;
    @ViewComponent
    private Div banner;
    @ViewComponent
    private H1 greetingH1;

    public MainViewTopMenu() {
        initContentWrapper();
        initFooter();
    }

    @Subscribe
    public void onReady(final ReadyEvent event) {
        boolean isAnonymous = currentAuthentication.getAuthentication().getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals(UserRoleService.ANONYMOUS_AUTHORTIY));
        if (isAnonymous) {
            navigationBarBox.setVisible(true);
        }
    }

    private void initContentWrapper() {
        contentWrapper = new VerticalLayout();
        contentWrapper.setSizeFull();
        contentWrapper.setSpacing(false);
        contentWrapper.setPadding(false);
        contentWrapper.setClassName("main-view-content-wrapper");
    }

    private void initFooter() {
        footerLayout = createFooterLayout();
        Image image = createLogo();
        footerLayout.add(image);

        footerSpacer = createFooterSpacer();
        footerLayout.add(footerSpacer);

        Div footerButtonsDiv = createFooterButtons();
        footerLayout.add(footerButtonsDiv);
    }
    @Override
    protected void updateTitle() {
        super.updateTitle();
        String viewTitle = getTitleFromOpenedView();
        UiComponentUtils.findComponent(getContent(), "viewHeaderBox").ifPresent(component -> component.setVisible(!Strings.isNullOrEmpty(viewTitle)));
    }

    @Override
    public void setInitialLayout(@Nullable Component initialLayout) {
        this.initialLayout = initialLayout;

        if (getContent().getContent() == null
                && initialLayout != null) {
            setContentWrapperContent(initialLayout);
            getContent().setContent(contentWrapper);
        }
    }
    @Nullable
    @Override
    public Component getInitialLayout() {
        return initialLayout;
    }

    private void setContentWrapperContent(@Nullable Component contentToDisplay) {
        contentWrapper.removeAll();
        if (contentToDisplay != null) {
            contentToDisplay.getStyle()
                    .setOverflow(Style.Overflow.AUTO)
                    .setFlexGrow("1");
            contentWrapper.add(contentToDisplay);
        }
        contentWrapper.add(footerLayout);
    }

    private Component contentToComponent(HasElement content) {
        return content.getElement().getComponent().orElseThrow(() ->
                new IllegalArgumentException("Content must be a Component"));
    }
    @Override
    protected String getTitleFromOpenedView() {
        return ViewControllerUtils.getPageTitle(contentWrapper.getComponentAt(0));
    }
    @Override
    public void showRouterLayoutContent(@Nullable HasElement content) {
        Component contentToDisplay = content != null
                ? contentToComponent(content)
                : getInitialLayout();

        setContentWrapperContent(contentToDisplay);
        super.showRouterLayoutContent(contentWrapper);
    }

    private Div createFooterButtons() {
        Div footerButtonsDiv = new Div();

        addFooterButton(footerButtonsDiv, "Accessibility");
        addFooterButton(footerButtonsDiv, "Contact Us");
        addFooterButton(footerButtonsDiv, "Help");
        return footerButtonsDiv;
    }

    private Button addFooterButton(Div parent, String text) {
        Button button = new Button(text);
        button.addClassNames("bottom-right-button");
        parent.add(button);
        return button;
    }

    private HorizontalLayout createFooterLayout() {
        footerLayout = new HorizontalLayout();
        footerLayout.setWidthFull();
        footerLayout.setMinHeight("3.75rem");
        footerLayout.getStyle().setFlexShrink("0");
        footerLayout.addClassNames(
                LumoUtility.Padding.Horizontal.MEDIUM,
                LumoUtility.Padding.Vertical.SMALL, "jmix-main-view-footer", "space-between"
        );
        return footerLayout;
    }

    private Image createLogo() {
        Image image = new Image("images/ppm_logo_small.png", "Program Pathways Mapper");
        image.setClassName("jmix-main-view-top-menu-logo");
        return image;
    }

    private Div createFooterSpacer() {
        Div footerSpacer = new Div();
        footerSpacer.setId("footerSpacer");
        footerSpacer.setClassName("bottom-left-footer-text");
        footerSpacer.setText("Powered by Program Pathways Mapper v 2.0.");
        return footerSpacer;
    }

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        greetingH1.setText("Welcome, " + currentAuthentication.getUser().getUsername());
//        bannerManager.show(banner, bannerHBox, sessionData);
        footerSpacer.setText("Powered by Program Pathways Mapper v 2.0.");
    }

}