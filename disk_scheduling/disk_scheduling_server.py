import socket               # Import socket module


def fcfs(requests, head_start):
    cost = 0
    for i in requests:
        cost += abs(i - head_start)
    return [cost, requests]

def get_job(requests, head_start):
    cost = requests[0]
    print type(requests[0])
    element = abs(requests[0] - head_start)
    for i in requests:
        if cost > abs(i - head_start):
            element = i
            cost = abs(i - head_start)
    requests.remove(element)
    return [element, cost, requests]



def sstf(req, head_start):
    order = []
    noOfCylinders = 0
    for i in range(len(requests)):
        [head_start, cost, req] = get_job(req, head_start)
        noOfCylinders += cost
        order.append(head_start)
    return [noOfCylinders, order]


def scan_left(requests, ind):
    cost = 0
    curr_value = requests[ind]
    for i in range(ind - 1, -1, -1):
        cost += abs(requests[i] - curr_value)
        curr_value = requests[i]
    order = requests[0:ind]
    order.sort(reverse = True)
    return [order, cost]


def scan_right(requests, ind):
    cost = 0
    curr_value = requests[ind]
    for i in range(ind + 1, len(requests)):
        cost += abs(requests[i] - curr_value)
        curr_value = requests[i]
    order = requests[ind + 1:]
    return [order, cost]




def scan(requests, head_start):
    requests.append(head_start)
    requests.sort()
    [l1, cost1] = scan_left(requests, requests.index(head_start))
    [l2, cost2]  = scan_right(requests, requests.index(head_start))
    l1.extend(l2)
    return [cost1 + cost2 , l1]


def scan_right_rev(requests, ind,):
    cost = 0
    curr_value = requests[0]
    for i in range(len(requests) - 1, ind, -1):
        cost += abs(requests[i] - curr_value)
        curr_value = requests[i]
    order = requests[ind + 1:]
    order.sort(reverse = True)
    return [order, cost]



def cscan(requests, head_start):
    requests.append(head_start)
    requests.sort()
    [l1, cost1] = scan_left(requests, requests.index(head_start))
    [l2, cost2] = scan_right_rev(requests, requests.index(head_start))
    l1.extend(l2)
    return [cost1 + cost2, l1]


def getList(re):
	re = re[1:len(re) - 1]
	li= []
	print re
	ans = ""
	for i in re:
		if i == ',':
			if (ans != ""):
				li.append(int(ans))
				ans = ""
		else:
			ans += i	
	return li



s = socket.socket()         # Create a socket object
host = socket.gethostname() # Get local machine name
port = 7838                # Reserve a port for your service.
s.bind((host, port))        # Bind to the port

s.listen(5)                 # Now wait for client connection.
while True:
   c, addr = s.accept()     # Establish connection with client.
   print 'Got connection from', addr
   re =  c.recv(1004)
   hs = (c.recv(1000)).strip()
   ch = (c.recv(1)).strip()
   choice = int(ch)
   head_start = int(hs)
   requests = getList(re)
   print(requests)
   if choice == 1:
        [cost, order] = fcfs(requests[:], head_start)
   elif choice == 2:
        [cost, order] = sstf(requests[:], head_start)
   elif choice == 3:
        [cost, order] = scan(requests[:], head_start)
   else:
        [cost, order] = cscan(requests[:], head_start)
   c.send(str(cost))
   c.send(str(order))
   print(str(cost))
   print(str(order))   
   c.close() 
