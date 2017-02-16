package com.p2p.core.P2PInterface;

import java.util.ArrayList;

public interface ISetting {
	// ACK 回调函数

	void ACK_vRetSetDeviceTime(int msgId, int result);

	void ACK_vRetGetDeviceTime(int msgId, int result);

	void ACK_vRetGetNpcSettings(String contactId, int msgId, int result);

	void ACK_vRetSetRemoteDefence(String contactId, int msgId, int result);

	void ACK_vRetSetRemoteRecord(int msgId, int result);

	void ACK_vRetSetNpcSettingsVideoFormat(int msgId, int result);

	void ACK_vRetSetNpcSettingsVideoVolume(int msgId, int result);

	void ACK_vRetSetNpcSettingsBuzzer(int msgId, int result);

	void ACK_vRetSetNpcSettingsMotion(int msgId, int result);

	void ACK_vRetSetNpcSettingsRecordType(int msgId, int result);

	void ACK_vRetSetNpcSettingsRecordTime(int msgId, int result);

	void ACK_vRetSetNpcSettingsRecordPlanTime(int msgId, int result);

	void ACK_vRetSetNpcSettingsNetType(int msgId, int result);

	void ACK_vRetSetAlarmEmail(int msgId, int result);

	void ACK_vRetGetAlarmEmail(int msgId, int result);

	void ACK_vRetSetAlarmBindId(int srcID, int result);

	void ACK_vRetGetAlarmBindId(int srcID, int result);

	void ACK_vRetSetInitPassword(int msgId, int result);

	void ACK_vRetSetDevicePassword(int msgId, int result);

	void ACK_vRetCheckDevicePassword(int msgId, int result, String deviceId);

	void ACK_vRetSetWifi(int msgId, int result);

	void ACK_vRetGetWifiList(int msgId, int result);

	void ACK_vRetSetDefenceArea(int msgId, int result);

	void ACK_vRetGetDefenceArea(int msgId, int result);

	void ACK_vRetGetRecordFileList(int msgId, int result);

	void ACK_vRetMessage(int msgId, int result);

	void ACK_vRetCustomCmd(int msgId, int result);

	void ACK_vRetGetDeviceVersion(int msgId, int result);

	void ACK_vRetCheckDeviceUpdate(int msgId, int result);

	void ACK_vRetDoDeviceUpdate(int msgId, int result);

	void ACK_vRetCancelDeviceUpdate(int msgId, int result);

	void ACK_vRetClearDefenceAreaState(int msgId, int result);

	void ACK_vRetGetDefenceStates(String contactId, int msgId, int result);

	void ACK_vRetSetImageReverse(int msgId, int result);

	void ACK_vRetSetInfraredSwitch(int msgId, int result);

	void ACK_vRetSetWiredAlarmInput(int msgId, int state);

	void ACK_vRetSetWiredAlarmOut(int msgId, int state);

	void ACK_vRetSetAutomaticUpgrade(int msgId, int state);

	void ACK_VRetSetVisitorDevicePassword(int msgId, int state);

	void ACK_vRetSetTimeZone(int msgId, int state);

	void ACK_vRetGetSDCard(int msgId, int state);

	void ACK_vRetSdFormat(int msgId, int state);

	void ACK_vRetSetGPIO(int msgId, int state);

	void ACK_vRetSetGPIO1_0(int msgId, int state);

	void ACK_vRetSetPreRecord(int msgId, int state);

	void ACK_vRetGetSensorSwitchs(int msgId, int state);

	void ACK_vRetSetSensorSwitchs(int msgId, int state);
	
	void ACK_vRetGetAlarmCenter(int msgId, int state);
	
	void ACK_vRetSetAlarmCenter(int msgId, int state);
	
	void ACK_VRetGetNvrIpcList(int msgId, int state);
	void ACK_VRetGetNvrInfo(String deviceId, int msgId, int state);
	void ACK_OpenDoor(int msgId, int state);
	void ACK_vRetGetFTPInfo(int msgId, int state);
	void ACK_vRetGetPIRLight(int msgId, int state);
	void ACK_vRetSetPIRLight(int msgId, int state);
	void ACK_vRetGetDefenceWorkGroup(int msgId, int state);
	void ACK_VRetGetPresetPos(int msgId, int state);
	void ACK_VRetSetKeepClient(String contactId, int msgId, int state);
	void ACK_VRetGetLed(String contactId, int msgId, int state);
	void ACK_VRetSetLed(String contactId, int msgId, int state);
	//移动侦测灵敏度ACK回调
	void ACK_vRetSetNpcSettingsMotionSens(int msgId, int result);
    //录像质量ACK回调
	void ACK_vRetGetVideoQuality(int msgId, int result);
    void ACK_vRetSetVideoQuality(int msgId, int result);
    //获取AP设备是否有设置过wifi回调
    void ACK_vRetGetApIsWifiSetting(String contactId, int msgId, int result);
    //配置AP设备的默认连接WIFI回调
    void ACK_vRetSetApStaWifiInfo(String contactId, int msgId, int result);

	// 设置结果回调
	void vRetGetRemoteDefenceResult(String contactId, int state);

	void vRetGetRemoteRecordResult(int state);

	void vRetGetBuzzerResult(int state);

	void vRetGetMotionResult(int state);

	void vRetGetVideoFormatResult(int type);

	void vRetGetRecordTypeResult(int type);

	void vRetGetRecordTimeResult(int time);

	void vRetGetNetTypeResult(int type);

	void vRetGetVideoVolumeResult(int value);

	void vRetGetRecordPlanTimeResult(String time);

	void vRetGetImageReverseResult(int type);

	void vRetGetInfraredSwitch(int state);

	void vRetGetWiredAlarmInput(int state);

	void vRetGetWiredAlarmOut(int state);

	void vRetGetAutomaticUpgrade(int state);

	void vRetGetTimeZone(int state);

	void vRetGetAudioDeviceType(int type);

	void vRetGetPreRecord(int state);

	void vRetGetSensorSwitchs(int result, ArrayList<int[]> data);

	void vRetSetRemoteDefenceResult(String contactId, int result);

	void vRetSetRemoteRecordResult(int result);

	void vRetSetBuzzerResult(int result);

	void vRetSetMotionResult(int result);

	void vRetSetVideoFormatResult(int result);

	void vRetSetRecordTypeResult(int result);

	void vRetSetRecordTimeResult(int result);

	void vRetSetNetTypeResult(int result);

	void vRetSetVolumeResult(int result);

	void vRetSetRecordPlanTimeResult(int result);

	void vRetSetDeviceTimeResult(int result);

	void vRetGetDeviceTimeResult(String time);

	void vRetAlarmEmailResult(int result, String email);

	void vRetAlarmEmailResultWithSMTP(int result, String email,
									  int smtpport, byte Entry, String[] SmptMessage, byte reserve);

	void vRetWifiResult(int result, int currentId, int count,
						int[] types, int[] strengths, String[] names);

	void vRetDefenceAreaResult(int result, ArrayList<int[]> data,
							   int group, int item);

	void vRetBindAlarmIdResult(int srcID, int result, int maxCount,
							   String[] data);

	void vRetSetInitPasswordResult(int result);

	void vRetSetDevicePasswordResult(int result);

	void vRetGetFriendStatus(int count, String[] contactIds,
							 int[] status, int[] types, boolean isFinish);

	void vRetGetRecordFiles(String[] names, byte option0, byte option1);

	void vRetMessage(String contactId, String msg);

	void vRetSysMessage(String msg);

	void vRetCustomCmd(int contactId, int len, byte[] cmd);

	void vRetGetDeviceVersion(int result, String cur_version,
							  int iUbootVersion, int iKernelVersion, int iRootfsVersion);

	void vRetCheckDeviceUpdate(String contactId, int result, String cur_version,
							   String upg_version);

	void vRetDoDeviceUpdate(String contactId, int result, int value);

	void vRetCancelDeviceUpdate(int result);

	void vRetDeviceNotSupport();

	void vRetClearDefenceAreaState(int result);

	void vRetSetImageReverse(int result);

	void vRetSetInfraredSwitch(int result);

	void vRetSetWiredAlarmInput(int state);

	void vRetSetWiredAlarmOut(int state);

	void vRetSetAutomaticUpgrade(int state);

	void vRetSetVisitorDevicePassword(int result);

	void vRetSetTimeZone(int result);

	void vRetGetSdCard(int result1, int result2, int SDcardID, int state);

	void VRetGetUsb(int result1, int result2, int SDcardID, int state);

	void vRetSdFormat(int result);

	void vRetSetGPIO(int result);

	void vRetSetPreRecord(int result);

	void vRetSetSensorSwitchs(int result);

	void vRecvSetLAMPStatus(String deviceId, int result);

	void vACK_RecvSetLAMPStatus(int result, int value);

	void vRecvGetLAMPStatus(String deviceId, int result);

	void vRetPresetMotorPos(byte[] result);

	void vRetDefenceSwitchStatus(int result);

	void vRetDefenceSwitchStatusResult(byte[] result);

	void vRetAlarmPresetMotorPos(byte[] result);

	void vRetIpConfig(byte[] result);
	
	void vRetGetAlarmCenter(int result, int state, String ipdress, int port, String userId);
	
	void vRetSetAlarmCenter(int result);
	
	void vRetDeviceNotSupportAlarmCenter();

	void vRetNPCVistorPwd(int pwd);
	
	void vRetDeleteDeviceAlarmID(String deviceId, int result, int result1);
	
	void vRetDeviceLanguege(int result, int languegecount, int curlanguege, int[] langueges);
	
	void vRetFocusZoom(String deviceId, int result);
	void vRetGetAllarmImage(int id, String filename, int errorCode);
	void vRetFishEyeData(int iSrcID, byte[] data, int datasize);
	void vRetGetNvrIpcList(String contactId, String[] date, int number);
	
	void vRetSetWifiMode(String id, int result);
	void vRetAPModeSurpport(String id, int result);
	void vRetDeviceType(String id, int mainType, int subType);
	void vRetNVRInfo(int iSrcID, byte[] data, int datasize);
	void vRetGetFocusZoom(String deviceId, int result, int value);
	void vRetSetFocusZoom(String deviceId, int result, int value);
	
	void vRetSetGPIO(String contactid, int result);
	void vRetGetGPIO(String contactid, int result, int bValueNs);
	
	void vRetGetDefenceWorkGroup(String contactid, byte[] data);
	void vRetSetDefenceWorkGroup(String contactid, byte[] data);
	
	void vRetFTPConfigInfo(String contactid, byte[] data);
	//Ehome
	void vRetGPIOStatus(String contactid, byte[] Level);
	void vRecvSetGPIOStatus(String contactid, byte[] data);
	//Ehome
	
	void vRetGetDefenceSwitch(int value);
	void vRetSetDefenceSwitch(int result);
	
	void vRetDefenceAreaName(String contactid, byte[] data);
	void vRetGetPIRLightControl(int value);
	void vRetFishInfo(String contactid, byte[] data);
    void vRetGetAutoSnapshotSwitch(int value);
    void vRetSetAutoSnapshotSwitch(int result);
    void vRecvGetPrepointSurpporte(String deviceId, int result);

	//移动侦测灵敏度获取ret回调
	void vRetGetMotionSensResult(int value);
	//移动侦测灵敏度设置ret回调
	void vRetSetMotionSensResult(int iResult);
    //录像质量
    void vRetVideoQuality(String contactId, byte[] data);
	//Index服务器设备信息
	void vRetGetIndexFriendStatus(int count, String[] contactIds, int[] IdProtery,
								  int[] status, int[] DevTypes, int[] SubType, int[] DefenceState,byte bRequestResult);
	void vRetGetIRLEDResult(int value);
	void vRetSetIRLEDResult(String contactId,int iResult);
    //获取AP设备是否有设置过wifi
    void vRetGetApIsWifiSetting(String contactId,byte[] data);
    //配置AP设备的默认连接WIFI
    void vRetSetApStaWifiInfo(String contactId,byte[] data);
}
