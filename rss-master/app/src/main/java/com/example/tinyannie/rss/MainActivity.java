package com.example.tinyannie.rss;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.jar.JarInputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity

        implements NavigationView.OnNavigationItemSelectedListener {
    ListView lvNews;
    ArrayList<dongNews> arrayNews;
    NewsAdapter adapter;

//    public class LoadImageInternet extends AsyncTask<String,Void,Bitmap>{
//        Bitmap bitmap=null;
//        @Override
//        protected Bitmap doInBackground(String... url) {
//            try {
//                String Url =url[0];
//                InputStream inputStream=new java.net.URL(Url).openStream();
//                bitmap=BitmapFactory.decodeStream(inputStream);
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        return bitmap;
//
//    }
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Feed back", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    class XMLRead extends AsyncTask<String,Integer,String>{

        @Override
        protected String doInBackground(String... params) {
            String kq=docNoiDung_Tu_URL(params[0]);
            return kq;
        }

        @Override
        protected void onPostExecute(String s) {
                XMLDOMParser parser=new XMLDOMParser();
                Document doc=parser.getDocument(s);
                NodeList nodeList=doc.getElementsByTagName("item");
                NodeList nodeListDescription=doc.getElementsByTagName("description");

                lvNews=(ListView) findViewById(R.id.lv_content);
                arrayNews=new ArrayList<>();
                for(int i=0;i<nodeList.getLength();i++){
                    String title="";
                    String link="";
                    int image=0;
                    String cdata=nodeListDescription.item(i+1).getTextContent();

                    //image_url=image_url+parser.getValue(element,"url");
                    //new LoadImageInternet().execute(image_url);
                    Element element=(Element) nodeList.item(i);
                    title=title+parser.getValue(element,"title")+"\n";
                    link=link+parser.getValue(element,"link")+"\n";
                    arrayNews.add(new dongNews(title,link,image));
            }
            adapter=new NewsAdapter(MainActivity.this,R.layout.news,arrayNews);
                lvNews.setAdapter(adapter);
            super.onPostExecute(s);
        }

    }

    private String docNoiDung_Tu_URL(String theUrl){
        StringBuilder content = new StringBuilder();
        try    {
            // create a url object
            URL url = new URL(theUrl);

            // create a urlconnection object
            URLConnection urlConnection = url.openConnection();

            // wrap the urlconnection in a bufferedreader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String line;

            // read from the urlconnection via the bufferedreader
            while ((line = bufferedReader.readLine()) != null){
                content.append(line + "\n");
            }
            bufferedReader.close();
        }
        catch(Exception e)    {
            e.printStackTrace();
        }
        return content.toString();
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(MainActivity.this,"Setting cqq",Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

       switch (id){

           case R.id.nav_dan_tri:
               runOnUiThread(new Runnable() {
               @Override
               public void run() {
                   new XMLRead().execute("https://vnexpress.net/rss/tin-moi-nhat.rss");
               }
           });
               break;
           case R.id.nav_doi_song:
               runOnUiThread(new Runnable() {
                   @Override
                   public void run() {
                       new XMLRead().execute("https://vnexpress.net/rss/tin-moi-nhat.rss");
                   }
               });
               break;
           case R.id.nav_kenh_14:
               runOnUiThread(new Runnable() {
                   @Override
                   public void run() {
                      new XMLRead().execute("https://vnexpress.net/rss/tin-moi-nhat.rss");
                   }
               });
           case R.id.nav_game_k:
               runOnUiThread(new Runnable() {
                   @Override
                   public void run() {
                       new XMLRead().execute("https://vnexpress.net/rss/tin-moi-nhat.rss");
                   }
               });
               break;
           case R.id.nav_phap_luat:
               runOnUiThread(new Runnable() {
                   @Override
                   public void run() {
                       new XMLRead().execute("https://vnexpress.net/rss/tin-moi-nhat.rss");
                   }
               });
               break;
           case R.id.nav_soha_news:
               runOnUiThread(new Runnable() {
                   @Override
                   public void run() {
                       new XMLRead().execute("https://vnexpress.net/rss/tin-moi-nhat.rss");
                   }
               });
               break;
           case R.id.nav_add_news:
           case R.id.nav_add_title:
           case R.id.nav_edit_news:
           case R.id.nav_edit_font:
           default:{};
       }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
