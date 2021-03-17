package net.den3.hackathonWeb;

import io.javalin.Javalin;
import net.den3.hackathonWeb.Store.*;
import net.den3.hackathonWeb.View.LoginPage;
import net.den3.hackathonWeb.View.RegisterPage;
import net.den3.hackathonWeb.View.TopPage;
import redis.clients.jedis.Jedis;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.net.URI;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
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

    private static Jedis getConnection() {
        try {
            TrustManager bogusTrustManager = new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            };

            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, new TrustManager[]{bogusTrustManager}, new java.security.SecureRandom());

            HostnameVerifier bogusHostnameVerifier = (hostname, session) -> true;

            return new Jedis(URI.create(System.getenv("REDIS_URL")),
                    sslContext.getSocketFactory(),
                    sslContext.getDefaultSSLParameters(),
                    bogusHostnameVerifier);

        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            throw new RuntimeException("Cannot obtain Redis connection!", e);
        }
    }


    public final static ILoginStore loginStore = new LoginStore();
    public final static IUserStore userStore = new UserStore();
    public final static IPostStore logStore = new PostStore();
    public final static Jedis jedis = getConnection();

    public static void main(String[] args) {
        Javalin app = Javalin.create().start(getHerokuAssignedPort());

        new LoginPage(app);
        new TopPage(app);
        new RegisterPage(app);

        app.get("/", ctx -> {
            Map<String, Object> model = new HashMap<>();
            model.put("message", "Hello World");
            model.put("now", LocalDateTime.now());
            ctx.render("/WEB-INF/templates/test.html", model);
        });
    }
}
