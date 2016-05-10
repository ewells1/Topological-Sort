import sys, os

curr = sys.argv[1]

with open(curr) as f:
  edges = [_ for _ in f]

nodes = []
for e in edges:
  edge = e.split()
  edge = [int(i) for i in edge]
  nodes.extend(edge)

n = max(nodes)
for i in nodes:
  print i
print n

with open(curr, 'w') as f:
  #f.write(edges[-1].split()[1])
  f.write(str(n))
  f.write('\n')
  f.write("0")
  f.write('\n')
  f.writelines(edges)


