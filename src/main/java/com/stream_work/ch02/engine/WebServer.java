package com.stream_work.ch02.engine;

import io.javalin.http.Context;

import java.util.ArrayList;
import java.util.List;

import io.javalin.Javalin;

public class WebServer {
  private final List<String> connections = new ArrayList<String>();

  public WebServer(final List<Connection> connectionList) {
    for (Connection connection: connectionList) {
        connections.add(connection.from.getComponent().getName() + ":"
            + connection.to.getComponent().getName());
    }
  }

  public void start() {
    Javalin app = Javalin.create(config -> {
        config.addSinglePageRoot("/", "/index.html");
        config.addStaticFiles("/public");
        config.showJavalinBanner = false;
      })
      .start(7000);

    app.get("/logicalplan.json", ctx -> logicalPlanHandler(ctx));
    app.get("/physicalplan.json", ctx -> physicalPlanHandler(ctx));
  }

  private void logicalPlanHandler(Context ctx) {
    ctx.json(connections);
  }

  private void physicalPlanHandler(Context ctx) {
    ctx.json("{'instance': 'test'}".replace("'", "\""));
  }
}