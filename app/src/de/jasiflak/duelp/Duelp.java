package de.jasiflak.duelp;


import android.os.Bundle;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.view.Menu;
import android.widget.TabHost;

public class Duelp extends TabActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Resources res = getResources();        
        TabHost tabhost = getTabHost();        
        TabHost.TabSpec spec;
        Intent intent;
        
        intent = new Intent().setClass(this,Test.class);        
        spec = tabhost.newTabSpec("hallotab1").setIndicator("HalloTab1",res.getDrawable(R.drawable.ic_tabs)).setContent(intent);
        tabhost.addTab(spec);
        
        
        intent = new Intent().setClass(this,Bla.class);        
        spec = tabhost.newTabSpec("hallotab2").setIndicator("HalloTab2",res.getDrawable(R.drawable.ic_tabs)).setContent(intent);
        tabhost.addTab(spec);
        
        
        intent = new Intent().setClass(this,Blub.class);        
        spec = tabhost.newTabSpec("hallotab3").setIndicator("HalloTab3",res.getDrawable(R.drawable.ic_tabs)).setContent(intent);
        tabhost.addTab(spec);
        
        
        
        tabhost.setCurrentTab(1);
        
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
}