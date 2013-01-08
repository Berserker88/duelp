package de.jasiflak.duelp;


import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.Menu;
import android.widget.TabHost;

public class Duelp extends TabActivity {

	public static String URL = "192.168.1.12:8080";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_tab_layout);
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
    
}