    public class StrictModeUtil{
	
	/**
     * �ϸ�ģʽ�����Υ�������
     */
    private void setStrictMode() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectAll() //disk,network,resourceMismatches��
                .penaltyLog()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectAll() //acitivity,broadcast,sql,io��
                .penaltyLog()
                .build());
    }
	}