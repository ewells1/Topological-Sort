import os, subprocess, csv

files = os.listdir("graphgen/graphs")

writer = csv.writer(open("OUTPUT.csv", 'w'))

for f in files:
  f = "graphgen/graphs/" + f
  #print f
  p = subprocess.Popen(['java', 'Toposort', f], stdout=subprocess.PIPE)
  out = p.communicate()
  nodes, edges, dfs, khan = out[0].split()
  writer.writerow([f, nodes, edges, dfs, khan])

