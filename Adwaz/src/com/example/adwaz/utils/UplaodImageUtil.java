package com.example.adwaz.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.BitmapFactory.Options;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore.Images;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

public class UplaodImageUtil {

	/**
	 * Resize, crop, rotate and Inserts the picture on the layout.
	 * 
	 * @param mImageView
	 *            to insert the bitmap.
	 * @param imageURI
	 *            from wich to obtain the bitmap.
	 * @return
	 * 
	 */
	public static Bitmap getBitmap(String imageURI, int reqWidth,
			int reqHeight, Context context) {
		// Get the original bitmap dimensions
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(imageURI, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth,
				reqHeight);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		options.inPurgeable = true; // for effeciency
		options.inInputShareable = true;

		Bitmap bitmap = BitmapFactory.decodeFile(imageURI, options);
		bitmap = Bitmap.createScaledBitmap(bitmap, reqWidth,
				reqHeight, true);
		Log.v("BITMAP HIEGHT AND WIDTH", "" + bitmap.getHeight() + "  "
				+ bitmap.getWidth());
		// need rotation?
		float rotation = rotationForImage(context,
				Uri.fromFile(new File(imageURI)));

		if (rotation != 0) {
			// rotate
			Matrix matrix = new Matrix();
			int width = bitmap.getWidth();
			int height = bitmap.getHeight();
			int newWidth = reqWidth;
			int newHeight = reqHeight;
			float scaleWidth = ((float) newWidth) / width;

			float scaleHeight = ((float) newHeight) / height;
		//	matrix.postScale(scaleWidth, scaleHeight);
			matrix.preRotate(rotation);

			return Bitmap.createBitmap(bitmap, 0, 0,newWidth,
					newHeight, matrix, true);

		} else {
			// use the original
			return BitmapFactory.decodeFile(imageURI, options);
		}

	}

	public static float rotationForImage(Context context, Uri uri) {
		try {
			if (uri.getScheme().equals("content")) {
				String[] projection = { Images.ImageColumns.ORIENTATION };
				Cursor c = context.getContentResolver().query(uri, projection,
						null, null, null);
				if (c.moveToFirst()) {
					return c.getInt(0);
				}
			} else if (uri.getScheme().equals("file")) {
				ExifInterface exif = new ExifInterface(uri.getPath());
				int rotation = (int) exifOrientationToDegrees(exif
						.getAttributeInt(ExifInterface.TAG_ORIENTATION,
								ExifInterface.ORIENTATION_NORMAL));
				return rotation;
			}
			return 0;

		} catch (IOException e) {
			Log.e("Error", "Error checking exif", e);
			return 0;
		}
	}

	private static float exifOrientationToDegrees(int exifOrientation) {
		if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
			return 90;
		} else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
			return 180;
		} else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
			return 270;
		}
		return 0;
	}

	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			// Calculate ratios of height and width to requested height and
			// width
			final int heightRatio = Math.round((float) height
					/ (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);

			// Choose the smallest ratio as inSampleSize value, this will
			// guarantee
			// a final image with both dimensions larger than or equal to the
			// requested height and width.
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}

		return inSampleSize;
	}

	/**
	 * This method is used for getting Base64 string converted from image
	 * ByteArray
	 * 
	 * @param byteArray
	 * @return
	 */
	public static String getBase64String(byte[] byteArray) {
		return Base64.encodeToString(byteArray, 0, byteArray.length,
				Base64.DEFAULT);
	}

	/**
	 * This method is used to get byte array from bitmap
	 * 
	 * @param bitmap
	 * @return
	 */
	public static byte[] getBitmapByteArray(Bitmap bitmap) {
		BitmapFactory.Options options = new Options();
		options.inPurgeable = true;
		options.inSampleSize = 5;
		byte[] bytes;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
		bytes = bos.toByteArray();

		return bytes;
	}

	public static byte[] getBitmapBytesFromSdcard(String imageUrl) {
		ByteArrayOutputStream bos = null;
		InputStream is;
		try {
			is = new FileInputStream(new File(imageUrl));
			bos = new ByteArrayOutputStream();
			byte[] b = new byte[1024];
			int bytesRead;
			while ((bytesRead = is.read(b)) != -1) {
				bos.write(b, 0, bytesRead);
			}
			is.close();
			b = null;

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return bos.toByteArray();

	}

}
