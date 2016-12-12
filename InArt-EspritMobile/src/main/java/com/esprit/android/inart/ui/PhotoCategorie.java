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
import android.widget.ListView;
import android.widget.Toast;

import com.esprit.android.inart.AbstractActivity;
import com.esprit.android.inart.AstralActivity;
import com.esprit.android.inart.MusicPhotoActivity;
import com.esprit.android.inart.BlackWhiteActivity;
import com.esprit.android.inart.JournalismActivity;
import com.esprit.android.inart.LandscapesActivity;
import com.esprit.android.inart.PeopleActivity;
import com.esprit.android.inart.PortraitActivity;
import com.esprit.android.inart.R;
import com.esprit.android.inart.adapters.SampleAdapterPhoto;
import com.esprit.android.inart.adapters.SampleData;

import java.util.List;

public class PhotoCategorie extends BaseActivity implements AdapterView.OnItemClickListener{
    private static final float BLUR_RADIUS = 20f;
    public static final String EXTRA_URL = "url";
  //  @Bind(R.id.image)
   // ImageView mImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //contend = (TextView) findViewById(R.id.contenu);
       // contend.setText("AZHBIHIHSOIUHSUOHOSUIHOIUSHSOIUHSISHS");
        overridePendingTransition(0, 0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity_photo);
        final ListView listView = (ListView) findViewById(R.id.list_view_photo);

        LayoutInflater layoutInflater = getLayoutInflater();
       /* View view = findViewById(android.R.id.content);
        Animation mLoadAnimation = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.linear_interpolator);
        mLoadAnimation.setDuration(2000);
        view.startAnimation(mLoadAnimation);
*/





        final SampleAdapterPhoto adapter = new SampleAdapterPhoto(this, R.id.txt_line1);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        final List<String> sampleData = SampleData.generateSampleData();
        for (String data : sampleData) {
            adapter.add(data);
        }
//        ButterKnife.bind(this);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.id.image);
        Bitmap blurredBitmap = blur( bitmap);
      //  mImageView.setImageBitmap(blurredBitmap);
      //  mBackground = mImageView;

    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Toast.makeText(this, "Item Clicked: " + position, Toast.LENGTH_SHORT).show();
       // int poss =  getIntent().getExtras().getInt("pos");
if(position==0 ){
    Intent intent = new Intent(
            PhotoCategorie.this,
            LandscapesActivity.class);
    startActivity(intent);


}
        if(position==1 ){
            Intent intent = new Intent(
                    PhotoCategorie.this,
                    PortraitActivity.class);
            startActivity(intent);


        }
        if(position==2 ){
            Intent intent = new Intent(
                    PhotoCategorie.this,
                    AbstractActivity.class);
            startActivity(intent);


        }
        if(position==3 ){
            Intent intent = new Intent(
                    PhotoCategorie.this,
                    BlackWhiteActivity.class);
            startActivity(intent);


        }
        if(position==4 ){
            Intent intent = new Intent(
                    PhotoCategorie.this,
                    JournalismActivity.class);
            startActivity(intent);


        }
        if(position==5 ){
            Intent intent = new Intent(
                    PhotoCategorie.this,
                    AstralActivity.class);
            startActivity(intent);


        }
        if(position==6 ){
            Intent intent = new Intent(
                    PhotoCategorie.this,
                    PeopleActivity.class);
            startActivity(intent);


        }
        if(position==7 ){
            Intent intent = new Intent(
                    PhotoCategorie.this,
                    MusicPhotoActivity.class);
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


