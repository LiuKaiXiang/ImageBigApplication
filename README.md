# ImageBigApplication

#功能
###跳转相册
###跳转相机
###做预览操作
###做可删除的预览操作

------------
# 使用步骤

1. 将*photolibrary*复制进你的Project中，然后在**settings.gradle**中添加`':photolibrary'`使项目识别到这个库，然后在**Project Structure**中将该库关联到**app**中;
2. 使用前在你的*activity*的*onCreat*方法中添加`ToolsUtil.setContext(this)`(或者使你的*activity*集成**BasePhotoActivity**)，该操作只是为了获取*Context*来初始化一些工具，这样你才可以随意运用内部控件;
3. 使用时你只需要调用**PhotoHelper**中的`sendPhotoDelete（）`、`sendPhotoLook（）`、`sendPhotoPreView（）`、`sendPhoto（）`、`sendCamera（）`这五个方法：
	
	```java
	 *sendPhotoDelete()：做可删除的预览操作
		返回值的获取
		@Override
    	protected void onActivityResult(int requestCode, int resultCode, Intent data){
			if (resultCode == RESULT_OK && data != null) {
				if (requestCode == PhotoUtil.PhotoCode.PREVIEW.getCode()) {
	                getImageList(data);
	            }
			}
		}
	 *sendPhotoLook():做预览操作
		无返回值
	 *sendPhotoPreView():做可选择的预览操作，相册中的预览就是使用的该方法
		返回值的获取
		@Override
    	protected void onActivityResult(int requestCode, int resultCode, Intent data){
			if (resultCode == RESULT_OK && data != null) {
				if (requestCode == PhotoUtil.PhotoCode.PREVIEW.getCode()) {
	                getImageList(data);
	            }
			}
		}
	 *sendPhoto():跳转相册
		返回值的获取
		@Override
    	protected void onActivityResult(int requestCode, int resultCode, Intent data){
			if (resultCode == RESULT_OK && data != null) {
				if (requestCode == PhotoUtil.PhotoCode.PHOTOALBUM.getCode()) {
	                getImageList(data);
	            }
			}
		}
	
	 *sendCamera():跳转相机
		返回值的获取
		@Override
    	protected void onActivityResult(int requestCode, int resultCode, Intent data){
			if (resultCode == RESULT_OK && data != null) {
				if (requestCode == PhotoUtil.PhotoCode.CAMERA.getCode()) {
	            PhotoItem item = new PhotoItem();
	            item.setImgPath(PhotoAlbum.getPath(this, data.getData()));
	            item.setThumbnailPath(PhotoAlbum.getPath(this, data.getData()));
	        	}
			}
		}
	
	private void getImageList(Intent data) {
	    ArrayList<PhotoItem> listExtra = data.getParcelableArrayListExtra(PhotoUtil.IMGS);
	}
	```

	
