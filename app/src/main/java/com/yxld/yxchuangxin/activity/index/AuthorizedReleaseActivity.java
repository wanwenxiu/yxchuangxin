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
public class AuthorizedReleaseActivity extends BaseActivity {

	/** 授权列表*/
	private ListView authorizedList;

	private TextView addr;
	@Override
	protected void initContentView(Bundle savedInstanceState) {

		setContentView(R.layout.authorized_release_activity);
		getSupportActionBar().setTitle("授权放行");
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
		addr.setText(Contains.cxwyMallUser.getUserSpare1()+"-"+Contains.cxwyMallUser.getUserUserName());
		authorizedList = (ListView) findViewById(R.id.AuthorizedList);
		List<Map<String,String>> mapList = new ArrayList<Map<String,String>>();
		for (int i = 0;i<9;i++){
			Map<String,String> map = new HashMap<>();
			map.put("name","小王");
			if(i==0){
				map.put("name","小张");
				map.put("time","有效期:2016年06月30号 14:00");
				map.put("state","未过期");
			}else{
				map.put("name","小王");
				map.put("time","有效期:2016年05月1号 14:00");
				map.put("state","已过期");
			}
			mapList.add(map);
		}
		authorizedList.setAdapter(new SimpleAdapter(this,mapList, R.layout.authorized_release_activity_item_layout,
				new String[]{"name","time","state"},new int[]{R.id.name, R.id.time, R.id.state}));

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

	}

}
