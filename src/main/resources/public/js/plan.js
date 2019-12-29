// Set up zoom support
var svg = d3.select("svg"),
  inner = svg.select("g"),
  zoom = d3.zoom().on("zoom", function() {
    inner.attr("transform", d3.event.transform);
  });

var render = new dagreD3.render();
// Left-to-right layout
var g = new dagreD3.graphlib.Graph();
g.setGraph({
  nodesep: 70,
  ranksep: 50,
  rankdir: "LR",
  marginx: 20,
  marginy: 20
});
function draw(nodes, links) {
  for (var id in nodes) {
    var node = nodes[id];
    var className = "running";
    var html = "<div>";
    if ('parallelism' in node) {
      html += "<span class=parallelism>" + nodep['parallelism'] + "</span>";
    }
    html += "<span class=name>" + id + "</span>";
    html += "</div>";
    g.setNode(id, {
      labelType: "html",
      label: html,
      rx: 5,
      ry: 5,
      padding: 0,
      class: className
    });
  }
  for (var i in links) {
    g.setEdge(links[i].source, links[i].target, {
      width: 40,
      arrowheadClass: 'arrowhead',
      curve: d3.curveBasis
    });
  }

  inner.call(render, g);
  // Zoom and scale to fit
  var graphWidth = g.graph().width + 80;
  var graphHeight = g.graph().height + 40;
  var width = parseInt(svg.style("width").replace(/px/, ""));
  var height = parseInt(svg.style("height").replace(/px/, ""));
  var zoomScale = Math.min(width / graphWidth, height / graphHeight);
  var translateX = (width / 2) - ((graphWidth * zoomScale) / 2)
  var translateY = 0;
  svg.call(zoom.transform, d3.zoomIdentity.translate(translateX, translateY).scale(zoomScale));
}

d3.json("/plan.json").then(function(data) {
  var width = window.innerWidth;
  var height = window.innerHeight;

  // Show job name
  d3.select("#job-name")
    .text(data.name);

  // Build nodes and links and then render
  var nodes = {};
  for (let i in data.sources) {
    nodes[data.sources[i].name] = {};
  }
  for (let j in data.operators) {
    nodes[data.operators[j].name] = {};
  }

  var links = [];
  for (var edge in data.edges) {
    links.push({
      source: data.edges[edge].from,
      target: data.edges[edge].to
    });
  }

  // Draw plan
  draw(nodes, links);
});
