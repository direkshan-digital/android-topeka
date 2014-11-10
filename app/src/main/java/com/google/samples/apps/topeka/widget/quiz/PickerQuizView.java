/*
 * Copyright 2014 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.samples.apps.topeka.widget.quiz;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.samples.apps.topeka.model.Category;
import com.google.samples.apps.topeka.model.quiz.PickerQuiz;

public class PickerQuizView extends AbsQuizView<PickerQuiz>
        implements SeekBar.OnSeekBarChangeListener {

    private TextView mCurrentSelection;
    private SeekBar mSeekBar;
    private int mStep;
    private int mMin;

    public PickerQuizView(Context context, Category category, PickerQuiz quiz) {
        super(context, category, quiz);
    }

    @Override
    protected View getQuizContentView() {
        initStep();
        mMin = getQuiz().getMin();
        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        initCurrentSelection();
        layout.addView(mCurrentSelection,
                new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0, 2));
        initSeekBar();
        layout.addView(mSeekBar, new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0, 1));
        setCurrentSelectionText(mMin);
        return layout;
    }

    private void initCurrentSelection() {
        mCurrentSelection = new TextView(getContext());
        mCurrentSelection
                .setTextAppearance(getContext(), android.R.style.TextAppearance_Material_Title);
    }

    private void initSeekBar() {
        int viewMax = getSeekBarMax();
        mSeekBar = new SeekBar(getContext());
        setMinHeight(mSeekBar);
        mSeekBar.setMax(viewMax);
        mSeekBar.setOnSeekBarChangeListener(this);
    }

    private void initStep() {
        int tmpStep = getQuiz().getStep();
        //make sure steps are never 0
        if (0 == tmpStep) {
            mStep = 1;
        } else {
            mStep = tmpStep;
        }
    }

    private int getSeekBarMax() {
        final int absMin = Math.abs(getQuiz().getMin());
        final int absMax = Math.abs(getQuiz().getMax());
        final int realMin = Math.min(absMin, absMax);
        final int realMax = Math.max(absMin, absMax);
        return realMax - realMin;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        setCurrentSelectionText(mMin + progress);
        if (!isAnswered()) {
            setAnswered(true);
        }
    }

    private void setCurrentSelectionText(int progress) {
        progress = progress / mStep * mStep;
        mCurrentSelection.setText(String.valueOf(progress));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        /* no-op */
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        /* no-op */
    }
}