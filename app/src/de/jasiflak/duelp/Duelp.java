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
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.Toast;

public class Duelp extends TabActivity {

	public static String URL = "10.12.41.43:8080";
	private AlertDialog mLoginDialog;
	private Context mContext;
	public static String mUser;

	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_tab_layout);
        mContext = this;
        
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
	                		   initializeTabBar();
	                	   }
	                	   else {
	                		   destroy();
	                	   }
                       } catch(Exception ex) {
                    	   Toast.makeText(mContext, "DUELP-Server nicht erreichbar", Toast.LENGTH_SHORT).show();
                    	   destroy();
                       }
                   }
               })
               .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       destroy();
                   }
               });
        mLoginDialog = builder.create();
        
        mLoginDialog.show();
        
        

        
    }
    
    
    public void initializeTabBar() {
    	Resources res = getResources();  
        TabHost tabhost = getTabHost();
        TabHost.TabSpec spec;
        Intent intent;
    	
    	intent = new Intent().setClass(this,Lernplan.class);        
        spec = tabhost.newTabSpec("tab1").setIndicator("Lernplan",res.getDrawable(R.drawable.ic_tabs_lernplan4)).setContent(intent);
        tabhost.addTab(spec);
        
        intent = new Intent().setClass(this,Faecher.class);
        spec = tabhost.newTabSpec("tab2").setIndicator("F�cher",res.getDrawable(R.drawable.ic_tabs_faecher2)).setContent(intent);
        tabhost.addTab(spec);
        
        intent = new Intent().setClass(this,TermineKalendar.class);        
        spec = tabhost.newTabSpec("tab3").setIndicator("Termine",res.getDrawable(R.drawable.ic_tabs_termine)).setContent(intent);
        tabhost.addTab(spec);      

        intent = new Intent().setClass(this,Orte.class);       
        spec = tabhost.newTabSpec("tab4").setIndicator("Orte",res.getDrawable(R.drawable.ic_tabs_orte)).setContent(intent);
        tabhost.addTab(spec);
        
        
        tabhost.setCurrentTab(0);
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