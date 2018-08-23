package com.example.frank.wuhanjikong.ui.map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.frank.wuhanjikong.R;
import com.example.frank.wuhanjikong.log.L;
import com.example.frank.wuhanjikong.ui.map.lib.InputTipTask;
import com.example.frank.wuhanjikong.ui.map.lib.PoiSearchTask;
import com.example.frank.wuhanjikong.ui.map.lib.PositionEntity;
import com.example.frank.wuhanjikong.ui.map.lib.RecomandAdapter;
import com.example.frank.wuhanjikong.ui.map.lib.RouteTask;

public class DestinationActivity  extends Activity implements View.OnClickListener,TextWatcher,AdapterView.OnItemClickListener {

    private ListView mRecommendList;

    private ImageView mBack_Image;

    private TextView mSearchText;

    private EditText mDestinaionText;

    private RecomandAdapter mRecomandAdapter;

    private RouteTask mRouteTask;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination);
        mRecommendList=(ListView) findViewById(R.id.recommend_list);
        mBack_Image=(ImageView) findViewById(R.id.destination_back);
        mBack_Image.setOnClickListener(this);

        mSearchText=(TextView) findViewById(R.id.destination_search);
        mSearchText.setOnClickListener(this);

        mDestinaionText=(EditText) findViewById(R.id.destination_edittext);
        mDestinaionText.addTextChangedListener(this);
        mRecomandAdapter=new RecomandAdapter(getApplicationContext());
        mRecommendList.setAdapter(mRecomandAdapter);
        mRecommendList.setOnItemClickListener(this);

        mRouteTask=RouteTask.getInstance(getApplicationContext());
    }

    @Override
    public void afterTextChanged(Editable arg0) {

        // TODO Auto-generated method stub

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {

        // TODO Auto-generated method stub

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if(RouteTask.getInstance(getApplicationContext()).getStartPoint()==null){
            Toast.makeText(getApplicationContext(), "检查网络，Key等问题", Toast.LENGTH_SHORT).show();
            return;
        }
        InputTipTask.getInstance( mRecomandAdapter).searchTips(getApplicationContext(),s.toString(),
                RouteTask.getInstance(getApplicationContext()).getStartPoint().city);
        //L.g("选中了城市：", RouteTask.getInstance(getApplicationContext()).getStartPoint().city);
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.destination_back:
                Intent intent =new Intent(DestinationActivity.this, MapMainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                finish();
                break;
            case R.id.destination_search:
                PoiSearchTask poiSearchTask=new PoiSearchTask(getApplicationContext(), mRecomandAdapter);
                poiSearchTask.search(mDestinaionText.getText().toString(),RouteTask.getInstance(getApplicationContext()).getStartPoint().city);
                break;
        }

    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                            long id) {

        PositionEntity entity = (PositionEntity) mRecomandAdapter.getItem(position);
        if (entity.latitue == 0 && entity.longitude == 0) {
            PoiSearchTask poiSearchTask=new PoiSearchTask(getApplicationContext(), mRecomandAdapter);
            poiSearchTask.search(entity.address,RouteTask.getInstance(getApplicationContext()).getStartPoint().city);

        } else {
            mRouteTask.setEndPoint(entity);
            mRouteTask.search();
            Intent intent = new Intent(DestinationActivity.this, MapMainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
            finish();
        }
    }

}

