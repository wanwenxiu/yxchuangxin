package com.yxld.yxchuangxin.activity.index;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.base.BaseActivity;
import com.yxld.yxchuangxin.contain.Contains;
import com.yxld.yxchuangxin.controller.DoorController;
import com.yxld.yxchuangxin.controller.impl.DoorControllerImpl;
import com.yxld.yxchuangxin.entity.BarcodedataAndroid;
import com.yxld.yxchuangxin.entity.CxwyYezhu;
import com.yxld.yxchuangxin.listener.ResultListener;
import com.yxld.yxchuangxin.util.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: AuthorizedReleaseActivity
 * @Description: 授权放行
 * @author wwx
 * @date 2016年5月27日 下午5:14:24
 */
public class AuthorizedReleaseActivity extends BaseActivity implements ResultListener<BarcodedataAndroid>{

	private int page = 10;

	private DoorController doorController;
	/** 授权列表*/
	private ListView authorizedList;

	private TextView addr;

	private CxwyYezhu yezhu = new CxwyYezhu();
	private List<Map<String,String>> mapList = new ArrayList<Map<String,String>>();

	@Override
	protected void initContentView(Bundle savedInstanceState) {

		setContentView(R.layout.authorized_release_activity);
		getSupportActionBar().setTitle("授权放行");
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		List<CxwyYezhu> list = Contains.cxwyYezhu;
		yezhu = list.get(0);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == android.R.id.home){
			onBackPressed();
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void initView() {
		addr = (TextView) findViewById(R.id.addr);
		addr.setText( yezhu.getYezhuLoupan()+""+yezhu.getYezhuLoudong()+"栋"+yezhu.getYezhuDanyuan()+"单元" +yezhu.getYezhuFanghao());
		authorizedList = (ListView) findViewById(R.id.AuthorizedList);
		authorizedList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(AuthorizedReleaseActivity.this,
						phoneOpenDoorActivity.class);
				Bundle bundle1 = new Bundle();
				bundle1.putString("name", "1");
				intent.putExtras(bundle1);
				startActivity(intent, bundle1);
			}
		});
	}

	@Override
	public void onClick(View v) {
	}

	@Override
	protected void initDataFromLocal() {
		initDataFromNet();
	}

	@Override
	protected void initDataFromNet() {
		super.initDataFromNet();
		if(doorController == null){
			doorController = new DoorControllerImpl();
		}

	}

	@Override
	public void onResponse(BarcodedataAndroid info) {
		if(isEmptyList(info.getRows())){
			ToastUtil.show(AuthorizedReleaseActivity.this, "没有查询到记录");
		}else{
			List<BarcodedataAndroid> curData = info.getRows();
			for (int i = 0;i<curData.size();i++){
				BarcodedataAndroid data = curData.get(i);
				Map<String,String> map = new HashMap<>();
				map.put("name",data.getName()+"	"+data.getDianhua());
				map.put("time","99");
				map.put("state",data.getLuopan());
				mapList.add(map);
			}
			authorizedList.setAdapter(new SimpleAdapter(this,mapList, R.layout.authorized_release_activity_item_layout,
					new String[]{"name","time","state"},new int[]{R.id.name, R.id.time, R.id.state}));
		}
		progressDialog.hide();
	}

	@Override
	public void onErrorResponse(String errMsg) {
		onError(errMsg);
	}
}
