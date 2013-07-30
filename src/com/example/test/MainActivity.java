package com.example.test;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	//StringBuilder mensajes = new StringBuilder();
	//TextView visor;
	
	//Generamos la variables para la geolocalizaci—n
	LocationManager manager = null;
	LocationListener listener = null;
	Geocoder geocoder = null;

	
	double longitude;
	double latitude;
	String country ;
	private ProgressDialog dialog;
	WeatherTask wt;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        
    	super.onCreate(savedInstanceState);
        
    	boolean providerEnable = false;
    	
        setContentView(R.layout.activity_main);
        
      //call the main layout from xml
        LinearLayout voidLayout = (LinearLayout)findViewById(R.id.voidlayout);
 
        //create a view to inflate the layout_item (the xml with the textView created before)
        View view = getLayoutInflater().inflate(R.layout.activity_main, voidLayout,false);
 
        LinearLayout mainLayout = (LinearLayout)findViewById(R.id.mainlayout);
        mainLayout.setVisibility(View.INVISIBLE);
        
        
        
        // Get a LocationManager, its listener and a Geocoder
		manager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		listener = new MyLocationListener();
		geocoder = new Geocoder(this);
        
        
        Log.d("app","Probando GEO Localizaci—n");
        
        
        //Inicalizamos los servicions
        // If the GPS_PROVIDER is enable use it
		if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

			// Location updates provided by the GPS_PROVIDER should be handled by the provide listener
			manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, listener);
			Toast.makeText(this, LocationManager.GPS_PROVIDER, Toast.LENGTH_SHORT).show();
			
			providerEnable=true;
			//Obtenemos la localizaci—n actual al iniciar
			Location currentLocation = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);   
		    latitude = currentLocation.getLatitude();
		    longitude = currentLocation.getLongitude();
			
		}
		// Otherwise, if the NETWORK_PROVIDER is enabled use it
		else if (manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
			// Location updates provided by the NETWORK_PROVIDER should be handled by the provide listener
			manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, listener);
			Toast.makeText(this, LocationManager.NETWORK_PROVIDER, Toast.LENGTH_SHORT).show();
			
			providerEnable=true;
			
			//Obtenemos la localizaci—n actual al iniciar
			Location currentLocation = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);   
		    latitude = currentLocation.getLatitude();
		    longitude = currentLocation.getLongitude();
			
		}
		//Check if the provider in enable
		if (providerEnable) {
			Log.d("app","Provider enable");
			//dialog.show(); 
			showLocation();
			
			WeatherTask wt = new WeatherTask();
			//A–adimos una referentcia del padre
			wt.setContext(this);
			wt.setLatitude(latitude);
			wt.setLongitude(longitude);
			wt.execute();
		}
		else {
			Toast.makeText(this, "No disponemos del servicio de geolocalizaci—n", Toast.LENGTH_SHORT).show();
		}
		
		
		
        
    }
    
    private void log(String text){
 	   Log.d("app",text);
 	   //mensajes.append(text);
 	   //mensajes.append("\n");
 	   //visor.setText(mensajes.toString());
    }

    private void showLocation() {
    	Toast.makeText(getApplicationContext(), longitude + " " + latitude, Toast.LENGTH_LONG).show();
    	Log.d("app","Nueva localizaci—n:" + longitude + " " + latitude);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    
    void updateMyLayout(Weather w) {
    	//This function paints de layout when
    	Log.d("app","Pintamos el layout");
    	
    	((TextView) findViewById(R.id.name)).setText(w.getWName());
    	((TextView) findViewById(R.id.description)).setText(w.getDescription());
    	((TextView) findViewById(R.id.main)).setText(w.getMain());
    	
    	String iconame = w.getIcon();
    	
    	log(iconame);
    	Resources res = getResources();
    	int resourceId = res.getIdentifier("w"+iconame, "drawable", getPackageName() );
    	
    	
    	Drawable drawable = res.getDrawable(resourceId );
    	
    	ImageView icon = (ImageView) findViewById(R.id.icono);
    	icon.setImageDrawable(drawable);
    	
    	
    	
    	((TextView) findViewById(R.id.temp)).setText(w.getTemp());
    	((TextView) findViewById(R.id.temp_min)).setText(w.getTempMin());
    	((TextView) findViewById(R.id.temp_max)).setText(w.getTempMax());
    	
    	((TextView) findViewById(R.id.pressure)).setText(w.getPressure());
    	((TextView) findViewById(R.id.humidity)).setText(w.getHumidity());
    	((TextView) findViewById(R.id.deg)).setText(w.getDeg());
    	((TextView) findViewById(R.id.speed)).setText(w.getSpeed());
    	
    	LinearLayout mainLayout = (LinearLayout)findViewById(R.id.mainlayout);
        mainLayout.setVisibility(View.VISIBLE);
    }
  
    // Listener for managing location changes
 	final class MyLocationListener implements LocationListener {

 		@Override
 		public void onLocationChanged(Location location) {
 			// TODO Auto-generated method stub
 			// Update the information in the interface "longitude,latitude"
 			longitude = location.getLongitude();
 			latitude = location.getLatitude();
 			wt.setLatitude(latitude);
			wt.setLongitude(longitude);
			//Indicate to the task that it must continue
			wt.setFinish(false);
 		}

 		@Override
 		public void onProviderDisabled(String provider) {
 			// TODO Auto-generated method stub

 		}

 		@Override
 		public void onProviderEnabled(String provider) {
 			// TODO Auto-generated method stub
 			
 		}

 		@Override
 		public void onStatusChanged(String provider, int status, Bundle extras) {
 			// TODO Auto-generated method stub
 		}

 	}
    
 	
}
