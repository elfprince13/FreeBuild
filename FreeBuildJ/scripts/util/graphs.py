def topsort(V,E):
    E = set(E) # don't want to mutate parent copy
    L = []
    outgoing,incoming = classify_edges(V,E)
    S = {v for v in V if has_no_incoming(v,E,incoming)}
    if not S: raise ValueError("Can't topsort a cyclic graph")
    else:
        while S:
            n = S.pop()
            L.append(n)
            while outgoing[n]:
                e = outgoing[n].pop()
                m = e[1]
                incoming[m].remove(e)
                E.remove(e)
                if not incoming[m]: S.add(m)
        if E: raise ValueError("Can't topsort a cyclic graph")
        else: return L
    
def has_no_incoming(v,E,cache=None):
    return set() == find_incoming(v,E,cache)

def find_incoming(v,E,cache=None):
    return find_edges(v,E,1) if cache==None else cache[v]

def find_outgoing(v,E,cache=None):
    return find_edges(v,E,0) if cache==None else cache[v]

def find_edges(v,E,endpoint=-1):
    return {e for e in E if ((v in e) if endpoint < 0 else (e[endpoint] == v))}

def classify_edges(V,E,edge_dim=2):
    caches = tuple({v : set() for v in V} for x in xrange(edge_dim))
    for e in E:
        for i,v in enumerate(e):
            caches[i][v].add(e)
    return caches

def test_topsort():
    V = {7,5,3,11,8,2,9,10}
    E = {(7,11),(7,8),
         (5,11),
         (3,8),(3,10),
         (11,2),(11,9),(11,10)}
    print topsort(V,E)
    