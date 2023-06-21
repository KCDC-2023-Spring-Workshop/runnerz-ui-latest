package dev.dashaun.ui.runnerz.views.helloworld;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import dev.dashaun.ui.runnerz.views.MainLayout;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@PageTitle("Hello World")
@Route(value = "hello", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class HelloWorldView extends HorizontalLayout {

    private final TextField name;
    private final Button sayHello;

    public HelloWorldView(HelloClient helloClient) {
        name = new TextField("Your name");
        sayHello = new Button("Say hello");
        sayHello.addClickListener(e -> {
            Notification.show(helloClient.hello(name.getValue()));
        });
        sayHello.addClickShortcut(Key.ENTER);

        setMargin(true);
        setVerticalComponentAlignment(Alignment.END, name, sayHello);

        add(name, sayHello);
    }

}

@Service
class HelloClient {
    private final RestTemplate restTemplate;

    public HelloClient(RestTemplateBuilder restTemplateBuilder, @Value("${apiBase}") String runnerServiceUri) {
        this.restTemplate = restTemplateBuilder.rootUri(runnerServiceUri).build();
    }

    public String hello(String name) {
        if (name.length() > 0) {
            return restTemplate.getForObject("/api/hello?name={name}", String.class, name);
        } else {
            return restTemplate.getForObject("/api/hello", String.class);
        }
    }

}