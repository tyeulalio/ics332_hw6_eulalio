README

=============================================================================
SchedulingAlgorithmNonPreemptiveFCFS:
  renderer(arr=10, cpu=400, io=40):	avg. ready time = 9924.53, cpu time = 38400, #preemptions = 0
  matrix(arr=20, cpu=10000, io=1):	avg. ready time = 467.71, cpu time = 950000, #preemptions = 0
  eclipse(arr=50, cpu=8, io=100):	avg. ready time = 10361.63, cpu time = 760, #preemptions = 0
  web_browser(arr=10, cpu=50, io=100):	avg. ready time = 10215.68, cpu time = 4800, #preemptions = 0
  emacs_editor(arr=20, cpu=8, io=2):	avg. ready time = 10458.8, cpu time = 760, #preemptions = 0
  bash_session(arr=30, cpu=3, io=6):	avg. ready time = 10459.77, cpu time = 285, #preemptions = 0
Number of context-switches/dispatches: 573
=============================================================================

=============================================================================
SchedulingAlgorithmPreemptiveShortestCPUBurstFirst:
  renderer(arr=10, cpu=400, io=40):	avg. ready time = 65.24, cpu time = 8584, #preemptions = 9487
  matrix(arr=20, cpu=10000, io=1):	avg. ready time = 11657, cpu time = 18, #preemptions = 51
  eclipse(arr=50, cpu=8, io=100):	avg. ready time = 6.75, cpu time = 65840, #preemptions = 12345
  web_browser(arr=10, cpu=50, io=100):	avg. ready time = 15.66, cpu time = 65482, #preemptions = 56305
  emacs_editor(arr=20, cpu=8, io=2):	avg. ready time = 2.52, cpu time = 526738, #preemptions = 69957
  bash_session(arr=30, cpu=3, io=6):	avg. ready time = 0, cpu time = 333324, #preemptions = 0
Number of context-switches/dispatches: 334656
=============================================================================

=============================================================================
SchedulingAlgorithmRoundRobin:
  renderer(arr=10, cpu=400, io=40):	avg. ready time = 29.55, cpu time = 246610, #preemptions = 0
  matrix(arr=20, cpu=10000, io=1):	avg. ready time = 29.42, cpu time = 253640, #preemptions = 0
  eclipse(arr=50, cpu=8, io=100):	avg. ready time = 30.46, cpu time = 57776, #preemptions = 0
  web_browser(arr=10, cpu=50, io=100):	avg. ready time = 31.37, cpu time = 162950, #preemptions = 0
  emacs_editor(arr=20, cpu=8, io=2):	avg. ready time = 29.42, cpu time = 202912, #preemptions = 0
  bash_session(arr=30, cpu=3, io=6):	avg. ready time = 30.42, cpu time = 76092, #preemptions = 0
Number of context-switches/dispatches: 124271
=============================================================================

Which algorithm leads to the most “fair” behavior in terms of ready time?
Round Robin leads to the most "fair" behavior since the average ready times for the six processes are generally around the same value. This makes sense because Round Robin doesn't take into account any other factors besides the time slice that each of the processes are set to run for.

Of SchedulingAlgorithmPreemptiveShortestCPUBurstFirst and SchedulingAlgorithmRoundRobin, which one leads to the most context switches? Why do you think that is?
SchedulingAlgorithmPreemptiveShortestCPUBurstFirst leads to the most context switches, by far. It has just over 300,000 context switches, while SchedulingAlgorithmRoundRobin has just over 100,000 switches. This is probably due to the fact that SchedulingAlgorithmPreemptiveShortestCPUBurstFirst is a preemptive algorithm and so forces context switches to happen whenever there is a process with a shorter burst time than the one that is running. On the other hand, SchedulingAlgorithmRoundRobin only has a context switch at fixed intervals of 10.

With SchedulingAlgorithmPreemptiveShortestCPUBurstFirst, how come the process called matrix is preempted much less often than, for instance, the process called renderer?
The process matrix is preempted much less often than renderer and the other processes because it barely gets any cpu time. It has a cpu burst of 10,000, which is far greater than any of the other processes' burt times. As a 	result, th eonly way that matrix will ever get cpu time, is if it is the only ready process at the moment. However, as soon as any other process is ready, it'll be preempted. If the process matrix is rarely running, then there 	aren't many chances that it can be preempted. Thus, matrix has a very low preemption value.

With SchedulingAlgorithmNonPreemptiveFCFS, the matrix process has a particular ready time (should be between 450 and 500). Could you have estimated that wait time without running the simulation? How? How close is the estimate to the simulated value?
Yes, it would be possible to estimate the runtime without running the simulation. If you put the processes in order by arrival time you get the following:

arr	process		cpu	io
10	renderer	400	40
10	web_browser	50	100
20	emacs		8	2
20	matrix		10,000	1
30	bash		3	6
50	eclipse		8	100

Emacs could be dispatched before or after matrix since they have the same arrival time. If you look at the io bursts, you can se that all processes starting before matrix will be ready before matrix completes since all the io values are less than matrix's cpu value. This means they will always run before matrix runs. So we know that matrix waits for these three processes to complete, after it has reached the ready state. 

We need to know whether bash and eclipse will always stay in order, as well, or else they might mess up any chance of having a predictable outcome. So the question is whether bach and eclipse will always execute after matrix. The answer is yes because matrix's io time is 1, which is shorter than bash's cpu time. This means that matrix will always be added to the queue right behind the processes that executed prior to matrix. Then, bash and eclipse will execute and be added to the end of the queue and the order will remain constant. 

The only variable element that we have is the order between emacs and matrix, as a result of their arrival times being the same. But emacs has such a short cpu time that this doesn't change the answer by much. We know that matrix has to wait for renderer, web_browser, and possibly emacs to execute before it gets to execute. So we can add their cpu times up: 400 + 50 + 8 = 458. Emacs might execute after matrix through so we can subtract its cpu time to get 450. This gives us our first range of 450-458, which is the time that matrix must wait in the ready state on average cycles of the process queue.

We apply the same logic to processes that occur after matrix because this is a cycle and so matrix has to wait for these to execute as well. So we add the runtimes of bash, eclipse, and possibly emacs to get a range of 12-20.

Now, we can add these ranges together to get our minimum and maximum estimates for the range and we get 462-478. This tells us that matrix will have to wait, on average 462-478 in the ready state while the other processes are executing. The average time produced during my test run was 467.71, which falls nicely into the estimated range.
