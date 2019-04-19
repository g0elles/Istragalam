package Utils;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;


  public class SqueareImageView extends AppCompatImageView {

        public SqueareImageView(Context context) {
            super(context);
        }

        public SqueareImageView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public SqueareImageView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, widthMeasureSpec);
        }
    }

