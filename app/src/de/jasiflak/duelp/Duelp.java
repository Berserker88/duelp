package de.jasiflak.duelp;


import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.Menu;
import android.widget.TabHost;

public class Duelp extends TabActivity {

	public static String URL = "10.12.40.240:8080";

	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_tab_layout);
        
        
        
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.login_layout, null))
        // Add action buttons
               .setPositiveButton(R.string.login, new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int id) {
                       initializeTabBar();
                   }
               })
               .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       destroy();
                   }
               });
        AlertDialog dialog = builder.create();
        
        dialog.show();
        
        

        
    }
    
    
    public void initializeTabBar() {
    	Resources res = getResources();  
        TabHost tabhost = getTabHost();
        TabHost.TabSpec spec;
        Intent intent;
    	
    	intent = new Intent().setClass(this,Lernplan.class);        
        spec = tabhost.newTabSpec("tab1").setIndicator("Lernplan",res.getDrawable(R.drawable.ic_tabs_lernplan)).setContent(intent);
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