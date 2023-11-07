package uo.mp.lab.util.logger;

public class BasicSimpleLogger implements SimpleLogger {

	@Override
	public void log(Exception ex) {
		System.err.println( "This is a fake implementation of a simple logger" );
		System.err.println( "This information should go to a more sofisticated log system" );
		ex.printStackTrace();}

}
