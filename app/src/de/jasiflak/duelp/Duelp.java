package de.jasiflak.duelp;



import java.util.ArrayList;

import com.google.gson.Gson;


import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.Toast;

public class Duelp extends TabActivity {


	public static String URL = "192.168.1.12:8080";

	private AlertDialog mLoginDialog;
	private Context mContext;
	public static String mUser;
	public static boolean mOfflineMode;
	private TabHost mTabhost;


	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_tab_layout);
        mContext = this;
        mOfflineMode = false;
        mTabhost = getTabHost();
        
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();
        final View layout = inflater.inflate(R.layout.login_layout, null);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(layout)
        // Add action buttons
               .setPositiveButton(R.string.login, new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int id) {
                       ArrayList<String> loginInfos = new ArrayList<String>();
                       EditText user = (EditText) layout.findViewById(R.id.username);
                       EditText pass = (EditText) layout.findViewById(R.id.password);
                       Log.i("debug", "user: " + user.getText().toString());
                       loginInfos.add(user.getText().toString());
                       loginInfos.add(pass.getText().toString());
                	   Log.i("debug", "Parameter: " + loginInfos.toString());
                       Gson gson = new Gson();
                       try {
	                       HttpAction httpLogin = new HttpAction("http://" + Duelp.URL + "/duelp-backend/rest/login", true, gson.toJson(loginInfos));
	                	   httpLogin.execute();
	                	   String answer = httpLogin.waitForAnswer();
	                	   Log.i("debug", "Antwort: " + answer);
	                	   if(answer.equals("yes")) {
	                		   mUser = user.getText().toString();
	                		   mOfflineMode = false;
	                		   Toast.makeText(mContext, "Willkommen " + mUser, Toast.LENGTH_LONG).show();
	                	   }
	                	   else {
	                		   mUser = null;
	                		   mOfflineMode = true;
	                		   Toast.makeText(mContext, "Falscher Username oder Passwort. Sie gelangen nun in den Offline-Modus", Toast.LENGTH_LONG).show();
	                	   }
                       } catch(Exception ex) {
                    	   Toast.makeText(mContext, "DUELP-Server nicht erreichbar. Sie gelangen nun in den Offline-Modus", Toast.LENGTH_LONG).show();
                    	   destroy();
                       }
                       initializeTabBar();
                   }
               })
               .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       mUser = null;
                       mOfflineMode = true;
                       Toast.makeText(mContext, "Sie gelangen nun in den Offline-Modus", Toast.LENGTH_LONG).show();
                       initializeTabBar();
                   }
               });
        mLoginDialog = builder.create();
        
        mLoginDialog.show();
        
        

        
    }
    
    
    public void initializeTabBar() {
    	Resources res = getResources();  
        TabHost.TabSpec spec;
        Intent intent;
    	
    	intent = new Intent().setClass(this,Lernplan.class);        
        spec = mTabhost.newTabSpec("tab1").setIndicator("Lernplan",res.getDrawable(R.drawable.ic_tabs_lernplan4)).setContent(intent);
        mTabhost.addTab(spec);
        
        intent = new Intent().setClass(this,Faecher.class);
        spec = mTabhost.newTabSpec("tab2").setIndicator("F�cher",res.getDrawable(R.drawable.ic_tabs_faecher2)).setContent(intent);
        mTabhost.addTab(spec);
        
        intent = new Intent().setClass(this,TermineKalendar.class);        
        spec = mTabhost.newTabSpec("tab3").setIndicator("Termine",res.getDrawable(R.drawable.ic_tabs_termine)).setContent(intent);
        mTabhost.addTab(spec);      

        intent = new Intent().setClass(this,Orte.class);       
        spec = mTabhost.newTabSpec("tab4").setIndicator("Orte",res.getDrawable(R.drawable.ic_tabs_orte)).setContent(intent);
        mTabhost.addTab(spec);
        
//        mTabhost.setOnTabChangedListener(new OnTabChangeListener() {
//			
//			@Override
//			public void onTabChanged(String tabId) {
//				if(!tabId.equals("tab1") && mOfflineMode) {
//					Toast.makeText(mContext, "Sie müssen eingeloggt sein um diese Funktion nutzen zu können", Toast.LENGTH_SHORT).show();
//					mTabhost.setCurrentTab(0);
//				}
//			}
//		});
        
        
        for(int i=1; i < mTabhost.getTabWidget().getChildCount(); i++)
        {
        	final int tabNr = i;
            getTabWidget().getChildAt(i).setOnClickListener(new OnClickListener() { 

                @Override 
                public void onClick(View v) { 

                    if (mOfflineMode)
                    	Toast.makeText(mContext, "Sie müssen eingeloggt sein um diese Funktion nutzen zu können", Toast.LENGTH_SHORT).show();
                    else
                        mTabhost.setCurrentTab(tabNr);
                } 
            });
        }
        
        
        
        mTabhost.setCurrentTab(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    private void destroy() {
    	android.os.Process.killProcess(android.os.Process.myPid());
    	super.onDestroy();
    }
    
}