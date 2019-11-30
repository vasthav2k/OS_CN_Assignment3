import matplotlib.pyplot as plt
import socket              

def readInput():
    numberOfRequests = int(input("Enter the number of request"))
    requests = []
    for i in range(numberOfRequests):
        requests.append(int(input("Enter the next request")))
    return requests



def readSchedulingAlgorithm():
    print("Enter the scheduling algorithm you want")
    print(" 1. First Come First Serve")
    print(" 2. Shortest Seek Time First")
    print(" 3. SCAN algorithm")
    print(" 4. circular algorithm")
    choice = int(input())
    return choice

def getList(re):
        re = re[1:len(re) - 1]
        li= []
        print(re)
        ans = ""
        for i in re:
                if i == ',':
                        if (ans != ""):
                                li.append(int(ans))
                                ans = ""
                else:
                        ans += i
        return li



s = socket.socket()   
host = socket.gethostname()
port = 7838                
s.connect((host, port))
requests = readInput()
s.send(str(requests))
head_start = int(input("Enter the starting position"))
s.send(str(head_start))
choice = readSchedulingAlgorithm()
s.send(str(choice))
cost = s.recv(1024)
order = s.recv(1024)
print("The order is" + order)
order = getList(order)
x_cod = []
for i in range(len(order)):
	x_cod.append(i)
plt.plot( order, x_cod,color = 'blue',  marker = 'o', markerfacecolor = 'green',markersize = 12)
plt.show()
print( "The cost is " + str(cost) )

s.close() 
