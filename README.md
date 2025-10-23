💡 Project Title:

Queue Management System (Client–Server Model)

🧩 Overview:

A Java-based network project using Swing for GUI and Sockets for communication.

Allows multiple clients to connect to one central server.

Manages a ticket-based queue system in real-time.

Clients can view and control ticket numbers (Next / Reset).

⚙️ Technologies Used:

Language: Java

GUI Framework: Swing

Networking: Socket Programming (TCP)

I/O Classes: BufferedReader, PrintWriter

Concurrency: Multi-threading for multiple clients

🖥️ Modules:
1. QueueServer.java

Acts as the main server handling all client connections.

Runs on port 5000.

Maintains a variable currentTicket to track the ticket number.

Listens for client requests using a ServerSocket.

Handles commands:

"NEXT" → increments ticket number and broadcasts to all clients.

"RESET" → resets the ticket number to ---.

Uses threads to manage multiple clients simultaneously.

Sends real-time updates to all connected clients.

2. QueueClient.java

A GUI-based client application for interacting with the server.

Connects to the server using the IP address provided by the user.

Displays:

Current Ticket Number

Counter Info

Serving History

Contains buttons:

Next Ticket: Sends "NEXT" command to the server.

Reset Queue: Sends "RESET" command to the server.

Exit: Closes the client application.

Uses a gradient background and stylized buttons for a modern look.

Continuously listens for updates from the server and refreshes the display in real time.

🔄 Working Principle:

Start the server first → waits for client connections.

Start one or more clients → connect to the server via IP.

When any client presses:

Next Ticket: All clients see the updated ticket number.

Reset Queue: All clients’ displays reset to initial state.

Server ensures synchronization between all clients.

🎯 Key Features:

Real-time ticket update across multiple clients.

Simple and effective queue control system.

Multi-client support through threading.

GUI with an attractive design and clear layout.

Error handling for server connection failure.

Log display of serving history.

🧠 Applications / Use Cases:

Hospitals – patient token system.

Banks – counter ticket management.

Offices – visitor or customer queue handling.

Service centers – managing sequential service orders.

📁 Project Highlights:

Uses Java Swing for modern UI design.

Employs Socket programming for client-server communication.

Demonstrates real-time data synchronization.

Fully implemented using core Java (no external libraries).

Easy to run and test on any system with Java installed.
