package me.ibrahimsn.viewmodel.ui.widgets;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by Daniyal on 4/6/2017.
 */

public class VerticalListMarginDecorator extends RecyclerView.ItemDecoration {

    private int space;
    private boolean isExploreMore = false;

    /**
     * common space- padding between two list item - pass value using the following function
     * (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics();
     * 10 -  padding you want to provide
     */

    public VerticalListMarginDecorator(Context mContext, int commonSpace) {
        this.space = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, commonSpace, mContext.getResources().getDisplayMetrics());
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = space;
        }
        outRect.left = space;
        outRect.bottom = space;
        outRect.right = space;

        if (isExploreMore) {
            if (parent.getChildAdapterPosition(view) == parent.getAdapter().getItemCount() - 1) {
                outRect.left = 0;
                outRect.right = 0;
            }
        }

    }

    public void setExploreMore(boolean isExploreMore) {
        this.isExploreMore = isExploreMore;
    }

}
