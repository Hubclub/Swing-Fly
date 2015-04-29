package com.hubclub.flyescape.android;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings.Secure;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.GameHelper;
import com.google.example.games.basegameutils.GameHelper.GameHelperListener;
import com.hubclub.swingfly.ActionResolver;
import com.hubclub.swingfly.MyGame;

public class AndroidLauncher extends AndroidApplication implements GameHelperListener, ActionResolver {
	
	private GameHelper gameHelper;
	private String appPackageName;
	
	private AdView adView;
	
	private final int HIDE_AD = 0;
	private final int SHOW_AD = 1;

	private AdRequest adRequest;
	
	
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		//initialize(new MyGame(this), config);
		
		if (gameHelper == null) {
			gameHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);
			gameHelper.enableDebugLog(true);
		}
		gameHelper.setup(this);
		
		appPackageName = getPackageName();
		
		 adView = (AdView) findViewById(R.id.adView);
		    
		 RelativeLayout layout = (RelativeLayout) findViewById(R.id.LinearLayout);
		 layout.removeAllViews();
		 
		 layout.addView(initializeForView(new MyGame(this),config));
		 
		 
		 
		 layout.addView(adView);
		 adRequest = new AdRequest.Builder().build();
		 adView.loadAd(adRequest);
		 
		 Log.e("MainActivity" ,Secure.getString(getContext().getContentResolver(),Secure.ANDROID_ID));
		
	}
	
		@Override
		public void onStart(){
			super.onStart();
			gameHelper.onStart(this);
		}
		
		@Override
		public void onStop(){
			super.onStop();
			gameHelper.onStop();
		}
		
		@Override
		public void onResume() {
		  super.onResume();
		  if (adView != null) {
		    adView.resume();
		  }
		}

		@Override
		public void onPause() {
		  if (adView != null) {
		    adView.pause();
		  }
		  super.onPause();
		}

		@Override
		public void onDestroy() {
		  if (adView != null) {
		    adView.destroy();
		  }
		  super.onDestroy();
		}
		
		@Override
		public void onActivityResult(int request, int response, Intent data) {
			super.onActivityResult(request, response, data);
			gameHelper.onActivityResult(request, response, data);
		}
		
		@Override
		public boolean getSignedInGPGS() {
			return gameHelper.isSignedIn();
		}
		
		@Override
		public void loginGPGS() {
			try {
				runOnUiThread(new Runnable(){
		public void run() {
			gameHelper.beginUserInitiatedSignIn();
			}
				});
			} catch (final Exception ex) {
			}
		}
		
		@Override
		public void submitScoreGPGS(int score) {
			Games.Leaderboards.submitScore(gameHelper.getApiClient(), "", score);
		}
		
		@Override
		public void unlockAchievementGPGS(String achievementId) {
			Log.w("SWING FLY", "ACHIEVEMENT");
			Games.Achievements.unlock(gameHelper.getApiClient(), achievementId);
		}
		
		@Override
		public void getLeaderboardGPGS() {
			if (gameHelper.isSignedIn()) {
				startActivityForResult(Games.Leaderboards.getLeaderboardIntent(gameHelper.getApiClient(), ""), 100);
			}
			else if (!gameHelper.isConnecting()) {
				loginGPGS();
			}
		}
		
		@Override
		public void getAchievementsGPGS() {
			if (gameHelper.isSignedIn()) {
				startActivityForResult(Games.Achievements.getAchievementsIntent(gameHelper.getApiClient()), 100);
			}
			else if (!gameHelper.isConnecting()) {
				loginGPGS();
			}
		}
		
		@Override
		public void onSignInFailed() {
		}
		
		@Override
		public void onSignInSucceeded() {
		}

		@Override
		public void openAppGPS() {
			try {
			    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
			} catch (android.content.ActivityNotFoundException anfe) {
			    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
			}
			
		}
		
		// ADS SECTION
		
		protected Handler handler = new Handler () {
			
			public void handleMessage (Message msg) {
			switch(msg.what) {
				case  SHOW_AD :{
					//adView.loadAd(adRequest);
					adView.setVisibility(View.VISIBLE);
					 break; }
				
				case HIDE_AD : {
					//adView.destroy();
					adView.setVisibility(View.GONE); break; }
			
				}
			}
			
		};

		@Override
		public void showAds(boolean show) {
			handler.sendEmptyMessage(show ? SHOW_AD : HIDE_AD);
			
		}
}
