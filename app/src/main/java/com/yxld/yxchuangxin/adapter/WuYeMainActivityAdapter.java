package com.yxld.yxchuangxin.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.activity.Main.WebViewActivity;
import com.yxld.yxchuangxin.activity.index.AuthorizedReleaseActivity;
import com.yxld.yxchuangxin.activity.index.CameraActivity;
import com.yxld.yxchuangxin.activity.index.ChengyuanguanliActivity;
import com.yxld.yxchuangxin.activity.index.ExpressActivity;
import com.yxld.yxchuangxin.activity.index.VisitorInvitationActivity;
import com.yxld.yxchuangxin.activity.index.YeZhuOpenDoorActivity;
import com.yxld.yxchuangxin.activity.index.phoneOpenDoorActivity;
import com.yxld.yxchuangxin.activity.index.selectimg.Repair;
import com.yxld.yxchuangxin.activity.mine.AboutUsActivity;
import com.yxld.yxchuangxin.activity.mine.EmployerActivity;
import com.yxld.yxchuangxin.activity.mine.MemberActivity;
import com.yxld.yxchuangxin.activity.mine.MineVisionUpdateMainActivity;
import com.yxld.yxchuangxin.contain.Contains;
import com.yxld.yxchuangxin.entity.AndroidWuYeEntity;
import com.yxld.yxchuangxin.util.ToastUtil;
import com.yxld.yxchuangxin.view.RectangleWaterWave;

import java.util.List;

/**
 * @ClassName: WuYeMainActivityAdapter
 * @Description: 社区物业主界面适配器
 * @author wwx
 * @date 2016年4月5日 下午2:40:00 
 *
 */
@SuppressLint("InflateParams")
public class WuYeMainActivityAdapter extends BaseAdapter {
	private List<AndroidWuYeEntity> mList;
	Context mContext;
	private int tags;

	public WuYeMainActivityAdapter(List<AndroidWuYeEntity> list, Context context,int tag) {
		this.mList = list;
		this.mContext = context;
		this.tags = tag;
	}

	// 是获取显示数据的数量
	@Override
	public int getCount() {
		return mList.size();
	}

	// 获得当前位置的元素
	@Override
	public Object getItem(int arg0) {
		return mList.get(arg0);
	}

	// 获取当前控件的id号
	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	// 创建list里面子元素
	@SuppressWarnings("deprecation")
	@Override
	public View getView(final int position, // 当前要构造的位置
						View view, ViewGroup parent) {
		final ViewHolder holder;
		if (view == null) {
			holder = new ViewHolder();
			// 视图构造器
			LayoutInflater lif = LayoutInflater.from(mContext);
			// 构造视图
			view = lif.inflate(R.layout.wuye_main_list_item, null);
			// 找到控件
			holder.reveallayout = (RectangleWaterWave) view.findViewById(R.id.reveallayout
			);
			holder.menuDestail = (TextView) view.findViewById(R.id.menuDestail);
			holder.menuName = (TextView) view.findViewById(R.id.menuName);
			holder.menuSrc = (ImageView) view.findViewById(R.id.menuSrc);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		// 填充数据
		final AndroidWuYeEntity sb = mList.get(position);
		// 填充控件
		holder.menuDestail.setText(sb.getMenuDestail());
		holder.menuName.setText(sb.getMenuName());
		holder.menuSrc.setImageResource(sb.getMenuSrc());

		holder.reveallayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				jumpActivity(tags,position);
			}
		});

		return view;
	}

	class ViewHolder {
	    TextView menuDestail;
	    TextView menuName;
	    ImageView menuSrc;
		RectangleWaterWave reveallayout;
	}

	public List<AndroidWuYeEntity> getmList() {
		return mList;
	}

	public void setmList(List<AndroidWuYeEntity> mList) {
		this.mList = mList;
		notifyDataSetChanged();
	}

	/**
	 * @param tag 二级菜单
	 * @param position
     */
	private void jumpActivity(int tag ,int position){
		Bundle bundle = new Bundle();
		switch (tag){
			case 0: //我的物业
				if (position == 1){  //居家安防
					startActivity(CameraActivity.class);
				}else if(position == 2){ //手机开门
					if(Contains.cxwyYezhu == null || Contains.cxwyYezhu.size() == 0){
						ToastUtil.show(mContext, "请配置房屋信息再进行邀请");
						return;
					}
					startActivity(YeZhuOpenDoorActivity.class);
//					Intent intent = new Intent(mContext,
//							phoneOpenDoorActivity.class);
//					Bundle bundle1 = new Bundle();
//					bundle1.putString("name", "1");
//					intent.putExtras(bundle1);
//					mContext.startActivity(intent, bundle1);
				}
				else if(position == 3){ //来访邀请
//					ToastUtil.show(mContext,"敬请期待");
					if(Contains.cxwyYezhu == null || Contains.cxwyYezhu.size() == 0){
						ToastUtil.show(mContext, "请配置房屋信息再进行邀请");
						return;
					}
					startActivity(VisitorInvitationActivity.class);
				}
 				else if(position == 4){ //访客授权记录
					startActivity(AuthorizedReleaseActivity.class);
				}
				else {
					ToastUtil.show(mContext,"敬请期待");
				}
				break;
			case 1: //专享服务
				if(position == 2){  //邮包查询
					startActivity(ExpressActivity.class);
				}else if(position == 1){
					startActivity(Repair.class);
				}
				else {
					ToastUtil.show(mContext,"敬请期待");
				}
//				if(position == 0){
//					if(Contains.cxwyYezhu == null || Contains.cxwyYezhu.size() == 0){
//						ToastUtil.show(mContext, "请配置房屋信息再进行查询");
//						return;
//					}
//
//					bundle.putString("curType", "物业服务");
//					startActivity(FeiYongListActivity.class,bundle);
//				}else if(position == 1){
//					if(Contains.cxwyYezhu == null || Contains.cxwyYezhu.size() == 0){
//						ToastUtil.show(mContext, "请配置房屋信息再进行查询");
//						return;
//					}
//					bundle.putString("curType", "水");
//					startActivity(FeiYongListActivity.class,bundle);
//				}else if(position == 2){
//					if(Contains.cxwyYezhu == null || Contains.cxwyYezhu.size() == 0){
//						ToastUtil.show(mContext, "请配置房屋信息再进行查询");
//						return;
//					}
//					bundle.putString("curType", "电");
//					startActivity(FeiYongListActivity.class,bundle);
//				}else if(position == 3){
//					if(Contains.cxwyYezhu == null || Contains.cxwyYezhu.size() == 0){
//						ToastUtil.show(mContext, "请配置房屋信息再进行查询");
//						return;
//					}
//					bundle.putString("curType", "机动车停放服务");
//					startActivity(FeiYongListActivity.class,bundle);
//				}else{
//					if(Contains.cxwyYezhu == null || Contains.cxwyYezhu.size() == 0){
//						ToastUtil.show(mContext, "请配置房屋信息再进行查询");
//						return;
//					}
//					bundle.putString("curType", "天然气");
//					startActivity(FeiYongListActivity.class,bundle);
//				}
				break;
			case 2: //我的社区
				ToastUtil.show(mContext,"敬请期待");
//				if(position == 0){ //我要报修
//					startActivity(Repair.class);
//				}else{ //报修处理
//					if(Contains.cxwyYezhu != null && Contains.cxwyYezhu.size() != 0){
//						startActivity(RepairListActivity.class);
//					}else{
//						ToastUtil.show(mContext, "请完善业主信息");
//					}
//				}
				break;
			case 3: //个人中心
				if(position == 0){  //房屋信息
					if(Contains.cxwyYezhu != null && Contains.cxwyYezhu.size() != 0){
						startActivity(EmployerActivity.class);
					}else {
						Toast.makeText(mContext, "业主信息尚未完善", Toast.LENGTH_SHORT).show();
					}
				}else if(position == 1){  //入住成员管理
					if(Contains.cxwyYezhu != null && Contains.cxwyYezhu.size() != 0){
						startActivity(ChengyuanguanliActivity.class);
					}else{
						ToastUtil.show(mContext, "您没有权限查看");
					}
				}else if(position == 2){  //房屋出租
					Intent tz = new Intent();
					tz.setClass(mContext, // context
							WebViewActivity.class);// 跳转的activity
					Bundle tz1 = new Bundle();
					tz1.putString("name", "出租信息");
					tz1.putString("address", "http://222.240.1.133/wygl/addchuzu.jsp");
					tz.putExtras(tz1);
					mContext.startActivity(tz);
				}else if(position == 3){  //版本更新
					startActivity(MineVisionUpdateMainActivity.class);
				}else if(position == 4){  //关于我们
					startActivity(AboutUsActivity.class);
				}else{ //我的账号
					startActivity(MemberActivity.class);
				}
//				if(position == 0){ //我要查询
//					startActivity(ExpressActivity.class);
//				}else{ //我要寄件
//					ToastUtil.show(mContext,"敬请期待");
//				}
				break;
//			case 4: //我的社区
//					ToastUtil.show(mContext,"敬请期待");
//				break;
//			case 5: //投诉建议
//				if(position == 0){
//					startActivity(ComplaintActivity.class);
//				}else{
//					ToastUtil.show(mContext,"敬请期待");
//				}
//				break;
//			case 6: //个人中心
//				if(position == 0){  //房屋信息
//					if(Contains.cxwyYezhu != null && Contains.cxwyYezhu.size() != 0){
//						startActivity(EmployerActivity.class);
//					}else {
//						Toast.makeText(mContext, "业主信息尚未完善", Toast.LENGTH_SHORT).show();
//					}
//				}else if(position == 1){  //入住成员管理
//					if(Contains.cxwyYezhu != null && Contains.cxwyYezhu.size() != 0){
//						startActivity(ChengyuanguanliActivity.class);
//					}else{
//						ToastUtil.show(mContext, "您没有权限查看");
//					}
//				}else if(position == 2){  //房屋出租
//					Intent tz = new Intent();
//					tz.setClass(mContext, // context
//							WebViewActivity.class);// 跳转的activity
//					Bundle tz1 = new Bundle();
//					tz1.putString("name", "出租信息");
//					tz1.putString("address", "http://222.240.1.133/wygl/addchuzu.jsp");
//					tz.putExtras(tz1);
//					mContext.startActivity(tz);
//				}else if(position == 3){  //版本更新
//					startActivity(MineVisionUpdateMainActivity.class);
//				}else{  //关于我们
//					startActivity(AboutUsActivity.class);
//				}
//				break;
		}
	}

	/**
	 * 启动Activity
	 * @param <T>
	 * @param clazz
	 */
	protected <T> void startActivity(Class<T> clazz) {
		Intent intent = new Intent(mContext, clazz);
		try {
			mContext.startActivity(intent);
		} catch (Exception e) {
			Toast.makeText(mContext, "敬请期待！"+ clazz.getSimpleName()
					+ "未注册！",Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 启动Activity
	 *
	 * @param clazz
	 */
	protected <T> void startActivity(Class<T> clazz, Bundle bundle) {
		Intent intent = new Intent(mContext, clazz);
		intent.putExtras(bundle);
		try {
			mContext.startActivity(intent);
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(mContext, "敬请期待！"+ clazz.getSimpleName()
					+ "未注册！",Toast.LENGTH_SHORT).show();
		}
	}
}