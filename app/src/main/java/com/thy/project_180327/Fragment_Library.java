package com.thy.project_180327;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by alofo on 2018-03-27.
 */

public class Fragment_Library extends Fragment implements SurfaceHolder.Callback {

    String apiKey = "524c5673647468793535517354685a";

    ArrayList<Item> items = new ArrayList<>();

    RecyclerView recyclerView;
    Adapter_All adapter;

    SwipeRefreshLayout refreshLayout;

    com.melnykov.fab.FloatingActionButton fab;

    URL url;
    XMLParserTask task;

    boolean isRun = true;
    boolean isWait = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_library, container, false);

        recyclerView = view.findViewById(R.id.recycler);
        adapter = new Adapter_All(getActivity(), items);
        recyclerView.setAdapter(adapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        XMLParsing();

        refreshLayout = view.findViewById(R.id.layout_swipe);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //갱신작업 수행.
                items.clear();
                XMLParsing();
            }
        });

        fab = view.findViewById(R.id.fab);
        fab.attachToRecyclerView(recyclerView);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView.scrollToPosition(0);
            }
        });

        return view;
    }

    void XMLParsing(){
        try {

            url = new URL("http://openapi.seoul.go.kr:8088/"+apiKey+"/xml/SearchCulturalFacilitiesDetailService/1/1000/");

            //별도 스레드객체 생성
            //스레드의 백그라운드 작업과 UI작업을 같이 할 수 있는 객체를 생성
            task = new XMLParserTask();
            //doInBackground()메소드 실행시키는 명령(메소드)

            task.execute(url); //Thread의 start()와 같은 역할

        } catch (MalformedURLException e) {
            e.printStackTrace();
//            Log.i("qqq", e.getMessage());
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        if(task == null){
            //별도 스레드객체 생성
            //스레드의 백그라운드 작업과 UI작업을 같이 할 수 있는 객체를 생성
//            task = new XMLParserTask();
            //doInBackground()메소드 실행시키는 명령(메소드)

//            task.execute(url); //Thread의 start()와 같은 역할
            XMLParsing();
        }else{
            resumeParsing();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        pauseParsing();
    }

    void pauseParsing(){
        task.pauseThread();
    }

    void resumeParsing(){
        task.resumeThread();
    }

    class XMLParserTask extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... urls) {

            URL url = urls[0];
            Log.i("eee", url.toString());
            try {
                InputStream is = url.openStream();

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                XmlPullParser xpp = factory.newPullParser();

                xpp.setInput(is, "utf-8");

                int eventType = xpp.next();

                Item item = null;
                String tagName = null;

                while(eventType != XmlPullParser.END_DOCUMENT && isRun){
                    eventType = xpp.next();

                    if(isWait){
                        synchronized (this){
                            wait();
                        }
                    }

                    switch(eventType){
                        case XmlPullParser.START_DOCUMENT:
                            break;

                        case XmlPullParser.START_TAG:
                            tagName = xpp.getName();

                            if(tagName.equals("row")){
                                item = new Item();
                            }else if(tagName.equals("CODENAME")){
                                xpp.next();
                                if(item != null) item.setCodeName(xpp.getText());

                            }else if(tagName.equals("FAC_NAME")){
                                xpp.next();
                                if(item != null) item.setFacName(xpp.getText());

                            }else if(tagName.equals("MAIN_IMG")){
                                xpp.next();
                                if(item != null) item.setImgURL(xpp.getText());

                            }else if(tagName.equals("ADDR")){
                                xpp.next();
                                if(item != null) item.setAddr(xpp.getText());

                            }else if(tagName.equals("PHNE")){
                                xpp.next();
                                if(item != null) item.setPhne(xpp.getText());

                            }else if(tagName.equals("HOMEPAGE")){
                                xpp.next();
                                if(item != null) item.setHomepage(xpp.getText());

                            }else if(tagName.equals("CLOSEDAY")){
                                xpp.next();
                                if(item != null) item.setCloseday(xpp.getText());

                            }
                            break;

                        case XmlPullParser.TEXT:
                            break;

                        case XmlPullParser.END_TAG:
                            tagName = xpp.getName();

                            if(tagName.equals("row")){
                                if(item.getCodeName() != null && item.getCodeName().toString().equals("도서관")) {
                                    items.add(item);
                                }

                                //Logcat에 기록 남기기...(디버깅 작업시 용이)
//                                Log.i("AAAA", item.getFacName());

                                item = null;

                                //리사이클러뷰의 아답터에게 데이터가 갱신되었다는 것을 통지
                                publishProgress();
                            }
                            break;

                        case XmlPullParser.END_DOCUMENT:
                            break;

                    }
                }

                Log.i("ffffffff", "dsdd");

            } catch (IOException e) {
                Log.i("asdAA", e.getMessage());
            } catch (XmlPullParserException e) {
                Log.i("asdAfffA", e.getMessage());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "읽기완료";
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);

            //리사이클러뷰의 갱신요청..
            adapter.notifyDataSetChanged();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            //swipeRefresh 로딩아이콘 지우기
            refreshLayout.setRefreshing(false);

//            Snackbar.make(recyclerView, s, Snackbar.LENGTH_SHORT).show();
        }

        void pauseThread(){
            isWait = true;
        }

        void resumeThread(){
            isWait = false;
            synchronized (this){
                this.notify();
            }
        }
    }
}
