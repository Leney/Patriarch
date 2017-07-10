package bean;

/**
 * �ƴ�Ѷ��Banner���������Ϣ Created by llj on 2017/3/31.
 */
public class AdInfo {
	/**
	 * �������--->��ת����
	 */
	public static final String AD_TYPE_REDIRECT = "redirect";
	/**
	 * �������--->��������
	 */
	public static final String AD_TYPE_DOWNLOAD = "download";
	/**
	 * �������--->Ʒ������
	 */
	public static final String AD_TYPE_BRAND = "brand";
	/**
	 * ��Ϊ��ת����ʱ==>>��ʶ��ת��ַ,��Ϊ��������ʱ==>>����apk�ĵ�ַ
	 */
	private String landingUrl;
	/**
	 * �����Դ��� ���ı�˵��
	 */
	private String adSourceMark;
	/**
	 * �����ַ����Ҫ�ϱ��û��ĵ����Ϊ
	 */
	private String[] clickUrls;
	/**
	 * ͼƬ��棬ͼƬ��ַ
	 */
	private String imageUrl;

	/**
	 * ͼ�Ĺ�棬icon��ַ
	 */
	private String icon;
	/**
	 * ͼ�Ĺ�棬�ұ�icon��ַ
	 */
	private String rightIconUrl;
	/**
	 * ͼ�Ĺ�棬����
	 */
	private String title;
	/**
	 * ͼ�Ĺ�棬����
	 */
	private String subTile;
	/**
	 * ͼ�Ĺ�棬�������
	 */
	private String clickText;
	/**
	 * ������� redirect=��ת���ͣ�download=�������ͣ�brand=Ʒ������
	 */
	private String adType;
	/**
	 * �ع������飬���������,�����ϱ�
	 */
	private String[] imprUrls;
	/**
	 * ����������棬������Ӧ�õİ���
	 */
	private String packageName;
	/**
	 * �����������,��װ��ʼ�ϱ����
	 */
	private String[] instInstallStartUrls;
	/**
	 * �����������,��װ�ɹ��ϱ����
	 */
	private String[] instInstallSuccUrls;
	/**
	 * �����������,���ؿ�ʼ�ϱ����
	 */
	private String[] instDownloadStartUrls;
	/**
	 * �����������,���سɹ��ϱ����
	 */
	private String[] instDownloadSuccUrls;
	/**
	 * deepLink����
	 */
	private String deepLink;
	/**
	 * �û��Ƿ����˴˹��
	 */
	private boolean isClick;

	public String getLandingUrl() {
		return landingUrl;
	}

	public void setLandingUrl(String landingUrl) {
		this.landingUrl = landingUrl;
	}

	public String getAdSourceMark() {
		return adSourceMark;
	}

	public void setAdSourceMark(String adSourceMark) {
		this.adSourceMark = adSourceMark;
	}

	public String[] getClickUrls() {
		return clickUrls;
	}

	public void setClickUrls(String[] clickUrls) {
		this.clickUrls = clickUrls;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getRightIconUrl() {
		return rightIconUrl;
	}

	public void setRightIconUrl(String rightIconUrl) {
		this.rightIconUrl = rightIconUrl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubTile() {
		return subTile;
	}

	public void setSubTile(String subTile) {
		this.subTile = subTile;
	}

	public String getClickText() {
		return clickText;
	}

	public void setClickText(String clickText) {
		this.clickText = clickText;
	}

	public String getAdType() {
		return adType;
	}

	public void setAdType(String adType) {
		this.adType = adType;
	}

	public String[] getImprUrls() {
		return imprUrls;
	}

	public void setImprUrls(String[] imprUrls) {
		this.imprUrls = imprUrls;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String[] getInstInstallStartUrls() {
		return instInstallStartUrls;
	}

	public void setInstInstallStartUrls(String[] instInstallStartUrls) {
		this.instInstallStartUrls = instInstallStartUrls;
	}

	public String[] getInstInstallSuccUrls() {
		return instInstallSuccUrls;
	}

	public void setInstInstallSuccUrls(String[] instInstallSuccUrls) {
		this.instInstallSuccUrls = instInstallSuccUrls;
	}

	public String[] getInstDownloadStartUrls() {
		return instDownloadStartUrls;
	}

	public void setInstDownloadStartUrls(String[] instDownloadStartUrls) {
		this.instDownloadStartUrls = instDownloadStartUrls;
	}

	public String[] getInstDownloadSuccUrls() {
		return instDownloadSuccUrls;
	}

	public void setInstDownloadSuccUrls(String[] instDownloadSuccUrls) {
		this.instDownloadSuccUrls = instDownloadSuccUrls;
	}

	public String getDeepLink() {
		return deepLink;
	}

	public void setDeepLink(String deepLink) {
		this.deepLink = deepLink;
	}

	public boolean isClick() {
		return isClick;
	}

	public void setClick(boolean click) {
		isClick = click;
	}

	// public DownloadInfo toDownloadInfo() {
	// DownloadInfo downloadInfo = new DownloadInfo();
	// if (!(TextUtils.isEmpty(icon))) {
	// downloadInfo.setIconUrl(this.icon);
	// } else if (!TextUtils.isEmpty(this.imageUrl)) {
	// downloadInfo.setIconUrl(this.imageUrl);
	// } else if (!TextUtils.isEmpty(this.rightIconUrl)) {
	// downloadInfo.setIconUrl(this.rightIconUrl);
	// }
	// downloadInfo.setSoftId("-2");
	// downloadInfo.setPackageName(this.packageName);
	// downloadInfo.setSize(-1);
	// downloadInfo.setName("��װ��");
	// downloadInfo.setUrl(this.landingUrl);
	// return downloadInfo;
	// }
}