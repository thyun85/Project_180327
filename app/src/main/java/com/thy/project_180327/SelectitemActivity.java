package com.thy.project_180327;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.GoogleMap;
import com.nhn.android.maps.NMapActivity;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapOverlayItem;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.overlay.NMapPOIdata;
import com.nhn.android.maps.overlay.NMapPOIitem;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager;
import com.nhn.android.mapviewer.overlay.NMapPOIdataOverlay;
import com.nhn.android.mapviewer.overlay.NMapResourceProvider;

public class SelectitemActivity extends NMapActivity {

    ScrollView scrollView;
    ImageView iv;
    TextView tv_facName, tv_codeName, tv_phne, tv_addr, tv_homepage, tv_closeday;

    String clientID = "H_uQbITcymDtbvgVBSrU";

    GestureDetector gestureDetector;

    NMapView mapView;
    NMapController mapController;
    NMapViewerResourceProvider nMapViewerResourceProvider;
    NMapCalloutCustomOldOverlay nMapCalloutCustomOldOverlay;
    NMapOverlayManager mOverlayManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectitem);

        scrollView = findViewById(R.id.scroll);

        iv = findViewById(R.id.iv_img);
        tv_facName = findViewById(R.id.tv_facname);
        tv_codeName = findViewById(R.id.tv_codename);
        tv_phne = findViewById(R.id.detail_phne);
        tv_addr = findViewById(R.id.detail_addr);
        tv_homepage = findViewById(R.id.detail_homepage);
        tv_closeday = findViewById(R.id.detail_closeday);

        Intent intent = getIntent();

        String facName = intent.getStringExtra("FacName");
        String codeName = intent.getStringExtra("CodeName");
        String phne = intent.getStringExtra("Phne");
        String addr = intent.getStringExtra("Addr");
        String imgURL = intent.getStringExtra("ImgURL");
        String homepage = intent.getStringExtra("Homepage");
        String closeday = intent.getStringExtra("Closeday");
        String xcoord = intent.getStringExtra("Xcoord");
        String ycoord = intent.getStringExtra("Ycoord");

        tv_facName.setText(facName);
        tv_codeName.setText(codeName);
        tv_phne.setText(phne);
        tv_addr.setText(addr);
        tv_homepage.setText(homepage);
        tv_closeday.setText(closeday);

        if(imgURL == null){
            iv.setVisibility(View.GONE);
        }else{
            iv.setVisibility(View.VISIBLE);
            Glide.with(this).load(imgURL).into(iv);
        }

        //iv에게 Transition의 Pair를 위한 이름 부여
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            iv.setTransitionName("IMG");
        }

        mapView = findViewById(R.id.mapview);
        mapView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == motionEvent.ACTION_UP) scrollView.requestDisallowInterceptTouchEvent(false);
                else{
                    scrollView.requestDisallowInterceptTouchEvent(true);
                    mapView.setClickable(true);
                }
                return false;
            }
        });
        mapView.setClientId(clientID);

        mapView.setScalingFactor(2.5f, true);

        //맵 컨드롤러객체 얻어오기
        mapController = mapView.getMapController();
        mapController.setZoomEnabled(true);

        NGeoPoint geoPoint = new NGeoPoint(Float.parseFloat(ycoord), Float.parseFloat(xcoord));
        mapController.setMapCenter(geoPoint, 11);

        nMapViewerResourceProvider = new NMapViewerResourceProvider(this);

        mOverlayManager = new NMapOverlayManager(this, mapView, nMapViewerResourceProvider);

        int markerId = NMapPOIflagType.PIN;

        // set POI data
        NMapPOIdata poiData = new NMapPOIdata(2, nMapViewerResourceProvider);
        poiData.beginPOIdata(2);
        poiData.addPOIitem(Float.parseFloat(ycoord), Float.parseFloat(xcoord), facName, markerId, 0);
        poiData.endPOIdata();

        // create POI data overlay
        NMapPOIdataOverlay poiDataOverlay = mOverlayManager.createPOIdataOverlay(poiData, null);
    }

}
