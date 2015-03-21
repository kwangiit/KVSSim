KVSSim
======

Simulation of key-value stores 

To run the simulations

1. Firstly, you need to download the PeerSim simulator package
2. compile
	javac -cp jep-2.3.0.jar:djep-1.0.0.jar:peersim-1.0.5.jar:peersim-doclet.jar program_source_dir/*.java
3. Run
	java -cp jep-2.3.0.jar:djep-1.0.0.jar:peersim-1.0.5.jar:peersim-doclet.jar:program_source_dir/ peersim.Simulator configureFile
