package de.jasiflak.duelp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.app.ProgressDialog;
import android.util.Log;



/**
 * What is this?
 * - This is a Child-Class of a Thread. This thing makes it easy for you to implement a http-request, wheather you need a POST or a GET request.
 * How to use this class?
 * - Simply call the constructor with the needed arguements. This creates your needed Thread but does NOT start it
 * - Then, when you want to send your request, simply call the method called "execute"
 * - if you want a synchron workflow or you have made a GET request and cant wait for the answer, then you should call the method called "waitForAnswer"
 * Special thing about waitForAnswer:
 * - this method throws a SecurityException as a timeout-indicator
 * 
 * You need Examples:
 * - GET:
 * 		TermineKalendarAdapter -> lines 68 - 75
 * - POST: 
 * 		TermineListeAdapter -> lines 148 -155
 * 
 * @author timmae
 *
 *  
 */


public class HttpAction extends Thread {
	private HttpClient mHttpClient;
	private HttpResponse mHttpResponse;
	private HttpPost mHttpPost;
	private HttpGet mHttpGet;
	private String mResponse, mURL;
	private Thread mThread;
	private boolean mIsPOST;
	private boolean mTimeout;
	private String mErrMessage;
	private boolean mError;
	
	
	public class HttpActionException extends Exception {
	    public HttpActionException(String message) {
	        super(message);
	    }
	}
	
	/**
	 * Simply constructs the Thread but it is NOT executed
	 * @param url the requesting url as a String
	 * @param isPOST a bool that indicates wheather your request is a POST or not (if it is not, the third param can be "null")
	 * @param jsonParams just needed if your request is POST. 
	 * @throws HttpActionException 
	 */
	public HttpAction(String url, boolean isPOST, String jsonParams) throws HttpActionException {
		mIsPOST = isPOST;
		mTimeout = false;
		mURL = url;
		mResponse = "";
		//set the connection-timeout
		HttpParams httpParameters = new BasicHttpParams();
		int timeoutConnection = 4000;
		HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
		
		mHttpClient = new DefaultHttpClient(httpParameters);
		if(!isPOST) {
			mHttpGet = new HttpGet(url);
		} else {
			mHttpPost = new HttpPost(url);
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		    nameValuePairs.add(new BasicNameValuePair("json", jsonParams));
		    try {
				mHttpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			} catch (Exception e) {
				throw new HttpActionException("Encoding fuckup with the Params");
			}
		}
	}
	
	public void wirfException(String message) throws HttpActionException {
		throw new HttpActionException("Encoding fuckup with the Params"); 
	}
	
	public void run() {
		if(mIsPOST) {

			try {
				mHttpResponse = mHttpClient.execute(mHttpPost);
			} catch(Exception ex) {
				mErrMessage = ex.getMessage();
				mError = true;
				return;
			}

			try {
				parseAnswer();
			} catch (IOException ex) {
				mErrMessage = ex.getMessage();
				mError = true;
				return;
			}
			
		} else {
			try {
			    mHttpResponse = mHttpClient.execute(mHttpGet);
			} catch(Exception ex) {
				Log.i("debug", "error while calling url: " + ex.getMessage());
				mErrMessage = ex.getMessage();
				mError = true;
				return;
			}
			try {
				parseAnswer();
				Log.i("debug", "answer parsed: " + mResponse);
			} catch (IOException ex) {
				Log.i("debug", "Error while parsing answer");
				mErrMessage = ex.getMessage();
				mError = true;
			}
		}
	}
	
	
	private void parseAnswer() throws IOException {
		StatusLine statusLine = mHttpResponse.getStatusLine();
		
	    if(statusLine.getStatusCode() == HttpStatus.SC_OK){
	        ByteArrayOutputStream out = new ByteArrayOutputStream();
	        mHttpResponse.getEntity().writeTo(out);
	        out.close();
	        mResponse = out.toString();
	        Log.i("debug", "Habe folgende Antwort erhalten: " + mResponse);
	    } else if(!mIsPOST){
	        //Closes the connection.
	        mHttpResponse.getEntity().getContent().close();
	    }
	}
	
	
	/**
	 * simply starts the Thread
	 */
	public void execute() {
		this.start();			
	}
	
	/**
	 * waits for the Thread to end 
	 * @return the Response if it is a GET request or null otherwise 
	 * @throws HttpActionException 
	 */
	public String waitForAnswer() throws HttpActionException {
		try {
			this.join();
		} catch (InterruptedException e) {
			throw new HttpActionException(e.getMessage());
		}
		if(mError)
			throw new HttpActionException(mErrMessage);
		return mResponse;
	}
}
