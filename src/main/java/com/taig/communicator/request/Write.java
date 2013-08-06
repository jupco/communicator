package com.taig.communicator.request;

import com.taig.communicator.data.Data;
import com.taig.communicator.event.Event;
import com.taig.communicator.io.Updateable;
import com.taig.communicator.method.Method;

import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public abstract class Write<R extends Response, E extends Event<R>, T> extends Read<R, E, T>
{
	protected Data data;

	public Write( Method.Type method, URL url, Data data, E event )
	{
		super( method, url, event );
		this.data = data;
	}

	public Data getData()
	{
		return data;
	}

	@Override
	public HttpURLConnection connect() throws IOException
	{
		HttpURLConnection connection = super.connect();

		if( data != null )
		{
			data.apply( connection );
		}

		connection.setDoOutput( true );
		return connection;
	}

	@Override
	protected R talk( HttpURLConnection connection ) throws IOException
	{
		send( connection );
		return super.talk( connection );
	}

	protected void send( HttpURLConnection connection ) throws IOException
	{
		if( data != null )
		{
			long length = data.getLength();
			Updateable.Stream.Output output = new Send( connection.getOutputStream(), length );

			try
			{
				state.send( length );
				write( output, data );
			}
			finally
			{
				output.close();
				data.close();
			}
		}
	}

	protected void write( Updateable.Stream.Output output, InputStream data ) throws IOException
	{
		byte[] buffer = new byte[1024];

		for( int read = 0; read != -1; read = data.read( buffer ) )
		{
			output.write( buffer, 0, read );
		}
	}

	protected class Send extends Updateable.Stream.Output
	{
		public Send( OutputStream stream, long length )
		{
			super( stream, length );
		}

		@Override
		public void update() throws IOException
		{
			if( cancelled )
			{
				throw new InterruptedIOException( "Connection cancelled" );
			}

			state.sending( written, getLength() );
		}
	}
}