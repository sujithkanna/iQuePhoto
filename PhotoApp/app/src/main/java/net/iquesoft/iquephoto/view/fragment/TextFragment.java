package net.iquesoft.iquephoto.view.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import net.iquesoft.iquephoto.DataHolder;
import net.iquesoft.iquephoto.core.EditorCommand;
import net.iquesoft.iquephoto.core.ImageEditorView;
import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.common.BaseFragment;
import net.iquesoft.iquephoto.di.components.IEditorActivityComponent;
import net.iquesoft.iquephoto.view.activity.interfaces.IEditorActivityView;
import net.iquesoft.iquephoto.view.dialog.FontPickerDialog;
import net.iquesoft.iquephoto.view.dialog.RGBColorPickerDialog;
import net.iquesoft.iquephoto.presenter.fragment.TextFragmentPresenterImpl;
import net.iquesoft.iquephoto.view.fragment.interfaces.ITextFragmentView;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static net.iquesoft.iquephoto.core.EditorCommand.TEXT;

public class TextFragment extends BaseFragment implements ITextFragmentView {

    private Context mContext;

    private int mColor = Color.BLACK;

    private String mText;
    private Typeface mTypeface;

    private Unbinder mUnbinder;

    private ImageEditorView mImageEditorView;

    private FontPickerDialog fontPickerDialog;
    private RGBColorPickerDialog RGBColorPickerDialog;

    @Inject
    IEditorActivityView editorActivityView;

    @Inject
    TextFragmentPresenterImpl presenter;

    @BindView(R.id.textOpacityValue)
    TextView opacityValueTextView;

    @BindView(R.id.textOpacitySeekBar)
    DiscreteSeekBar seekBar;

    @BindView(R.id.textSettingsLayout)
    LinearLayout textSettingsLayout;

    @BindView(R.id.textField)
    EditText editText;

    public static TextFragment newInstance() {
        return new TextFragment();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.getComponent(IEditorActivityComponent.class).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_text, container, false);

        mUnbinder = ButterKnife.bind(this, v);

        mContext = v.getContext();

        mImageEditorView = DataHolder.getInstance().getEditorView();

        fontPickerDialog = new FontPickerDialog(v.getContext());
        RGBColorPickerDialog = new RGBColorPickerDialog(v.getContext());

        opacityValueTextView.setText(String.valueOf(seekBar.getProgress()));

        seekBar.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                opacityValueTextView.setText(String.valueOf(value));
            }

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {

            }
        });

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.init(this);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @OnClick(R.id.addTextButton)
    void onClickAddText() {
        mText = editText.getText().toString();
        if (!mText.isEmpty()) {
            mColor = RGBColorPickerDialog.getColor();
            mTypeface = fontPickerDialog.getTypeface();
            mImageEditorView.addText(mText, mTypeface, mColor, seekBar.getProgress());
        } else {
            Toast.makeText(mContext, getResources().getString(R.string.text_is_empty), Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.textColorButton)
    void onClickTextColorButton() {
        RGBColorPickerDialog.show();
    }

    @OnClick(R.id.textButton)
    void onClickTextButton() {
        fontPickerDialog.show();
    }

    @OnClick(R.id.textBackButton)
    void onClickBack() {
        editorActivityView.navigateBack(true);
    }

    @OnClick(R.id.textApplyButton)
    void onClickApply() {
        editorActivityView.getImageEditorView().apply(TEXT);
    }

    public int getColor() {
        return mColor;
    }

    public void setColor(int color) {
        mColor = color;
    }

    public void setTypeface(Typeface typeface) {
        mTypeface = typeface;
    }
}