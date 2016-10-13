package com.yxld.yxchuangxin.activity.Main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.activity.index.AuthorizedReleaseActivity;
import com.yxld.yxchuangxin.activity.index.ComplaintActivity;
import com.yxld.yxchuangxin.activity.index.ExpressActivity;
import com.yxld.yxchuangxin.activity.index.FeiYongListActivity;
import com.yxld.yxchuangxin.activity.index.selectimg.Repair;
import com.yxld.yxchuangxin.activity.mine.EmployerActivity;
import com.yxld.yxchuangxin.activity.mine.JiaoFeiListActivity;
import com.yxld.yxchuangxin.activity.mine.RepairListActivity;
import com.yxld.yxchuangxin.adapter.WuYeMainActivityAdapter;
import com.yxld.yxchuangxin.base.BaseFragment;
import com.yxld.yxchuangxin.contain.Contains;
import com.yxld.yxchuangxin.entity.AndroidWuYeEntity;
import com.yxld.yxchuangxin.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author
 * 社区物业frgment
 */
public class ItemWuyeFragment extends BaseFragment {
	/** 列表Listview*/
	private ListView buttomListView;

	/** 构建三级菜单实体*/
	private List<AndroidWuYeEntity> listData = new ArrayList<AndroidWuYeEntity>();

	/** 构建三级菜单适配器*/
	private WuYeMainActivityAdapter adapter;

	private int lastActivityTag;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {

		View contextView = inflater.inflate(R.layout.fragment_item, container, false);

		buttomListView = (ListView) contextView.findViewById(R.id.ButtomlistView);

		//获取Activity传递过来的参数
		Bundle mBundle = getArguments();
		lastActivityTag = mBundle.getInt("arg");

		listData.clear();
		switch (lastActivityTag){
			case 0: //我的物业
				listData.add(new AndroidWuYeEntity(R.mipmap.wuye_cheliangshibie,"车辆识别","快速通行,安全到家"));
				listData.add(new AndroidWuYeEntity(R.mipmap.wuye_jujiaanfang,"居家安防","专属安防报警系统,出门在外,有我放心"));
				listData.add(new AndroidWuYeEntity(R.mipmap.wuye_fangwuchuzhu,"房屋出租","实时房源滚动更新,让您轻松,省时,安心"));
				break;
			case 1: //费用缴纳
				listData.add(new AndroidWuYeEntity(R.mipmap.wuye_feiyong,"物业服务费","手机操作 方便快捷 欠费预交 一键解决"));
				listData.add(new AndroidWuYeEntity(R.mipmap.wuye_feiyong,"水费","代收代缴,一键立清"));
				listData.add(new AndroidWuYeEntity(R.mipmap.wuye_feiyong,"电费","代收代缴,一键立清"));
				listData.add(new AndroidWuYeEntity(R.mipmap.wuye_feiyong,"机动车停放服务费","月卡充值,方便又实惠"));
				listData.add(new AndroidWuYeEntity(R.mipmap.wuye_feiyong,"天然气","代收代缴,一键立清"));
				break;
			case 2:  //放心出入
				listData.add(new AndroidWuYeEntity(R.mipmap.wuye_shoujikaimen,"手机开门","智能高效,安全省心"));
				listData.add(new AndroidWuYeEntity(R.mipmap.wuye_laifangyaoqing,"来访邀请","授权门禁二维码,亲友来访更容易"));
				listData.add(new AndroidWuYeEntity(R.mipmap.wuye_shouquanfangxing,"授权放行","为陌生人授权门禁二维码"));
				break;
			case 3:  //邮包查询
				listData.add(new AndroidWuYeEntity(R.mipmap.wuye_youbaochaxun,"邮包查询","为您提供方便及时的快递物流信息"));
				break;
			case 4://报修
				listData.add(new AndroidWuYeEntity(R.mipmap.wuye_woyaobaoxiu,"我要报修","您的专属维修专家，解决日常保修烦恼"));
				break;
			case 5://投诉建议
				listData.add(new AndroidWuYeEntity(R.mipmap.wuye_woyaotoushu,"我要投诉","您的贴心服务管家,竭诚为您服务"));
				listData.add(new AndroidWuYeEntity(R.mipmap.wuye_woyaojianyi,"我要建议","您的建议,我们不断提升服务的动力"));
				break;
			case 6:
				listData.add(new AndroidWuYeEntity(R.mipmap.wuye_linlifenxiang,"邻里分享","这是详细内容详细内容"));
				listData.add(new AndroidWuYeEntity(R.mipmap.wuye_ershoushichang,"二手市场","这是详细内容详细内容"));
				listData.add(new AndroidWuYeEntity(R.mipmap.wuye_yishiting,"帮帮忙","这是详细内容详细内容"));
				listData.add(new AndroidWuYeEntity(R.mipmap.wuye_bangbangmang,"议事厅","这是详细内容详细内容"));
				break;
			case 7: //我的
				listData.add(new AndroidWuYeEntity(R.mipmap.wuye_fangwuxinxi,"房屋信息","这是详细内容详细内容"));
//				if(Contains.cxwyYezhu != null && Contains.cxwyYezhu.size()>0 && Contains.cxwyYezhu.get(0).getYezhuParentId() == 0){
//				}
				listData.add(new AndroidWuYeEntity(R.mipmap.wuye_fangwuxinxi,"入住成员管理","这是详细内容详细内容"));
				listData.add(new AndroidWuYeEntity(R.mipmap.wuye_woyaobaoxiu,"全部报修","这是详细内容详细内容"));
				listData.add(new AndroidWuYeEntity(R.mipmap.wuye_woyaobaoxiu,"已处理报修","这是详细内容详细内容"));
				listData.add(new AndroidWuYeEntity(R.mipmap.wuye_woyaobaoxiu,"未处理报修","这是详细内容详细内容"));
				break;
		}

		adapter = new WuYeMainActivityAdapter(listData,getActivity(),lastActivityTag);
		buttomListView.setAdapter(adapter);

		return contextView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	protected void initDataFromLocal() {

	}
}