package net.den3.hackathonWeb;

import io.javalin.Javalin;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Main {
    private static int getHerokuAssignedPort() {
        String herokuPort = System.getenv("PORT");
        if (herokuPort != null) {
            return Integer.parseInt(herokuPort);
        }
        return 7000;
    }

    public static void main(String[] args) {
        Javalin app = Javalin.create().start(getHerokuAssignedPort());

        app.get("/", ctx -> {
            Map<String, Object> model = new HashMap<>();
            model.put("message", "Hello World");
            model.put("now", LocalDateTime.now());
            ctx.render("/WEB-INF/templates/test.html", model);
        });
    }
}
