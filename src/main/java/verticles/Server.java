package verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import service.UserService;

public class Server extends AbstractVerticle {

    private UserService service = new UserService();

    public void start() {

        HttpServer server = vertx.createHttpServer();
        Router router = Router.router(vertx);


        Route getHandler = router.get("/getUsers").produces("*/json").handler(routingContext -> {
            routingContext.response().setChunked(true).end(Json.encodePrettily(service.findAll()));
        });


        router.get("/getUsers/:name").produces("*/json").handler(routingContext -> {
            String name = routingContext.request().getParam("name");
            routingContext.response().setChunked(true).end(Json.encodePrettily(service.findByUsername(name)));

        });

        router.get("/").produces("*/json").handler(
                routingContext -> routingContext.response().setChunked(true).end("Hello Vertx"));

        router.post("/addUser").handler(BodyHandler.create()).handler(routingContext -> {
            HttpServerResponse response = routingContext.response();
            response.setChunked(true);
            service.create("Pablo");
            response.end("User added");
        });

        server.requestHandler(router::accept).listen(8080);
    }
}
