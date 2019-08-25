package com.fareway.activity;

import android.app.Activity;
import android.graphics.Bitmap;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.fareway.R;
import com.fareway.utility.AppUtilFw;
import com.fareway.utility.Constant;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.util.EnumMap;
import java.util.Map;

public class ShopperId extends AppCompatActivity {

    AppUtilFw appUtil;
    private Activity activity;
    /** inisialisasi objek di layout */
   // EditText textInput;
   // Button tombolGenerate;
    ImageView FwBarcode;
    TextView Shopper;
    Bitmap bitmap = null;

    private static final int WHITE = 0xFFFFFFFF;
    private static final int BLACK = 0xFF000000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopper_id);
        activity=ShopperId.this;
        appUtil=new AppUtilFw(activity);
        getSupportActionBar().setTitle("Shopper ID");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        /** hubungkan objek di layout ke variabelnya */
        //textInput = (EditText) findViewById(R.id.input_isi);

       // tombolGenerate = (Button) findViewById(R.id.btn_generate);
        Log.i("shopper","start1");
       // FwBarcode = (ImageView) findViewById(R.id.fw_barcode);
        Shopper = (TextView) findViewById(R.id.txt_shopper);
        Log.i("shopper","start2");

        /** buat gambar barcode nya ketika tombol di tekan*/
       /* tombolGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    bitmap = encodeAsBitmap(textInput.getText().toString(), BarcodeFormat.CODE_128, 600, 300);

                    gambarBarcode.setImageBitmap(bitmap);
                    textnya.setText(textInput.getText().toString());
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }
        });*/
        try {
            //bitmap = encodeAsBitmap(appUtil.getPrefrence("ShopperID"), BarcodeFormat.CODE_128, 600, 300);
            bitmap = encodeAsBitmap(Constant.PRISHOPPERID+appUtil.getPrefrence("ShopperID"), BarcodeFormat.CODE_128, 800, 300);

            //FwBarcode.setImageBitmap(bitmap);
            Shopper.setText(appUtil.getPrefrence("ShopperID").toString());
            Log.i("shopper","id");
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    Bitmap encodeAsBitmap(String contents, BarcodeFormat format, int img_width, int img_height) throws WriterException {
        String contentsToEncode = contents;
        if (contentsToEncode == null) {
            return null;
        }
        Map<EncodeHintType, Object> hints = null;
        String encoding = guessAppropriateEncoding(contentsToEncode);
        if (encoding != null) {
            hints = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
            hints.put(EncodeHintType.CHARACTER_SET, encoding);
        }
        MultiFormatWriter writer = new MultiFormatWriter();
        BitMatrix result;
        try {
            result = writer.encode(contentsToEncode, format, img_width, img_height, hints);
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }
        int width = result.getWidth();
        int height = result.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    private static String guessAppropriateEncoding(CharSequence contents) {
        // Very crude at the moment
        for (int i = 0; i < contents.length(); i++) {
            if (contents.charAt(i) > 0xFF) {
                Log.i("shopper","utf");
                return "UTF-8";
            }
        }
        return null;
    }
}
