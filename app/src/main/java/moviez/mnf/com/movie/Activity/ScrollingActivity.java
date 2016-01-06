package moviez.mnf.com.movie.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;
import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import fr.castorflex.android.circularprogressbar.CircularProgressDrawable;
import moviez.mnf.com.movie.AppController;
import moviez.mnf.com.movie.DataSet.CastDetail.CrewDetailsData;
import moviez.mnf.com.movie.R;
import moviez.mnf.com.movie.tools.*;
import moviez.mnf.com.movie.tools.Config;

public class ScrollingActivity extends AppCompatActivity {
    CircleImageView pro;
    TextView ovrview,date,place,name;
    CrewDetailsData data;
    RelativeLayout mainRel;

    public Gson gson = new Gson();
    ImageView down,share;
    CircularProgressBar smothCirCast;
    TextView  birthTag,tagplace,tagOvr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        String key = intent.getExtras().getString("id");
       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        birthTag = (TextView) findViewById(R.id.birthTag);
        tagplace = (TextView) findViewById(R.id.tagplace);
        tagOvr = (TextView) findViewById(R.id.tagOvr);

        pro =(CircleImageView) findViewById(R.id.imgpro);
        ovrview = (TextView) findViewById(R.id.ovr);
        date = (TextView) findViewById(R.id.birthday);
        place = (TextView) findViewById(R.id.place);
        name = (TextView) findViewById(R.id.namecast);
        mainRel = (RelativeLayout) findViewById(R.id.mainRel);
        smothCirCast = (CircularProgressBar) findViewById(R.id.smothCirCast);
       // makeJsonCrewRequest("http://api.themoviedb.org/3/person/"+key+"?api_key=7cf008680165ec352b68dce08866495f");
        makeJsonCrewRequest(Config.BASE_URL+"person/"+key+"?api_key="+Config.API_KEY);

    }


    public void makeJsonCrewRequest(String uu){
        smothCirCast.setVisibility(View.VISIBLE);
        ((CircularProgressDrawable)smothCirCast.getIndeterminateDrawable()).start();
        JsonObjectRequest reqst = new JsonObjectRequest(com.android.volley.Request.Method.GET, uu,null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                data = gson.fromJson(response.toString(), CrewDetailsData.class);

                ((CircularProgressDrawable)smothCirCast.getIndeterminateDrawable()).progressiveStop();
                smothCirCast.setVisibility(View.INVISIBLE);

                mainRel.setVisibility(View.VISIBLE);

                Utils.loadImage(pro, Config.IMAGE_BASE_URL + data.getProfilePath());
                pro.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent strt = new Intent(ScrollingActivity.this, ImageViewActivity.class);
                        strt.putExtra("id", data.getProfilePath().toString());
                        strt.putExtra("key", "1");
                        startActivity(strt);
                    }
                });

                if(data.getBirthday()!=null) {
                    birthTag.setVisibility(View.VISIBLE);
                    date.setText(data.getBirthday().toString());
                }
                if(data.getBiography()!=null&&!data.getBiography().toString().equals("")){
                    tagOvr.setVisibility(View.VISIBLE);
                    ovrview.setText(data.getBiography().toString());
                }
                if(data.getPlaceOfBirth()!=null){
                    tagplace.setVisibility(View.VISIBLE);
                    place.setText(data.getPlaceOfBirth().toString());
                }
                if(data.getName()!=null){
                    name.setText(data.getName().toString());
                }

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), "Error  " + volleyError.getMessage(), Toast.LENGTH_SHORT).show();

                ((CircularProgressDrawable)smothCirCast.getIndeterminateDrawable()).progressiveStop();
                smothCirCast.setVisibility(View.INVISIBLE);
            }
        });

        AppController.getInstance().addToRequestQueue(reqst);

    }


}
