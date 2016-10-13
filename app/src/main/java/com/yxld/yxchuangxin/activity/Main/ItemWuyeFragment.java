package com.yxld.yxchuangxin.activity.Main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.adapter.WuYeMainActivityAdapter;
import com.yxld.yxchuangxin.base.BaseFragment;
import com.yxld.yxchuangxin.entity.AndroidWuYeEntity;

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
				listData.add(new AndroidWuYeEntity(R.mipmap.wuye_shoujikaimen,"业主开门","二维扫码，安全省心"));
				listData.add(new AndroidWuYeEntity(R.mipmap.wuye_laifangyaoqing,"来访邀请","授权门禁二维码，亲友来访更容易"));
				listData.add(new AndroidWuYeEntity(R.mipmap.wuye_shouquanfangxing,"访客授权记录","保存所授权的亲朋二维码的记录"));
				listData.add(new AndroidWuYeEntity(R.mipmap.wuye_shouquanfangxing,"授权放行","我的地盘我做主，授权放行我审核"));
				break;
			case 1: //专享服务
				listData.add(new AndroidWuYeEntity(R.mipmap.wuye_feiyong,"费用缴纳","手机操作 方便快捷 欠费预交 一键解决"));
				listData.add(new AndroidWuYeEntity(R.mipmap.wuye_feiyong,"维修服务","您的专属维修专家，解决日常报修烦恼"));
				listData.add(new AndroidWuYeEntity(R.mipmap.wuye_feiyong,"邮包查寄","邮包信息我来查，精确及时到您家"));
				listData.add(new AndroidWuYeEntity(R.mipmap.wuye_feiyong,"投诉建议","您的困惑，督促我们日常工作的完善"));
//				listData.add(new AndroidWuYeEntity(R.mipmap.wuye_feiyong,"天然气","代收代缴,一键立清"));
				break;
			case 2:  //我的社区
				listData.add(new AndroidWuYeEntity(R.mipmap.wuye_linlifenxiang,"邻里分享","身边的快乐，你我来分享"));
				listData.add(new AndroidWuYeEntity(R.mipmap.wuye_ershoushichang,"二手市场","世上没有无用的东西，只有放错地方的资源"));
				listData.add(new AndroidWuYeEntity(R.mipmap.wuye_bangbangmang,"议事厅","有事邻里来商议，和谐环境共创建"));
//				listData.add(new AndroidWuYeEntity(R.mipmap.wuye_woyaobaoxiu,"我要报修","您的专属维修专家，解决日常报修烦恼"));
//				listData.add(new AndroidWuYeEntity(R.mipmap.wuye_woyaobaoxiu,"保修处理","处理过程实时跟踪，报修结果及时反馈"));
				break;
			case 3:  //个人中心
//				listData.add(new AndroidWuYeEntity(R.mipmap.wuye_youbaochaxun,"我要查询","邮包信息我来查，精确及时到您家"));
//				listData.add(new AndroidWuYeEntity(R.mipmap.wuye_youbaochaxun,"我要寄件","快递寄件请找我，各大物流随您挑"));
				listData.add(new AndroidWuYeEntity(R.mipmap.wuye_fangwuxinxi,"房屋信息","我的房屋信息"));
				listData.add(new AndroidWuYeEntity(R.mipmap.wuye_fangwuxinxi,"入住成员管理","我的成员管理"));
				listData.add(new AndroidWuYeEntity(R.mipmap.wuye_fangwuchuzhu,"房屋出租","房屋信息，实时发布"));
				listData.add(new AndroidWuYeEntity(R.mipmap.wuye_woyaobaoxiu,"版本更新","这是详细内容详细内容"));
				listData.add(new AndroidWuYeEntity(R.mipmap.wuye_woyaobaoxiu,"关于我们","这是详细内容详细内容"));
				listData.add(new AndroidWuYeEntity(R.mipmap.wuye_woyaobaoxiu,"我的账号","这是详细内容详细内容"));
				break;
//			case 4://我的社区
//			break;
//			case 5://投诉建议
//				listData.add(new AndroidWuYeEntity(R.mipmap.wuye_woyaotoushu,"我要投诉","您的困惑，督促我们日常工作的完善"));
//				listData.add(new AndroidWuYeEntity(R.mipmap.wuye_yishiting,"我要建议","您的建议，引导我们服务品质的提升"));
//				break;
//			case 6://个人中心
//				listData.add(new AndroidWuYeEntity(R.mipmap.wuye_fangwuxinxi,"房屋信息","我的房屋信息"));
//				listData.add(new AndroidWuYeEntity(R.mipmap.wuye_fangwuxinxi,"入住成员管理","我的成员管理"));
//				listData.add(new AndroidWuYeEntity(R.mipmap.wuye_fangwuchuzhu,"房屋出租","房屋信息，实时发布"));
//				listData.add(new AndroidWuYeEntity(R.mipmap.wuye_woyaobaoxiu,"版本更新","这是详细内容详细内容"));
//				listData.add(new AndroidWuYeEntity(R.mipmap.wuye_woyaobaoxiu,"关于我们","这是详细内容详细内容"));
//				break;
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