package org.softwaregeeks.needletagger.common;

import org.softwaregeeks.needletagger.ConfigurationActivity;
import org.softwaregeeks.needletagger.InformationActivity;
import org.softwaregeeks.needletagger.MusicListActivity;
import org.softwaregeeks.needletagger.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class ActivityHelper
{
	private static ProgressDialog progressDialog;
	private static ImageButton buttonConfiguration;
	private static ImageButton buttonInformation;
	private static ImageView imageViewLogo;
	private static OnClickListener onClickListener;
	
	public static void setStartProcess(Activity activity)
	{
		progressDialog = new ProgressDialog(activity);
		progressDialog.setTitle(activity.getString(R.string.dialogProcessTitleCaption));
		progressDialog.setMessage(activity.getString(R.string.dialogProcessCaption));
		progressDialog.show();
	}
	
	public static void setEndProcess(Activity activity)
	{
		if( progressDialog!=null)
			progressDialog.hide();
	}
	
	public static void setNavigationBar(Activity activity)
	{
		loadNavigationBar(activity);
		setOnClickListener(activity);
		setNavigationBarOnClickListener();
	}
	
	private static void loadNavigationBar(Activity activity) {
		imageViewLogo = (ImageView) activity.findViewById(R.id.logo);
		buttonConfiguration = (ImageButton) activity.findViewById(R.id.configuration);
		buttonInformation = (ImageButton) activity.findViewById(R.id.infomation);
	}
	
	private static void setNavigationBarOnClickListener()
	{
		imageViewLogo.setOnClickListener(onClickListener);
		buttonConfiguration.setOnClickListener(onClickListener);
		buttonInformation.setOnClickListener(onClickListener);
	}
	
	public static void setOnClickListener(final Activity activity)
	{
		onClickListener = new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				switch (v.getId())
				{
				case R.id.logo:
					{
						Intent intent = new Intent(activity.getApplicationContext(), MusicListActivity.class);
						activity.startActivity(intent);
						activity.overridePendingTransition(0,0);
						activity.finish();
					}
					break;
				case R.id.configuration:
					{
						Intent intent = new Intent(activity.getApplicationContext(), ConfigurationActivity.class);
						activity.startActivity(intent);
						activity.overridePendingTransition(0,0);
					}
					break;
				case R.id.infomation:
					{
						Intent intent = new Intent(activity.getApplicationContext(), InformationActivity.class);
						activity.startActivity(intent);
						activity.overridePendingTransition(0,0);
//						activity.finish();
					}
					break;
				}
			}
		};
	}
	
	public static void setHeaderProgressBar(Activity activity,boolean isVisible)
	{
		if( activity == null )
			return;
		
		ProgressBar progressBar = (ProgressBar) activity.findViewById(R.id.progress);
		if( isVisible )
			progressBar.setVisibility(View.VISIBLE);
		else
			progressBar.setVisibility(View.INVISIBLE);
	}
	
	public static Object getParameter(Activity activity,String name)
	{
		Intent intent = activity.getIntent();
		if( intent == null )
			return null;
		
		Bundle bundle = intent.getExtras();
		if( bundle == null )
			return null;
		
		return bundle.get(name);
	}
}
