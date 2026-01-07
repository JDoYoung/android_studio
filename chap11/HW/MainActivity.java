package com.example.hw_11;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    private Stack<ImageView> pillar1 = new Stack<>();
    private Stack<ImageView> pillar2 = new Stack<>();
    private Stack<ImageView> pillar3 = new Stack<>();

    private ImageView selectedDisk = null;
    private int selectedPillarNumber = -1;

    private ImageView square1, square2, square3;
    private ImageView pillarView1, pillarView2, pillarView3;

    private static final int SIZE_SQUARE1 = 3;
    private static final int SIZE_SQUARE2 = 2;
    private static final int SIZE_SQUARE3 = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        start();
        setClickListeners();
    }

    private void initViews() {
        square1 = findViewById(R.id.square1);
        square2 = findViewById(R.id.square2);
        square3 = findViewById(R.id.square3);

        pillarView1 = findViewById(R.id.pillar1);
        pillarView2 = findViewById(R.id.pillar2);
        pillarView3 = findViewById(R.id.pillar3);
    }

    private void start() {
        pillar1.push(square1);
        pillar1.push(square2);
        pillar1.push(square3);
    }

    private void setClickListeners() {
        square1.setOnClickListener(v -> onDiskClicked(square1));
        square2.setOnClickListener(v -> onDiskClicked(square2));
        square3.setOnClickListener(v -> onDiskClicked(square3));

        pillarView1.setOnClickListener(v -> onPillarClicked(1));
        pillarView2.setOnClickListener(v -> onPillarClicked(2));
        pillarView3.setOnClickListener(v -> onPillarClicked(3));
    }

    private void onDiskClicked(ImageView clickedDisk) {
        if (!pillar1.isEmpty() && pillar1.peek() == clickedDisk) {
            selectedDisk = clickedDisk;
            selectedPillarNumber = 1;
        } else if (!pillar2.isEmpty() && pillar2.peek() == clickedDisk) {
            selectedDisk = clickedDisk;
            selectedPillarNumber = 2;
        } else if (!pillar3.isEmpty() && pillar3.peek() == clickedDisk) {
            selectedDisk = clickedDisk;
            selectedPillarNumber = 3;
        }
    }

    private void onPillarClicked(int pillarNumber) {
        if (selectedDisk == null) return;
        if (selectedPillarNumber == pillarNumber) return;

        if (isMove(selectedPillarNumber, pillarNumber)) {
            move(selectedPillarNumber, pillarNumber);
            selectedDisk = null;
            selectedPillarNumber = -1;
            updateDiskPositions();
            end();
        }
    }

    private boolean isMove(int fromPillar, int toPillar) {
        Stack<ImageView> from = getPillarStack(fromPillar);
        Stack<ImageView> to = getPillarStack(toPillar);

        if (to.isEmpty()) return true;

        ImageView fromDisk = from.peek();
        ImageView toDisk = to.peek();

        return getDiskSize(fromDisk) < getDiskSize(toDisk);
    }

    private void move(int fromPillar, int toPillar) {
        Stack<ImageView> from = getPillarStack(fromPillar);
        Stack<ImageView> to = getPillarStack(toPillar);

        ImageView disk = from.pop();
        to.push(disk);
    }

    private Stack<ImageView> getPillarStack(int pillarNumber) {
        switch (pillarNumber) {
            case 1: return pillar1;
            case 2: return pillar2;
            case 3: return pillar3;
            default: return null;
        }
    }

    private int getDiskSize(ImageView disk) {
        if (disk == square1) return SIZE_SQUARE1;
        if (disk == square2) return SIZE_SQUARE2;
        if (disk == square3) return SIZE_SQUARE3;
        return 0;
    }

    private void updateDiskPositions() {
        updatePillarDisks(pillar1, pillarView1);
        updatePillarDisks(pillar2, pillarView2);
        updatePillarDisks(pillar3, pillarView3);
    }

    // 화면 마다 원판 위치가 달라서 그거 보정해주는 함수. 폰과, 안드로이드 스튜디오에서 가상 화면과 원판 위치가 다름
    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    private void updatePillarDisks(Stack<ImageView> pillar, ImageView pillarView) {
        ImageView[] disks = pillar.toArray(new ImageView[0]);

        for (int i = 0; i < disks.length; i++) {
            ImageView disk = disks[i];

            int diskWidth, diskHeight;
            if (disk == square1) {
                diskWidth = dpToPx(100);
                diskHeight = dpToPx(20);
            } else if (disk == square2) {
                diskWidth = dpToPx(100);
                diskHeight = dpToPx(20);
            } else {
                diskWidth = dpToPx(50);
                diskHeight = dpToPx(10);
            }

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    diskWidth,
                    diskHeight
            );

            params.addRule(RelativeLayout.ALIGN_LEFT, pillarView.getId());
            params.addRule(RelativeLayout.ALIGN_RIGHT, pillarView.getId());
            params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

            int leftMargin, rightMargin;

            if (disk == square1) {
                leftMargin = dpToPx(10);
                rightMargin = dpToPx(10);
            } else if (disk == square2) {
                leftMargin = dpToPx(20);
                rightMargin = dpToPx(20);
            } else {
                leftMargin = dpToPx(30);
                rightMargin = dpToPx(30);
            }

            params.leftMargin = leftMargin;
            params.rightMargin = rightMargin;

            // 원판 초기 위치가 302니까 302, 그리고 원판간 간격 14
            params.bottomMargin = dpToPx(302) + (i * dpToPx(14));

            disk.setLayoutParams(params);
        }
    }

    private void end() {
        if (pillar3.size() == 3) {
            Toast.makeText(this, "게임 종료", Toast.LENGTH_SHORT).show();
        }
    }
}
