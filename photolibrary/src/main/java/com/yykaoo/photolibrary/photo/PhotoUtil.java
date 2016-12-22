package com.yykaoo.photolibrary.photo;

public class PhotoUtil {

    public static final String camera = "camera";

    public static final String POSITION = "position";
    public static final String IMGS = "imgs";

    public static final String MAXPIC = "maxpic";
    public static final String SEND_PHOTO = "send_photo";

    /**
     * 界面传值code
     */
    public static final String CODE = "code";
    private static int Camera = 608;
    private static int PhotoAlbum = 609;
    private static int Preview = 610;

    /**
     * 显示类型
     */
    public static final String TYPE = "type";
    private static int Look = 500;
    private static int Delete = 501;
    private static int TypePreView = 502;
    public static String FromWhere = "fromWhere";

    /**
     * 图片的类型
     */
    public static final String IMGTYPE = "imgType";
    private static int Local = 600;
    private static int Network = 601;

    /**
     * Activity界面传值的code name: PhotoCode <BR>
     * description: please write your description <BR>
     * create date: 2015-6-9
     *
     * @author: HAOWU){GeTao}
     */
    public enum PhotoCode {
        /**
         * 相机
         */
        CAMERA(Camera),
        /**
         * 相册
         */
        PHOTOALBUM(PhotoAlbum),
        /**
         * 预览
         */
        PREVIEW(Preview);

        private int code;

        private PhotoCode(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }

    public enum PhotoType {
        /**
         * 查看
         */
        LOOK(Look),
        /**
         * 删除
         */
        DELETE(Delete),
        /**
         * 提交
         */
        PREVIEW(TypePreView);

        private int value;

        private PhotoType(int intVlue) {
            this.value = intVlue;
        }

        public int getIntVlue() {
            return value;
        }
    }

    public enum PhotoImgType {

        /**
         * 本地
         */
        LOCAL(Local),
        /**
         * 网络
         */
        NETWORK(Network);

        private int value;

        private PhotoImgType(int intVlue) {
            this.value = intVlue;
        }

        public int getIntVlue() {
            return value;
        }
    }

}
