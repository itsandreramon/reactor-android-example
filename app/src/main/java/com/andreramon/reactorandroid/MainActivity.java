package com.andreramon.reactorandroid;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import java.time.Duration;
import java.util.concurrent.Executor;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class MainActivity extends AppCompatActivity {

  private static final String TAG = "MainActivity";

  private Executor mainExecutor;
  private TextView counterTextView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    bindResources();

    // get Android main-thread
    mainExecutor = ContextCompat.getMainExecutor(this);

    // asynchronously retrieve results
    Flux.just("1", "2", "3")
        .subscribeOn(Schedulers.parallel())
        .delayElements(Duration.ofSeconds(1))
        .publishOn(Schedulers.fromExecutor(mainExecutor))
        .subscribe(s -> {
          counterTextView.setText(s);
        });
  }

  private void bindResources() {
    counterTextView = findViewById(R.id.text_view_counter);
  }
}