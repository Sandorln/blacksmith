package blacksmith.com.sandorln.blacksmith.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import java.util.Random;

import blacksmith.com.sandorln.blacksmith.Dto.upDto;

/**
 * Created by SanDorln on 2016-08-29.
 */
public class UpStateBarView extends View {

    upDto upinfo;
    Random random;

    public UpStateBarView(Context context, upDto upinfo) {
        super(context);
        this.upinfo = upinfo;
        random = new Random();
        random.setSeed(random.nextInt());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paintLine = new Paint();

        int width = canvas.getWidth() / 30;

        upinfo.setMinGreatX(random.nextInt(width * 18) + width * 5);
        upinfo.setMaxGreatX((int) (upinfo.getMinGreatX() + width * 1.5));
        upinfo.setMinNormalX(upinfo.getMinGreatX() - width * 3);
        upinfo.setMaxNormalX(upinfo.getMaxGreatX() + width * 3);

        paintLine.setARGB(255, 255, 255, 255);
        paintLine.setStrokeWidth(5);
        canvas.drawLine(width, canvas.getHeight() / 2, width * 30, canvas.getHeight() / 2, paintLine);

        paintLine.setARGB(255, 0, 0, 255);
        paintLine.setStrokeWidth(15);
        canvas.drawLine(upinfo.getMinNormalX(), canvas.getHeight() / 2, upinfo.getMaxNormalX(), canvas.getHeight() / 2, paintLine);

        paintLine.setARGB(255, 0, 255, 0);
        paintLine.setStrokeWidth(20);
        canvas.drawLine(upinfo.getMinGreatX(), canvas.getHeight() / 2, upinfo.getMaxGreatX(), canvas.getHeight() / 2, paintLine);
    }
}