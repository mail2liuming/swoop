package com.vc.swoop;

import java.util.ArrayList;
import java.util.List; 
 
   
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.vc.util.ObjectHelper;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class PicFragment extends Fragment {

    private static final String KEY_RES = "Fragment:Res";
	private String res; 
	private List<String> urls = new ArrayList<String>();
	private int position;
	private DisplayImageOptions options;
    
	public static PicFragment newInstance(String res,List<String> urls,int position) {
		PicFragment fragment = new PicFragment();
		
		fragment.res = res;
		fragment.urls = urls;
		
		fragment.position = position;
        
        return fragment;
    }
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);  
        options = new DisplayImageOptions.Builder().showImageForEmptyUri(R.drawable.img).build();//
//        if ((savedInstanceState != null) && savedInstanceState.containsKey(KEY_RES)) {
//        	res = savedInstanceState.getString(KEY_RES);
//        }
    }
	
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        
		ImageView image = new ImageView(getActivity()); 
//        fb.display(image,res);
        LinearLayout layout = new LinearLayout(getActivity());
        layout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
        layout.setGravity(Gravity.CENTER);
        layout.addView(image);
        image.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
        image.setScaleType(ImageView.ScaleType.CENTER_CROP);
        ImageLoader.getInstance().displayImage(res, image,options); 
        image.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(),
						ImagePagerActivity.class);
				String[] imageStrings=new String[urls.size()];
				for (int i = 0; i < urls.size(); i++) {
					imageStrings[i]=urls.get(i);
				}
				intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS,imageStrings);
				intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX,position);
				startActivity(intent);
			}
		});
        return layout;
    }
	
	
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_RES, res);
    }
	
}
