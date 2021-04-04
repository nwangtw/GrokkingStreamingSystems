package com.stream_work.ch02.engine;

import io.javalin.http.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.javalin.Javalin;

public class WebServer {

  class Node extends HashMap<String, String> {
    public Node(String name) {
      super();

      this.put("name", name);
    }
  }

  class Edge extends HashMap<String, String> {
    public Edge(Node from, Node to) {
        super();

        this.put("from", from.get("name"));
        this.put("to", to.get("name"));
    }
  }

  private final String jobName;
  private final List<Node> sources = new ArrayList<Node>();
  private final List<Node> operators = new ArrayList<Node>();
  private final List<Edge> edges = new ArrayList<Edge>();

  public WebServer(final String jobName, final List<Connection> connectionList) {
    this.jobName = jobName;
    Map<Node, Integer> incomingCountMap = new HashMap<Node, Integer>();
    for (Connection connection: connectionList) {
      Node from = new Node(connection.from.getComponent().getName());
      Node to = new Node(connection.to.getComponent().getName());

      Integer count = incomingCountMap.getOrDefault(to, 0);
      incomingCountMap.put(from, count);
      count = incomingCountMap.getOrDefault(to, 0);
      incomingCountMap.put(to, count + 1);

      edges.add(new Edge(from, to));
    }
    for (Node node: incomingCountMap.keySet()) {
      if (incomingCountMap.get(node) == 0) {
        sources.add(node);
      } else {
        operators.add(node);
      }
    }
  }

  public void start() {
    Javalin app = Javalin.create(config -> {
        config.addSinglePageRoot("/", "/index.html");
        config.addStaticFiles("/public");
        config.showJavalinBanner = false;
      })
      .start(7000);

    app.get("/plan.json", ctx -> planHandler(ctx));
  }

  private void planHandler(Context ctx) {
    Map<String, Object> plan = new HashMap<String, Object>();
    plan.put("name", jobName);
    plan.put("sources", sources);
    plan.put("operators", operators);
    plan.put("edges", edges);

    ctx.json(plan);
  }
}