# Round-Robing-Scheduler
	This is a task reminder application. 
	
	Internally the application uses a scheduler thread which does round-robin scheduling of threads.
	There are separate threads for:
		1) Comparing the earliest reminder time with the machine clock.
		2) A separate thread for GUI(so as to prevent unresponsiveness of the application).
		3) A scheduler thread which has the highest priority than all other threads.
		
	-The threads are stored in circular-linked-list.
    -The access to the linked-list is synchronized for insertion and deletion of a node.
    -
    -Reminders are stored in a file inside a queue.
    -Everytime they are retrieved(read) from a file. The top most is compared with the current time and appropriate action is taken.

    The reminders stored in the file has a queue. We convert the queue into the array and then perform the checking.
	
    After every timeslice the next thread in the circular LL is given the CPU-resource.