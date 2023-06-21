package dev.dashaun.ui.runnerz.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Run Form")
@Route(value = "run-form", layout = MainLayout.class)
@Uses(Icon.class)
class RunFormView extends Div {

    private final TextField title = new TextField("Title");
    private final DateTimePicker startedOn = new DateTimePicker("Started On");
    private final DateTimePicker completedOn = new DateTimePicker("Completed On");
    private final IntegerField miles = new IntegerField("Miles");
    private final Select<Location> location = new Select<>();
    private final Button cancel = new Button("Cancel");
    private final Button save = new Button("Save");

    private final Binder<Run> binder = new Binder<>(Run.class);

    public RunFormView(RunnerService runnerService) {
        addClassName("run-form-view");
        add(createTitle());
        add(createFormLayout());
        add(createButtonLayout());
        binder.bindInstanceFields(this);
        location.setLabel("Location");
        location.setItems(Location.values());

        clearForm();

        cancel.addClickListener(e -> clearForm());
        save.addClickListener(e -> {
            try{
                Run run = new Run();
                run.setTitle(title.getValue());
                run.setMiles(miles.getValue());
                run.setStartedOn(startedOn.getValue());
                run.setCompletedOn(completedOn.getValue());
                run.setLocation(location.getValue());

                String id = runnerService.createRun(run);
                Notification.show(binder.getBean().getClass().getSimpleName() + " details stored.");
                Notification.show("Saved: " + id);
                clearForm();
            }catch(Exception exception){
                System.err.println(exception.getMessage());
                Notification.show("Oops");
            }

        });
    }

    private void clearForm() {
        binder.setBean(new Run());
    }

    private com.vaadin.flow.component.Component createTitle() {
        return new H3("A Single Run");
    }

    private com.vaadin.flow.component.Component createFormLayout() {
        VerticalLayout formLayout = new VerticalLayout();
        formLayout.add(title, startedOn, completedOn, miles, location);
        return formLayout;
    }

    private com.vaadin.flow.component.Component createButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save);
        buttonLayout.add(cancel);
        return buttonLayout;
    }

}