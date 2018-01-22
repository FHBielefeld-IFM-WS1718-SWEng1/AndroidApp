package partyplaner.data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

/**
 * Created by André on 05.01.2018.
 */

public class PaPlaImage {

    private Bitmap bitmap;

    public PaPlaImage(Bitmap bitmap) {
        if (bitmap != null) {
            this.bitmap = bitmap;
        }
    }

    public PaPlaImage(String base64) {
        if (base64 != null) {
            byte[] decoded = Base64.decode(base64, Base64.DEFAULT);
            this.bitmap = convertFromBytearrayToBitmap(decoded);
        }
    }

    public String convertToBase64() {
        if (bitmap != null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream .toByteArray();

            return Base64.encodeToString(byteArray, Base64.DEFAULT);
            //return new String(Base64.encode(convertFromBitmapToBytearray(bitmap), Base64.DEFAULT));
        }
        return null;
    }

    public Bitmap convertToBitmap() {
        if (bitmap != null) {
            return bitmap;
        }
        return null;
    }

    private byte[] convertFromBitmapToBytearray(Bitmap bitmap) {
        if (bitmap != null) {
            int size = bitmap.getRowBytes() * bitmap.getHeight();
            ByteBuffer byteBuffer = ByteBuffer.allocate(size);
            bitmap.copyPixelsToBuffer(byteBuffer);
            return byteBuffer.array();
        }
        return null;
    }

    private Bitmap convertFromBytearrayToBitmap(byte[] byteArray) {
        if (bitmap != null) {
            return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        }
        return null;
    }
}
