package com.streamwork.ch04.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.javalin.Javalin;
import io.javalin.http.Context;

public class WebServer {

  class Node extends HashMap<String, String> {
    public Node(String name, int parallelism) {
      super();

      this.put("name", name);
      this.put("parallelism", String.valueOf(parallelism));
    }
  }

  class Edge extends HashMap<String, String> {
    public Edge(Node from, Node to, String stream) {
        super();

        this.put("from", from.get("name"));
        this.put("to", to.get("name"));
        this.put("stream", stream);
        this.put("from_parallelism", from.get("parallelism"));
        this.put("to_parallelism", to.get("parallelism"));
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
      Node from = new Node(connection.from.getComponent().getName(), connection.from.getComponent().getParallelism());
      Node to = new Node(connection.to.getComponent().getName(), connection.to.getComponent().getParallelism());

      Integer count = incomingCountMap.getOrDefault(to, 0);
      incomingCountMap.put(from, count);
      count = incomingCountMap.getOrDefault(to, 0);
      incomingCountMap.put(to, count + 1);

      edges.add(new Edge(from, to, connection.channel));
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
        config.addStaticFiles("/public");
      })
      .start(7000);

    app.get("/", ctx -> indexHandler(ctx));
    app.get("/plan.json", ctx -> planHandler(ctx));
  }

  private void indexHandler(Context ctx) {
    StringBuilder graph = new StringBuilder();
    for (Edge edge : edges) {
      String from = edge.get("from");
      String to = edge.get("to");
      graph.append(String.format(
        "%s(%s x%s) --> | %s | %s(%s x%s)\n",
        from.replaceAll("\\s",""),
        from,
        edge.get("from_parallelism"),
        edge.get("stream"),
        to.replaceAll("\\s",""),
        to,
        edge.get("to_parallelism")
      ));
    }
    ctx.render("index.twig", Map.of("job", jobName, "graph", graph));
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
