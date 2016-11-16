package net.iquesoft.iquephoto.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.model.Font;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FontsAdapter extends RecyclerView.Adapter<FontsAdapter.ViewHolder> {

    private int mSelectedFontPosition = 0;

    private Context mContext;

    private String mText;

    private List<Font> mFontsList;

    private OnFontClickListener mOnFontClickListener;

    public interface OnFontClickListener {
        void onClick(Font font);
    }

    public void setOnFontClickListener(OnFontClickListener onFontClickListener) {
        mOnFontClickListener = onFontClickListener;
    }

    public FontsAdapter(List<Font> fonts) {
        mFontsList = fonts;
    }

    @Override
    public FontsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_font, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FontsAdapter.ViewHolder holder, int position) {
        final Font font = mFontsList.get(position);

        holder.fontFamilyTextView.setText(font.getTitle());

        holder.fontPreviewTextView.setTypeface(getTypeface(font.getTypeface()));

        holder.itemView.setOnClickListener(view -> {
            notifyItemChanged(mSelectedFontPosition);
            mSelectedFontPosition = position;
            notifyItemChanged(position);
        });

        if (mSelectedFontPosition == position) {
            mOnFontClickListener.onClick(font);
            holder.itemView.setAlpha(1f);
            holder.fontSelectedImageView.setVisibility(View.GONE);
            //holder.fontSelectedImageView.setVisibility(View.VISIBLE);
        } else {
            holder.itemView.setAlpha(0.5f);
            holder.fontSelectedImageView.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return mFontsList.size();
    }

    private Typeface getTypeface(String path) {
        return Typeface.createFromAsset(mContext.getAssets(), path);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.fontPreviewTextView)
        TextView fontPreviewTextView;

        @BindView(R.id.fontFamilyTextView)
        TextView fontFamilyTextView;

        @BindView(R.id.fontSelectedImageView)
        ImageView fontSelectedImageView;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
