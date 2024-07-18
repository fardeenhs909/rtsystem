package com.company.rtsystem.view.referencetable;

import com.company.rtsystem.entity.ReferenceTable;
import com.company.rtsystem.entity.ReferenceValue;
import com.company.rtsystem.view.mainviewtopmenu.MainViewTopMenu;
import com.company.rtsystem.view.referencevalue.ReferenceValueDetailView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.HasValueAndElement;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import io.jmix.core.Metadata;
import io.jmix.core.MetadataTools;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.flowui.DialogWindows;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.data.grid.DataGridItems;
import io.jmix.flowui.kit.action.ActionPerformedEvent;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.model.*;
import io.jmix.flowui.view.*;
import io.jmix.security.role.assignment.RoleAssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Route(value = "referenceTables", layout = MainViewTopMenu.class)
@ViewController("referenceTable.list")
@ViewDescriptor("reference-table-list-view.xml")
@LookupComponent("referenceTablesDataGrid")
@DialogMode(width = "64em")
public class ReferenceTableListView extends StandardListView<ReferenceTable> {

    @ViewComponent
    private DataContext dataContext;
    @ViewComponent
    private CollectionContainer<ReferenceTable> referenceTablesDc;
    @ViewComponent
    private InstanceContainer<ReferenceTable> referenceTableDc;
    @ViewComponent
    private InstanceLoader<ReferenceTable> referenceTableDl;
    @ViewComponent
    private VerticalLayout listLayout;
    @ViewComponent
    private FormLayout form;
    @ViewComponent
    private HorizontalLayout detailActions;
    @ViewComponent
    private CollectionLoader<ReferenceValue> referenceValuesDl;
    @ViewComponent
    private DataGrid<ReferenceTable> referenceTablesDataGrid;
    @ViewComponent
    private DataGrid<ReferenceValue> refValuesDataGrid;
    @Autowired
    private UiComponents uiComponents;
    @Autowired
    private DialogWindows dialogWindows;
    @Autowired
    private Metadata metadata;
    @ViewComponent
    private JmixButton refValueCreateBtn;

    @Subscribe("referenceTablesDataGrid.create")
    public void onReferenceTablesDataGridCreate(final ActionPerformedEvent event) {
        dataContext.clear();
        ReferenceTable entity = dataContext.create(ReferenceTable.class);
        referenceTableDc.setItem(entity);
        referenceValuesDl.setParameter("referenceTable", entity);
        referenceValuesDl.load();
        updateControls(true);
    }

    @Subscribe("referenceTablesDataGrid.edit")
    public void onReferenceTablesDataGridEdit(final ActionPerformedEvent event) {
        updateControls(true);
    }

    @Subscribe("saveBtn")
    public void onSaveButtonClick(final ClickEvent<JmixButton> event) {
        dataContext.save();
        referenceTablesDc.replaceItem(referenceTableDc.getItem());
        updateControls(false);
    }

    @Subscribe("cancelBtn")
    public void onCancelButtonClick(final ClickEvent<JmixButton> event) {
        dataContext.clear();
        referenceTableDl.load();
        updateControls(false);
    }

    @Subscribe
    public void onInit(final InitEvent event) {
        Optional.ofNullable(referenceTablesDataGrid.getItems())
                .map(DataGridItems::getItems)
                .filter(items -> !items.isEmpty())
                .ifPresent(items -> {
                    ReferenceTable firstItem = items.stream().findFirst().get();
                    referenceTablesDataGrid.select(firstItem);
                });
        addEditButtonColumn(refValuesDataGrid);

//        if (!userRoleService.currentUserHasRole("system-full-access")) {
//            tableNameField.setEnabled(false);
//            displayNameField.setEnabled(false);
//        }
    }

    @Subscribe(id = "refValueCreateBtn", subject = "clickListener")
    public void onRefValueCreateBtnClick(final ClickEvent<JmixButton> event) {
        DialogWindow<ReferenceValueDetailView> window =
                dialogWindows.view(this, ReferenceValueDetailView.class).build();
        ReferenceValue referenceValue = metadata.create(ReferenceValue.class);
        referenceValue.setReferenceTable(referenceTablesDataGrid.getSingleSelectedItem());
        window.getView().setEntityToEdit(referenceValue);

        window.addAfterCloseListener(afterCloseEvent -> {
            if (afterCloseEvent.closedWith(StandardOutcome.SAVE)) {
                referenceValuesDl.load();
            }
        });
        window.open();
    }

    @Subscribe(id = "referenceTablesDc", target = Target.DATA_CONTAINER)
    public void onReferenceTablesDcItemChange(final InstanceContainer.ItemChangeEvent<ReferenceTable> event) {
        ReferenceTable entity = event.getItem();
        dataContext.clear();
        if (entity != null) {
            refValueCreateBtn.setEnabled(true);
            referenceTableDl.setEntityId(entity.getId());
            referenceTableDl.load();
            referenceValuesDl.setParameter("referenceTable", entity);
            referenceValuesDl.load();
        } else {
            referenceTableDl.setEntityId(null);
            referenceTableDc.setItem(null);
        }
    }

    private void updateControls(boolean editing) {
        form.getChildren().forEach(component -> {
            if (component instanceof HasValueAndElement<?, ?> field) {
                field.setReadOnly(!editing);
            }
        });

        detailActions.setVisible(editing);
        listLayout.setEnabled(!editing);
    }

    public void addEditButtonColumn(DataGrid<ReferenceValue> table) {
        DataGrid.Column<ReferenceValue> activeColumn = refValuesDataGrid.addComponentColumn(value -> {
            Span span = uiComponents.create(Span.class);
            Button button = uiComponents.create(Button.class);
            button.setText("Edit");
            button.addClickListener(event -> {
                DialogWindow<ReferenceValueDetailView> window =
                        dialogWindows.view(this, ReferenceValueDetailView.class).build();
                window.getView().setEntityToEdit(value);
                window.addAfterCloseListener(afterCloseEvent -> {
                    if (afterCloseEvent.closedWith(StandardOutcome.SAVE)) {
                        referenceValuesDl.load();
                    }
                });
                window.open();
            });
            span.add(button);
            return span;
        }).setHeader("Edit").setAutoWidth(true).setSortable(true).setFlexGrow(0);
        table.setColumnPosition(activeColumn, table.getColumns().size() - 1);
    }
}