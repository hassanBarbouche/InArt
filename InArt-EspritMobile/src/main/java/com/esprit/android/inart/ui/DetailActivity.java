package com.esprit.android.inart.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.esprit.android.inart.AddMusic;
import com.esprit.android.inart.AddPhoto;
import com.esprit.android.inart.CinemaListEvent;
import com.esprit.android.inart.LandscapesActivity;
import com.esprit.android.inart.ListMusicActivity;
import com.esprit.android.inart.MusicListEvent;
import com.esprit.android.inart.MusicNewsActivity;
import com.esprit.android.inart.MyPhotosList;
import com.esprit.android.inart.PaintingListEvent;
import com.esprit.android.inart.PhotographyListEvent;
import com.esprit.android.inart.PostThingActivity;
import com.esprit.android.inart.R;
import com.esprit.android.inart.SpaceCinemaActivity;
import com.esprit.android.inart.SpaceMusicActivity;
import com.esprit.android.inart.SpacePaintingActivity;
import com.esprit.android.inart.SpacePhotographyActivity;
import com.esprit.android.inart.SpaceTheatreActivity;
import com.esprit.android.inart.TheatreListEvent;
import com.esprit.android.inart.VideoActivity;
import com.esprit.android.inart.adapters.SampleAdapter;
import com.esprit.android.inart.adapters.SampleData2;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Bind;

public class DetailActivity extends BaseActivity implements AdapterView.OnItemClickListener{
    private static final float BLUR_RADIUS = 20f;
    public static final String EXTRA_URL = "url";
    @Bind(R.id.image)
    ImageView mImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //contend = (TextView) findViewById(R.id.contenu);
       // contend.setText("AZHBIHIHSOIUHSUOHOSUIHOIUSHSOIUHSISHS");
        overridePendingTransition(0, 0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);
        final ListView listView = (ListView) findViewById(R.id.list_view);

        LayoutInflater layoutInflater = getLayoutInflater();
       /* View view = findViewById(android.R.id.content);
        Animation mLoadAnimation = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.linear_interpolator);
        mLoadAnimation.setDuration(2000);
        view.startAnimation(mLoadAnimation);
*/





        final SampleAdapter adapter = new SampleAdapter(this, R.id.txt_line1);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        final List<String> sampleData = SampleData2.generateSampleData();
        for (String data : sampleData) {
            adapter.add(data);
        }
        ButterKnife.bind(this);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.id.image);
        Bitmap blurredBitmap = blur( bitmap);
        mImageView.setImageBitmap(blurredBitmap);
        mBackground = mImageView;
        final String imageUrl = getIntent().getExtras().getString(EXTRA_URL);
        int pos =  getIntent().getExtras().getInt("pos");
        if (pos==0)
        { mImageView.setImageResource(R.drawable.jazz1);
            moveBackground();}
        if (pos==1)
        { mImageView.setImageResource(R.drawable.cinema1);
            moveBackground();}
        if (pos==2)
        { mImageView.setImageResource(R.drawable.theatre1);
            moveBackground();}
        if (pos==3)
        { mImageView.setImageResource(R.drawable.photography1);
            moveBackground();}
        if (pos==4)
        { mImageView.setImageResource(R.drawable.painting);
            moveBackground();}
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Toast.makeText(this, "Item Clicked: " + position, Toast.LENGTH_SHORT).show();
        int poss =  getIntent().getExtras().getInt("pos");
if(position==5 && poss==3)
{
    Intent intent = new Intent(
            DetailActivity.this,
            PostThingActivity.class);
    intent.putExtra("categorie","photo");
    startActivity(intent);


}
        if(position==5 && poss==0){
            Intent intent = new Intent(
                    DetailActivity.this,
                    PostThingActivity.class);
            intent.putExtra("categorie","music");
            startActivity(intent);


        }

        if(position==5 && poss==1){
            Intent intent = new Intent(
                    DetailActivity.this,
                    PostThingActivity.class);
            intent.putExtra("categorie","video");
            startActivity(intent);


        }

        if(position==1 && poss==1){
            Intent intent = new Intent(
                    DetailActivity.this,
                    VideoActivity.class);

            startActivity(intent);


        }
        if(position==2 && poss==0){
            Intent intent = new Intent(
                    DetailActivity.this,
                    MusicListEvent.class);

            startActivity(intent);


        }
        if(position==2 && poss==1){
            Intent intent = new Intent(
                    DetailActivity.this,
                    CinemaListEvent.class);

            startActivity(intent);


        }
        if(position==2 && poss==2){
            Intent intent = new Intent(
                    DetailActivity.this,
                    TheatreListEvent.class);

            startActivity(intent);


        }
        if(position==2 && poss==4){
            Intent intent = new Intent(
                    DetailActivity.this,
                    PaintingListEvent.class);

            startActivity(intent);


        }
        if(position==2 && poss==0){
            Intent intent = new Intent(
                    DetailActivity.this,
                    MusicListEvent.class);

            startActivity(intent);


        }
        if(position==1 && poss==0){
            Intent intent = new Intent(
                    DetailActivity.this,
                    MusicCategorie.class);
            startActivity(intent);


        }
        if(position==4 && poss==0){
            Intent intent = new Intent(
                    DetailActivity.this,
                    MyPhotosList.class);
            startActivity(intent);


        }
        if(position==0 && poss==0){
            Intent intent = new Intent(
                    DetailActivity.this,
                    MusicNewsActivity.class);
            startActivity(intent);


        }
        if(position==3 && poss==0){
            Intent intent = new Intent(
                    DetailActivity.this,
                    SpaceMusicActivity.class);
            startActivity(intent);


        }

        if(position==3 && poss==1){
            Intent intent = new Intent(
                    DetailActivity.this,
                    SpaceCinemaActivity.class);
            startActivity(intent);


        }

        if(position==3 && poss==2){
            Intent intent = new Intent(
                    DetailActivity.this,
                    SpaceTheatreActivity.class);
            startActivity(intent);


        }


        if(position==3 && poss==3){
            Intent intent = new Intent(
                    DetailActivity.this,
                    SpacePhotographyActivity.class);
            startActivity(intent);


        }

        if(position==3 && poss==4){
            Intent intent = new Intent(
                    DetailActivity.this,
                    SpacePaintingActivity.class);
            startActivity(intent);


        }


        if(position==1 && poss==3){
            Intent intent = new Intent(
                    DetailActivity.this,
                    PhotoCategorie.class);
            startActivity(intent);


        }
        if(position==2 && poss==3){
            Intent intent = new Intent(
                    DetailActivity.this,
                    ListMusicActivity.class);
            startActivity(intent);


        }
    }
    public Bitmap blur(Bitmap image) {
        if (null == image) return null;

        Bitmap outputBitmap = Bitmap.createBitmap(image);
        final RenderScript renderScript = RenderScript.create(this);
        Allocation tmpIn = Allocation.createFromBitmap(renderScript, image);
        Allocation tmpOut = Allocation.createFromBitmap(renderScript, outputBitmap);

        //Intrinsic Gausian blur filter
        ScriptIntrinsicBlur theIntrinsic = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
        theIntrinsic.setRadius(BLUR_RADIUS);
        theIntrinsic.setInput(tmpIn);
        theIntrinsic.forEach(tmpOut);
        tmpOut.copyTo(outputBitmap);
        return outputBitmap;
    }
}


