package de.jasiflak.duelp;



import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.google.gson.Gson;


import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.Toast;

public class Duelp extends TabActivity {



	public static String URL = "duelp.dyndns-server.com:8080";

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
        mOfflineMode = true;
        mTabhost = getTabHost();
        
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        final LayoutInflater inflater = this.getLayoutInflater();
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
                       
                       loginInfos.add(user.getText().toString());
                       loginInfos.add(pass.getText().toString());
                	   Log.i("debug", "Parameter: " + loginInfos.toString());
                       
                	   checkLogin(loginInfos);
                       
                       initializeTabBar();
                   }
               })
               .setNeutralButton(R.string.register, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
					final View layoutRegister = inflater.inflate(R.layout.register_layout, null);
					
					builder.setView(layoutRegister)
					
						.setPositiveButton(R.string.register, new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								ArrayList<String> registerInfos = new ArrayList<String>();
								EditText user = (EditText) layoutRegister.findViewById(R.id.usernameReg);
								EditText pass = (EditText) layoutRegister.findViewById(R.id.passwordReg);
								EditText street = (EditText) layoutRegister.findViewById(R.id.strasseReg);
								EditText nr = (EditText) layoutRegister.findViewById(R.id.hnrReg);
								EditText plz = (EditText) layoutRegister.findViewById(R.id.plzReg);
								EditText city = (EditText) layoutRegister.findViewById(R.id.ortReg);
								
								registerInfos.add(user.getText().toString());
								registerInfos.add(pass.getText().toString());
								registerInfos.add(street.getText().toString());
								registerInfos.add(nr.getText().toString());
								registerInfos.add(plz.getText().toString());
								registerInfos.add(city.getText().toString());
								
								Log.i("debug", "Parameter: " + registerInfos.toString());
								
								register(registerInfos);
								
								initializeTabBar();
										
							}
						})
						
							.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
									finish();
								}
							});
					
					
					
					AlertDialog registerDialog = builder.create();
					
					registerDialog.setCanceledOnTouchOutside(false);
			        registerDialog.setOnCancelListener(new OnCancelListener() {
						
						@Override
						public void onCancel(DialogInterface dialog) {
							finish();
						}
					});
			        mLoginDialog.dismiss();
			        registerDialog.show();
				}
			})
               .setNegativeButton(R.string.offline, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       mUser = null;
                       mOfflineMode = true;
                       Toast.makeText(mContext, "Sie gelangen nun in den Offline-Modus", Toast.LENGTH_LONG).show();
                       initializeTabBar();
                   }
               });
        
        mLoginDialog = builder.create();
        mLoginDialog.setCanceledOnTouchOutside(false);
        mLoginDialog.setOnCancelListener(new OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				finish();
			}
		});
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
        spec = mTabhost.newTabSpec("tab2").setIndicator("Fächer",res.getDrawable(R.drawable.ic_tabs_faecher2)).setContent(intent);
        mTabhost.addTab(spec);
        
        intent = new Intent().setClass(this,TermineKalendar.class);        
        spec = mTabhost.newTabSpec("tab3").setIndicator("Termine",res.getDrawable(R.drawable.ic_tabs_termine)).setContent(intent);
        mTabhost.addTab(spec);      

        intent = new Intent().setClass(this,Orte.class);       
        spec = mTabhost.newTabSpec("tab4").setIndicator("Orte",res.getDrawable(R.drawable.ic_tabs_orte)).setContent(intent);
        mTabhost.addTab(spec);
        
        
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
    	if(mOfflineMode)
    		getMenuInflater().inflate(R.menu.offline_menu, menu);
    	else
    		getMenuInflater().inflate(R.menu.online_menu, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.exit_app:
                finish();
                return true;
            case R.id.unregister_user:
            	boolean success = unregister();
            	if(success) {
            		Toast.makeText(mContext, "Ihr Account wurde erfolgreich gelöscht!", Toast.LENGTH_SHORT).show();
            		Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage( getBaseContext().getPackageName() );
            		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            		startActivity(i);
            	} else
            		Toast.makeText(mContext, "Ihr Account konte NICHT geloescht werden!", Toast.LENGTH_SHORT).show();
            	
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
    private void destroy() {
    	android.os.Process.killProcess(android.os.Process.myPid());
    	super.onDestroy();
    }
    
    
    private void checkLogin(ArrayList<String> loginInfos) {
    	
    	httpRequest("login", loginInfos);
    	
    }
    
    private void register(ArrayList<String> regInfos) {
    	
    	for(String value : regInfos) {
    		if(value.equals("")) {
    			mUser = null;
				mOfflineMode = true;
				Toast.makeText(mContext, "Bitte ALLE Felder ausfüllen!", Toast.LENGTH_SHORT).show();
				return;
    		}
    	}
    	
    	Geocoder gCoder = new Geocoder(mContext, Locale.getDefault());
    	try {
    		List<Address> address = gCoder.getFromLocationName(regInfos.get(2)+" "+regInfos.get(3)+", "+regInfos.get(4)+" "+regInfos.get(5), 1);
    		
    		if(address.size() > 0) {
    			double lat, lng;
    			lat = address.get(0).getLatitude();
    			lng = address.get(0).getLongitude();
    			regInfos.add(""+lat);
    			regInfos.add(""+lng);
    		} else {
    			regInfos.add("51.3");
        		regInfos.add("6.2");
    		}
    	} catch(Exception ex) {
    		regInfos.add("51.3");
    		regInfos.add("6.2");
    	}
    	
    	httpRequest("register", regInfos);
    }
    
    
    private boolean unregister() {
    	ArrayList<String> user = new ArrayList<String>();
    	user.add(mUser);
    	if(httpRequest("unregister", user))
    		return true;
    	return false;
    }
    
    
    private boolean httpRequest(String urlMethod, ArrayList<String> param) {
    	Gson gson = new Gson();
		try {
			HttpAction httpReq = new HttpAction("http://" + Duelp.URL + "/duelp-backend/rest/"+urlMethod, true, gson.toJson(param));
			httpReq.execute();
			String answer = httpReq.waitForAnswer();
			Log.i("debug", "Antwort: " + answer);
			if(answer.equals("yes")) {
				mUser = param.get(0);
				mOfflineMode = false;
				if(!urlMethod.equals("unregister"))
					Toast.makeText(mContext, "Willkommen " + mUser, Toast.LENGTH_LONG).show();
				return true;
			}
			else {
				mUser = null;
				mOfflineMode = true;
				if(urlMethod.equals("login"))
					Toast.makeText(mContext, "Username/Passwort Kombination nicht gefunden", Toast.LENGTH_SHORT).show();
				else
					Toast.makeText(mContext, "Username existiert bereits", Toast.LENGTH_SHORT).show();
			}
		} catch(Exception ex) {
		   	Toast.makeText(mContext, "DUELP-Server nicht erreichbar. Sie sind nun im Offline-Modus", Toast.LENGTH_SHORT).show();
		}
		return false;
    }
    
}



